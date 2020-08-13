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

import com.liferay.headless.delivery.dto.v1_0.ClassPKReference;
import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentLink;
import com.liferay.headless.delivery.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.delivery.dto.v1_0.Layout;
import com.liferay.headless.delivery.dto.v1_0.Mapping;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageSectionDefinition;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.page.template.util.AlignConverter;
import com.liferay.layout.page.template.util.BorderRadiusConverter;
import com.liferay.layout.page.template.util.JustifyConverter;
import com.liferay.layout.page.template.util.MarginConverter;
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.page.template.util.ShadowConverter;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
						backgroundFragmentImage = _toBackgroundFragmentImage(
							containerLayoutStructureItem.
								getBackgroundImageJSONObject(),
							saveMappingConfiguration);
						fragmentLink = _toFragmentLink(
							containerLayoutStructureItem.getLinkJSONObject(),
							saveMappingConfiguration);
						layout = _toLayout(containerLayoutStructureItem);

						JSONObject itemConfigJSONObject =
							containerLayoutStructureItem.
								getItemConfigJSONObject();

						styles = _toStylesMap(
							itemConfigJSONObject.getJSONObject("styles"),
							saveMappingConfiguration);
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

			if (object instanceof String) {
				return (String)object;
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

		if (saveMapping && jsonObject.has("collectionFieldId")) {
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

		InfoDisplayContributor<Object> infoDisplayContributor =
			(InfoDisplayContributor<Object>)
				_infoDisplayContributorTracker.getInfoDisplayContributor(
					className);

		if (infoDisplayContributor == null) {
			return null;
		}

		long classPK = jsonObject.getLong("classPK");

		try {
			InfoDisplayObjectProvider<Object> infoDisplayObjectProvider =
				(InfoDisplayObjectProvider<Object>)
					infoDisplayContributor.getInfoDisplayObjectProvider(
						classPK);

			if (infoDisplayObjectProvider == null) {
				return null;
			}

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

	private FragmentLink _toFragmentLink(
		JSONObject jsonObject, boolean saveMapping) {

		if (jsonObject == null) {
			return null;
		}

		if (jsonObject.isNull("href") &&
			!_isSaveFragmentMappedValue(jsonObject, saveMapping)) {

			return null;
		}

		return new FragmentLink() {
			{
				setHref(
					() -> {
						if (_isSaveFragmentMappedValue(
								jsonObject, saveMapping)) {

							return _toFragmentMappedValue(
								_toDefaultMappingValue(jsonObject, null),
								jsonObject);
						}

						return new FragmentInlineValue() {
							{
								value = jsonObject.getString("href");
							}
						};
					});
				setTarget(
					() -> {
						String target = jsonObject.getString("target");

						if (Validator.isNull(target)) {
							return null;
						}

						return Target.create(
							StringUtil.upperCaseFirstLetter(
								target.substring(1)));
					});
			}
		};
	}

	private FragmentMappedValue _toFragmentMappedValue(
		FragmentInlineValue fragmentInlineValue, JSONObject jsonObject) {

		return new FragmentMappedValue() {
			{
				mapping = new Mapping() {
					{
						defaultFragmentInlineValue = fragmentInlineValue;
						itemReference = _toItemReference(jsonObject);

						setFieldKey(
							() -> {
								String collectionFieldId = jsonObject.getString(
									"collectionFieldId");

								if (Validator.isNotNull(collectionFieldId)) {
									return collectionFieldId;
								}

								String fieldId = jsonObject.getString(
									"fieldId");

								if (Validator.isNotNull(fieldId)) {
									return fieldId;
								}

								String mappedField = jsonObject.getString(
									"mappedField");

								if (Validator.isNotNull(mappedField)) {
									return mappedField;
								}

								return null;
							});
					}
				};
			}
		};
	}

	private String _toItemClassName(JSONObject jsonObject) {
		String classNameIdString = jsonObject.getString("classNameId");

		if (Validator.isNull(classNameIdString)) {
			return null;
		}

		long classNameId = 0;

		try {
			classNameId = Long.parseLong(classNameIdString);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Item class name could not be set since class name " +
							"ID %s could not be parsed to a long",
						classNameIdString),
					numberFormatException);
			}

			return null;
		}

		String className = null;

		try {
			className = _portal.getClassName(classNameId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Item class name could not be set since no class name " +
						"could be obtained for class name ID " + classNameId,
					exception);
			}

			return null;
		}

		return className;
	}

	private Long _toitemClassPK(JSONObject jsonObject) {
		String classPKString = jsonObject.getString("classPK");

		if (Validator.isNull(classPKString)) {
			return null;
		}

		Long classPK = null;

		try {
			classPK = Long.parseLong(classPKString);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Item class PK could not be set since class PK %s " +
							"could not be parsed to a long",
						classPKString),
					numberFormatException);
			}

			return null;
		}

		return classPK;
	}

	private Object _toItemReference(JSONObject jsonObject) {
		String collectionFieldId = jsonObject.getString("collectionFieldId");
		String fieldId = jsonObject.getString("fieldId");
		String mappedField = jsonObject.getString("mappedField");

		if (Validator.isNull(collectionFieldId) && Validator.isNull(fieldId) &&
			Validator.isNull(mappedField)) {

			return null;
		}

		if (Validator.isNotNull(collectionFieldId)) {
			return new ContextReference() {
				{
					contextSource = ContextSource.COLLECTION_ITEM;
				}
			};
		}

		if (Validator.isNotNull(mappedField)) {
			return new ContextReference() {
				{
					contextSource = ContextSource.DISPLAY_PAGE_ITEM;
				}
			};
		}

		return new ClassPKReference() {
			{
				className = _toItemClassName(jsonObject);
				classPK = _toitemClassPK(jsonObject);
			}
		};
	}

	private Layout _toLayout(
		ContainerLayoutStructureItem containerLayoutStructureItem) {

		return new Layout() {
			{
				align = Align.create(
					AlignConverter.convertToExternalValue(
						containerLayoutStructureItem.getAlign()));
				borderRadius = BorderRadius.create(
					BorderRadiusConverter.convertToExternalValue(
						containerLayoutStructureItem.getBorderRadius()));
				borderWidth = containerLayoutStructureItem.getBorderWidth();
				justify = Justify.create(
					JustifyConverter.convertToExternalValue(
						containerLayoutStructureItem.getJustify()));
				marginBottom = MarginConverter.convertToExternalValue(
					containerLayoutStructureItem.getMarginBottom());
				marginLeft = MarginConverter.convertToExternalValue(
					containerLayoutStructureItem.getMarginLeft());
				marginRight = MarginConverter.convertToExternalValue(
					containerLayoutStructureItem.getMarginRight());
				marginTop = MarginConverter.convertToExternalValue(
					containerLayoutStructureItem.getMarginTop());
				opacity = containerLayoutStructureItem.getOpacity();
				paddingBottom = PaddingConverter.convertToExternalValue(
					containerLayoutStructureItem.getPaddingBottom());
				paddingLeft = PaddingConverter.convertToExternalValue(
					containerLayoutStructureItem.getPaddingLeft());
				paddingRight = PaddingConverter.convertToExternalValue(
					containerLayoutStructureItem.getPaddingRight());
				paddingTop = PaddingConverter.convertToExternalValue(
					containerLayoutStructureItem.getPaddingTop());
				shadow = Shadow.create(
					ShadowConverter.convertToExternalValue(
						containerLayoutStructureItem.getShadow()));

				setBorderColor(
					() -> {
						String borderColor =
							containerLayoutStructureItem.getBorderColor();

						if (Validator.isNull(borderColor)) {
							return null;
						}

						return borderColor;
					});
				setContentDisplay(
					() -> {
						String contentDisplay =
							containerLayoutStructureItem.getContentDisplay();

						if (Validator.isNull(contentDisplay)) {
							return null;
						}

						return ContentDisplay.create(
							StringUtil.upperCaseFirstLetter(contentDisplay));
					});
				setWidthType(
					() -> {
						String widthType =
							containerLayoutStructureItem.getWidthType();

						if (Validator.isNotNull(widthType)) {
							return WidthType.create(
								StringUtil.upperCaseFirstLetter(widthType));
						}

						String containerType =
							containerLayoutStructureItem.getContainerType();

						if (Validator.isNotNull(containerType)) {
							return WidthType.create(
								StringUtil.upperCaseFirstLetter(containerType));
						}

						return null;
					});
			}
		};
	}

	private Map<String, Object> _toStylesMap(
		JSONObject jsonObject, boolean saveMappingConfiguration) {

		return new HashMap<String, Object>() {
			{
				Set<String> keys = jsonObject.keySet();

				Iterator<String> iterator = keys.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					Object value = jsonObject.get(key);

					if (Objects.equals(key, "backgroundImage")) {
						JSONObject backgroundImageJSONObject =
							(JSONObject)value;

						value = _toBackgroundFragmentImage(
							backgroundImageJSONObject,
							saveMappingConfiguration);
					}

					put(key, value);
				}
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