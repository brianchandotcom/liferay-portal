/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentStyle;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewportStyle;
import com.liferay.headless.delivery.dto.v1_0.Mapping;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.FragmentMappedValueUtil;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
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
import java.util.Objects;
import java.util.function.Function;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseStyledLayoutStructureItemMapper
	implements LayoutStructureItemMapper {

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
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, className,
				ClassPKInfoItemIdentifier.INFO_ITEM_SERVICE_FILTER);

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
				infoItemFieldValuesProvider.getInfoFieldValue(
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
						fieldKey = FragmentMappedValueUtil.getFieldKey(
							jsonObject);
						itemReference = FragmentMappedValueUtil.toItemReference(
							jsonObject);
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

						return _toBackgroundFragmentImage(
							backgroundImageJSONObject,
							saveMappingConfiguration);
					});

				setHidden(
					() -> {
						if (Objects.equals(
								jsonObject.getString("display"), "block")) {

							return false;
						}

						if (Objects.equals(
								jsonObject.getString("display"), "none")) {

							return true;
						}

						return null;
					});
			}
		};
	}

	@Reference
	protected InfoItemServiceRegistry infoItemServiceRegistry;

	@Reference
	protected Portal portal;

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
						if (FragmentMappedValueUtil.isSaveFragmentMappedValue(
								jsonObject, saveMappingConfiguration)) {

							return toFragmentMappedValue(
								toDefaultMappingValue(
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
				id = viewportSize.getViewportSizeId();

				setFragmentViewportStyle(
					() -> new FragmentViewportStyle() {
						{
							backgroundColor = styleJSONObject.getString(
								"backgroundColor", null);
							borderColor = styleJSONObject.getString(
								"borderColor", null);
							borderRadius = styleJSONObject.getString(
								"borderRadius", null);
							borderWidth = styleJSONObject.getString(
								"borderWidth", null);
							fontFamily = styleJSONObject.getString(
								"fontFamily", null);
							fontSize = styleJSONObject.getString(
								"fontSize", null);
							fontWeight = styleJSONObject.getString(
								"fontWeight", null);
							height = styleJSONObject.getString("height", null);
							marginBottom = styleJSONObject.getString(
								"marginBottom", null);
							marginLeft = styleJSONObject.getString(
								"marginLeft", null);
							marginRight = styleJSONObject.getString(
								"marginRight", null);
							marginTop = styleJSONObject.getString(
								"marginTop", null);
							maxHeight = styleJSONObject.getString(
								"maxHeight", null);
							maxWidth = styleJSONObject.getString(
								"maxWidth", null);
							minHeight = styleJSONObject.getString(
								"minHeight", null);
							minWidth = styleJSONObject.getString(
								"minWidth", null);
							opacity = styleJSONObject.getString(
								"opacity", null);
							overflow = styleJSONObject.getString(
								"overflow", null);
							paddingBottom = styleJSONObject.getString(
								"paddingBottom", null);
							paddingLeft = styleJSONObject.getString(
								"paddingLeft", null);
							paddingRight = styleJSONObject.getString(
								"paddingRight", null);
							paddingTop = styleJSONObject.getString(
								"paddingTop", null);
							shadow = styleJSONObject.getString("shadow", null);
							textAlign = styleJSONObject.getString(
								"textAlign", null);
							textColor = styleJSONObject.getString(
								"textColor", null);
							width = styleJSONObject.getString("width", null);

							setHidden(
								() -> {
									if (Objects.equals(
											styleJSONObject.getString(
												"display"),
											"block")) {

										return false;
									}

									if (Objects.equals(
											styleJSONObject.getString(
												"display"),
											"none")) {

										return true;
									}

									return null;
								});
						}
					});
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
		BaseStyledLayoutStructureItemMapper.class);

}