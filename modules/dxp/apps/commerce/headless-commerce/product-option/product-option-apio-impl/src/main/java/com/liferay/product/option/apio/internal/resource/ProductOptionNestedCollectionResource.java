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

package com.liferay.product.option.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionRelModel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.product.apio.identifier.ProductDefinitionIdentifier;
import com.liferay.product.option.apio.identifier.ProductOptionIdentifier;

import java.util.List;

import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class ProductOptionNestedCollectionResource
	implements
		NestedCollectionResource<CPDefinitionOptionRel, Long,
			ProductOptionIdentifier, Long, ProductDefinitionIdentifier> {

	@Override
	public NestedCollectionRoutes<CPDefinitionOptionRel, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CPDefinitionOptionRel, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "product-options";
	}

	@Override
	public ItemRoutes<CPDefinitionOptionRel, Long> itemRoutes(
		ItemRoutes.Builder<CPDefinitionOptionRel, Long> builder) {

		return builder.addGetter(
			this::_getCPDefinitionOptionRel
		).build();
	}

	@Override
	public Representor<CPDefinitionOptionRel, Long> representor(
		Representor.Builder<CPDefinitionOptionRel, Long> builder) {

		return builder.types(
			"ProductOption"
		).identifier(
			CPDefinitionOptionRel::getCPDefinitionId
		).addBidirectionalModel(
			"product", "productOptions", ProductDefinitionIdentifier.class,
			CPDefinitionOptionRelModel::getGroupId
		).addDate(
			"dateCreated", CPDefinitionOptionRel::getCreateDate
		).addDate(
			"dateModified", CPDefinitionOptionRel::getModifiedDate
		).addString(
			"title", this::_getTitle
		).addString(
			"description", CPDefinitionOptionRel::getDescription
		).build();
	}

	private CPDefinitionOptionRel _getCPDefinitionOptionRel(
		Long cpDefinitionOptionRelId) {

		try {
			return _cpDefinitionOptionRelService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<CPDefinitionOptionRel> _getPageItems(
		Pagination pagination, Long cpDefinitionId) {

		try {
			List<CPDefinitionOptionRel> cpDefinitions =
				_cpDefinitionOptionRelService.getCPDefinitionOptionRels(
					cpDefinitionId, pagination.getStartPosition(),
					pagination.getEndPosition());
			int count =
				_cpDefinitionOptionRelService.getCPDefinitionOptionRelsCount(
					cpDefinitionId);

			return new PageItems<>(cpDefinitions, count);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private String _getTitle(CPDefinitionOptionRel cpDefinitionOptionRel) {
		return cpDefinitionOptionRel.getTitle(LocaleUtil.getSiteDefault());
	}

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

}