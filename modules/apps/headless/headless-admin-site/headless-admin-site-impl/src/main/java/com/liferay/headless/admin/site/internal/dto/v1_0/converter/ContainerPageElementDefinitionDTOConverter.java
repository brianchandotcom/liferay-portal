/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.ContainerPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.HtmlProperties;
import com.liferay.headless.admin.site.dto.v1_0.Layout;
import com.liferay.headless.admin.site.dto.v1_0.PageElementDefinition;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentLinkValueUtil;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.converter.AlignConverter;
import com.liferay.layout.converter.ContentDisplayConverter;
import com.liferay.layout.converter.ContentVisibilityConverter;
import com.liferay.layout.converter.FlexWrapConverter;
import com.liferay.layout.converter.JustifyConverter;
import com.liferay.layout.util.constants.StyledLayoutStructureConstants;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem",
	service = DTOConverter.class
)
public class ContainerPageElementDefinitionDTOConverter
	extends BaseStyledLayoutStructureItem
	implements DTOConverter
		<ContainerStyledLayoutStructureItem, ContainerPageElementDefinition> {

	@Override
	public String getContentType() {
		return ContainerPageElementDefinition.class.getSimpleName();
	}

	@Override
	public ContainerPageElementDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			ContainerStyledLayoutStructureItem
				containerStyledLayoutStructureItem)
		throws Exception {

		Long scopeGroupId = (Long)dtoConverterContext.getAttribute(
			"scopeGroupId");

		if (scopeGroupId == null) {
			throw new UnsupportedOperationException();
		}

		return new ContainerPageElementDefinition() {
			{
				setContentVisibility(
					() -> {
						String contentVisibility =
							containerStyledLayoutStructureItem.
								getContentVisibility();

						if ((contentVisibility == null) ||
							contentVisibility.isEmpty()) {

							return null;
						}

						return ContentVisibility.create(
							ContentVisibilityConverter.convertToExternalValue(
								contentVisibility));
					});
				setCssClasses(
					() -> toCssClasses(
						containerStyledLayoutStructureItem.getCssClasses()));
				setCustomCSS(
					() -> toCustomCSS(
						containerStyledLayoutStructureItem.getCustomCSS()));
				setFragmentLink(
					() -> FragmentLinkValueUtil.toFragmentLink(
						_infoItemServiceRegistry,
						containerStyledLayoutStructureItem.getLinkJSONObject(),
						scopeGroupId));
				setFragmentStyle(
					() -> toFragmentStyle(
						_infoItemServiceRegistry,
						containerStyledLayoutStructureItem.
							getStylesJSONObject(),
						scopeGroupId));
				setFragmentViewports(
					() -> toFragmentViewports(
						containerStyledLayoutStructureItem.
							getItemConfigJSONObject()));
				setHtmlProperties(
					() -> _toHtmlProperties(
						containerStyledLayoutStructureItem));
				setIndexed(containerStyledLayoutStructureItem::isIndexed);
				setLayout(() -> _toLayout(containerStyledLayoutStructureItem));
				setName(containerStyledLayoutStructureItem::getName);
				setType(PageElementDefinition.Type.CONTAINER);
			}
		};
	}

	private HtmlProperties _toHtmlProperties(
		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem) {

		return new HtmlProperties() {
			{
				setHtmlTag(
					() -> _internalToExternalValuesMap.get(
						containerStyledLayoutStructureItem.getHtmlTag()));
			}
		};
	}

	private Layout _toLayout(
		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem) {

		if ((containerStyledLayoutStructureItem.getAlign() == null) &&
			(containerStyledLayoutStructureItem.getContentDisplay() == null) &&
			(containerStyledLayoutStructureItem.getFlexWrap() == null) &&
			(containerStyledLayoutStructureItem.getJustify() == null) &&
			(containerStyledLayoutStructureItem.getWidthType() == null)) {

			return null;
		}

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
				setWidthType(
					() -> {
						String widthType =
							containerStyledLayoutStructureItem.getWidthType();

						if (Validator.isNull(widthType) ||
							Objects.equals(
								widthType,
								StyledLayoutStructureConstants.WIDTH_TYPE)) {

							return null;
						}

						return WidthType.create(
							StringUtil.upperCaseFirstLetter(widthType));
					});
			}
		};
	}

	private static final Map<String, HtmlProperties.HtmlTag>
		_internalToExternalValuesMap = HashMapBuilder.put(
			"article", HtmlProperties.HtmlTag.ARTICLE
		).put(
			"aside", HtmlProperties.HtmlTag.ASIDE
		).put(
			"div", HtmlProperties.HtmlTag.DIV
		).put(
			"footer", HtmlProperties.HtmlTag.FOOTER
		).put(
			"header", HtmlProperties.HtmlTag.HEADER
		).put(
			"nav", HtmlProperties.HtmlTag.NAV
		).put(
			"section", HtmlProperties.HtmlTag.SECTION
		).build();

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}