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

package com.liferay.wiki.web.display.context.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.exception.NoSuchFileEntryException;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.social.WikiActivityKeys;

import java.util.ResourceBundle;

import javax.portlet.PortletURL;

/**
 * @author Adolfo Pérez
 */
public class WikiSocialActivityHelper {

	public WikiSocialActivityHelper(WikiRequestHelper wikiRequestHelper) {
		_wikiRequestHelper = wikiRequestHelper;
	}

	public String getSocialActivityActionJSP(
			SocialActivity socialActivity, JSONObject extraDataJSONObject)
		throws PortalException {

		int type = socialActivity.getType();

		long fileEntryId = extraDataJSONObject.getLong("fileEntryId");

		FileEntry fileEntry = fetchFileEntry(fileEntryId);

		if (((type == SocialActivityConstants.TYPE_ADD_ATTACHMENT) ||
			 (type == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) ||
			 (type ==
				 SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH)) &&
			(fileEntry != null)) {

			return "/wiki/page_activity_attachment_action.jsp";
		}
		else if ((type == SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) ||
				 (type == WikiActivityKeys.ADD_PAGE) ||
				 (type == WikiActivityKeys.UPDATE_PAGE)) {

			return "/wiki/page_activity_page_action.jsp";
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getSocialActivityDescription(
			WikiPage page, SocialActivity socialActivity,
			JSONObject extraDataJSONObject, ResourceBundle resourceBundle)
		throws PortalException {

		double version = extraDataJSONObject.getDouble("version");

		WikiPage socialActivityWikiPage = WikiPageLocalServiceUtil.fetchPage(
			page.getNodeId(), page.getTitle(), version);

		User socialActivityUser = UserLocalServiceUtil.fetchUser(
			socialActivity.getUserId());

		if (socialActivityUser == null) {
			socialActivityUser = UserLocalServiceUtil.getDefaultUser(
				socialActivity.getCompanyId());
		}

		String userName = HtmlUtil.escape(socialActivityUser.getFullName());

		if (Validator.isNull(userName)) {
			userName = "Liferay";
		}

		int type = socialActivity.getType();

		if ((type == SocialActivityConstants.TYPE_ADD_ATTACHMENT) ||
			(type == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) ||
			(type ==
				SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH)) {

			String label = "x-added-the-attachment-x";

			if (type == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) {
				label = "x-removed-the-attachment-x";
			}
			else if (type ==
						SocialActivityConstants.
							TYPE_RESTORE_ATTACHMENT_FROM_TRASH) {

				label = "x-restored-the-attachment-x";
			}

			String title = extraDataJSONObject.getString("fileEntryTitle");

			long fileEntryId = extraDataJSONObject.getLong("fileEntryId");

			String url = getDownloadURL(fileEntryId);

			String titleLink = getLink(title, url);

			return LanguageUtil.format(
				resourceBundle, label, new Object[] {userName, titleLink},
				false);
		}
		else if (type == SocialActivityConstants.TYPE_ADD_COMMENT) {
			String url = getPageURL(page) + "#wikiCommentsPanel";

			return LanguageUtil.format(
				resourceBundle, "x-added-a-comment",
				new Object[] {userName, url}, false);
		}
		else if ((type == SocialActivityConstants.TYPE_MOVE_TO_TRASH) ||
				 (type == SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) ||
				 (type == WikiActivityKeys.ADD_PAGE) ||
				 (type == WikiActivityKeys.UPDATE_PAGE)) {

			String pageURL = getPageURL(socialActivityWikiPage, version);

			if (type == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
				return LanguageUtil.format(
					resourceBundle, "activity-wiki-page-move-to-trash",
					new Object[] {StringPool.BLANK, userName, page.getTitle()},
					false);
			}
			else if (type == SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {
				String titleLink = getLink(page.getTitle(), pageURL);

				return LanguageUtil.format(
					resourceBundle, "activity-wiki-page-restore-from-trash",
					new Object[] {StringPool.BLANK, userName, titleLink},
					false);
			}
			else if (type == WikiActivityKeys.ADD_PAGE) {
				String titleLink = getLink(page.getTitle(), pageURL.toString());

				return LanguageUtil.format(
					resourceBundle, "x-added-the-page-x",
					new Object[] {userName, titleLink}, false);
			}
			else if (type == WikiActivityKeys.UPDATE_PAGE) {
				String title = String.valueOf(version);
				String url = pageURL;

				if ((socialActivityWikiPage != null) &&
					socialActivityWikiPage.isMinorEdit()) {

					title += String.format(
						" (%s)",
						LanguageUtil.get(resourceBundle, "minor-edit"));
				}

				String titleURL = getLink(title, url);

				return LanguageUtil.format(
					resourceBundle, "x-updated-the-page-to-version-x",
					new Object[] {userName, titleURL}, false);
			}
			else {
				return StringPool.BLANK;
			}
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getSocialActivityIcon(SocialActivity socialActivity) {
		int type = socialActivity.getType();

		if (type == SocialActivityConstants.TYPE_ADD_ATTACHMENT) {
			return "icon-paperclip";
		}
		else if (type ==
					SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) {

			return "icon-remove";
		}
		else if (type == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			return "icon-trash";
		}
		else if (type ==
					SocialActivityConstants.
						TYPE_RESTORE_ATTACHMENT_FROM_TRASH) {

			return "icon-undo";
		}
		else if (type == SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {
			return "icon-undo";
		}
		else if (type == WikiActivityKeys.ADD_PAGE) {
			return "icon-plus";
		}
		else if (type == WikiActivityKeys.UPDATE_PAGE) {
			return "icon-edit";
		}
		else {
			return StringPool.BLANK;
		}
	}

	public boolean isSocialActivitySupported(SocialActivity socialActivity) {
		int type = socialActivity.getType();

		if ((type == SocialActivityConstants.TYPE_ADD_ATTACHMENT) ||
			(type == SocialActivityConstants.TYPE_ADD_COMMENT) ||
			(type == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) ||
			(type == SocialActivityConstants.TYPE_MOVE_TO_TRASH) ||
			(type ==
				SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH) ||
			(type == SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) ||
			(type == WikiActivityKeys.ADD_PAGE) ||
			(type == WikiActivityKeys.UPDATE_PAGE)) {

			return true;
		}

		return false;
	}

	protected String getDownloadURL(long fileEntryId) throws PortalException {
		FileEntry fileEntry = fetchFileEntry(fileEntryId);

		if (fileEntry != null) {
			return PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(
				_wikiRequestHelper.getThemeDisplay(), fileEntry,
				StringPool.BLANK);
		}

		return StringPool.BLANK;
	}

	protected String getLink(String title, String url) {
		if (Validator.isNull(url)) {
			return title;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<a href='");
		sb.append(url);
		sb.append("'>");
		sb.append(title);
		sb.append("</a>");

		return sb.toString();
	}

	protected String getPageURL(WikiPage page) {
		if (page == null) {
			return StringPool.BLANK;
		}

		WikiNode node = page.getNode();

		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/wiki/view");
		portletURL.setParameter("nodeName", node.getName());
		portletURL.setParameter("title", page.getTitle());

		return portletURL.toString();
	}

	protected String getPageURL(WikiPage page, double version) {
		if (page == null) {
			return null;
		}

		WikiNode node = page.getNode();

		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/wiki/view");
		portletURL.setParameter("nodeName", node.getName());
		portletURL.setParameter("title", page.getTitle());
		portletURL.setParameter("version", String.valueOf(version));

		return portletURL.toString();
	}

	private FileEntry fetchFileEntry(long fileEntryId) throws PortalException {
		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		return null;
	}

	private final WikiRequestHelper _wikiRequestHelper;

}