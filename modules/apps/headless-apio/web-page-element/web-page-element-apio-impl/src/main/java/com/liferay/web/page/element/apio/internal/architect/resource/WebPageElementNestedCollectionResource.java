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

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalContent;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import com.liferay.web.page.element.apio.architect.identifier.WebPageElementIdentifier;
import com.liferay.web.page.element.apio.internal.architect.form.WebPageElementCreatorForm;
import com.liferay.web.page.element.apio.internal.architect.form.WebPageElementUpdaterForm;
import com.liferay.web.page.element.apio.internal.model.JournalArticleWrapper;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		NestedCollectionResource<JournalArticleWrapper, Long,
			WebPageElementIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<JournalArticleWrapper, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<JournalArticleWrapper, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems, ThemeDisplay.class
		).addCreator(
			this::_addJournalArticle, ThemeDisplay.class,
			_hasPermission.forAddingIn(WebSiteIdentifier.class),
			WebPageElementCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "web-page-element";
	}

	@Override
	public ItemRoutes<JournalArticleWrapper, Long> itemRoutes(
		ItemRoutes.Builder<JournalArticleWrapper, Long> builder) {

		return builder.addGetter(
			this::_getJournalArticleWrapper, ThemeDisplay.class
		).addRemover(
			idempotent(this::_deleteJournalArticle), _hasPermission::forDeleting
		).addUpdater(
			this::_updateJournalArticle, ThemeDisplay.class,
			_hasPermission::forUpdating, WebPageElementUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<JournalArticleWrapper> representor(
		Representor.Builder<JournalArticleWrapper, Long> builder) {

		return builder.types(
			"WebPageElement"
		).identifier(
			JournalArticle::getId
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
		).addLocalizedStringByLocale(
			"renderedContent", this::_getJournalArticleHtml
		).addString(
			"description", JournalArticle::getDescription
		).addString(
			"text", JournalArticle::getContent
		).addString(
			"title", JournalArticle::getTitle
		).build();
	}

	private JournalArticleWrapper _addJournalArticle(
			long webSiteId, WebPageElementCreatorForm webPageElementCreatorForm,
			ThemeDisplay themeDisplay)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(webSiteId);

		JournalArticle journalArticle = _journalArticleService.addArticle(
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

		return new JournalArticleWrapper(journalArticle, themeDisplay);
	}

	private void _deleteJournalArticle(long journalArticleId)
		throws PortalException {

		JournalArticle article = _journalArticleService.getArticle(
			journalArticleId);

		_journalArticleService.deleteArticle(
			article.getGroupId(), article.getArticleId(),
			article.getArticleResourceUuid(), new ServiceContext());
	}

	private String _getJournalArticleHtml(
		JournalArticleWrapper journalArticleWrapper, Locale locale) {

		JournalArticleDisplay display = _journalContent.getDisplay(
			journalArticleWrapper.getGroupId(),
			journalArticleWrapper.getArticleId(), Constants.VIEW,
			locale.getLanguage(), journalArticleWrapper.getThemeDisplay());

		return display.getContent();
	}

	private JournalArticleWrapper _getJournalArticleWrapper(
			long articleId, ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticle article = _journalArticleService.getArticle(articleId);

		return new JournalArticleWrapper(article, themeDisplay);
	}

	private PageItems<JournalArticleWrapper> _getPageItems(
		Pagination pagination, long webSiteId, ThemeDisplay themeDisplay) {

		List<JournalArticleWrapper> journalArticleWrappers = Stream.of(
			_journalArticleService.getArticles(
				webSiteId, 0, pagination.getStartPosition(),
				pagination.getEndPosition(), null)
		).flatMap(
			List::stream
		).map(
			journalArticle -> new JournalArticleWrapper(
				journalArticle, themeDisplay)
		).collect(
			Collectors.toList()
		);
		int count = _journalArticleService.getArticlesCount(webSiteId, 0);

		return new PageItems<>(journalArticleWrappers, count);
	}

	private JournalArticleWrapper _updateJournalArticle(
			long journalArticleId,
			WebPageElementUpdaterForm webPageElementUpdaterForm,
			ThemeDisplay themeDisplay)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(webPageElementUpdaterForm.getGroup());

		JournalArticle article = _journalArticleService.updateArticle(
			webPageElementUpdaterForm.getUser(),
			webPageElementUpdaterForm.getGroup(), 0,
			String.valueOf(journalArticleId),
			webPageElementUpdaterForm.getVersion(),
			webPageElementUpdaterForm.getTitleMap(),
			webPageElementUpdaterForm.getDescriptionMap(),
			webPageElementUpdaterForm.getText(), null, serviceContext);

		return new JournalArticleWrapper(article, themeDisplay);
	}

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalContent _journalContent;

}