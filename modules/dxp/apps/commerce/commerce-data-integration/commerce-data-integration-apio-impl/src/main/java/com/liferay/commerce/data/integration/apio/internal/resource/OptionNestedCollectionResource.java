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
import com.liferay.commerce.data.integration.apio.identifiers.OptionIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.OptionForm;
import com.liferay.commerce.data.integration.apio.internal.security.permission.OptionPermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.OptionHelper;
import com.liferay.commerce.data.integration.apio.internal.util.ProductDefinitionHelper;
import com.liferay.commerce.data.integration.apio.internal.util.ProductIndexerHelper;
import com.liferay.commerce.product.exception.CPOptionKeyException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.search.CPOptionIndexer;
import com.liferay.commerce.product.service.CPOptionService;
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
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import java.util.Collections;
import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class OptionNestedCollectionResource
	implements
		NestedCollectionResource<Document, Long, OptionIdentifier, Long,
				WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<Document, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Document, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCPOption,
			_optionPermissionChecker.forAdding()::apply,
			OptionForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "options";
	}

	@Override
	public ItemRoutes<Document, Long> itemRoutes(
		ItemRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getOption
		).addRemover(
			idempotent(_cpOptionService::deleteCPOption),
			_optionPermissionChecker.forDeleting()::apply
		).addUpdater(
			this::_updateCPOption,
			_optionPermissionChecker.forUpdating()::apply,
			OptionForm::buildForm
		).build();
	}

	@Override
	public Representor<Document> representor(
		Representor.Builder<Document, Long> builder) {

		return builder.types(
			"Option"
		).identifier(
			document -> GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))
		).addBidirectionalModel(
			"webSite", "options", WebSiteIdentifier.class,
			document -> GetterUtil.getLong(document.get(Field.GROUP_ID))
		).addString(
			"name", document -> document.get(Field.NAME)
		).addString(
			"fieldType",
			document ->
				document.get(CPOptionIndexer.FIELD_DDM_FORM_FIELD_TYPE_NAME)
		).addString(
			"key", document -> document.get(CPOptionIndexer.FIELD_KEY)
		).build();
	}

	private Document _addCPOption(Long webSiteId, OptionForm optionForm) {
		try {
			CPOption cpOption = _optionHelper.createCPOption(
				webSiteId, optionForm.getNameMap(),
				optionForm.getDescriptionMap(), optionForm.getFieldType(),
				optionForm.getKey());

			Indexer<CPOption> indexer = _productIndexerHelper.getIndexer(
				CPOption.class);

			return indexer.getDocument(cpOption);
		}
		catch (CPOptionKeyException cpoke) {
			throw new BadRequestException(
				String.format(
					"CPOption key '%s' already been defined",
					optionForm.getKey()),
				cpoke);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Document _getOption(Long cpOptionId) {
		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext();

			SearchContext searchContext = _optionHelper.buildSearchContext(
				String.valueOf(cpOptionId), String.valueOf(cpOptionId),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null, serviceContext);

			Indexer<CPOption> indexer = _productIndexerHelper.getIndexer(
				CPOption.class);

			Hits hits = indexer.search(searchContext);

			if (hits.getLength() == 0) {
				throw new NotFoundException(
					"Unable to find option with ID " + cpOptionId);
			}

			if (hits.getLength() > 1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"More than one option found with ID " + cpOptionId);
				}

				CPOption cpOption = _cpOptionService.getCPOption(cpOptionId);

				return indexer.getDocument(cpOption);
			}

			List<Document> documents = hits.toList();

			return documents.get(0);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Document> _getPageItems(
		Pagination pagination, Long webSiteId) {

		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext(webSiteId, new long[0]);

			SearchContext searchContext = _optionHelper.buildSearchContext(
				null, null, pagination.getStartPosition(),
				pagination.getEndPosition(), null, serviceContext);

			Indexer<CPOption> indexer = _productIndexerHelper.getIndexer(
				CPOption.class);

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

	private Document _updateCPOption(Long cpOptionId, OptionForm optionForm) {
		try {
			CPOption cpOption = _optionHelper.updateCPOption(
				cpOptionId, optionForm.getNameMap(),
				optionForm.getDescriptionMap(), optionForm.getFieldType(),
				optionForm.getKey());

			Indexer<CPOption> indexer = _productIndexerHelper.getIndexer(
				CPOption.class);

			return indexer.getDocument(cpOption);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OptionNestedCollectionResource.class);

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private OptionPermissionChecker _optionPermissionChecker;

	@Reference
	private OptionHelper _optionHelper;

	@Reference
	private ProductDefinitionHelper _productDefinitionHelper;

	@Reference
	private ProductIndexerHelper _productIndexerHelper;

}