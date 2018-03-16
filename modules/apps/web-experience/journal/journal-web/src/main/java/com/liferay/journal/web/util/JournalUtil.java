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

package com.liferay.journal.web.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.configuration.JournalGroupServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.diff.DiffVersion;
import com.liferay.portal.kernel.diff.DiffVersionsInfo;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.subscription.service.SubscriptionLocalServiceUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

/**
 * @author Tom Wang
 */
public class JournalUtil {

	public static final int MAX_STACK_SIZE = 20;

	public static void addRecentArticle(
		PortletRequest portletRequest, JournalArticle article) {

		if (article != null) {
			Stack<JournalArticle> stack = _getRecentArticles(portletRequest);

			stack.push(article);
		}
	}

	public static DiffVersionsInfo getDiffVersionsInfo(
		long groupId, String articleId, double sourceVersion,
		double targetVersion) {

		double previousVersion = 0;
		double nextVersion = 0;

		List<JournalArticle> articles =
			JournalArticleServiceUtil.getArticlesByArticleId(
				groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new ArticleVersionComparator(true));

		for (JournalArticle article : articles) {
			if ((article.getVersion() < sourceVersion) &&
				(article.getVersion() > previousVersion)) {

				previousVersion = article.getVersion();
			}

			if ((article.getVersion() > targetVersion) &&
				((article.getVersion() < nextVersion) || (nextVersion == 0))) {

				nextVersion = article.getVersion();
			}
		}

		List<DiffVersion> diffVersions = new ArrayList<>();

		for (JournalArticle article : articles) {
			DiffVersion diffVersion = new DiffVersion(
				article.getUserId(), article.getVersion(),
				article.getModifiedDate());

			diffVersions.add(diffVersion);
		}

		return new DiffVersionsInfo(diffVersions, nextVersion, previousVersion);
	}

	public static boolean getEmailArticleAnyEventEnabled(
		JournalGroupServiceConfiguration journalGroupServiceConfiguration) {

		if (journalGroupServiceConfiguration.emailArticleAddedEnabled() ||
			journalGroupServiceConfiguration.
				emailArticleApprovalDeniedEnabled() ||
			journalGroupServiceConfiguration.
				emailArticleApprovalGrantedEnabled() ||
			journalGroupServiceConfiguration.
				emailArticleApprovalRequestedEnabled() ||
			journalGroupServiceConfiguration.emailArticleReviewEnabled() ||
			journalGroupServiceConfiguration.emailArticleUpdatedEnabled()) {

			return true;
		}

		return false;
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		return getEmailDefinitionTerms(
			portletRequest, emailFromAddress, emailFromName, StringPool.BLANK);
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName, String emailType) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String fromAddress = HtmlUtil.escape(emailFromAddress);
		String fromName = HtmlUtil.escape(emailFromName);
		String toAddress = LanguageUtil.get(
			themeDisplay.getLocale(), "the-address-of-the-email-recipient");
		String toName = LanguageUtil.get(
			themeDisplay.getLocale(), "the-name-of-the-email-recipient");

		if (emailType.equals("requested")) {
			toName = fromName;
			toAddress = fromAddress;

			fromName = LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-sender");
			fromAddress = LanguageUtil.get(
				themeDisplay.getLocale(), "the-address-of-the-email-sender");
		}

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$ARTICLE_CONTENT]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-web-content"));
		definitionTerms.put(
			"[$ARTICLE_DIFFS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-web-content-compared-with-the-previous-version-web-" +
					"content"));
		definitionTerms.put(
			"[$ARTICLE_ID$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-web-content-id"));
		definitionTerms.put(
			"[$ARTICLE_TITLE$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-web-content-title"));
		definitionTerms.put(
			"[$ARTICLE_URL$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-web-content-url"));
		definitionTerms.put(
			"[$ARTICLE_VERSION$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-web-content-version"));
		definitionTerms.put("[$FROM_ADDRESS$]", fromAddress);
		definitionTerms.put("[$FROM_NAME$]", fromName);

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_NAME$]", HtmlUtil.escape(portletDisplay.getTitle()));

		definitionTerms.put("[$TO_ADDRESS$]", toAddress);
		definitionTerms.put("[$TO_NAME$]", toName);

		return definitionTerms;
	}

	public static long getPreviewPlid(
			JournalArticle article, ThemeDisplay themeDisplay)
		throws Exception {

		if (article != null) {
			Layout layout = article.getLayout();

			if (layout != null) {
				return layout.getPlid();
			}
		}

		Layout layout = LayoutLocalServiceUtil.fetchFirstLayout(
			themeDisplay.getScopeGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (layout == null) {
			layout = LayoutLocalServiceUtil.fetchFirstLayout(
				themeDisplay.getScopeGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
		}

		if (layout != null) {
			return layout.getPlid();
		}

		return themeDisplay.getPlid();
	}

	public static boolean isSubscribedToArticle(
		long companyId, long groupId, long userId, long articleId) {

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId, JournalArticle.class.getName(), articleId);
	}

	public static boolean isSubscribedToFolder(
			long companyId, long groupId, long userId, long folderId)
		throws PortalException {

		return isSubscribedToFolder(companyId, groupId, userId, folderId, true);
	}

	public static boolean isSubscribedToFolder(
			long companyId, long groupId, long userId, long folderId,
			boolean recursive)
		throws PortalException {

		List<Long> ancestorFolderIds = new ArrayList<>();

		if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
				folderId);

			ancestorFolderIds.add(folderId);

			if (recursive) {
				ancestorFolderIds.addAll(folder.getAncestorFolderIds());

				ancestorFolderIds.add(groupId);
			}
		}
		else {
			ancestorFolderIds.add(groupId);
		}

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId, JournalFolder.class.getName(),
			ArrayUtil.toLongArray(ancestorFolderIds));
	}

	public static boolean isSubscribedToStructure(
		long companyId, long groupId, long userId, long ddmStructureId) {

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId, DDMStructure.class.getName(), ddmStructureId);
	}

	public static void removeRecentArticle(
		PortletRequest portletRequest, String articleId, double version) {

		Stack<JournalArticle> stack = _getRecentArticles(portletRequest);

		Iterator<JournalArticle> itr = stack.iterator();

		while (itr.hasNext()) {
			JournalArticle journalArticle = itr.next();

			String journalArticleId = journalArticle.getArticleId();

			if (journalArticleId.equals(articleId) &&
				((journalArticle.getVersion() == version) || (version == 0))) {

				itr.remove();
			}
		}
	}

	public static class FiniteUniqueStack<E> extends Stack<E> {

		@Override
		public E push(E item) {
			if (contains(item)) {
				if (!item.equals(peek())) {
					remove(item);
					super.push(item);
				}
			}
			else if (size() < _maxSize) {
				super.push(item);
			}

			return item;
		}

		private FiniteUniqueStack(int maxSize) {
			_maxSize = maxSize;
		}

		private final int _maxSize;

	}

	private static Stack<JournalArticle> _getRecentArticles(
		PortletRequest portletRequest) {

		PortletSession portletSession = portletRequest.getPortletSession();

		Stack<JournalArticle> recentArticles =
			(Stack<JournalArticle>)portletSession.getAttribute(
				WebKeys.JOURNAL_RECENT_ARTICLES);

		if (recentArticles == null) {
			recentArticles = new FiniteUniqueStack<>(MAX_STACK_SIZE);

			portletSession.setAttribute(
				WebKeys.JOURNAL_RECENT_ARTICLES, recentArticles);
		}

		return recentArticles;
	}

}