/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.social;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

/**
 * @author Samuel Kong
 * @author Ryan Park
 */
public class WikiActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!WikiPagePermission.contains(
				permissionChecker, activity.getClassPK(), ActionKeys.VIEW)) {

			return null;
		}

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), themeDisplay);
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);

		int activityType = activity.getType();

		// Link

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getPageResource(
				activity.getClassPK());

		String link =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/wiki/find_page?pageResourcePrimKey=" + activity.getClassPK();

		// Title

		String titlePattern = null;

		if ((activityType == WikiActivityKeys.ADD_COMMENT) ||
			(activityType == SocialActivityConstants.TYPE_ADD_COMMENT)) {

			titlePattern = "activity-wiki-add-comment";
		}
		else if (activityType == WikiActivityKeys.ADD_PAGE) {
			titlePattern = "activity-wiki-add-page";
		}
		else if (activityType == WikiActivityKeys.UPDATE_PAGE) {
			titlePattern = "activity-wiki-update-page";
		}

		if (Validator.isNotNull(groupName)) {
			titlePattern += "-in";
		}

		String pageTitle = wrapLink(
			link, HtmlUtil.escape(cleanContent(pageResource.getTitle())));

		Object[] titleArguments = new Object[] {
			groupName, creatorUserName, pageTitle
		};

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		String body = StringPool.BLANK;

		return new SocialActivityFeedEntry(link, title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		WikiPage.class.getName()
	};

}