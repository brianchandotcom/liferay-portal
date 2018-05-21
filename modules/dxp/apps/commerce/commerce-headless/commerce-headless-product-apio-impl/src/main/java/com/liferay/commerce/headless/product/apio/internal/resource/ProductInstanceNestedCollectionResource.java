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

package com.liferay.commerce.headless.product.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.headless.product.apio.identifier.ProductDefinitionIdentifier;
import com.liferay.commerce.headless.product.apio.identifier.ProductInstanceIdentifier;
import com.liferay.commerce.headless.product.apio.internal.form.ProductInstanceCreatorForm;
import com.liferay.commerce.headless.product.apio.internal.util.ProductIndexerHelper;
import com.liferay.commerce.headless.product.apio.internal.util.ProductInstanceHelper;
import com.liferay.commerce.product.exception.CPInstanceDisplayDateException;
import com.liferay.commerce.product.exception.CPInstanceExpirationDateException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.search.CPInstanceIndexer;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class ProductInstanceNestedCollectionResource
	implements
		NestedCollectionResource<Document, Long, ProductInstanceIdentifier,
			Long, ProductDefinitionIdentifier> {

	@Override
	public NestedCollectionRoutes<Document, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCPInstance,
			_hasPermission.forAddingEntries(CPInstance.class),
			ProductInstanceCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "product-skus";
	}

	@Override
	public ItemRoutes<Document, Long> itemRoutes(
		ItemRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getCPInstance
		).addUpdater(
			this::_updateCPInstance,
			_hasPermission.forUpdating(CPInstance.class),
			ProductInstanceCreatorForm::buildForm
		).addRemover(
			idempotent(_cpInstanceService::deleteCPInstance),
			_hasPermission.forDeleting(CPInstance.class)
		).build();
	}

	@Override
	public Representor<Document, Long> representor(
		Representor.Builder<Document, Long> builder) {

		return builder.types(
			"ProductSKUs"
		).identifier(
			document -> GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))
		).addBidirectionalModel(
			"product", "productSKUs", ProductDefinitionIdentifier.class,
			document -> GetterUtil.getLong(
				document.get(CPInstanceIndexer.FIELD_CP_DEFINITION_ID))
		).addDate(
			"dateCreated",
			document -> Try.fromFallible(
				() -> document.getDate(Field.CREATE_DATE)
			).getUnchecked()
		).addDate(
			"dateModified",
			document -> Try.fromFallible(
				() -> document.getDate(Field.MODIFIED_DATE)
			).getUnchecked()
		).addString(
			"sku", document -> document.get(CPInstanceIndexer.FIELD_SKU)
		).build();
	}

	private Document _addCPInstance(
			Long cpDefinitionId, ProductInstanceCreatorForm form)
		throws PortalException {

		try {
			CPInstance cpInstance = _productInstanceHelper.createCPInstance(
				cpDefinitionId, form.getSku(), form.getGtin(),
				form.getManufacturerPartNumber(), form.getPurchasable(),
				form.getWidth(), form.getHeight(), form.getDepth(),
				form.getWeight(), form.getCost(), form.getPrice(),
				form.getPromoPrice(), form.getPublished(),
				form.getDisplayDate(), form.getExpirationDate(),
				form.getNeverExpire());

			Indexer<CPInstance> indexer = _productIndexerHelper.getIndexer(
				CPInstance.class);

			return indexer.getDocument(cpInstance);
		}
		catch (CPInstanceDisplayDateException cpidde) {
			throw new BadRequestException(
				"Display Date no defined on Product Instance " + form.getSku(),
				cpidde);
		}
		catch (CPInstanceExpirationDateException cpiede) {
			throw new BadRequestException(
				"Expiration Date no defined on Product Instance " +
					form.getSku(),
				cpiede);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Document _getCPInstance(Long cpInstanceId) {
		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext();

			SearchContext searchContext =
				_productInstanceHelper.buildSearchContext(
					String.valueOf(cpInstanceId), null,
					String.valueOf(cpInstanceId), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null, serviceContext);

			Indexer<CPInstance> indexer = _productIndexerHelper.getIndexer(
				CPInstance.class);

			Hits hits = indexer.search(searchContext);

			if (hits.getLength() == 0) {
				throw new NotFoundException(
					"Unable to find product option with ID " + cpInstanceId);
			}

			if (hits.getLength() > 1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"More than one document found for sku with ID " +
							cpInstanceId);
				}

				CPInstance cpInstance = _cpInstanceService.getCPInstance(
					cpInstanceId);

				return indexer.getDocument(cpInstance);
			}

			List<Document> documents = hits.toList();

			return documents.get(0);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Document> _getPageItems(
		Pagination pagination, Long cpDefinitionId) {

		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext();

			SearchContext searchContext =
				_productInstanceHelper.buildSearchContext(
					null, String.valueOf(cpDefinitionId), null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null, serviceContext);

			Indexer<CPInstance> indexer = _productIndexerHelper.getIndexer(
				CPInstance.class);

			Hits hits = indexer.search(searchContext);

			List<Document> documents = Collections.<Document>emptyList();

			if (hits.getLength() > 0) {
				documents = hits.toList();
			}

			return new PageItems<>(documents, hits.getLength());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Document _updateCPInstance(
		Long cpInstanceId, ProductInstanceCreatorForm form) {

		try {
			CPInstance cpInstance = _productInstanceHelper.updateCPInstance(
				cpInstanceId, form.getSku(), form.getGtin(),
				form.getManufacturerPartNumber(), form.getPurchasable(),
				form.getWidth(), form.getHeight(), form.getDepth(),
				form.getWeight(), form.getCost(), form.getPrice(),
				form.getPromoPrice(), form.getPublished(),
				form.getDisplayDate(), form.getExpirationDate(),
				form.getNeverExpire());

			Indexer<CPInstance> indexer = _productIndexerHelper.getIndexer(
				CPInstance.class);

			return indexer.getDocument(cpInstance);
		}
		catch (CPInstanceDisplayDateException cpidde) {
			throw new BadRequestException(
				"Display Date not defined on Product Instance " + form.getSku(),
				cpidde);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductInstanceNestedCollectionResource.class);

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private HasPermission _hasPermission;

	@Reference
	private ProductIndexerHelper _productIndexerHelper;

	@Reference
	private ProductInstanceHelper _productInstanceHelper;

}