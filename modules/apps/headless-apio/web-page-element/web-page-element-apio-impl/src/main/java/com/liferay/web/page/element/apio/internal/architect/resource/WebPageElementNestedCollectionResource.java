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

import com.liferay.aggregate.rating.apio.architect.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetTagModel;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.comment.apio.architect.identifier.CommentIdentifier;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalContent;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
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
			WebPageElementIdentifier, Long, ContentSpaceIdentifier> {

	@Override
	public NestedCollectionRoutes<JournalArticleWrapper, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<JournalArticleWrapper, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems, ThemeDisplay.class
		).addCreator(
			this::_addJournalArticle, ThemeDisplay.class,
			_hasPermission.forAddingIn(ContentSpaceIdentifier.class),
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
			"contentSpace", "webPageElements", ContentSpaceIdentifier.class,
			JournalArticle::getGroupId
		).addDate(
			"dateCreated", JournalArticle::getCreateDate
		).addDate(
			"dateModified", JournalArticle::getModifiedDate
		).addDate(
			"datePublished", JournalArticle::getDisplayDate
		).addDate(
			"lastReviewed", JournalArticle::getReviewDate
		).addLinkedModel(
			"aggregateRating", AggregateRatingIdentifier.class,
			this::_createClassNameClassPK
		).addLinkedModel(
			"author", PersonIdentifier.class, JournalArticle::getUserId
		).addLinkedModel(
			"creator", PersonIdentifier.class, JournalArticle::getUserId
		).addLocalizedStringByLocale(
			"renderedContent", this::_getJournalArticleHtml
		).addRelatedCollection(
			"categories", CategoryIdentifier.class
		).addRelatedCollection(
			"comments", CommentIdentifier.class
		).addString(
			"description", JournalArticleWrapper::getDescription
		).addString(
			"text", JournalArticle::getContent
		).addString(
			"title", JournalArticle::getTitle
		).addStringList(
			"keywords", this::_getJournalArticleAssetTags
		).build();
	}

	private JournalArticleWrapper _addJournalArticle(
			long contentSpaceId,
			WebPageElementCreatorForm webPageElementCreatorForm,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Locale locale = themeDisplay.getLocale();

		ServiceContext serviceContext =
			webPageElementCreatorForm.getServiceContext(contentSpaceId);

		JournalArticle journalArticle = _journalArticleService.addArticle(
			contentSpaceId, 0, 0, 0, null, true,
			webPageElementCreatorForm.getTitleMap(locale),
			webPageElementCreatorForm.getDescriptionMap(locale),
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

	private ClassNameClassPK _createClassNameClassPK(
		JournalArticle journalArticle) {

		return ClassNameClassPK.create(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());
	}

	private void _deleteJournalArticle(long journalArticleId)
		throws PortalException {

		JournalArticle journalArticle = _journalArticleService.getArticle(
			journalArticleId);

		_journalArticleService.deleteArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getArticleResourceUuid(), new ServiceContext());
	}

	private List<String> _getJournalArticleAssetTags(
		JournalArticle journalArticle) {

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		return ListUtil.toList(assetTags, AssetTagModel::getName);
	}

	private String _getJournalArticleHtml(
		JournalArticleWrapper journalArticleWrapper, Locale locale) {

		JournalArticleDisplay journalArticleDisplay =
			_journalContent.getDisplay(
				journalArticleWrapper.getGroupId(),
				journalArticleWrapper.getArticleId(), null,
				locale.getLanguage(), journalArticleWrapper.getThemeDisplay());

		String content = journalArticleDisplay.getContent();

		if (content == null) {
			return null;
		}

		return content.replaceAll("[\\t\\n]", "");
	}

	private JournalArticleWrapper _getJournalArticleWrapper(
			long journalArticleId, ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticle journalArticle = _journalArticleService.getArticle(
			journalArticleId);

		return new JournalArticleWrapper(journalArticle, themeDisplay);
	}

	private PageItems<JournalArticleWrapper> _getPageItems(
		Pagination pagination, long contentSpaceId, ThemeDisplay themeDisplay) {

		List<JournalArticleWrapper> journalArticleWrappers = Stream.of(
			_journalArticleService.getArticles(
				contentSpaceId, 0, pagination.getStartPosition(),
				pagination.getEndPosition(), null)
		).flatMap(
			List::stream
		).map(
			journalArticle -> new JournalArticleWrapper(
				journalArticle, themeDisplay)
		).collect(
			Collectors.toList()
		);
		int count = _journalArticleService.getArticlesCount(contentSpaceId, 0);

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

		JournalArticle journalArticle = _journalArticleService.updateArticle(
			webPageElementUpdaterForm.getUser(),
			webPageElementUpdaterForm.getGroup(), 0,
			String.valueOf(journalArticleId),
			webPageElementUpdaterForm.getVersion(),
			webPageElementUpdaterForm.getTitleMap(),
			webPageElementUpdaterForm.getDescriptionMap(),
			webPageElementUpdaterForm.getText(), null, serviceContext);

		return new JournalArticleWrapper(journalArticle, themeDisplay);
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalContent _journalContent;

}