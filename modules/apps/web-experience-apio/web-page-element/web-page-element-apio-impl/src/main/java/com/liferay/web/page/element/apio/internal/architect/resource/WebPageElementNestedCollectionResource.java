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

package com.liferay.web.page.element.apio.internal.architect.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleModel;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.architect.context.auth.MockPermissions;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import com.liferay.web.page.element.apio.architect.identifier.JournalArticleId;
import com.liferay.web.page.element.apio.internal.architect.form.WebPageElementCreatorForm;
import com.liferay.web.page.element.apio.internal.architect.form.WebPageElementUpdaterForm;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

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
			JournalArticleId, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<JournalArticle, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<JournalArticle, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addJournalArticle, MockPermissions::validPermission,
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
			this::_getJournalArticle
		).addRemover(
			this::_deleteJournalArticle, MockPermissions::validPermission
		).addUpdater(
			this::_updateJournalArticle, MockPermissions::validPermission,
			WebPageElementUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<JournalArticle, Long> representor(
		Representor.Builder<JournalArticle, Long> builder) {

		return builder.types(
			"WebPageElement"
		).identifier(
			JournalArticle::getFolderId
		).addBidirectionalModel(
			"webSite", "webPageElements", WebSiteIdentifier.class,
			JournalArticleModel::getGroupId
		).addDate(
			"dateCreated", JournalArticle::getCreateDate
		).addDate(
			"dateModified", JournalArticle::getModifiedDate
		).addDate(
			"datePublished", JournalArticle::getLastPublishDate
		).addDate(
			"lastReviewed", JournalArticle::getReviewDate
		).addLinkedModel(
			"author", PersonIdentifier.class, this::_getUserOptional
		).addLinkedModel(
			"creator", PersonIdentifier.class, this::_getUserOptional
		).addString(
			"description", JournalArticle::getDescription
		).addString(
			"text", JournalArticle::getContent
		).addString(
			"title", JournalArticle::getTitle
		).build();
	}

	private JournalArticle _addJournalArticle(
		Long webSiteId, WebPageElementCreatorForm webPageElementCreatorForm) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(webSiteId);

		Try<JournalArticle> journalArticleTry = Try.fromFallible(() ->
			_journalArticleService.addArticle(
				webSiteId, webPageElementCreatorForm.getFolder(), 0, 0, null,
				true, webPageElementCreatorForm.getTitleMap(),
				webPageElementCreatorForm.getDescriptionMap(),
				webPageElementCreatorForm.getText(),
				webPageElementCreatorForm.getStructure(),
				webPageElementCreatorForm.getTemplate(), null,
				webPageElementCreatorForm.getDisplayDateMonth(),
				webPageElementCreatorForm.getDisplayDateDay(),
				webPageElementCreatorForm.getDisplayDateYear(),
				webPageElementCreatorForm.getDisplayDateHour(),
				webPageElementCreatorForm.getDisplayDateMinute(), 0, 0, 0, 0, 0,
				true, 0, 0, 0, 0, 0, true, true, null, serviceContext));

		return journalArticleTry.getUnchecked();
	}

	private void _deleteJournalArticle(Long journalArticleId) {
		try {
			JournalArticle article = _journalArticleService.getArticle(
				journalArticleId);

			_journalArticleService.deleteArticle(
				article.getGroupId(), article.getArticleId(),
				article.getArticleResourceUuid(), new ServiceContext());
		}
		catch (NoSuchArticleException nsae) {
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private JournalArticle _getJournalArticle(Long journalArticleId) {
		try {
			return _journalArticleService.getArticle(journalArticleId);
		}
		catch (NoSuchArticleException nsae) {
			throw new NotFoundException(
				"Unable to get article " + journalArticleId, nsae);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
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

	private Long _getUserOptional(JournalArticle journalArticle) {
		return journalArticle.getUserId();
	}

	private JournalArticle _updateJournalArticle(
		Long journalArticleId,
		WebPageElementUpdaterForm webPageElementUpdaterForm) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(webPageElementUpdaterForm.getGroup());

		Try<JournalArticle> journalArticleTry = Try.fromFallible(() ->
			_journalArticleService.updateArticle(
				webPageElementUpdaterForm.getUser(),
				webPageElementUpdaterForm.getGroup(),
				webPageElementUpdaterForm.getFolder(),
				String.valueOf(journalArticleId),
				webPageElementUpdaterForm.getVersion(),
				webPageElementUpdaterForm.getTitleMap(),
				webPageElementUpdaterForm.getDescriptionMap(),
				webPageElementUpdaterForm.getText(), null, serviceContext));

		return journalArticleTry.getUnchecked();
	}

	@Reference
	private JournalArticleService _journalArticleService;

}