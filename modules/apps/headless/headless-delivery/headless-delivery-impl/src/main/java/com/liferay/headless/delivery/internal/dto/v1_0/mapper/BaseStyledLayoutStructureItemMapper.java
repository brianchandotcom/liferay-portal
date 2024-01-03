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

/**
 * @author Pavel Savinov
 */
public abstract class BaseStyledLayoutStructureItemMapper
	implements LayoutStructureItemMapper {

	public BaseStyledLayoutStructureItemMapper(
		InfoItemServiceRegistry infoItemServiceRegistry, Portal portal) {

		this.infoItemServiceRegistry = infoItemServiceRegistry;
		this.portal = portal;
	}

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
					setValue(() -> valueString);
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
				setDefaultFragmentInlineValue(() -> fragmentInlineValue);
				setMapping(
					() -> new Mapping() {
						{
							setFieldKey(
								() -> FragmentMappedValueUtil.getFieldKey(
									jsonObject));
							setItemReference(
								() -> FragmentMappedValueUtil.toItemReference(
									jsonObject));
						}
					});
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
				setBackgroundColor(
					() -> jsonObject.getString("backgroundColor", null));
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
				setBorderColor(() -> jsonObject.getString("borderColor", null));
				setBorderRadius(
					() -> jsonObject.getString("borderRadius", null));
				setBorderWidth(() -> jsonObject.getString("borderWidth", null));
				setFontFamily(() -> jsonObject.getString("fontFamily", null));
				setFontSize(() -> jsonObject.getString("fontSize", null));
				setFontWeight(() -> jsonObject.getString("fontWeight", null));
				setHeight(() -> jsonObject.getString("height", null));
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
				setMarginBottom(
					() -> jsonObject.getString("marginBottom", null));
				setMarginLeft(() -> jsonObject.getString("marginLeft", null));
				setMarginRight(() -> jsonObject.getString("marginRight", null));
				setMarginTop(() -> jsonObject.getString("marginTop", null));
				setMaxHeight(() -> jsonObject.getString("maxHeight", null));
				setMaxWidth(() -> jsonObject.getString("maxWidth", null));
				setMinHeight(() -> jsonObject.getString("minHeight", null));
				setMinWidth(() -> jsonObject.getString("minWidth", null));
				setOpacity(() -> jsonObject.getString("opacity", null));
				setOverflow(() -> jsonObject.getString("overflow", null));
				setPaddingBottom(
					() -> jsonObject.getString("paddingBottom", null));
				setPaddingLeft(() -> jsonObject.getString("paddingLeft", null));
				setPaddingRight(
					() -> jsonObject.getString("paddingRight", null));
				setPaddingTop(() -> jsonObject.getString("paddingTop", null));
				setShadow(() -> jsonObject.getString("shadow", null));
				setTextAlign(() -> jsonObject.getString("textAlign", null));
				setTextColor(() -> jsonObject.getString("textColor", null));
				setWidth(() -> jsonObject.getString("width", null));
			}
		};
	}

	protected final InfoItemServiceRegistry infoItemServiceRegistry;
	protected final Portal portal;

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
				setTitle(
					() -> _toTitleFragmentInlineValue(jsonObject, urlValue));
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
								setValue(() -> urlValue);
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
				setFragmentViewportStyle(
					() -> new FragmentViewportStyle() {
						{
							setBackgroundColor(
								() -> styleJSONObject.getString(
									"backgroundColor", null));
							setBorderColor(
								() -> styleJSONObject.getString(
									"borderColor", null));
							setBorderRadius(
								() -> styleJSONObject.getString(
									"borderRadius", null));
							setBorderWidth(
								() -> styleJSONObject.getString(
									"borderWidth", null));
							setFontFamily(
								() -> styleJSONObject.getString(
									"fontFamily", null));
							setFontSize(
								() -> styleJSONObject.getString(
									"fontSize", null));
							setFontWeight(
								() -> styleJSONObject.getString(
									"fontWeight", null));
							setHeight(
								() -> styleJSONObject.getString(
									"height", null));
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
							setMarginBottom(
								() -> styleJSONObject.getString(
									"marginBottom", null));
							setMarginLeft(
								() -> styleJSONObject.getString(
									"marginLeft", null));
							setMarginRight(
								() -> styleJSONObject.getString(
									"marginRight", null));
							setMarginTop(
								() -> styleJSONObject.getString(
									"marginTop", null));
							setMaxHeight(
								() -> styleJSONObject.getString(
									"maxHeight", null));
							setMaxWidth(
								() -> styleJSONObject.getString(
									"maxWidth", null));
							setMinHeight(
								() -> styleJSONObject.getString(
									"minHeight", null));
							setMinWidth(
								() -> styleJSONObject.getString(
									"minWidth", null));
							setOpacity(
								() -> styleJSONObject.getString(
									"opacity", null));
							setOverflow(
								() -> styleJSONObject.getString(
									"overflow", null));
							setPaddingBottom(
								() -> styleJSONObject.getString(
									"paddingBottom", null));
							setPaddingLeft(
								() -> styleJSONObject.getString(
									"paddingLeft", null));
							setPaddingRight(
								() -> styleJSONObject.getString(
									"paddingRight", null));
							setPaddingTop(
								() -> styleJSONObject.getString(
									"paddingTop", null));
							setShadow(
								() -> styleJSONObject.getString(
									"shadow", null));
							setTextAlign(
								() -> styleJSONObject.getString(
									"textAlign", null));
							setTextColor(
								() -> styleJSONObject.getString(
									"textColor", null));
							setWidth(
								() -> styleJSONObject.getString("width", null));
						}
					});
				setId(viewportSize::getViewportSizeId);
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
				setValue(() -> title);
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseStyledLayoutStructureItemMapper.class);

}