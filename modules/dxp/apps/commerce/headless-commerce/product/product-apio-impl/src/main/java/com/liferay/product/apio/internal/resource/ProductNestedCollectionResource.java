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
import com.liferay.commerce.product.exception.CPDefinitionProductTypeNameException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionModel;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.architect.context.auth.MockPermissions;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.product.apio.identifier.ProductDefinitionIdentifier;
import com.liferay.product.apio.internal.form.ProductCreatorForm;
import com.liferay.product.apio.internal.util.ProductResourceCollectionUtil;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;
import java.util.stream.Stream;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/Product">Product </a> resources through
 * a web API. The resources are mapped from the internal model {@code
 * CPDefinition}.
 *
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class ProductNestedCollectionResource
	implements
		NestedCollectionResource<CPDefinition, Long,
			ProductDefinitionIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<CPDefinition, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CPDefinition, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCPDefinition, MockPermissions::validPermission,
			ProductCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "product";
	}

	@Override
	public ItemRoutes<CPDefinition, Long> itemRoutes(
		ItemRoutes.Builder<CPDefinition, Long> builder) {

		return builder.addGetter(
			this::_getCPDefinition
		).addRemover(
			this::_deleteCPDefinition, MockPermissions::validPermission
		).build();
	}

	@Override
	public Representor<CPDefinition, Long> representor(
		Representor.Builder<CPDefinition, Long> builder) {

		return builder.types(
			"product"
		).identifier(
			CPDefinition::getCPDefinitionId
		).addBidirectionalModel(
			"webSite", "product", WebSiteIdentifier.class,
			CPDefinitionModel::getGroupId
		).addDate(
			"dateCreated", CPDefinition::getCreateDate
		).addDate(
			"dateModified", CPDefinition::getModifiedDate
		).addDate(
			"datePublished", CPDefinition::getLastPublishDate
		).addLinkedModel(
			"author", PersonIdentifier.class, this::_getUserOptional
		).addLinkedModel(
			"creator", PersonIdentifier.class, this::_getUserOptional
		).addString(
			"description", CPDefinition::getDescription
		).addString(
			"title", CPDefinition::getTitle
		).build();
	}

	private CPDefinition _addCPDefinition(
			Long webSiteId, ProductCreatorForm productCreatorForm)
		throws PortalException {

		try {
			return _productResourceCollectionUtil.createCPDefinition(
				webSiteId, productCreatorForm.getTitleMap(),
				productCreatorForm.getDescriptionMap(),
				productCreatorForm.getProductTypeName(),
				_getAssetCategoryIds(productCreatorForm.getAssetCategoryIds()));
		}
		catch (CPDefinitionProductTypeNameException cpdptne) {
			throw new NotFoundException(
				"Unable to get product name " +
					productCreatorForm.getProductTypeName(),
				cpdptne);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private void _deleteCPDefinition(Long cpDefinitionId) {
		try {
			_cpDefinitionService.deleteCPDefinition(cpDefinitionId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private long[] _getAssetCategoryIds(List<Long> assetCategoryIdList) {
		Stream<Long> assetCategoryIdStream = assetCategoryIdList.stream();

		return assetCategoryIdStream.mapToLong(
			Long::longValue
		).toArray();
	}

	private CPDefinition _getCPDefinition(Long cpDefinitionId) {
		try {
			return _cpDefinitionService.getCPDefinition(cpDefinitionId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<CPDefinition> _getPageItems(
		Pagination pagination, Long webSiteId) {

		try {
			List<CPDefinition> cpDefinitions =
				_cpDefinitionService.getCPDefinitions(
					webSiteId, StringPool.BLANK, StringPool.BLANK,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);
			int count = _cpDefinitionService.getCPDefinitionsCount(
				webSiteId, StringPool.BLANK, StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED);

			return new PageItems<>(cpDefinitions, count);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Long _getUserOptional(CPDefinition cpDefinition) {
		return cpDefinition.getUserId();
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ProductResourceCollectionUtil _productResourceCollectionUtil;

}