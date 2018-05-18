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

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.headless.product.apio.identifier.ProductOptionIdentifier;
import com.liferay.commerce.headless.product.apio.identifier.ProductOptionValueIdentifier;
import com.liferay.commerce.headless.product.apio.internal.util.ProductIndexerHelper;
import com.liferay.commerce.headless.product.apio.internal.util.ProductOptionValueHelper;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.search.CPDefinitionOptionValueRelIndexer;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
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

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class ProductOptionValueNestedCollectionResource
	implements
		NestedCollectionResource<Document, Long,
			ProductOptionValueIdentifier, Long, ProductOptionIdentifier> {

	@Override
	public NestedCollectionRoutes<Document, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "product-option-values";
	}

	@Override
	public ItemRoutes<Document, Long> itemRoutes(
		ItemRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getCPDefinitionOptionValueRel
		).build();
	}

	@Override
	public Representor<Document, Long> representor(
		Representor.Builder<Document, Long> builder) {

		return builder.types(
			"ProductOptionValue"
		).identifier(
			document -> GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))
		).addBidirectionalModel(
			"productOption", "productOptionValues",
			ProductOptionIdentifier.class,
			document -> GetterUtil.getLong(
				document.get(
					CPDefinitionOptionValueRelIndexer.
						FIELD_CP_DEFINITION_OPTION_REL_ID))
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
			"name", document -> document.get(Field.TITLE)
		).build();
	}

	private Document _getCPDefinitionOptionValueRel(
		Long cpDefinitionOptionValueRelId) {

		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext();

			SearchContext searchContext =
				ProductOptionValueHelper.buildSearchContext(
					String.valueOf(cpDefinitionOptionValueRelId), null,
					String.valueOf(cpDefinitionOptionValueRelId),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null, serviceContext);

			Indexer<CPDefinitionOptionValueRel> indexer =
				_productIndexerHelper.getIndexer(
					CPDefinitionOptionValueRel.class);

			Hits hits = indexer.search(searchContext);

			if (hits.getLength() == 0) {
				throw new NotFoundException(
					"Unable to find product option value with ID " +
						cpDefinitionOptionValueRelId);
			}

			if (hits.getLength() > 1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"More than one document found for product option " +
							"value with ID " + cpDefinitionOptionValueRelId);
				}

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelService.
						getCPDefinitionOptionValueRel(
							cpDefinitionOptionValueRelId);

				return indexer.getDocument(cpDefinitionOptionValueRel);
			}

			List<Document> documents = hits.toList();

			return documents.get(0);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Document> _getPageItems(
		Pagination pagination, Long cpDefinitionOptionRelId) {

		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext();

			SearchContext searchContext =
				ProductOptionValueHelper.buildSearchContext(
					null, String.valueOf(cpDefinitionOptionRelId), null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null, serviceContext);

			Indexer<CPDefinitionOptionValueRel> indexer =
				_productIndexerHelper.getIndexer(
					CPDefinitionOptionValueRel.class);

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
		ProductOptionValueNestedCollectionResource.class);

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private ProductIndexerHelper _productIndexerHelper;

}