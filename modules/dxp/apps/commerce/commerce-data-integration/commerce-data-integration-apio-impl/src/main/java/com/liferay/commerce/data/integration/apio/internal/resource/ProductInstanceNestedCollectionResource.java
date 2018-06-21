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
import com.liferay.commerce.data.integration.apio.identifiers.ProductInstanceIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.ProductInstanceCreatorForm;
import com.liferay.commerce.data.integration.apio.internal.util.ProductInstanceHelper;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class ProductInstanceNestedCollectionResource
	implements
		NestedCollectionResource<CPInstance, Long, ProductInstanceIdentifier,
			Long, ProductDefinitionIdentifier> {

	@Override
	public NestedCollectionRoutes<CPInstance, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CPInstance, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCPInstance,
			_hasPermission.forAddingIn(ProductInstanceIdentifier.class),
			ProductInstanceCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "product-skus";
	}

	@Override
	public ItemRoutes<CPInstance, Long> itemRoutes(
		ItemRoutes.Builder<CPInstance, Long> builder) {

		return builder.addGetter(
			_cpInstanceService::getCPInstance
		).addRemover(
			idempotent(_cpInstanceService::deleteCPInstance),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<CPInstance> representor(
		Representor.Builder<CPInstance, Long> builder) {

		return builder.types(
			"ProductSKUs"
		).identifier(
			CPInstance::getCPInstanceId
		).addBidirectionalModel(
			"product", "productSKUs", ProductDefinitionIdentifier.class,
			CPInstance::getCPDefinitionId
		).addDate(
			"dateCreated",
			CPInstance::getCreateDate
		).addDate(
			"dateModified",
			CPInstance::getModifiedDate
		).addString(
			"externalReferenceCode",
			CPInstance::getExternalReferenceCode
		).addString(
			"sku", CPInstance::getSku
		).build();
	}

	private CPInstance _addCPInstance(
			Long cpDefinitionId, ProductInstanceCreatorForm form)
		throws PortalException {

		return _productInstanceHelper.upsertCPInstance(
			cpDefinitionId, form.getSku(), form.getGtin(),
			form.getManufacturerPartNumber(), form.getPurchasable(),
			form.getWidth(), form.getHeight(), form.getDepth(),
			form.getWeight(), form.getCost(), form.getPrice(),
			form.getPromoPrice(), form.getPublished(),
			form.getDisplayDate(), form.getExpirationDate(),
			form.getNeverExpire(), form.getExternalReferenceCode());
	}

	private PageItems<CPInstance> _getPageItems(
		Pagination pagination, Long cpDefinitionId) throws PortalException {

		List<CPInstance> cpInstances = _cpInstanceService.getCPDefinitionInstances(cpDefinitionId,
			pagination.getStartPosition(), pagination.getEndPosition());

		int total = _cpInstanceService.getCPDefinitionInstancesCount(cpDefinitionId, WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(cpInstances, total);
	}

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private ProductInstanceHelper _productInstanceHelper;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPInstance)"
	)
	private HasPermission<Long> _hasPermission;

}