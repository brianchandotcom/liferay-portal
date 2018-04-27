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

package com.liferay.product.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRelModel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.product.option.apio.identifier.ProductOptionIdentifier;
import com.liferay.product.option.value.apio.identifier.ProductOptionValueIdentifier;

import java.util.List;

import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class ProductOptionValueNestedCollectionResource
	implements
		NestedCollectionResource<CPDefinitionOptionValueRel, Long,
			ProductOptionValueIdentifier, Long, ProductOptionIdentifier> {

	@Override
	public NestedCollectionRoutes<CPDefinitionOptionValueRel, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CPDefinitionOptionValueRel, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "product-option-values";
	}

	@Override
	public ItemRoutes<CPDefinitionOptionValueRel, Long> itemRoutes(
		ItemRoutes.Builder<CPDefinitionOptionValueRel, Long> builder) {

		return builder.addGetter(
			this::_getCPDefinitionOptionValueRel
		).build();
	}

	@Override
	public Representor<CPDefinitionOptionValueRel, Long> representor(
		Representor.Builder<CPDefinitionOptionValueRel, Long> builder) {

		return builder.types(
			"ProductOptionValue"
		).identifier(
			CPDefinitionOptionValueRel::getCPDefinitionOptionValueRelId
		).addBidirectionalModel(
			"productOption", "productOptionValues",
			ProductOptionIdentifier.class,
			CPDefinitionOptionValueRelModel::getCPDefinitionOptionRelId
		).addDate(
			"dateCreated", CPDefinitionOptionValueRel::getCreateDate
		).addDate(
			"dateModified", CPDefinitionOptionValueRel::getModifiedDate
		).addNumber(
			"priority", CPDefinitionOptionValueRel::getPriority
		).addString(
			"name", this::_getName
		).build();
	}

	private CPDefinitionOptionValueRel _getCPDefinitionOptionValueRel(
		Long cpDefinitionOptionValueRelId) {

		try {
			return _cpDefinitionOptionValueRelService.
				getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private String _getName(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

		return cpDefinitionOptionValueRel.getTitle(LocaleUtil.getSiteDefault());
	}

	private PageItems<CPDefinitionOptionValueRel> _getPageItems(
		Pagination pagination, Long cpDefinitionOptionRelId) {

		try {
			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				_cpDefinitionOptionValueRelService.
					getCPDefinitionOptionValueRels(
						cpDefinitionOptionRelId, pagination.getStartPosition(),
						pagination.getEndPosition());
			int count =
				_cpDefinitionOptionValueRelService.
					getCPDefinitionOptionValueRelsCount(
						cpDefinitionOptionRelId);

			return new PageItems<>(cpDefinitionOptionValueRels, count);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

}