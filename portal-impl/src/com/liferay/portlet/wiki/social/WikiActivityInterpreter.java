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

package com.liferay.portlet.wiki.social;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
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

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getPageResource(
				activity.getClassPK());

		int activityType = activity.getType();

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
			activity.getExtraData());

		// Only those users who can edit the page should see the activities
		// related to non-approved versions

		if (activityType == WikiActivityKeys.UPDATE_PAGE) {
			double version = extraDataJSONObject.getDouble("version");

			WikiPage page = WikiPageLocalServiceUtil.getPage(
				pageResource.getNodeId(), pageResource.getTitle(), version);

			if (!page.isApproved() &&
				!WikiPagePermission.contains(
					permissionChecker, activity.getClassPK(),
					ActionKeys.UPDATE)) {

				return null;
			}
		}

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), themeDisplay);
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);

		// Link

		String link =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/wiki/find_page?pageResourcePrimKey=" + activity.getClassPK();

		// Title

		String titlePattern = null;

		if ((activityType == WikiActivityKeys.ADD_COMMENT) ||
			(activityType == SocialActivityConstants.TYPE_ADD_COMMENT)) {

			if (Validator.isNull(groupName)) {
				titlePattern = "activity-wiki-add-comment";
			}
			else {
				titlePattern = "activity-wiki-add-comment-in";
			}
		}
		else if (activityType == WikiActivityKeys.ADD_PAGE) {
			if (Validator.isNull(groupName)) {
				titlePattern = "activity-wiki-add-page";
			}
			else {
				titlePattern = "activity-wiki-add-page-in";
			}
		}
		else if (activityType == WikiActivityKeys.UPDATE_PAGE) {
			if (Validator.isNull(groupName)) {
				titlePattern = "activity-wiki-update-page";
			}
			else {
				titlePattern = "activity-wiki-update-page-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_ADD_ATTACHMENT) {
			if (Validator.isNull(groupName)) {
				titlePattern = "activity-wiki-add-attachment";
			}
			else {
				titlePattern = "activity-wiki-add-attachment-in";
			}
		}
		else if (
			activityType ==
				SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) {

			if (Validator.isNull(groupName)) {
				titlePattern = "activity-wiki-remove-attachment";
			}
			else {
				titlePattern = "activity-wiki-remove-attachment-in";
			}
		}
		else if (
			activityType ==
				SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				titlePattern = "activity-wiki-restore-attachment";
			}
			else {
				titlePattern = "activity-wiki-restore-attachment-in";
			}
		}

		// Atachment

		String attachmentTitle = null;

		if ((activityType == SocialActivityConstants.TYPE_ADD_ATTACHMENT) ||
			 (activityType ==
				 SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) ||
			 (activityType ==
				SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH)) {

			FileEntry fileEntry = null;
			FileVersion fileVersion = null;

			try {
				fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
					extraDataJSONObject.getLong("fileEntryId"));
			}
			catch (NoSuchModelException nsme) {
			}

			if (fileEntry != null) {
				fileVersion = fileEntry.getFileVersion();
			}

			String fileEntryTitle = extraDataJSONObject.getString("title");

			// Attachment Link

			if ((fileVersion != null) && !fileVersion.isInTrash()) {
				StringBundler sb = new StringBundler(9);

				sb.append(themeDisplay.getPathMain());
				sb.append("/wiki/get_page_attachment?p_l_id=");
				sb.append(themeDisplay.getPlid());
				sb.append("&nodeId=");
				sb.append(pageResource.getNodeId());
				sb.append("&title=");
				sb.append(HttpUtil.encodeURL(pageResource.getTitle()));
				sb.append("&fileName=");
				sb.append(fileEntryTitle);

				attachmentTitle = wrapLink(
					sb.toString(), HtmlUtil.escape(fileEntryTitle));
			}
			else {
				attachmentTitle = HtmlUtil.escape(fileEntryTitle);
			}
		}

		String pageTitle = wrapLink(
			link, HtmlUtil.escape(pageResource.getTitle()));

		Object[] titleArguments = new Object[] {
			groupName, creatorUserName, pageTitle, attachmentTitle
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