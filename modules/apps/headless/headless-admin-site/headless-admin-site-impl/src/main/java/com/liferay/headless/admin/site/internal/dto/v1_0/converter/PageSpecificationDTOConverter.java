/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Layout",
	service = DTOConverter.class
)
public class PageSpecificationDTOConverter
	implements DTOConverter<Layout, PageSpecification> {

	@Override
	public String getContentType() {
		return PageSpecification.class.getSimpleName();
	}

	@Override
	public PageSpecification toDTO(
			DTOConverterContext dtoConverterContext, Layout layout)
		throws Exception {

		return new PageSpecification() {
			{
				setExternalReferenceCode(layout::getExternalReferenceCode);
				setType(
					() -> {
						if (layout.isTypeAssetDisplay() ||
							layout.isTypeContent()) {

							return Type.CONTENT_PAGE_SPECIFICATION;
						}

						return Type.WIDGET_PAGE_SPECIFICATION;
					});
			}
		};
	}

}