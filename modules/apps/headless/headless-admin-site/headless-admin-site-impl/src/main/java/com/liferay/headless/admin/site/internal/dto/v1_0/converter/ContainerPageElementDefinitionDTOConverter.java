/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.ContainerPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.HtmlProperties;
import com.liferay.headless.admin.site.dto.v1_0.PageElementDefinition;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem",
	service = DTOConverter.class
)
public class ContainerPageElementDefinitionDTOConverter
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
					containerStyledLayoutStructureItem::getContentVisibility);
				setCssClasses(
					() -> {
						if (SetUtil.isEmpty(
								containerStyledLayoutStructureItem.
									getCssClasses())) {

							return null;
						}

						return ArrayUtil.toStringArray(
							containerStyledLayoutStructureItem.getCssClasses());
					});
				setCustomCSS(containerStyledLayoutStructureItem::getCustomCSS);
				setHtmlProperties(
					() -> _toHtmlProperties(
						containerStyledLayoutStructureItem));
				setIndexed(containerStyledLayoutStructureItem::isIndexed);
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

}