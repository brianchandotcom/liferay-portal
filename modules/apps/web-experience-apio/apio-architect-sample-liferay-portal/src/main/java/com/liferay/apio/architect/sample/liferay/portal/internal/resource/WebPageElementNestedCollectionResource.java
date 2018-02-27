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

package com.liferay.apio.architect.sample.liferay.portal.internal.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.liferay.portal.internal.form.WebPageElementCreatorForm;
import com.liferay.apio.architect.sample.liferay.portal.internal.form.WebPageElementUpdaterForm;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSite;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSiteService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;

import java.util.List;
import java.util.Optional;

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
	implements NestedCollectionResource <JournalArticle, Long, WebSite, Long> {

	@Override
	public NestedCollectionRoutes<JournalArticle> collectionRoutes(
		NestedCollectionRoutes.Builder<JournalArticle, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addJournalArticle, WebPageElementCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "web-page-elements";
	}

	@Override
	public ItemRoutes<JournalArticle> itemRoutes(
		ItemRoutes.Builder<JournalArticle, Long> builder) {

		return builder.addGetter(
			this::_getJournalArticle
		).addRemover(
			this::_deleteJournalArticle
		).addUpdater(
			this::_updateJournalArticle, WebPageElementUpdaterForm::buildForm
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
			"webSite", "webPageElements", WebSite.class,
			this::_getWebSiteOptional, WebSite::getWebSiteId
		).addDate(
			"dateCreated", JournalArticle::getCreateDate
		).addDate(
			"dateModified", JournalArticle::getModifiedDate
		).addDate(
			"datePublished", JournalArticle::getLastPublishDate
		).addDate(
			"lastReviewed", JournalArticle::getReviewDate
		).addLinkedModel(
			"author", User.class, this::_getUserOptional
		).addLinkedModel(
			"creator", User.class, this::_getUserOptional
		).addString(
			"description", JournalArticle::getDescription
		).addString(
			"text", JournalArticle::getContent
		).addString(
			"title", JournalArticle::getTitle
		).build();
	}

	private JournalArticle _addJournalArticle(
		Long groupId, WebPageElementCreatorForm webPageElementCreatorForm) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		Try<JournalArticle> journalArticleTry = Try.fromFallible(() ->
			_journalArticleService.addArticle(
				groupId, webPageElementCreatorForm.getFolder(), 0, 0, null,
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
		Pagination pagination, Long groupId) {

		List<JournalArticle> journalArticles =
			_journalArticleService.getArticles(
				groupId, 0, pagination.getStartPosition(),
				pagination.getEndPosition(), null);
		int count = _journalArticleService.getArticlesCount(groupId, 0);

		return new PageItems<>(journalArticles, count);
	}

	private Optional<User> _getUserOptional(JournalArticle journalArticle) {
		try {
			return Optional.ofNullable(
				_userService.getUserById(journalArticle.getUserId()));
		}
		catch (NoSuchUserException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get user " + journalArticle.getUserId(), e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Optional<WebSite> _getWebSiteOptional(
		JournalArticle journalArticle) {

		return _webSiteService.getWebSite(journalArticle.getGroupId());
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
	private DLFolderService _dlFolderService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private UserService _userService;

	@Reference
	private WebSiteService _webSiteService;

}