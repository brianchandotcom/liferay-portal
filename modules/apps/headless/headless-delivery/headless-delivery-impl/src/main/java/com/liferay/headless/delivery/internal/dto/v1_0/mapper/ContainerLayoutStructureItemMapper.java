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

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentLink;
import com.liferay.headless.delivery.dto.v1_0.HtmlProperties;
import com.liferay.headless.delivery.dto.v1_0.Layout;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageSectionDefinition;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.FragmentMappedValueUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.LocalizedValueUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.StyledLayoutStructureItemUtil;
import com.liferay.layout.page.template.util.AlignConverter;
import com.liferay.layout.page.template.util.BorderRadiusConverter;
import com.liferay.layout.page.template.util.ContentDisplayConverter;
import com.liferay.layout.page.template.util.ContentVisibilityConverter;
import com.liferay.layout.page.template.util.FlexWrapConverter;
import com.liferay.layout.page.template.util.HtmlTagConverter;
import com.liferay.layout.page.template.util.JustifyConverter;
import com.liferay.layout.page.template.util.MarginConverter;
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.page.template.util.ShadowConverter;
import com.liferay.layout.util.structure.CommonStylesUtil;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemMapper.class)
public class ContainerLayoutStructureItemMapper
	extends BaseStyledLayoutStructureItemMapper {

	@Override
	public String getClassName() {
		return ContainerStyledLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageSectionDefinition() {
					{
						cssClasses =
							StyledLayoutStructureItemUtil.getCssClasses(
								containerStyledLayoutStructureItem);
						customCSS = StyledLayoutStructureItemUtil.getCustomCSS(
							containerStyledLayoutStructureItem);
						customCSSViewports =
							StyledLayoutStructureItemUtil.getCustomCSSViewports(
								containerStyledLayoutStructureItem);
						fragmentLink = _toFragmentLink(
							containerStyledLayoutStructureItem.
								getLinkJSONObject(),
							saveMappingConfiguration);
						indexed =
							containerStyledLayoutStructureItem.isIndexed();
						layout = _toLayout(containerStyledLayoutStructureItem);

						setContentVisibility(
							() -> {
								String contentVisibility =
									containerStyledLayoutStructureItem.
										getContentVisibility();

								if (Validator.isNull(contentVisibility)) {
									return null;
								}

								return ContentVisibilityConverter.
									convertToExternalValue(contentVisibility);
							});
						setFragmentStyle(
							() -> {
								JSONObject itemConfigJSONObject =
									containerStyledLayoutStructureItem.
										getItemConfigJSONObject();

								return toFragmentStyle(
									itemConfigJSONObject.getJSONObject(
										"styles"),
									saveMappingConfiguration);
							});
						setFragmentViewports(
							() -> getFragmentViewPorts(
								containerStyledLayoutStructureItem.
									getItemConfigJSONObject()));
						setHtmlProperties(
							() -> _toHtmlProperties(
								containerStyledLayoutStructureItem));

						setName(containerStyledLayoutStructureItem::getName);
					}
				};
				type = Type.SECTION;
			}
		};
	}

	private Object _getStyleProperty(
		JSONObject stylesJSONObject, String propertyKey) {

		Object styleValue = stylesJSONObject.get(propertyKey);

		if (styleValue != null) {
			return styleValue;
		}

		return CommonStylesUtil.getDefaultStyleValue(propertyKey);
	}

	private FragmentLink _toFragmentLink(
		JSONObject jsonObject, boolean saveMapping) {

		if (jsonObject == null) {
			return null;
		}

		boolean saveFragmentMappedValue =
			FragmentMappedValueUtil.isSaveFragmentMappedValue(
				jsonObject, saveMapping);

		if (jsonObject.isNull("href") && !saveFragmentMappedValue) {
			return null;
		}

		return new FragmentLink() {
			{
				setHref(
					() -> {
						if (saveFragmentMappedValue) {
							return toFragmentMappedValue(
								toDefaultMappingValue(jsonObject, null),
								jsonObject);
						}

						return new FragmentInlineValue() {
							{
								value_i18n =
									LocalizedValueUtil.toLocalizedValues(
										jsonObject.getJSONObject("href"));
							}
						};
					});
				setTarget(
					() -> {
						String target = jsonObject.getString("target");

						if (Validator.isNull(target)) {
							return null;
						}

						if (StringUtil.equalsIgnoreCase(target, "_parent") ||
							StringUtil.equalsIgnoreCase(target, "_top")) {

							target = "_self";
						}

						return Target.create(
							StringUtil.upperCaseFirstLetter(
								target.substring(1)));
					});
			}
		};
	}

	private HtmlProperties _toHtmlProperties(
		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem) {

		String value = containerStyledLayoutStructureItem.getHtmlTag();

		if (Validator.isNull(value)) {
			return null;
		}

		return new HtmlProperties() {
			{
				setHtmlTag(
					() -> HtmlTag.create(
						HtmlTagConverter.convertToExternalValue(value)));
			}
		};
	}

	private Layout _toLayout(
		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem) {

		JSONObject itemConfigJSONObject =
			containerStyledLayoutStructureItem.getItemConfigJSONObject();

		JSONObject stylesJSONObject = itemConfigJSONObject.getJSONObject(
			"styles");

		return new Layout() {
			{
				setAlign(
					() -> {
						String align =
							containerStyledLayoutStructureItem.getAlign();

						if (Validator.isNull(align)) {
							return null;
						}

						return Align.create(
							AlignConverter.convertToExternalValue(align));
					});
				setBorderColor(
					() -> {
						Object borderColor = _getStyleProperty(
							stylesJSONObject, "borderColor");

						if (Validator.isNull(borderColor)) {
							return null;
						}

						return GetterUtil.getString(borderColor);
					});
				setBorderRadius(
					() -> {
						Object borderRadius = _getStyleProperty(
							stylesJSONObject, "borderRadius");

						if (Validator.isNull(borderRadius)) {
							return null;
						}

						return BorderRadius.create(
							BorderRadiusConverter.convertToExternalValue(
								GetterUtil.getString(borderRadius)));
					});
				setBorderWidth(
					() -> {
						Object borderWidth = _getStyleProperty(
							stylesJSONObject, "borderWidth");

						if (Validator.isNull(borderWidth)) {
							return null;
						}

						return GetterUtil.getInteger(borderWidth);
					});
				setContentDisplay(
					() -> {
						Object contentDisplay =
							containerStyledLayoutStructureItem.
								getContentDisplay();

						if (Validator.isNull(contentDisplay)) {
							return null;
						}

						return ContentDisplay.create(
							ContentDisplayConverter.convertToExternalValue(
								GetterUtil.getString(contentDisplay)));
					});
				setFlexWrap(
					() -> {
						String flexWrap =
							containerStyledLayoutStructureItem.getFlexWrap();

						if (Validator.isNull(flexWrap)) {
							return null;
						}

						return FlexWrap.create(
							FlexWrapConverter.convertToExternalValue(flexWrap));
					});
				setJustify(
					() -> {
						String justify =
							containerStyledLayoutStructureItem.getJustify();

						if (Validator.isNull(justify)) {
							return null;
						}

						return Justify.create(
							JustifyConverter.convertToExternalValue(justify));
					});
				setMarginBottom(
					() -> {
						Object marginBottom = _getStyleProperty(
							stylesJSONObject, "marginBottom");

						if (Validator.isNull(marginBottom)) {
							return null;
						}

						return GetterUtil.getInteger(
							MarginConverter.convertToExternalValue(
								GetterUtil.getString(marginBottom)));
					});
				setMarginLeft(
					() -> {
						Object marginLeft = _getStyleProperty(
							stylesJSONObject, "marginLeft");

						if (Validator.isNull(marginLeft)) {
							return null;
						}

						return GetterUtil.getInteger(
							MarginConverter.convertToExternalValue(
								GetterUtil.getString(marginLeft)));
					});
				setMarginRight(
					() -> {
						Object marginRight = _getStyleProperty(
							stylesJSONObject, "marginRight");

						if (Validator.isNull(marginRight)) {
							return null;
						}

						return GetterUtil.getInteger(
							MarginConverter.convertToExternalValue(
								GetterUtil.getString(marginRight)));
					});
				setMarginTop(
					() -> {
						Object marginTop = _getStyleProperty(
							stylesJSONObject, "marginTop");

						if (Validator.isNull(marginTop)) {
							return null;
						}

						return GetterUtil.getInteger(
							MarginConverter.convertToExternalValue(
								GetterUtil.getString(marginTop)));
					});
				setOpacity(
					() -> {
						Object opacity = _getStyleProperty(
							stylesJSONObject, "opacity");

						if (Validator.isNull(opacity)) {
							return null;
						}

						return GetterUtil.getInteger(opacity, 100);
					});
				setPaddingBottom(
					() -> {
						Object paddingBottom = _getStyleProperty(
							stylesJSONObject, "paddingBottom");

						if (Validator.isNull(paddingBottom)) {
							return null;
						}

						return GetterUtil.getInteger(
							PaddingConverter.convertToExternalValue(
								GetterUtil.getString(paddingBottom)));
					});
				setPaddingLeft(
					() -> {
						Object paddingLeft = _getStyleProperty(
							stylesJSONObject, "paddingLeft");

						if (Validator.isNull(paddingLeft)) {
							return null;
						}

						return GetterUtil.getInteger(
							PaddingConverter.convertToExternalValue(
								GetterUtil.getString(paddingLeft)));
					});
				setPaddingRight(
					() -> {
						Object paddingRight = _getStyleProperty(
							stylesJSONObject, "paddingRight");

						if (Validator.isNull(paddingRight)) {
							return null;
						}

						return GetterUtil.getInteger(
							PaddingConverter.convertToExternalValue(
								GetterUtil.getString(paddingRight)));
					});
				setPaddingTop(
					() -> {
						Object paddingTop = _getStyleProperty(
							stylesJSONObject, "paddingTop");

						if (Validator.isNull(paddingTop)) {
							return null;
						}

						return GetterUtil.getInteger(
							PaddingConverter.convertToExternalValue(
								GetterUtil.getString(paddingTop)));
					});
				setShadow(
					() -> {
						Object shadow = _getStyleProperty(
							stylesJSONObject, "shadow");

						if (Validator.isNull(shadow)) {
							return null;
						}

						return Shadow.create(
							ShadowConverter.convertToExternalValue(
								GetterUtil.getString(shadow)));
					});
				setWidthType(
					() -> {
						String widthType =
							containerStyledLayoutStructureItem.getWidthType();

						if (Validator.isNotNull(widthType)) {
							return WidthType.create(
								StringUtil.upperCaseFirstLetter(widthType));
						}

						return null;
					});
			}
		};
	}

}