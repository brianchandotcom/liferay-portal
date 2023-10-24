/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.PageRule;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.LayoutStructureRule",
	service = DTOConverter.class
)
public class PageRuleDTOConverter
	implements DTOConverter<LayoutStructureRule, PageRule> {

	@Override
	public String getContentType() {
		return PageRule.class.getSimpleName();
	}

	@Override
	public PageRule toDTO(
			DTOConverterContext dtoConverterContext,
			LayoutStructureRule layoutStructureRule)
		throws Exception {

		return new PageRule() {
			{
				id = layoutStructureRule.getId();
				name = layoutStructureRule.getName();
			}
		};
	}

}