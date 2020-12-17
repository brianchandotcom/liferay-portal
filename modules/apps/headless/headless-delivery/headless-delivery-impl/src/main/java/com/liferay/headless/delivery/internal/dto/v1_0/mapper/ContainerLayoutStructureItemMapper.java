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
import com.liferay.headless.delivery.dto.v1_0.Layout;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageSectionDefinition;
import com.liferay.layout.page.template.util.AlignConverter;
import com.liferay.layout.page.template.util.BorderRadiusConverter;
import com.liferay.layout.page.template.util.JustifyConverter;
import com.liferay.layout.page.template.util.MarginConverter;
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.page.template.util.ShadowConverter;
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
						backgroundColor = GetterUtil.getString(
							containerStyledLayoutStructureItem.
								getBackgroundColorCssClass(),
							null);
						backgroundFragmentImage = toBackgroundFragmentImage(
							containerStyledLayoutStructureItem.
								getBackgroundImageJSONObject(),
							saveMappingConfiguration);
						fragmentLink = _toFragmentLink(
							containerStyledLayoutStructureItem.
								getLinkJSONObject(),
							saveMappingConfiguration);
						layout = _toLayout(containerStyledLayoutStructureItem);

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
							() -> {
								JSONObject itemConfigJSONObject =
									containerStyledLayoutStructureItem.
										getItemConfigJSONObject();

								return getFragmentViewPorts(
									itemConfigJSONObject);
							});
					}
				};
				type = Type.SECTION;
			}
		};
	}

	private FragmentLink _toFragmentLink(
		JSONObject jsonObject, boolean saveMapping) {

		if (jsonObject == null) {
			return null;
		}

		if (jsonObject.isNull("href") &&
			!isSaveFragmentMappedValue(jsonObject, saveMapping)) {

			return null;
		}

		return new FragmentLink() {
			{
				setHref(
					() -> {
						if (isSaveFragmentMappedValue(
								jsonObject, saveMapping)) {

							return toFragmentMappedValue(
								toDefaultMappingValue(jsonObject, null),
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

	private Layout _toLayout(
		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem) {

		return new Layout() {
			{
				align = Align.create(
					AlignConverter.convertToExternalValue(
						containerStyledLayoutStructureItem.getAlign()));
				borderRadius = BorderRadius.create(
					BorderRadiusConverter.convertToExternalValue(
						containerStyledLayoutStructureItem.getBorderRadius()));
				borderWidth =
					containerStyledLayoutStructureItem.getBorderWidth();
				justify = Justify.create(
					JustifyConverter.convertToExternalValue(
						containerStyledLayoutStructureItem.getJustify()));
				marginBottom = MarginConverter.convertToExternalValue(
					containerStyledLayoutStructureItem.getMarginBottom());
				marginLeft = MarginConverter.convertToExternalValue(
					containerStyledLayoutStructureItem.getMarginLeft());
				marginRight = MarginConverter.convertToExternalValue(
					containerStyledLayoutStructureItem.getMarginRight());
				marginTop = MarginConverter.convertToExternalValue(
					containerStyledLayoutStructureItem.getMarginTop());
				opacity = containerStyledLayoutStructureItem.getOpacity();
				paddingBottom = PaddingConverter.convertToExternalValue(
					containerStyledLayoutStructureItem.getPaddingBottom());
				paddingLeft = PaddingConverter.convertToExternalValue(
					containerStyledLayoutStructureItem.getPaddingLeft());
				paddingRight = PaddingConverter.convertToExternalValue(
					containerStyledLayoutStructureItem.getPaddingRight());
				paddingTop = PaddingConverter.convertToExternalValue(
					containerStyledLayoutStructureItem.getPaddingTop());
				shadow = Shadow.create(
					ShadowConverter.convertToExternalValue(
						containerStyledLayoutStructureItem.getShadow()));

				setBorderColor(
					() -> {
						String borderColor =
							containerStyledLayoutStructureItem.getBorderColor();

						if (Validator.isNull(borderColor)) {
							return null;
						}

						return borderColor;
					});
				setContentDisplay(
					() -> {
						String contentDisplay =
							containerStyledLayoutStructureItem.
								getContentDisplay();

						if (Validator.isNull(contentDisplay)) {
							return null;
						}

						return ContentDisplay.create(
							StringUtil.upperCaseFirstLetter(contentDisplay));
					});
				setWidthType(
					() -> {
						String widthType =
							containerStyledLayoutStructureItem.getWidthType();

						if (Validator.isNotNull(widthType)) {
							return WidthType.create(
								StringUtil.upperCaseFirstLetter(widthType));
						}

						String containerType =
							containerStyledLayoutStructureItem.
								getContainerType();

						if (Validator.isNotNull(containerType)) {
							return WidthType.create(
								StringUtil.upperCaseFirstLetter(containerType));
						}

						return null;
					});
			}
		};
	}

}