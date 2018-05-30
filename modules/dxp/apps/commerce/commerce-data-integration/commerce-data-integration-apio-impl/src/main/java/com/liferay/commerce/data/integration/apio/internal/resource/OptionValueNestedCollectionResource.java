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
import com.liferay.commerce.data.integration.apio.identifiers.OptionValueIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.OptionValueForm;
import com.liferay.commerce.data.integration.apio.internal.security.permission.OptionValuePermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.OptionValueHelper;
import com.liferay.commerce.data.integration.apio.internal.util.ProductIndexerHelper;
import com.liferay.commerce.product.exception.CPOptionValueKeyException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.search.CPOptionValueIndexer;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.commerce.product.service.CPOptionValueService;
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
public class OptionValueNestedCollectionResource
	implements
		NestedCollectionResource<Document, Long, OptionValueIdentifier, Long,
			OptionIdentifier> {

	@Override
	public NestedCollectionRoutes<Document, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Document, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCPOptionValue,
			_optionValuePermissionChecker.forAdding()::apply,
			OptionValueForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "option-values";
	}

	@Override
	public ItemRoutes<Document, Long> itemRoutes(
		ItemRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getOptionValue
		).addRemover(
			idempotent(_cpOptionValueService::deleteCPOptionValue),
			_optionValuePermissionChecker.forDeleting()::apply
		).addUpdater(
			this::_updateCPOptionValue,
			_optionValuePermissionChecker.forUpdating()::apply,
			OptionValueForm::buildForm
		).build();
	}

	@Override
	public Representor<Document> representor(
		Representor.Builder<Document, Long> builder) {

		return builder.types(
			"OptionValue"
		).identifier(
			document -> GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))
		).addBidirectionalModel(
			"option", "values", OptionIdentifier.class,
			document -> GetterUtil.getLong(
				document.get(CPOptionValueIndexer.FIELD_CP_OPTION_ID))
		).addString(
			"name", document -> document.get(Field.NAME)
		).addString(
			"key", document -> document.get(CPOptionValueIndexer.FIELD_KEY)
		).build();
	}

	private Document _addCPOptionValue(
		Long cpOptionId, OptionValueForm optionValueForm) {

		try {
			CPOptionValue cpOptionValue =
				_optionValueHelper.createCPOptionValue(
					cpOptionId, optionValueForm.getNameMap(),
					optionValueForm.getKey());

			Indexer<CPOptionValue> indexer = _productIndexerHelper.getIndexer(
				CPOptionValue.class);

			return indexer.getDocument(cpOptionValue);
		}
		catch (CPOptionValueKeyException cpovke) {
			throw new BadRequestException(
				String.format(
					"CPOptionValue key '%s' already been defined",
					optionValueForm.getKey()),
				cpovke);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Document _getOptionValue(Long cpOptionValueId) {
		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext();

			SearchContext searchContext = _optionValueHelper.buildSearchContext(
				String.valueOf(cpOptionValueId), null, null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null, serviceContext);

			Indexer<CPOptionValue> indexer = _productIndexerHelper.getIndexer(
				CPOptionValue.class);

			Hits hits = indexer.search(searchContext);

			if (hits.getLength() == 0) {
				throw new NotFoundException(
					"Unable to find option value with ID " + cpOptionValueId);
			}

			if (hits.getLength() > 1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"More than one option value found with ID " +
							cpOptionValueId);
				}

				CPOptionValue cpOptionValue =
					_cpOptionValueService.getCPOptionValue(cpOptionValueId);

				return indexer.getDocument(cpOptionValue);
			}

			List<Document> documents = hits.toList();

			return documents.get(0);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Document> _getPageItems(
		Pagination pagination, Long cpOptionId) {

		try {
			CPOption cpOption = _cpOptionService.getCPOption(cpOptionId);

			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext(
					cpOption.getGroupId(), new long[0]);

			SearchContext searchContext = _optionValueHelper.buildSearchContext(
				null, String.valueOf(cpOptionId), null,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null, serviceContext);

			Indexer<CPOptionValue> indexer = _productIndexerHelper.getIndexer(
				CPOptionValue.class);

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

	private Document _updateCPOptionValue(
		Long cpOptionValueId, OptionValueForm optionValueForm) {

		try {
			CPOptionValue cpOptionValue =
				_optionValueHelper.updateCPOptionValue(
					cpOptionValueId, optionValueForm.getNameMap(),
					optionValueForm.getKey());

			Indexer<CPOptionValue> indexer = _productIndexerHelper.getIndexer(
				CPOptionValue.class);

			return indexer.getDocument(cpOptionValue);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OptionValueNestedCollectionResource.class);

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private CPOptionValueService _cpOptionValueService;

	@Reference
	private OptionValueHelper _optionValueHelper;

	@Reference
	private OptionValuePermissionChecker _optionValuePermissionChecker;

	@Reference
	private ProductIndexerHelper _productIndexerHelper;

}