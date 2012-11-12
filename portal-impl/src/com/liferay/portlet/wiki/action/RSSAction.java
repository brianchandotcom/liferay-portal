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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.util.RSSUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 */
public class RSSAction extends com.liferay.portal.struts.RSSAction {

	@Override
	protected byte[] getRSS(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

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
			request, "displayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);

		PortletURL feedURL = new PortletURLImpl(
			request, PortletKeys.WIKI, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		feedURL.setParameter("nodeId", String.valueOf(nodeId));

		PortletURL entryURL = new PortletURLImpl(
			request, PortletKeys.WIKI, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		entryURL.setParameter("nodeId", String.valueOf(nodeId));
		entryURL.setParameter("title", title);

		Locale locale = themeDisplay.getLocale();

		String rss = StringPool.BLANK;

		if ((nodeId > 0) && Validator.isNotNull(title)) {
			rss = WikiPageServiceUtil.getPagesRSS(
				companyId, nodeId, title, max, type, version, displayStyle,
				feedURL.toString(), entryURL.toString(), locale);
		}
		else if (nodeId > 0) {
			rss = WikiPageServiceUtil.getNodePagesRSS(
				nodeId, max, type, version, displayStyle, feedURL.toString(),
				entryURL.toString());
		}

		return rss.getBytes(StringPool.UTF8);
	}

}