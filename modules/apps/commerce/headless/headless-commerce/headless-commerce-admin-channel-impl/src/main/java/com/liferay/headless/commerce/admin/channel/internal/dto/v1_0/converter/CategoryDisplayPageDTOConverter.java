/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.CategoryDisplayPage;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = "dto.class.name=com.liferay.headless.commerce.admin.channel.dto.v1_0.CategoryDisplayPage",
	service = DTOConverter.class
)
public class CategoryDisplayPageDTOConverter
	implements DTOConverter<CPDisplayLayout, CategoryDisplayPage> {

	@Override
	public String getContentType() {
		return CategoryDisplayPage.class.getSimpleName();
	}

	@Override
	public CategoryDisplayPage toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.getCPDisplayLayout(
				(Long)dtoConverterContext.getId());

		return new CategoryDisplayPage() {
			{
				setActions(dtoConverterContext::getActions);
				setCategoryId(cpDisplayLayout::getClassPK);
				setId(cpDisplayLayout::getCPDisplayLayoutId);
				setPageUuid(cpDisplayLayout::getLayoutUuid);
			}
		};
	}

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

}