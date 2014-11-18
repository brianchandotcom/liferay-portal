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

package com.liferay.wiki.web.wiki.portlet.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.RSSUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.util.WikiServiceUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 */
public class RSSAction extends com.liferay.portal.struts.RSSAction {

	@Override
	protected byte[] getRSS(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = ParamUtil.getLong(request, "companyId");
		long nodeId = ParamUtil.getLong(request, "nodeId");
		String title = ParamUtil.getString(request, "title");
		int max = ParamUtil.getInteger(
			request, "max", SearchContainer.DEFAULT_DELTA);
		String type = ParamUtil.getString(
			request, "type", RSSUtil.FORMAT_DEFAULT);
		double version = ParamUtil.getDouble(
			request, "version", RSSUtil.VERSION_DEFAULT);
		String displayStyle = ParamUtil.getString(
			request, "displayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			themeDisplay.getScopeGroupId(), WikiPortletKeys.WIKI);

		StringBundler sb = new StringBundler(4);

		sb.append(layoutFullURL);
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("wiki/");
		sb.append(nodeId);

		String feedURL = sb.toString();

		String entryURL = feedURL + StringPool.SLASH + title;

		Locale locale = themeDisplay.getLocale();

		String rss = StringPool.BLANK;

		if (nodeId > 0) {
			String attachmentURLPrefix = WikiServiceUtil.getAttachmentURLPrefix(
				themeDisplay.getPathMain(), themeDisplay.getPlid(), nodeId,
				title);

			if (Validator.isNotNull(title)) {
				rss = WikiPageServiceUtil.getPagesRSS(
					companyId, nodeId, title, max, type, version, displayStyle,
					feedURL, entryURL, attachmentURLPrefix, locale);
			}
			else {
				rss = WikiPageServiceUtil.getNodePagesRSS(
					nodeId, max, type, version, displayStyle, feedURL, entryURL,
					attachmentURLPrefix);
			}
		}

		return rss.getBytes(StringPool.UTF8);
	}

}