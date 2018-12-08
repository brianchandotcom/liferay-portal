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

package com.liferay.wiki.web.internal.portlet.action;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.rss.util.RSSUtil;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.service.WikiPageService;
import com.liferay.wiki.web.internal.display.context.util.WikiRequestHelper;
import com.liferay.wiki.web.internal.util.WikiUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(property = "path=/wiki/rss", service = StrutsAction.class)
public class RSSAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		if (!isRSSFeedsEnabled(request)) {
			_portal.sendRSSFeedsDisabledError(request, response);

			return null;
		}

		try {
			ServletResponseUtil.sendFile(
				request, response, null, getRSS(request),
				ContentTypes.TEXT_XML_UTF8);

			return null;
		}
		catch (Exception e) {
			_portal.sendError(e, request, response);

			return null;
		}
	}

	protected byte[] getRSS(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String rss = StringPool.BLANK;

		long nodeId = ParamUtil.getLong(request, "nodeId");

		if (nodeId <= 0) {
			return rss.getBytes(StringPool.UTF8);
		}

		String title = ParamUtil.getString(request, "title");
		int max = ParamUtil.getInteger(
			request, "max", SearchContainer.DEFAULT_DELTA);
		String type = ParamUtil.getString(
			request, "type", RSSUtil.FORMAT_DEFAULT);
		double version = ParamUtil.getDouble(
			request, "version", RSSUtil.VERSION_DEFAULT);
		String displayStyle = ParamUtil.getString(
			request, "displayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);

		String layoutFullURL = _portal.getLayoutFullURL(
			themeDisplay.getScopeGroupId(), WikiPortletKeys.WIKI);

		StringBundler sb = new StringBundler(4);

		sb.append(layoutFullURL);
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("wiki/");
		sb.append(nodeId);

		String feedURL = sb.toString();

		String entryURL = feedURL + StringPool.SLASH + title;

		String attachmentURLPrefix = WikiUtil.getAttachmentURLPrefix(
			themeDisplay.getPathMain(), themeDisplay.getPlid(), nodeId, title);

		if (Validator.isNotNull(title)) {
			rss = _wikiPageService.getPagesRSS(
				nodeId, title, max, type, version, displayStyle, feedURL,
				entryURL, attachmentURLPrefix, themeDisplay.getLocale());
		}
		else {
			rss = _wikiPageService.getNodePagesRSS(
				nodeId, max, type, version, displayStyle, feedURL, entryURL,
				attachmentURLPrefix);
		}

		return rss.getBytes(StringPool.UTF8);
	}

	protected boolean isRSSFeedsEnabled(HttpServletRequest request)
		throws Exception {

		WikiRequestHelper wikiRequestHelper = new WikiRequestHelper(request);

		WikiGroupServiceOverriddenConfiguration
			wikiGroupServiceOverriddenConfiguration =
				wikiRequestHelper.getWikiGroupServiceOverriddenConfiguration();

		return wikiGroupServiceOverriddenConfiguration.enableRss();
	}

	@Reference(unbind = "-")
	protected void setWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	@Reference
	private Portal _portal;

	private WikiPageService _wikiPageService;

}