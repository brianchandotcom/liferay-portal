/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.social;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

/**
 * @author Roberto Diaz
 */
public class JournalActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		int activityType = activity.getType();

		JournalArticle article =
			JournalArticleLocalServiceUtil.getJournalArticle(
				activity.getClassPK());

		if ((activityType == JournalActivityKeys.ADD_JOURNAL_ARTICLE) &&
			!JournalPermission.contains(
				permissionChecker, activity.getGroupId(),
				ActionKeys.ADD_ARTICLE)) {

			return null;
		}
		else if ((activityType == JournalActivityKeys.UPDATE_JOURNAL_ARTICLE) &&
			!JournalArticlePermission.contains(
				permissionChecker, article, ActionKeys.UPDATE)) {

			return null;
		}

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), themeDisplay);
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);

		// Link

		String link = null;

		JournalArticle lastestArticle =
			JournalArticleLocalServiceUtil.getLatestArticle(
				article.getGroupId(), article.getArticleId());

		if (Validator.isNotNull(lastestArticle.getLayoutUuid()) &&
			!article.isInTrash()) {

			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				themeDisplay.getScopeGroup(), false, themeDisplay);

			link =
				groupFriendlyURL.concat(
					JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
						lastestArticle.getUrlTitle());
		}

		// Title

		String titlePattern = null;

		if (activityType == JournalActivityKeys.ADD_JOURNAL_ARTICLE) {
			if (Validator.isNull(groupName)) {
				titlePattern = "activity-journal-add-article";
			}
			else {
				titlePattern = "activity-journal-add-article-in";
			}
		}
		else if (activityType == JournalActivityKeys.UPDATE_JOURNAL_ARTICLE) {
			if (Validator.isNull(groupName)) {
				titlePattern = "activity-journal-update-article";
			}
			else {
				titlePattern = "activity-journal-update-article-in";
			}
		}

		String eventTitle = getValue(
			activity.getExtraData(), "title", article.getTitle());

		Object[] titleArguments = null;

		if (Validator.isNotNull(link)) {
			titleArguments = new Object[] {
				groupName, creatorUserName, wrapLink(link, eventTitle)
			};
		}
		else {
			titleArguments = new Object[] {
				groupName, creatorUserName, eventTitle,
			};
		}

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		String body = StringPool.BLANK;

		double articleVersion = article.getVersion();

		if (articleVersion > 1) {
			body = LanguageUtil.format(
				themeDisplay.getLocale(),"created-new-journal-version",
				articleVersion);
		}

		return new SocialActivityFeedEntry(link, title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		JournalArticle.class.getName()
	};

}