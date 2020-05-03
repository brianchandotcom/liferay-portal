/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter;

import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.delivery.dto.v1_0.Layout;
import com.liferay.headless.delivery.dto.v1_0.Mapping;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageSectionDefinition;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporter.class)
public class ContainerLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return ContainerLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		ContainerLayoutStructureItem containerLayoutStructureItem =
			(ContainerLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageSectionDefinition() {
					{
						backgroundColor = GetterUtil.getString(
							containerLayoutStructureItem.
								getBackgroundColorCssClass(),
							null);
						backgroundImage = _toBackgroundFragmentImage(
							containerLayoutStructureItem.
								getBackgroundImageJSONObject(),
							saveMappingConfiguration);
						layout = new Layout() {
							{
								paddingBottom =
									PaddingConverter.convertToExternalValue(
										containerLayoutStructureItem.
											getPaddingBottom());
								paddingHorizontal =
									PaddingConverter.convertToExternalValue(
										containerLayoutStructureItem.
											getPaddingHorizontal());
								paddingTop =
									PaddingConverter.convertToExternalValue(
										containerLayoutStructureItem.
											getPaddingTop());

								setContainerType(
									() -> {
										String containerType =
											containerLayoutStructureItem.
												getContainerType();

										if (Validator.isNull(containerType)) {
											return null;
										}

										return ContainerType.create(
											StringUtil.upperCaseFirstLetter(
												containerType));
									});
							}
						};
					}
				};
				type = PageElement.Type.SECTION;
			}
		};
	}

	private Function<Object, String> _getImageURLTransformerFunction() {
		return object -> {
			if (object instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)object;

				return jsonObject.getString("url");
			}

			return StringPool.BLANK;
		};
	}

	private boolean _isSaveFragmentMappedValue(
		JSONObject jsonObject, boolean saveMapping) {

		if (saveMapping && jsonObject.has("classNameId") &&
			jsonObject.has("classPK") && jsonObject.has("fieldId")) {

			return true;
		}

		if (saveMapping && jsonObject.has("mappedField")) {
			return true;
		}

		return false;
	}

	private FragmentImage _toBackgroundFragmentImage(
		JSONObject jsonObject, boolean saveMappingConfiguration) {

		if (jsonObject == null) {
			return null;
		}

		String urlValue = jsonObject.getString("url");

		return new FragmentImage() {
			{
				title = _toTitleFragmentInlineValue(jsonObject, urlValue);

				setUrl(
					() -> {
						if (_isSaveFragmentMappedValue(
								jsonObject, saveMappingConfiguration)) {

							return _toFragmentMappedValue(
								_toDefaultMappingValue(
									jsonObject,
									_getImageURLTransformerFunction()),
								jsonObject);
						}

						if (Validator.isNull(urlValue)) {
							return null;
						}

						return new FragmentInlineValue() {
							{
								value = urlValue;
							}
						};
					});
			}
		};
	}

	private FragmentInlineValue _toDefaultMappingValue(
		JSONObject jsonObject, Function<Object, String> transformerFunction) {

		long classNameId = jsonObject.getLong("classNameId");

		if (classNameId == 0) {
			return null;
		}

		String className = null;

		try {
			className = _portal.getClassName(classNameId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get class name for default mapping value",
					exception);
			}
		}

		if (Validator.isNull(className)) {
			return null;
		}

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

		if (infoDisplayContributor == null) {
			return null;
		}

		long classPK = jsonObject.getLong("classPK");

		try {
			InfoDisplayObjectProvider infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(classPK);

			Map<String, Object> fieldValues =
				infoDisplayContributor.getInfoDisplayFieldsValues(
					infoDisplayObjectProvider.getDisplayObject(),
					LocaleUtil.getMostRelevantLocale());

			Object fieldValue = fieldValues.get(
				jsonObject.getString("fieldId"));

			if (transformerFunction != null) {
				fieldValue = transformerFunction.apply(fieldValue);
			}

			String valueString = GetterUtil.getString(fieldValue);

			if (Validator.isNull(valueString)) {
				return null;
			}

			return new FragmentInlineValue() {
				{
					value = valueString;
				}
			};
		}
		catch (Exception exception) {
			_log.error("Unable to get default mapped value", exception);
		}

		return null;
	}

	private FragmentMappedValue _toFragmentMappedValue(
		FragmentInlineValue fragmentInlineValue, JSONObject jsonObject) {

		return new FragmentMappedValue() {
			{
				mapping = new Mapping() {
					{
						defaultValue = fragmentInlineValue;

						setFieldKey(
							() -> {
								String fieldId = jsonObject.getString(
									"fieldId");

								if (Validator.isNotNull(fieldId)) {
									return fieldId;
								}

								return jsonObject.getString("mappedField");
							});
						setItemKey(
							() -> {
								String classNameId = jsonObject.getString(
									"classNameId");

								if (Validator.isNull(classNameId)) {
									return null;
								}

								String classPK = jsonObject.getString(
									"classPK");

								if (Validator.isNull(classPK)) {
									return null;
								}

								return StringBundler.concat(
									classNameId, StringPool.POUND, classPK);
							});
					}
				};
			}
		};
	}

	private FragmentInlineValue _toTitleFragmentInlineValue(
		JSONObject jsonObject, String urlValue) {

		String title = jsonObject.getString("title");

		if (Validator.isNull(title) || title.equals(urlValue)) {
			return null;
		}

		return new FragmentInlineValue() {
			{
				value = title;
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContainerLayoutStructureItemExporter.class);

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Portal _portal;

}