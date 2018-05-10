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

package com.liferay.web.page.element.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.apio.identifier.WebSiteIdentifier;
import com.liferay.web.page.element.apio.identifier.WebPageElementIdentifier;
import com.liferay.web.page.element.apio.internal.form.WebPageElementCreatorForm;
import com.liferay.web.page.element.apio.internal.form.WebPageElementUpdaterForm;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/WebPageElement">WebPageElement </a> resources through
 * a web API. The resources are mapped from the internal model {@code
 * JournalArticle}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class WebPageElementNestedCollectionResource
	implements
		NestedCollectionResource<JournalArticle, Long,
			WebPageElementIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<JournalArticle, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<JournalArticle, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addJournalArticle,
			_hasPermission::forAddingRootJournalArticle,
			WebPageElementCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "web-page-elements";
	}

	@Override
	public ItemRoutes<JournalArticle, Long> itemRoutes(
		ItemRoutes.Builder<JournalArticle, Long> builder) {

		return builder.addGetter(
			_journalArticleService::getArticle
		).addRemover(
			idempotent(this::_deleteJournalArticle),
			_hasPermission.forDeleting(JournalArticle.class)
		).addUpdater(
			this::_updateJournalArticle,
			_hasPermission.forUpdating(JournalArticle.class),
			WebPageElementUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<JournalArticle> representor(
		Representor.Builder<JournalArticle, Long> builder) {

		return builder.types(
			"WebPageElement"
		).identifier(
			JournalArticle::getFolderId
		).addBidirectionalModel(
			"webSite", "webPageElements", WebSiteIdentifier.class,
			JournalArticle::getGroupId
		).addDate(
			"dateCreated", JournalArticle::getCreateDate
		).addDate(
			"dateModified", JournalArticle::getModifiedDate
		).addDate(
			"datePublished", JournalArticle::getLastPublishDate
		).addDate(
			"lastReviewed", JournalArticle::getReviewDate
		).addLinkedModel(
			"author", PersonIdentifier.class, JournalArticle::getUserId
		).addLinkedModel(
			"creator", PersonIdentifier.class, JournalArticle::getUserId
		).addString(
			"description", JournalArticle::getDescription
		).addString(
			"text", JournalArticle::getContent
		).addString(
			"title", JournalArticle::getTitle
		).build();
	}

	private JournalArticle _addJournalArticle(
			Long webSiteId, WebPageElementCreatorForm webPageElementCreatorForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(webSiteId);

		return _journalArticleService.addArticle(
			webSiteId, 0, 0, 0, null, true,
			webPageElementCreatorForm.getTitleMap(),
			webPageElementCreatorForm.getDescriptionMap(),
			webPageElementCreatorForm.getText(),
			webPageElementCreatorForm.getStructure(),
			webPageElementCreatorForm.getTemplate(), null,
			webPageElementCreatorForm.getDisplayDateMonth(),
			webPageElementCreatorForm.getDisplayDateDay(),
			webPageElementCreatorForm.getDisplayDateYear(),
			webPageElementCreatorForm.getDisplayDateHour(),
			webPageElementCreatorForm.getDisplayDateMinute(), 0, 0, 0, 0, 0,
			true, 0, 0, 0, 0, 0, true, true, null, serviceContext);
	}

	private void _deleteJournalArticle(Long journalArticleId)
		throws PortalException {

		JournalArticle article = _journalArticleService.getArticle(
			journalArticleId);

		_journalArticleService.deleteArticle(
			article.getGroupId(), article.getArticleId(),
			article.getArticleResourceUuid(), new ServiceContext());
	}

	private PageItems<JournalArticle> _getPageItems(
		Pagination pagination, Long webSiteId) {

		List<JournalArticle> journalArticles =
			_journalArticleService.getArticles(
				webSiteId, 0, pagination.getStartPosition(),
				pagination.getEndPosition(), null);
		int count = _journalArticleService.getArticlesCount(webSiteId, 0);

		return new PageItems<>(journalArticles, count);
	}

	private JournalArticle _updateJournalArticle(
			Long journalArticleId,
			WebPageElementUpdaterForm webPageElementUpdaterForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(webPageElementUpdaterForm.getGroup());

		return _journalArticleService.updateArticle(
			webPageElementUpdaterForm.getUser(),
			webPageElementUpdaterForm.getGroup(), 0,
			String.valueOf(journalArticleId),
			webPageElementUpdaterForm.getVersion(),
			webPageElementUpdaterForm.getTitleMap(),
			webPageElementUpdaterForm.getDescriptionMap(),
			webPageElementUpdaterForm.getText(), null, serviceContext);
	}

	@Reference
	private HasPermission _hasPermission;

	@Reference
	private JournalArticleService _journalArticleService;

}