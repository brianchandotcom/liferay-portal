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

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.ProductDefinitionIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.ProductOptionIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.ProductOptionForm;
import com.liferay.commerce.data.integration.apio.internal.util.ProductIndexerHelper;
import com.liferay.commerce.data.integration.apio.internal.util.ProductOptionHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.search.CPDefinitionOptionRelIndexer;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import java.util.Collections;
import java.util.List;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class ProductOptionNestedCollectionResource
	implements
		NestedCollectionResource<Document, Long,
			ProductOptionIdentifier, Long, ProductDefinitionIdentifier> {

	@Override
	public NestedCollectionRoutes<Document, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Document, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCPDefinitionOptionRel,
			_hasPermission.forAddingIn(ProductOptionIdentifier.class),
			ProductOptionForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "product-options";
	}

	@Override
	public ItemRoutes<Document, Long> itemRoutes(
		ItemRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getCPDefinitionOptionRel
		).addRemover(
			idempotent(
				_cpDefinitionOptionRelService::
					deleteCPDefinitionOptionRel),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<Document> representor(
		Representor.Builder<Document, Long> builder) {

		return builder.types(
			"ProductOption"
		).identifier(
			document -> GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))
		).addBidirectionalModel(
			"product", "productOptions", ProductDefinitionIdentifier.class,
			document -> GetterUtil.getLong(
				document.get(
					CPDefinitionOptionRelIndexer.FIELD_CP_DEFINITION_ID))
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
		).addString(
			"description", document -> document.get(Field.DESCRIPTION)
		).build();
	}

	private Document _addCPDefinitionOptionRel(
		Long cpDefinitionId, ProductOptionForm productOptionForm) {

		try {
			User user = _userLocalService.getUserById(
				PrincipalThreadLocal.getUserId());
			CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
				cpDefinitionId);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setCompanyId(user.getCompanyId());
			serviceContext.setTimeZone(user.getTimeZone());
			serviceContext.setUserId(user.getUserId());
			serviceContext.setScopeGroupId(cpDefinition.getGroupId());

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelService.addCPDefinitionOptionRel(
					cpDefinitionId, productOptionForm.getOptionId(),
					serviceContext);

			Indexer<CPDefinitionOptionRel> indexer =
				_productIndexerHelper.getIndexer(CPDefinitionOptionRel.class);

			return indexer.getDocument(cpDefinitionOptionRel);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Document _getCPDefinitionOptionRel(Long cpDefinitionOptionRelId) {
		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext();

			SearchContext searchContext =
				ProductOptionHelper.buildSearchContext(
					String.valueOf(cpDefinitionOptionRelId), null,
					String.valueOf(cpDefinitionOptionRelId), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null, serviceContext);

			Indexer<CPDefinitionOptionRel> indexer =
				_productIndexerHelper.getIndexer(CPDefinitionOptionRel.class);

			Hits hits = indexer.search(searchContext);

			if (hits.getLength() == 0) {
				throw new NotFoundException(
					"Unable to find product option with ID " +
						cpDefinitionOptionRelId);
			}

			if (hits.getLength() > 1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"More than one document found for product option " +
							"with ID " + cpDefinitionOptionRelId);
				}

				CPDefinitionOptionRel cpDefinitionOptionRel =
					_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
						cpDefinitionOptionRelId);

				return indexer.getDocument(cpDefinitionOptionRel);
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
				ProductOptionHelper.buildSearchContext(
					null, String.valueOf(cpDefinitionId), null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null, serviceContext);

			Indexer<CPDefinitionOptionRel> indexer =
				_productIndexerHelper.getIndexer(CPDefinitionOptionRel.class);

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
		ProductOptionNestedCollectionResource.class);

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ProductIndexerHelper _productIndexerHelper;

	@Reference
	private UserLocalService _userLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinitionOptionRel)"
	)
	private HasPermission<Long> _hasPermission;


}