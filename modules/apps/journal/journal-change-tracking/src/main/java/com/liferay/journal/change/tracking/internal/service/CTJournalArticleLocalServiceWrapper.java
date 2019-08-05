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

package com.liferay.journal.change.tracking.internal.service;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.engine.exception.CTEngineException;
import com.liferay.change.tracking.engine.exception.CTEntryCTEngineException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleLocalServiceWrapper;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTJournalArticleLocalServiceWrapper
	extends JournalArticleLocalServiceWrapper {

	public CTJournalArticleLocalServiceWrapper() {
		super(null);
	}

	public CTJournalArticleLocalServiceWrapper(
		JournalArticleLocalService journalArticleLocalService) {

		super(journalArticleLocalService);
	}

	@Override
	public JournalArticle addArticle(
			long userId, long groupId, long folderId, long classNameId,
			long classPK, String articleId, boolean autoArticleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap,
			Map<Locale, String> friendlyURLMap, String content,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.addArticle(
				userId, groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, version, titleMap, descriptionMap,
				friendlyURLMap, content, ddmStructureKey, ddmTemplateKey,
				layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, reviewDateMonth,
				reviewDateDay, reviewDateYear, reviewDateHour, reviewDateMinute,
				neverReview, indexable, smallImage, smallImageURL,
				smallImageFile, images, articleURL, serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_ADDITION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle addArticle(
			long userId, long groupId, long folderId, long classNameId,
			long classPK, String articleId, boolean autoArticleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.addArticle(
				userId, groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, version, titleMap, descriptionMap, content,
				ddmStructureKey, ddmTemplateKey, layoutUuid, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
				reviewDateHour, reviewDateMinute, neverReview, indexable,
				smallImage, smallImageURL, smallImageFile, images, articleURL,
				serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_ADDITION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle addArticle(
			long userId, long groupId, long folderId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.addArticle(
				userId, groupId, folderId, titleMap, descriptionMap, content,
				ddmStructureKey, ddmTemplateKey, serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_ADDITION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle checkArticleResourcePrimKey(
			long groupId, String articleId, double version)
		throws PortalException {

		JournalArticle journalArticle = super.checkArticleResourcePrimKey(
			groupId, articleId, version);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public void checkNewLine(long groupId, String articleId, double version)
		throws PortalException {

		super.checkNewLine(groupId, articleId, version);

		JournalArticle journalArticle = super.fetchArticle(
			groupId, articleId, version);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);
	}

	@Override
	public JournalArticle copyArticle(
			long userId, long groupId, String oldArticleId, String newArticleId,
			boolean autoArticleId, double version)
		throws PortalException {

		JournalArticle journalArticle = super.copyArticle(
			userId, groupId, oldArticleId, newArticleId, autoArticleId,
			version);

		_registerChange(journalArticle, CTConstants.CT_CHANGE_TYPE_ADDITION);

		return journalArticle;
	}

	@Override
	public JournalArticle deleteArticle(JournalArticle article)
		throws PortalException {

		JournalArticle journalArticle = super.deleteArticle(article);

		_unregisterChange(journalArticle);

		return journalArticle;
	}

	@Override
	public JournalArticle deleteArticle(
			JournalArticle article, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.deleteArticle(
			article, articleURL, serviceContext);

		_unregisterChange(journalArticle);

		return journalArticle;
	}

	@Override
	public JournalArticle deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.deleteArticle(
			groupId, articleId, version, articleURL, serviceContext);

		_unregisterChange(journalArticle);

		return journalArticle;
	}

	@Override
	public JournalArticle fetchArticle(long id) {
		JournalArticle journalArticle = super.fetchArticle(id);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return null;
	}

	@Override
	public JournalArticle fetchArticle(long groupId, String articleId) {
		JournalArticle journalArticle = super.fetchArticle(groupId, articleId);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return null;
	}

	@Override
	public JournalArticle fetchArticle(
		long groupId, String articleId, double version) {

		JournalArticle journalArticle = super.fetchArticle(
			groupId, articleId, version);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return null;
	}

	@Override
	public JournalArticle fetchArticleByUrlTitle(
		long groupId, String urlTitle) {

		JournalArticle journalArticle = super.fetchArticleByUrlTitle(
			groupId, urlTitle);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return null;
	}

	@Override
	public JournalArticle fetchDisplayArticle(long groupId, String articleId) {
		JournalArticle journalArticle = super.fetchDisplayArticle(
			groupId, articleId);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return null;
	}

	@Override
	public JournalArticle fetchJournalArticle(long id) {
		JournalArticle journalArticle = super.fetchJournalArticle(id);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return null;
	}

	@Override
	public JournalArticle fetchLatestArticle(long resourcePrimKey) {
		JournalArticle journalArticle = super.fetchLatestArticle(
			resourcePrimKey);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return fetchLatestArticle(
			resourcePrimKey, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public JournalArticle fetchLatestArticle(long resourcePrimKey, int status) {
		JournalArticle journalArticle = super.fetchLatestArticle(
			resourcePrimKey, status);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return fetchLatestArticle(resourcePrimKey, status, true);
	}

	@Override
	public JournalArticle fetchLatestArticle(
		long resourcePrimKey, int status, boolean preferApproved) {

		JournalArticle journalArticle = super.fetchLatestArticle(
			resourcePrimKey, status, preferApproved);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		journalArticle = null;

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		DynamicQuery withStatusDynamicQuery =
			_getChangeTrackingAwareDynamicQuery();

		Property resourcePrimKeyProperty = PropertyFactoryUtil.forName(
			"resourcePrimKey");

		withStatusDynamicQuery.add(resourcePrimKeyProperty.eq(resourcePrimKey));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		if (status == WorkflowConstants.STATUS_ANY) {
			if (preferApproved) {
				withStatusDynamicQuery.add(
					statusProperty.eq(WorkflowConstants.STATUS_APPROVED));

				List<JournalArticle> journalArticles = dynamicQuery(
					withStatusDynamicQuery, 0, 1, orderByComparator);

				if (!journalArticles.isEmpty()) {
					journalArticle = journalArticles.get(0);
				}
			}

			if (journalArticle == null) {
				DynamicQuery withoutStatusDynamicQuery =
					_getChangeTrackingAwareDynamicQuery();

				withoutStatusDynamicQuery.add(
					resourcePrimKeyProperty.eq(resourcePrimKey));

				List<JournalArticle> journalArticles = dynamicQuery(
					withoutStatusDynamicQuery, 0, 1, orderByComparator);

				if (!journalArticles.isEmpty()) {
					journalArticle = journalArticles.get(0);
				}
			}
		}
		else {
			withStatusDynamicQuery.add(statusProperty.eq(status));

			List<JournalArticle> journalArticles = dynamicQuery(
				withStatusDynamicQuery, 0, 1, orderByComparator);

			if (!journalArticles.isEmpty()) {
				journalArticle = journalArticles.get(0);
			}
		}

		return journalArticle;
	}

	@Override
	public JournalArticle fetchLatestArticle(
		long resourcePrimKey, int[] statuses) {

		JournalArticle journalArticle = super.fetchLatestArticle(
			resourcePrimKey, statuses);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		DynamicQuery dynamicQuery = _getChangeTrackingAwareDynamicQuery();

		Property resourcePrimKeyProperty = PropertyFactoryUtil.forName(
			"resourcePrimKey");

		dynamicQuery.add(resourcePrimKeyProperty.eq(resourcePrimKey));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.in(statuses));

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		List<JournalArticle> journalArticles = dynamicQuery(
			dynamicQuery, 0, 1, orderByComparator);

		if (!journalArticles.isEmpty()) {
			return journalArticles.get(0);
		}

		return null;
	}

	@Override
	public JournalArticle fetchLatestArticle(
		long groupId, String articleId, int status) {

		JournalArticle journalArticle = super.fetchLatestArticle(
			groupId, articleId, status);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		DynamicQuery dynamicQuery = _getChangeTrackingAwareDynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		Property articleIdProperty = PropertyFactoryUtil.forName("articleId");

		dynamicQuery.add(articleIdProperty.eq(articleId));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		if (status == WorkflowConstants.STATUS_ANY) {
			dynamicQuery.add(
				statusProperty.ne(WorkflowConstants.STATUS_IN_TRASH));

			List<JournalArticle> journalArticles = dynamicQuery(
				dynamicQuery, 0, 1, orderByComparator);

			if (!journalArticles.isEmpty()) {
				return journalArticles.get(0);
			}
		}

		dynamicQuery.add(statusProperty.eq(status));

		List<JournalArticle> journalArticles = dynamicQuery(
			dynamicQuery, 0, 1, orderByComparator);

		if (!journalArticles.isEmpty()) {
			return journalArticles.get(0);
		}

		return null;
	}

	@Override
	public JournalArticle getArticle(long id) throws PortalException {
		JournalArticle journalArticle = super.getArticle(id);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION + "id=" + id);
	}

	@Override
	public JournalArticle getArticle(long groupId, String articleId)
		throws PortalException {

		JournalArticle journalArticle = super.getArticle(groupId, articleId);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION);
		sb.append("groupId=");
		sb.append(groupId);
		sb.append(", articleId=");
		sb.append(articleId);

		throw new NoSuchArticleException(sb.toString());
	}

	@Override
	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException {

		JournalArticle journalArticle = super.getArticle(
			groupId, articleId, version);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(7);

		sb.append(_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION);
		sb.append("groupId=");
		sb.append(groupId);
		sb.append(", articleId=");
		sb.append(articleId);
		sb.append(", version=");
		sb.append(version);

		throw new NoSuchArticleException(sb.toString());
	}

	@Override
	public JournalArticle getArticle(
			long groupId, String className, long classPK)
		throws PortalException {

		JournalArticle journalArticle = super.getArticle(
			groupId, className, classPK);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(7);

		sb.append(_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION);
		sb.append("groupId=");
		sb.append(groupId);
		sb.append(", className=");
		sb.append(className);
		sb.append(", classPK=");
		sb.append(classPK);

		throw new NoSuchArticleException(sb.toString());
	}

	@Override
	public JournalArticle getArticleByUrlTitle(long groupId, String urlTitle)
		throws PortalException {

		JournalArticle journalArticle = super.getArticleByUrlTitle(
			groupId, urlTitle);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION);
		sb.append("groupId=");
		sb.append(groupId);
		sb.append(", urlTitle=");
		sb.append(urlTitle);

		throw new NoSuchArticleException(sb.toString());
	}

	@Override
	public List<JournalArticle> getArticles() {
		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles());

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticles(long groupId) {
		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles(groupId));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticles(long groupId, int start, int end) {
		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles(groupId, start, end));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticles(
		long groupId, int start, int end,
		OrderByComparator<JournalArticle> obc) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles(groupId, start, end, obc));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticles(long groupId, long folderId) {
		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles(groupId, folderId));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticles(
		long groupId, long folderId, int start, int end) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles(groupId, folderId, start, end));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticles(
		long groupId, long folderId, int status, int start, int end) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles(groupId, folderId, status, start, end));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticles(
		long groupId, long folderId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles(
				groupId, folderId, start, end, orderByComparator));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticles(long groupId, String articleId) {
		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles(groupId, articleId));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticles(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticles(
				groupId, articleId, start, end, orderByComparator));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticlesByResourcePrimKey(
		long resourcePrimKey) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticlesByResourcePrimKey(resourcePrimKey));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticlesBySmallImageId(long smallImageId) {
		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticlesBySmallImageId(smallImageId));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, long classNameId, String ddmStructureKey, int status,
		int start, int end, OrderByComparator<JournalArticle> obc) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticlesByStructureId(
				groupId, classNameId, ddmStructureKey, status, start, end,
				obc));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, String ddmStructureKey, int status, int start, int end,
		OrderByComparator<JournalArticle> obc) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticlesByStructureId(
				groupId, ddmStructureKey, status, start, end, obc));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, String ddmStructureKey, int start, int end,
		OrderByComparator<JournalArticle> obc) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getArticlesByStructureId(
				groupId, ddmStructureKey, start, end, obc));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public int getArticlesCount(long groupId) {
		super.getArticlesCount(groupId);

		List<JournalArticle> journalArticles = getArticles(groupId);

		return journalArticles.size();
	}

	@Override
	public int getArticlesCount(long groupId, long folderId) {
		super.getArticlesCount(groupId, folderId);

		List<JournalArticle> journalArticles = getArticles(groupId, folderId);

		return journalArticles.size();
	}

	@Override
	public int getArticlesCount(long groupId, long folderId, int status) {
		super.getArticlesCount(groupId, folderId, status);

		List<JournalArticle> journalArticles = getArticles(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return journalArticles.size();
	}

	@Override
	public int getArticlesCount(long groupId, String articleId) {
		super.getArticlesCount(groupId, articleId);

		List<JournalArticle> journalArticles = getArticles(groupId, articleId);

		return journalArticles.size();
	}

	@Override
	public List<JournalArticle> getCompanyArticles(
		long companyId, double version, int status, int start, int end) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getCompanyArticles(companyId, version, status, start, end));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getCompanyArticles(
		long companyId, int status, int start, int end) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getCompanyArticles(companyId, status, start, end));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public int getCompanyArticlesCount(
		long companyId, double version, int status, int start, int end) {

		super.getCompanyArticlesCount(companyId, version, status, start, end);

		List<JournalArticle> journalArticles = getCompanyArticles(
			companyId, version, status, start, end);

		return journalArticles.size();
	}

	@Override
	public int getCompanyArticlesCount(long companyId, int status) {
		super.getCompanyArticlesCount(companyId, status);

		List<JournalArticle> journalArticles = getCompanyArticles(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return journalArticles.size();
	}

	@Override
	public JournalArticle getDisplayArticle(long groupId, String articleId)
		throws PortalException {

		JournalArticle journalArticle = super.getDisplayArticle(
			groupId, articleId);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION);
		sb.append("groupId=");
		sb.append(groupId);
		sb.append(", articleId=");
		sb.append(articleId);

		throw new NoSuchArticleException(sb.toString());
	}

	@Override
	public JournalArticle getDisplayArticleByUrlTitle(
			long groupId, String urlTitle)
		throws PortalException {

		JournalArticle journalArticle = super.getDisplayArticleByUrlTitle(
			groupId, urlTitle);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION);
		sb.append("groupId=");
		sb.append(groupId);
		sb.append(", urlTitle=");
		sb.append(urlTitle);

		throw new NoSuchArticleException(sb.toString());
	}

	@Override
	public List<JournalArticle> getIndexableArticlesByDDMStructureKey(
		String[] ddmStructureKeys) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getIndexableArticlesByDDMStructureKey(ddmStructureKeys));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getIndexableArticlesByResourcePrimKey(
		long resourcePrimKey) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getIndexableArticlesByResourcePrimKey(resourcePrimKey));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public JournalArticle getLatestArticle(long resourcePrimKey)
		throws PortalException {

		JournalArticle journalArticle = super.getLatestArticle(resourcePrimKey);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return getLatestArticle(resourcePrimKey, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public JournalArticle getLatestArticle(long resourcePrimKey, int status)
		throws PortalException {

		JournalArticle journalArticle = super.getLatestArticle(
			resourcePrimKey, status);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return getLatestArticle(resourcePrimKey, status, true);
	}

	@Override
	public JournalArticle getLatestArticle(
			long resourcePrimKey, int status, boolean preferApproved)
		throws PortalException {

		JournalArticle journalArticle = super.getLatestArticle(
			resourcePrimKey, status, preferApproved);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		journalArticle = null;

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		DynamicQuery withStatusDynamicQuery =
			_getChangeTrackingAwareDynamicQuery();

		Property resourcePrimKeyProperty = PropertyFactoryUtil.forName(
			"resourcePrimKey");

		withStatusDynamicQuery.add(resourcePrimKeyProperty.eq(resourcePrimKey));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		if (status == WorkflowConstants.STATUS_ANY) {
			if (preferApproved) {
				withStatusDynamicQuery.add(
					statusProperty.eq(WorkflowConstants.STATUS_APPROVED));

				List<JournalArticle> journalArticles = dynamicQuery(
					withStatusDynamicQuery, 0, 1, orderByComparator);

				if (!journalArticles.isEmpty()) {
					journalArticle = journalArticles.get(0);
				}
			}

			if (journalArticle == null) {
				DynamicQuery withoutStatusDynamicQuery =
					_getChangeTrackingAwareDynamicQuery();

				withoutStatusDynamicQuery.add(
					resourcePrimKeyProperty.eq(resourcePrimKey));

				List<JournalArticle> journalArticles = dynamicQuery(
					withoutStatusDynamicQuery, 0, 1, orderByComparator);

				if (!journalArticles.isEmpty()) {
					journalArticle = journalArticles.get(0);
				}
			}
		}
		else {
			withStatusDynamicQuery.add(statusProperty.eq(status));

			List<JournalArticle> journalArticles = dynamicQuery(
				withStatusDynamicQuery, 0, 1, orderByComparator);

			if (!journalArticles.isEmpty()) {
				journalArticle = journalArticles.get(0);
			}
		}

		if (journalArticle == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION);
			sb.append("resourcePrimKey=");
			sb.append(resourcePrimKey);
			sb.append(", status=");
			sb.append(status);

			throw new NoSuchArticleException(sb.toString());
		}

		return journalArticle;
	}

	@Override
	public JournalArticle getLatestArticle(long groupId, String articleId)
		throws PortalException {

		JournalArticle journalArticle = super.getLatestArticle(
			groupId, articleId);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public JournalArticle getLatestArticle(
			long groupId, String articleId, int status)
		throws PortalException {

		JournalArticle journalArticle = super.getLatestArticle(
			groupId, articleId, status);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		return _getFirstArticle(
			groupId, articleId, status, new ArticleVersionComparator());
	}

	@Override
	public JournalArticle getLatestArticle(
			long groupId, String className, long classPK)
		throws PortalException {

		JournalArticle journalArticle = super.getLatestArticle(
			groupId, className, classPK);

		if (_isRetrievable(journalArticle)) {
			return journalArticle;
		}

		DynamicQuery dynamicQuery = _getChangeTrackingAwareDynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		long classNameId = _classNameLocalService.getClassNameId(className);

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		dynamicQuery.add(classNameIdProperty.eq(classNameId));

		Property classPKProperty = PropertyFactoryUtil.forName("classPK");

		dynamicQuery.add(classPKProperty.eq(classPK));

		List<JournalArticle> journalArticles = dynamicQuery(
			dynamicQuery, 0, 1, new ArticleVersionComparator());

		if (journalArticles.isEmpty()) {
			StringBundler sb = new StringBundler(7);

			sb.append(_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION);
			sb.append("groupId=");
			sb.append(groupId);
			sb.append(", className=");
			sb.append(className);
			sb.append(", classPK=");
			sb.append(classPK);

			throw new NoSuchArticleException(sb.toString());
		}

		return journalArticles.get(0);
	}

	@Override
	public List<JournalArticle> getNoAssetArticles() {
		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getNoAssetArticles());

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getNoPermissionArticles() {
		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getNoPermissionArticles());

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getStructureArticles(
		long groupId, String ddmStructureKey) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getStructureArticles(groupId, ddmStructureKey));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getStructureArticles(
		long groupId, String ddmStructureKey, int start, int end,
		OrderByComparator<JournalArticle> obc) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getStructureArticles(
				groupId, ddmStructureKey, start, end, obc));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getStructureArticles(
		String[] ddmStructureKeys) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getStructureArticles(ddmStructureKeys));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public int getStructureArticlesCount(long groupId, String ddmStructureKey) {
		super.getStructureArticlesCount(groupId, ddmStructureKey);

		List<JournalArticle> journalArticles = getStructureArticles(
			groupId, ddmStructureKey);

		return journalArticles.size();
	}

	@Override
	public List<JournalArticle> getTemplateArticles(
		long groupId, String ddmTemplateKey) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getTemplateArticles(groupId, ddmTemplateKey));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> getTemplateArticles(
		long groupId, String ddmTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> obc) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.getTemplateArticles(
				groupId, ddmTemplateKey, start, end, obc));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public int getTemplateArticlesCount(long groupId, String ddmTemplateKey) {
		super.getTemplateArticlesCount(groupId, ddmTemplateKey);

		List<JournalArticle> journalArticles = getTemplateArticles(
			groupId, ddmTemplateKey);

		return journalArticles.size();
	}

	@Override
	public JournalArticle moveArticle(
			long groupId, String articleId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.moveArticle(
			groupId, articleId, newFolderId, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle moveArticleFromTrash(
			long userId, long groupId, JournalArticle article, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.moveArticleFromTrash(
			userId, groupId, article, newFolderId, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle moveArticleToTrash(
			long userId, JournalArticle article)
		throws PortalException {

		JournalArticle journalArticle = super.moveArticleToTrash(
			userId, article);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_DELETION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle moveArticleToTrash(
			long userId, long groupId, String articleId)
		throws PortalException {

		JournalArticle journalArticle = super.moveArticleToTrash(
			userId, groupId, articleId);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_DELETION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle removeArticleLocale(
			long groupId, String articleId, double version, String languageId)
		throws PortalException {

		JournalArticle journalArticle = super.removeArticleLocale(
			groupId, articleId, version, languageId);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle restoreArticleFromTrash(
			long userId, JournalArticle article)
		throws PortalException {

		JournalArticle journalArticle = super.restoreArticleFromTrash(
			userId, article);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public List<JournalArticle> search(
		long groupId, List<Long> folderIds, Locale locale, int status,
		int start, int end) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.search(groupId, folderIds, locale, status, start, end));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> search(
		long groupId, long folderId, int status, int start, int end) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.search(groupId, folderId, status, start, end));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String keywords, Double version, String ddmStructureKey,
		String ddmTemplateKey, Date displayDateGT, Date displayDateLT,
		int status, Date reviewDate, int start, int end,
		OrderByComparator<JournalArticle> obc) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.search(
				companyId, groupId, folderIds, classNameId, keywords, version,
				ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
				status, reviewDate, start, end, obc));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String ddmStructureKey, String ddmTemplateKey,
		Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
		boolean andOperator, int start, int end,
		OrderByComparator<JournalArticle> obc) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.search(
				companyId, groupId, folderIds, classNameId, articleId, version,
				title, description, content, ddmStructureKey, ddmTemplateKey,
				displayDateGT, displayDateLT, status, reviewDate, andOperator,
				start, end, obc));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public List<JournalArticle> search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String[] ddmStructureKeys, String[] ddmTemplateKeys,
		Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
		boolean andOperator, int start, int end,
		OrderByComparator<JournalArticle> obc) {

		List<JournalArticle> journalArticles = new ArrayList<>(
			super.search(
				companyId, groupId, folderIds, classNameId, articleId, version,
				title, description, content, ddmStructureKeys, ddmTemplateKeys,
				displayDateGT, displayDateLT, status, reviewDate, andOperator,
				start, end, obc));

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return journalArticles;
	}

	@Override
	public int searchCount(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String keywords, Double version, String ddmStructureKey,
		String ddmTemplateKey, Date displayDateGT, Date displayDateLT,
		int status, Date reviewDate) {

		super.searchCount(
			companyId, groupId, folderIds, classNameId, keywords, version,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate);

		List<JournalArticle> journalArticles = search(
			companyId, groupId, folderIds, classNameId, keywords, version,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return journalArticles.size();
	}

	@Override
	public int searchCount(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String ddmStructureKey, String ddmTemplateKey,
		Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
		boolean andOperator) {

		super.searchCount(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);

		List<JournalArticle> journalArticles = search(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return journalArticles.size();
	}

	@Override
	public int searchCount(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String[] ddmStructureKeys, String[] ddmTemplateKeys,
		Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
		boolean andOperator) {

		super.searchCount(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, ddmStructureKeys, ddmTemplateKeys,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);

		List<JournalArticle> journalArticles = search(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, ddmStructureKeys, ddmTemplateKeys,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return journalArticles.size();
	}

	@Override
	public BaseModelSearchResult<JournalArticle> searchJournalArticles(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String ddmStructureKey, String ddmTemplateKey,
			String keywords, LinkedHashMap<String, Object> params, int start,
			int end, Sort sort)
		throws PortalException {

		BaseModelSearchResult<JournalArticle> baseModelSearchResult =
			super.searchJournalArticles(
				companyId, groupId, folderIds, classNameId, ddmStructureKey,
				ddmTemplateKey, keywords, params, start, end, sort);

		List<JournalArticle> journalArticles = new ArrayList<>(
			baseModelSearchResult.getBaseModels());

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return new BaseModelSearchResult<>(
			journalArticles, journalArticles.size());
	}

	@Override
	public BaseModelSearchResult<JournalArticle> searchJournalArticles(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, String title,
			String description, String content, int status,
			String ddmStructureKey, String ddmTemplateKey,
			LinkedHashMap<String, Object> params, boolean andSearch, int start,
			int end, Sort sort)
		throws PortalException {

		BaseModelSearchResult<JournalArticle> baseModelSearchResult =
			super.searchJournalArticles(
				companyId, groupId, folderIds, classNameId, articleId, title,
				description, content, status, ddmStructureKey, ddmTemplateKey,
				params, andSearch, start, end, sort);

		List<JournalArticle> journalArticles = new ArrayList<>(
			baseModelSearchResult.getBaseModels());

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return new BaseModelSearchResult<>(
			journalArticles, journalArticles.size());
	}

	@Override
	public BaseModelSearchResult<JournalArticle> searchJournalArticles(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws PortalException {

		BaseModelSearchResult<JournalArticle> baseModelSearchResult =
			super.searchJournalArticles(
				groupId, userId, creatorUserId, status, start, end);

		List<JournalArticle> journalArticles = new ArrayList<>(
			baseModelSearchResult.getBaseModels());

		journalArticles.removeIf(
			journalArticle -> !_isRetrievable(journalArticle));

		return new BaseModelSearchResult<>(
			journalArticles, journalArticles.size());
	}

	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap,
			Map<Locale, String> friendlyURLMap, String content,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.updateArticle(
				userId, groupId, folderId, articleId, version, titleMap,
				descriptionMap, friendlyURLMap, content, ddmStructureKey,
				ddmTemplateKey, layoutUuid, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, indexable, smallImage,
				smallImageURL, smallImageFile, images, articleURL,
				serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String layoutUuid, ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.updateArticle(
				userId, groupId, folderId, articleId, version, titleMap,
				descriptionMap, content, layoutUuid, serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.updateArticle(
				userId, groupId, folderId, articleId, version, titleMap,
				descriptionMap, content, ddmStructureKey, ddmTemplateKey,
				layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, reviewDateMonth,
				reviewDateDay, reviewDateYear, reviewDateHour, reviewDateMinute,
				neverReview, indexable, smallImage, smallImageURL,
				smallImageFile, images, articleURL, serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, String content, ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.updateArticle(
				userId, groupId, folderId, articleId, version, content,
				serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticle(long id, String urlTitle)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.updateArticle(id, urlTitle));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticleTranslation(
			long groupId, String articleId, double version, Locale locale,
			String title, String description, String content,
			Map<String, byte[]> images, ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateArticleTranslation(
			groupId, articleId, version, locale, title, description, content,
			images, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateContent(
			long groupId, String articleId, double version, String content)
		throws PortalException {

		JournalArticle journalArticle = super.updateContent(
			groupId, articleId, version, content);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateStatus(
			long userId, JournalArticle article, int status, String articleURL,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateStatus(
			userId, article, status, articleURL, serviceContext,
			workflowContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateStatus(
			long userId, long classPK, int status,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateStatus(
			userId, classPK, status, workflowContext, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateStatus(
			long userId, long groupId, String articleId, double version,
			int status, String articleURL,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateStatus(
			userId, groupId, articleId, version, status, articleURL,
			workflowContext, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		// Needed for synchronization

	}

	private DynamicQuery _getChangeTrackingAwareDynamicQuery() {
		Optional<CTCollection> activeCTCollectionOptional =
			_ctManager.getActiveCTCollectionOptional(
				CompanyThreadLocal.getCompanyId(),
				PrincipalThreadLocal.getUserId());

		if (!activeCTCollectionOptional.isPresent()) {
			return dynamicQuery();
		}

		CTCollection activeCTCollection = activeCTCollectionOptional.get();

		List<CTEntry> ctEntries = new ArrayList<>(
			_ctManager.getCTCollectionCTEntries(
				activeCTCollection.getCompanyId(),
				activeCTCollection.getCtCollectionId(),
				_portal.getClassNameId(JournalArticle.class.getName())));

		Stream<CTEntry> ctEntryStream = ctEntries.stream();

		List<Long> classPKs = ctEntryStream.map(
			CTEntry::getModelClassPK
		).collect(
			Collectors.toList()
		);

		DynamicQuery journalArticleDynamicQuery = dynamicQuery();

		if (ListUtil.isNotEmpty(classPKs)) {
			Property idProperty = PropertyFactoryUtil.forName("id");

			journalArticleDynamicQuery.add(idProperty.in(classPKs));
		}

		return journalArticleDynamicQuery;
	}

	private JournalArticle _getFirstArticle(
			long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws PortalException {

		DynamicQuery dynamicQuery = _getChangeTrackingAwareDynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		Property articleIdProperty = PropertyFactoryUtil.forName("articleId");

		dynamicQuery.add(articleIdProperty.eq(articleId));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		if (status == WorkflowConstants.STATUS_ANY) {
			dynamicQuery.add(
				statusProperty.ne(WorkflowConstants.STATUS_IN_TRASH));
		}
		else {
			dynamicQuery.add(statusProperty.eq(status));
		}

		List<JournalArticle> journalArticles = dynamicQuery(
			dynamicQuery, 0, 1, orderByComparator);

		if (journalArticles.isEmpty()) {
			StringBundler sb = new StringBundler(7);

			sb.append(_NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION);
			sb.append("groupId=");
			sb.append(groupId);
			sb.append("articleId=");
			sb.append(articleId);
			sb.append(", status=");
			sb.append(status);

			throw new NoSuchArticleException(sb.toString());
		}

		return journalArticles.get(0);
	}

	private boolean _isRetrievable(JournalArticle journalArticle) {
		if (journalArticle == null) {
			return false;
		}

		if (_ctManager.isModelUpdateInProgress()) {
			return true;
		}

		return _ctManager.isRetrievableVersion(
			journalArticle.getCompanyId(), PrincipalThreadLocal.getUserId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle.getId());
	}

	private void _registerChange(JournalArticle journalArticle, int changeType)
		throws CTEngineException {

		_registerChange(journalArticle, changeType, false);
	}

	private void _registerChange(
			JournalArticle journalArticle, int changeType, boolean force)
		throws CTEngineException {

		if (journalArticle == null) {
			return;
		}

		try {
			_ctManager.registerModelChange(
				journalArticle.getCompanyId(), PrincipalThreadLocal.getUserId(),
				_portal.getClassNameId(JournalArticle.class.getName()),
				journalArticle.getId(), journalArticle.getResourcePrimKey(),
				changeType, force);
		}
		catch (CTEngineException ctee) {
			if (ctee instanceof CTEntryCTEngineException) {
				if (_log.isWarnEnabled()) {
					_log.warn(ctee.getMessage());
				}
			}
			else {
				throw ctee;
			}
		}
	}

	private void _unregisterChange(JournalArticle journalArticle) {
		if (journalArticle == null) {
			return;
		}

		_ctManager.unregisterModelChange(
			journalArticle.getCompanyId(), PrincipalThreadLocal.getUserId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle.getId());
	}

	private static final String _NO_SUCH_ARTICLE_IN_CURRENT_CHANGE_COLLECTION =
		"No JournalArticle exists in the current change collection or in " +
			"production with the following parameters: ";

	private static final Log _log = LogFactoryUtil.getLog(
		CTJournalArticleLocalServiceWrapper.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTManager _ctManager;

	@Reference
	private Portal _portal;

}