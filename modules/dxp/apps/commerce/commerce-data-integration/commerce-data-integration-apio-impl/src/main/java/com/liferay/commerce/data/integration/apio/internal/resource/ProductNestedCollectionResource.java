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

package com.liferay.commerce.data.integration.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.ProductDefinitionIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.ProductCreatorForm;
import com.liferay.commerce.data.integration.apio.internal.util.ProductDefinitionHelper;
import com.liferay.commerce.product.exception.CPDefinitionProductTypeNameException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/Product">Product</a> resources through a web API. The
 * resources are mapped from the internal model {@code CPDefinition}.
 *
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class ProductNestedCollectionResource
	implements
		NestedCollectionResource<CPDefinition, Long,
			ProductDefinitionIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<CPDefinition, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CPDefinition, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCPDefinition,
			_hasPermission.forAddingIn(ProductDefinitionIdentifier.class),
			ProductCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "products";
	}

	@Override
	public ItemRoutes<CPDefinition, Long> itemRoutes(
		ItemRoutes.Builder<CPDefinition, Long> builder) {

		return builder.addGetter(
			_cpDefinitionService::getCPDefinition
		).addRemover(
			idempotent(_cpDefinitionService::deleteCPDefinition),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<CPDefinition> representor(
		Representor.Builder<CPDefinition, Long> builder) {

		return builder.types(
			"Product"
		).identifier(
			CPDefinition::getCPDefinitionId
		).addBidirectionalModel(
			"webSite", "products", WebSiteIdentifier.class,
			CPDefinition::getGroupId
		).addDate(
			"dateCreated",
			CPDefinition::getCreateDate
		).addDate(
			"dateModified",
			CPDefinition::getModifiedDate
		).addLinkedModel(
			"author", PersonIdentifier.class,
			CPDefinition::getUserId
		).addString(
			"description",
			CPDefinition::getDescription
		).addString(
			"productType",
			CPDefinition::getProductTypeName
		).addString(
			"name", CPDefinition::getName
		).addString(
			"externalReferenceCode",
			CPDefinition::getExternalReferenceCode
		).addStringList(
			"skus",
			this::_getSKUs
		).build();
	}

	private List<String> _getSKUs(CPDefinition cpDefinition) {
		return Arrays.asList(_cpInstanceLocalService.getSKUs(
			cpDefinition.getCPDefinitionId()));
	}

	private CPDefinition _addCPDefinition(
		Long webSiteId, ProductCreatorForm productCreatorForm) throws PortalException {

		try {
			return _productDefinitionHelper.upsertCPDefinition(
				webSiteId, productCreatorForm.getTitleMap(),
				productCreatorForm.getDescriptionMap(),
				productCreatorForm.getShortDescriptionMap(),
				productCreatorForm.getProductTypeName(),
				ArrayUtil.toLongArray(
					productCreatorForm.getAssetCategoryIds()),
				productCreatorForm.getExternalReferenceCode());
		}
		catch (CPDefinitionProductTypeNameException cpdptne) {
			throw new NotFoundException(
				"Product type not available: " +
					productCreatorForm.getProductTypeName(),
				cpdptne);
		}
	}

	private PageItems<CPDefinition> _getPageItems(
		Pagination pagination, Long webSiteId) throws PortalException {

		List<CPDefinition> cpDefinitions = _cpDefinitionService.getCPDefinitions(webSiteId, null, null, WorkflowConstants.STATUS_APPROVED,
				pagination.getStartPosition(), pagination.getEndPosition(), null);

		int total = _cpDefinitionService.getCPDefinitionsCount(webSiteId, null, null, WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(cpDefinitions, total);
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ProductDefinitionHelper _productDefinitionHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinition)"
	)
	private HasPermission<Long> _hasPermission;

}