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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.type.grouped.constants.GroupedCPTypeConstants;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.commerce.shop.by.diagram.constants.CSDiagramCPTypeConstants;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.LinkedProduct;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.LinkedProductDTOConverterContext;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.LinkedProductResource;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/linked-product.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {LinkedProductResource.class, NestedFieldSupport.class}
)
public class LinkedProductResourceImpl
	extends BaseLinkedProductResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = Product.class, value = "linkedProducts")
	@Override
	public Page<LinkedProduct> getProductIdLinkedProductsPage(
			@NestedFieldId(value = "productId") Long productId,
			Pagination pagination)
		throws Exception {

		int cProductCSDiagramEntriesCount =
			_csDiagramEntryService.getCProductCSDiagramEntriesCount(productId);
		int entryCProductCPDefinitionGroupedEntriesCount =
			_cpDefinitionGroupedEntryService.
				getEntryCProductCPDefinitionGroupedEntriesCount(productId);

		return Page.of(
			ListUtil.concat(
				transform(
					_cpDefinitionGroupedEntryService.
						getEntryCProductCPDefinitionGroupedEntries(
							productId, pagination.getStartPosition(),
							pagination.getEndPosition(), null),
					cpDefinitionGroupedEntry ->
						_linkedProductDTOConverter.toDTO(
							new LinkedProductDTOConverterContext(
								contextAcceptLanguage.isAcceptAllLanguages(),
								null, _dtoConverterRegistry,
								cpDefinitionGroupedEntry.
									getCPDefinitionGroupedEntryId(),
								contextAcceptLanguage.getPreferredLocale(),
								GroupedCPTypeConstants.NAME, contextUriInfo,
								contextUser))),
				transform(
					_csDiagramEntryService.getCProductCSDiagramEntries(
						productId, pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					csDiagramEntry -> _linkedProductDTOConverter.toDTO(
						new LinkedProductDTOConverterContext(
							contextAcceptLanguage.isAcceptAllLanguages(), null,
							_dtoConverterRegistry,
							csDiagramEntry.getCSDiagramEntryId(),
							contextAcceptLanguage.getPreferredLocale(),
							CSDiagramCPTypeConstants.NAME, contextUriInfo,
							contextUser)))),
			pagination,
			entryCProductCPDefinitionGroupedEntriesCount +
				cProductCSDiagramEntriesCount);
	}

	@Reference
	private CPDefinitionGroupedEntryService _cpDefinitionGroupedEntryService;

	@Reference
	private CSDiagramEntryService _csDiagramEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.LinkedProductDTOConverter)"
	)
	private DTOConverter<CPDefinitionGroupedEntry, LinkedProduct>
		_linkedProductDTOConverter;

}