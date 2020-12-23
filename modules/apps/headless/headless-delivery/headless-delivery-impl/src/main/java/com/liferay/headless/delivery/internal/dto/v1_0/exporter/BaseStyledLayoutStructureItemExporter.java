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

package com.liferay.headless.delivery.internal.dto.v1_0.exporter;

import com.liferay.headless.delivery.dto.v1_0.ClassPKReference;
import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentStyle;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewportStyle;
import com.liferay.headless.delivery.dto.v1_0.Mapping;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseStyledLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	protected FragmentViewport[] getFragmentViewPorts(JSONObject jsonObject) {
		if ((jsonObject == null) || (jsonObject.length() == 0)) {
			return null;
		}

		List<FragmentViewport> fragmentViewports = new ArrayList<>();

		FragmentViewport mobileLandscapeFragmentViewportStyle =
			_toFragmentViewportStyle(jsonObject, ViewportSize.MOBILE_LANDSCAPE);

		if (mobileLandscapeFragmentViewportStyle != null) {
			fragmentViewports.add(mobileLandscapeFragmentViewportStyle);
		}

		FragmentViewport portraitMobileFragmentViewportStyle =
			_toFragmentViewportStyle(jsonObject, ViewportSize.PORTRAIT_MOBILE);

		if (portraitMobileFragmentViewportStyle != null) {
			fragmentViewports.add(portraitMobileFragmentViewportStyle);
		}

		FragmentViewport tabletFragmentViewportStyle = _toFragmentViewportStyle(
			jsonObject, ViewportSize.TABLET);

		if (tabletFragmentViewportStyle != null) {
			fragmentViewports.add(tabletFragmentViewportStyle);
		}

		if (ListUtil.isEmpty(fragmentViewports)) {
			return null;
		}

		return fragmentViewports.toArray(new FragmentViewport[0]);
	}

	protected Function<Object, String> getImageURLTransformerFunction() {
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

	protected boolean isSaveFragmentMappedValue(
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

	protected FragmentImage toBackgroundFragmentImage(
		JSONObject jsonObject, boolean saveMappingConfiguration) {

		if (jsonObject == null) {
			return null;
		}

		String urlValue = jsonObject.getString("url");

		return new FragmentImage() {
			{
				title = toTitleFragmentInlineValue(jsonObject, urlValue);

				setUrl(
					() -> {
						if (isSaveFragmentMappedValue(
								jsonObject, saveMappingConfiguration)) {

							return toFragmentMappedValue(
								toDefaultMappingValue(
									jsonObject,
									getImageURLTransformerFunction()),
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

	protected FragmentInlineValue toDefaultMappingValue(
		JSONObject jsonObject, Function<Object, String> transformerFunction) {

		long classNameId = jsonObject.getLong("classNameId");

		if (classNameId == 0) {
			return null;
		}

		String className = null;

		try {
			className = portal.getClassName(classNameId);
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

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, className);

		if ((infoItemFieldValuesProvider == null) ||
			(infoItemObjectProvider == null)) {

			return null;
		}

		long classPK = jsonObject.getLong("classPK");

		try {
			Object infoItem = infoItemObjectProvider.getInfoItem(
				new ClassPKInfoItemIdentifier(classPK));

			if (infoItem == null) {
				return null;
			}

			InfoFieldValue<Object> infoFieldValue =
				infoItemFieldValuesProvider.getInfoItemFieldValue(
					infoItem, jsonObject.getString("fieldId"));

			if (infoFieldValue == null) {
				return null;
			}

			Object infoFieldValueValue = infoFieldValue.getValue(
				LocaleUtil.getMostRelevantLocale());

			if (transformerFunction != null) {
				infoFieldValueValue = transformerFunction.apply(
					infoFieldValueValue);
			}

			String valueString = GetterUtil.getString(infoFieldValueValue);

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

	protected FragmentMappedValue toFragmentMappedValue(
		FragmentInlineValue fragmentInlineValue, JSONObject jsonObject) {

		return new FragmentMappedValue() {
			{
				mapping = new Mapping() {
					{
						defaultFragmentInlineValue = fragmentInlineValue;
						itemReference = toItemReference(jsonObject);

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

	protected FragmentStyle toFragmentStyle(
		JSONObject jsonObject, boolean saveMappingConfiguration) {

		if ((jsonObject == null) || (jsonObject.length() == 0)) {
			return null;
		}

		return new FragmentStyle() {
			{
				backgroundColor = jsonObject.getString("backgroundColor", null);
				borderColor = jsonObject.getString("borderColor", null);
				borderRadius = jsonObject.getString("borderRadius", null);
				borderWidth = jsonObject.getString("borderWidth", null);
				fontFamily = jsonObject.getString("fontFamily", null);
				fontSize = jsonObject.getString("fontSize", null);
				fontWeight = jsonObject.getString("fontWeight", null);
				height = jsonObject.getString("height", null);
				marginBottom = jsonObject.getString("marginBottom", null);
				marginLeft = jsonObject.getString("marginLeft", null);
				marginRight = jsonObject.getString("marginRight", null);
				marginTop = jsonObject.getString("marginTop", null);
				maxHeight = jsonObject.getString("maxHeight", null);
				maxWidth = jsonObject.getString("maxWidth", null);
				minHeight = jsonObject.getString("minHeight", null);
				minWidth = jsonObject.getString("minWidth", null);
				opacity = jsonObject.getString("opacity", null);
				overflow = jsonObject.getString("overflow", null);
				paddingBottom = jsonObject.getString("paddingBottom", null);
				paddingLeft = jsonObject.getString("paddingLeft", null);
				paddingRight = jsonObject.getString("paddingRight", null);
				paddingTop = jsonObject.getString("paddingTop", null);
				shadow = jsonObject.getString("shadow", null);
				textAlign = jsonObject.getString("textAlign", null);
				textColor = jsonObject.getString("textColor", null);
				width = jsonObject.getString("width", null);

				setBackgroundFragmentImage(
					() -> {
						Object backgroundImage = jsonObject.get(
							"backgroundImage");

						if (backgroundImage == null) {
							return null;
						}

						JSONObject backgroundImageJSONObject =
							(JSONObject)backgroundImage;

						return toBackgroundFragmentImage(
							backgroundImageJSONObject,
							saveMappingConfiguration);
					});
			}
		};
	}

	protected String toItemClassName(JSONObject jsonObject) {
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
			className = portal.getClassName(classNameId);
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

	protected Long toitemClassPK(JSONObject jsonObject) {
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

	protected Object toItemReference(JSONObject jsonObject) {
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
				className = toItemClassName(jsonObject);
				classPK = toitemClassPK(jsonObject);
			}
		};
	}

	protected FragmentInlineValue toTitleFragmentInlineValue(
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

	@Reference
	protected InfoItemServiceTracker infoItemServiceTracker;

	@Reference
	protected Portal portal;

	private FragmentViewport _toFragmentViewportStyle(
		JSONObject jsonObject, ViewportSize viewportSize) {

		JSONObject viewportJSONObject = jsonObject.getJSONObject(
			viewportSize.getViewportSizeId());

		if ((viewportJSONObject == null) ||
			(viewportJSONObject.length() == 0)) {

			return null;
		}

		JSONObject styleJSONObject = viewportJSONObject.getJSONObject("styles");

		if ((styleJSONObject == null) || (styleJSONObject.length() == 0)) {
			return null;
		}

		return new FragmentViewport() {
			{
				setId(viewportSize.getViewportSizeId());
				setFragmentViewportStyle(
					() -> new FragmentViewportStyle() {
						{
							marginBottom = styleJSONObject.getString(
								"marginBottom", null);
							marginLeft = styleJSONObject.getString(
								"marginLeft", null);
							marginRight = styleJSONObject.getString(
								"marginRight", null);
							marginTop = styleJSONObject.getString(
								"marginTop", null);
							paddingBottom = styleJSONObject.getString(
								"paddingBottom", null);
							paddingLeft = styleJSONObject.getString(
								"paddingLeft", null);
							paddingRight = styleJSONObject.getString(
								"paddingRight", null);
							paddingTop = styleJSONObject.getString(
								"paddingTop", null);
						}
					});
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseStyledLayoutStructureItemExporter.class);

}