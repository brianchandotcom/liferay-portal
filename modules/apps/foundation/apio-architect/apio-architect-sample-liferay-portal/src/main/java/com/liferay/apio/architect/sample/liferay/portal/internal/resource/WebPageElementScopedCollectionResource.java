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
import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.resource.ScopedCollectionResource;
import com.liferay.apio.architect.routes.Routes;
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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.ws.rs.BadRequestException;
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
@Component(immediate = true, service = CollectionResource.class)
public class WebPageElementScopedCollectionResource
	implements ScopedCollectionResource<JournalArticle, LongIdentifier> {

	@Override
	public String getName() {
		return "web-page-elements";
	}

	@Override
	public Representor<JournalArticle, LongIdentifier> representor(
		Representor.Builder<JournalArticle, LongIdentifier> builder) {

		return builder.types(
			"WebPageElement"
		).identifier(
			journalArticle -> journalArticle::getId
		).addBidirectionalModel(
			"webSite", "webPageElements", WebSite.class,
			this::_getWebSiteOptional, WebSite::getWebSiteLongIdentifier
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

	@Override
	public Routes<JournalArticle> routes(
		Routes.Builder<JournalArticle, LongIdentifier> builder) {

		return builder.addCollectionPageGetter(
			this::_getPageItems, LongIdentifier.class
		).addCollectionPageItemCreator(
			this::_addJournalArticle, LongIdentifier.class
		).addCollectionPageItemGetter(
			this::_getJournalArticle
		).addCollectionPageItemRemover(
			this::_deleteJournalArticle
		).addCollectionPageItemUpdater(
			this::_updateJournalArticle
		).build();
	}

	private JournalArticle _addJournalArticle(
		LongIdentifier groupLongIdentifier, Map<String, Object> body) {

		String folderIdString = (String)body.get("folder");
		String title = (String)body.get("title");
		String description = (String)body.get("description");
		String content = (String)body.get("text");
		String ddmStructureKey = (String)body.get("structure");
		String ddmTemplateKey = (String)body.get("template");
		String displayDateString = (String)body.get("dateDisplayed");

		Supplier<BadRequestException> incorrectBodyExceptionSupplier =
			() -> new BadRequestException("Invalid body");

		if (Validator.isNull(folderIdString) || Validator.isNull(title) ||
			Validator.isNull(description) || Validator.isNull(content) ||
			Validator.isNull(ddmStructureKey) ||
			Validator.isNull(ddmTemplateKey) ||
			Validator.isNull(displayDateString)) {

			throw incorrectBodyExceptionSupplier.get();
		}

		Try<Long> folderIdLongTry = Try.fromFallible(
			() -> Long.valueOf(folderIdString));

		long folderId = folderIdLongTry.orElse(0L);

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(Locale.getDefault(), title);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(Locale.getDefault(), description);

		Calendar calendar = Calendar.getInstance();

		Try<DateFormat> dateFormatTry = Try.success(
			DateUtil.getISO8601Format());

		Date displayDate = dateFormatTry.map(
			dateFormat -> dateFormat.parse(displayDateString)
		).mapFailMatching(
			ParseException.class, incorrectBodyExceptionSupplier
		).getUnchecked();

		calendar.setTime(displayDate);

		int displayDateMonth = calendar.get(Calendar.MONTH);
		int displayDateDay = calendar.get(Calendar.DATE);
		int displayDateYear = calendar.get(Calendar.YEAR);
		int displayDateHour = calendar.get(Calendar.HOUR);
		int displayDateMinute = calendar.get(Calendar.MINUTE);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupLongIdentifier.getId());

		Try<JournalArticle> journalArticleTry = Try.fromFallible(() ->
			_journalArticleService.addArticle(
				groupLongIdentifier.getId(), folderId, 0, 0, null, true,
				titleMap, descriptionMap, content, ddmStructureKey,
				ddmTemplateKey, null, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute, 0, 0, 0, 0,
				0, true, 0, 0, 0, 0, 0, true, true, null, serviceContext));

		return journalArticleTry.getUnchecked();
	}

	private void _deleteJournalArticle(
		LongIdentifier journalArticleLongIdentifier) {

		try {
			JournalArticle article = _journalArticleService.getArticle(
				journalArticleLongIdentifier.getId());

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

	private JournalArticle _getJournalArticle(
		LongIdentifier journalArticleLongIdentifier) {

		try {
			return _journalArticleService.getArticle(
				journalArticleLongIdentifier.getId());
		}
		catch (NoSuchArticleException nsae) {
			throw new NotFoundException(
				"Unable to get article " +
					journalArticleLongIdentifier.getId(),
				nsae);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<JournalArticle> _getPageItems(
		Pagination pagination, LongIdentifier groupLongIdentifier) {

		List<JournalArticle> journalArticles =
			_journalArticleService.getArticles(
				groupLongIdentifier.getId(), 0, pagination.getStartPosition(),
				pagination.getEndPosition(), null);
		int count = _journalArticleService.getArticlesCount(
			groupLongIdentifier.getId(), 0);

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
		LongIdentifier journalArticleLongIdentifier, Map<String, Object> body) {

		String userIdString = (String)body.get("user");
		String groupIdString = (String)body.get("group");
		String folderIdString = (String)body.get("folder");
		String versionString = (String)body.get("version");
		String title = (String)body.get("title");
		String description = (String)body.get("description");
		String content = (String)body.get("text");

		Supplier<BadRequestException> incorrectBodyExceptionSupplier =
			() -> new BadRequestException("Invalid body");

		if (Validator.isNull(userIdString) || Validator.isNull(groupIdString) ||
			Validator.isNull(folderIdString) ||
			Validator.isNull(versionString) || Validator.isNull(title) ||
			Validator.isNull(description) || Validator.isNull(content)) {

			throw incorrectBodyExceptionSupplier.get();
		}

		Try<Long> userIdLongTry = Try.fromFallible(
			() -> Long.valueOf(userIdString));

		long userId = userIdLongTry.orElseThrow(incorrectBodyExceptionSupplier);

		Try<Long> groupIdLongTry = Try.fromFallible(
			() -> Long.valueOf(groupIdString));

		long groupId = groupIdLongTry.orElseThrow(
			incorrectBodyExceptionSupplier);

		Try<Long> folderIdLongTry = Try.fromFallible(
			() -> Long.valueOf(folderIdString));

		long folderId = folderIdLongTry.orElse(0L);

		Try<Double> versionDoubleTry = Try.fromFallible(
			() -> Double.valueOf(versionString));

		double version = versionDoubleTry.orElseThrow(
			incorrectBodyExceptionSupplier);

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(Locale.getDefault(), title);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(Locale.getDefault(), description);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		Try<JournalArticle> journalArticleTry = Try.fromFallible(() ->
			_journalArticleService.updateArticle(
				userId, groupId, folderId,
				String.valueOf(journalArticleLongIdentifier.getId()), version,
				titleMap, descriptionMap, content, null, serviceContext));

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