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

package com.liferay.product.apio.internal.architect.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionModel;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleModel;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.architect.context.auth.MockPermissions;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.product.apio.architect.identifier.CPDefinitionId;
import com.liferay.product.apio.internal.architect.form.ProductCreatorForm;
import com.liferay.product.apio.internal.architect.form.ProductUpdaterForm;
import com.liferay.product.apio.internal.util.ProductResourceCollectionUtil;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

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
			CPDefinitionId, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<CPDefinition, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CPDefinition, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
/*		).addCreator(
			this::_addJournalArticle, MockPermissions::validPermission,
			ProductCreatorForm::buildForm*/
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
/*		).addUpdater(
			this::_updateJournalArticle, MockPermissions::validPermission,
			ProductUpdaterForm::buildForm*/
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

/*	private CPDefinition _addJournalArticle(
		Long webSiteId, ProductCreatorForm productCreatorForm) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(webSiteId);

		Try<CPDefinition> journalArticleTry = Try.fromFallible(() ->
			_journalArticleService.addArticle(
				webSiteId, productCreatorForm.getFolder(), 0, 0, null,
				true, productCreatorForm.getTitleMap(),
				productCreatorForm.getDescriptionMap(),
				productCreatorForm.getText(),
				productCreatorForm.getStructure(),
				productCreatorForm.getTemplate(), null,
				productCreatorForm.getDisplayDateMonth(),
				productCreatorForm.getDisplayDateDay(),
				productCreatorForm.getDisplayDateYear(),
				productCreatorForm.getDisplayDateHour(),
				productCreatorForm.getDisplayDateMinute(), 0, 0, 0, 0, 0,
				true, 0, 0, 0, 0, 0, true, true, null, serviceContext));

		return journalArticleTry.getUnchecked();
	}*/

	private void _deleteCPDefinition(Long cpDefinitionId) {
		try {
			_cpDefinitionService.deleteCPDefinition(cpDefinitionId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
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
	private JournalArticleService _journalArticleService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ProductResourceCollectionUtil _productResourceCollectionUtil;

}