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
import com.liferay.commerce.headless.product.apio.internal.form.ProductCreatorForm;
import com.liferay.commerce.headless.product.apio.internal.util.ProductDefinitionHelper;
import com.liferay.commerce.product.exception.CPDefinitionProductTypeNameException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.person.apio.identifier.PersonIdentifier;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.site.apio.identifier.WebSiteIdentifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
		NestedCollectionResource<Document, Long,
			ProductDefinitionIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<Document, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCPDefinition, (credentials, s) -> true,
			ProductCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "products";
	}

	@Override
	public ItemRoutes<Document, Long> itemRoutes(
		ItemRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getCPDefinition
		).addRemover(
			idempotent(_cpDefinitionService::deleteCPDefinition),
			_hasPermission.forDeleting(CPDefinition.class)
		).build();
	}

	@Override
	public Representor<Document, Long> representor(
		Representor.Builder<Document, Long> builder) {

		return builder.types(
			"Product"
		).identifier(
			document -> GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))
		).addBidirectionalModel(
			"webSite", "products", WebSiteIdentifier.class,
			document -> GetterUtil.getLong(document.get(Field.GROUP_ID))
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
		).addLinkedModel(
			"author", PersonIdentifier.class,
			document -> GetterUtil.getLong(document.get(Field.USER_ID))
		).addString(
			"description",
			document -> GetterUtil.getString(
				document.get(Field.DESCRIPTION), "-")
		).addStringList(
			"skus", document -> Arrays.asList(document.getValues("skus"))
		).addString(
			"title", document -> document.get(Field.TITLE)
		).build();
	}

	private Document _addCPDefinition(
			Long webSiteId, ProductCreatorForm productCreatorForm)
		throws PortalException {

		try {
			CPDefinition cpDefinition =
				_productDefinitionHelper.createCPDefinition(
					webSiteId, productCreatorForm.getTitleMap(),
					productCreatorForm.getDescriptionMap(),
					productCreatorForm.getProductTypeName(),
					ArrayUtil.toLongArray(
						productCreatorForm.getAssetCategoryIds()));

			Indexer<CPDefinition> indexer = _productDefinitionHelper.getIndexer(
				CPDefinition.class);

			return indexer.getDocument(cpDefinition);
		}
		catch (CPDefinitionProductTypeNameException cpdptne) {
			throw new NotFoundException(
				"Product type not available: " +
					productCreatorForm.getProductTypeName(),
				cpdptne);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Document _getCPDefinition(Long cpDefinitionId) {
		try {
			ServiceContext serviceContext =
				_productDefinitionHelper.getServiceContext();

			SearchContext searchContext =
				_productDefinitionHelper.buildSearchContext(
					String.valueOf(cpDefinitionId), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null, serviceContext);

			Indexer<CPDefinition> indexer = _productDefinitionHelper.getIndexer(
				CPDefinition.class);

			Hits hits = indexer.search(searchContext);

			if (hits.getLength() == 0) {
				throw new NotFoundException(
					"Unable to find product with ID " + cpDefinitionId);
			}
			else if (hits.getLength() > 1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"More than one document found for product with ID " +
							cpDefinitionId);
				}

				CPDefinition cpDefinition =
					_cpDefinitionService.getCPDefinition(cpDefinitionId);

				return indexer.getDocument(cpDefinition);
			}

			List<Document> documents = hits.toList();

			return documents.get(0);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Document> _getPageItems(
			Pagination pagination, Long webSiteId)
		throws PortalException {

		try {
			ServiceContext serviceContext =
				_productDefinitionHelper.getServiceContext(
					webSiteId, new long[0]);

			SearchContext searchContext =
				_productDefinitionHelper.buildSearchContext(
					null, pagination.getStartPosition(),
					pagination.getEndPosition(), null, serviceContext);

			Indexer<CPDefinition> indexer = _productDefinitionHelper.getIndexer(
				CPDefinition.class);

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

	private static final Log _log = LogFactoryUtil.getLog(
		ProductNestedCollectionResource.class);

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private HasPermission _hasPermission;

	@Reference
	private ProductDefinitionHelper _productDefinitionHelper;

}