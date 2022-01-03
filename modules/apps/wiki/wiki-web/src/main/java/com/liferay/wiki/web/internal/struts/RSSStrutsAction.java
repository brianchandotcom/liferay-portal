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

package com.liferay.wiki.web.internal.struts;

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
import com.liferay.wiki.web.internal.display.context.helper.WikiRequestHelper;
import com.liferay.wiki.web.internal.util.WikiUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(property = "path=/wiki/rss", service = StrutsAction.class)
public class RSSStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (!_isRSSFeedsEnabled(httpServletRequest)) {
			_portal.sendRSSFeedsDisabledError(
				httpServletRequest, httpServletResponse);

			return null;
		}

		try {
			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse, null,
				_getRSS(httpServletRequest), ContentTypes.TEXT_XML_UTF8);

			return null;
		}
		catch (Exception exception) {
			_portal.sendError(
				exception, httpServletRequest, httpServletResponse);

			return null;
		}
	}

	@Reference(unbind = "-")
	protected void setWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	private byte[] _getRSS(HttpServletRequest httpServletRequest)
		throws Exception {

		String rss = StringPool.BLANK;

		long nodeId = ParamUtil.getLong(httpServletRequest, "nodeId");

		if (nodeId <= 0) {
			return rss.getBytes(StringPool.UTF8);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String title = ParamUtil.getString(httpServletRequest, "title");
		int max = ParamUtil.getInteger(
			httpServletRequest, "max", SearchContainer.DEFAULT_DELTA);
		String type = ParamUtil.getString(
			httpServletRequest, "type", RSSUtil.FORMAT_DEFAULT);
		double version = ParamUtil.getDouble(
			httpServletRequest, "version", RSSUtil.VERSION_DEFAULT);
		String displayStyle = ParamUtil.getString(
			httpServletRequest, "displayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);

		String layoutFullURL = _portal.getLayoutFullURL(
			themeDisplay.getScopeGroupId(), WikiPortletKeys.WIKI);

		String feedURL = StringBundler.concat(
			layoutFullURL, Portal.FRIENDLY_URL_SEPARATOR, "wiki/", nodeId);

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

	private boolean _isRSSFeedsEnabled(HttpServletRequest httpServletRequest)
		throws Exception {

		WikiRequestHelper wikiRequestHelper = new WikiRequestHelper(
			httpServletRequest);

		WikiGroupServiceOverriddenConfiguration
			wikiGroupServiceOverriddenConfiguration =
				wikiRequestHelper.getWikiGroupServiceOverriddenConfiguration();

		return wikiGroupServiceOverriddenConfiguration.enableRss();
	}

	@Reference
	private Portal _portal;

	private WikiPageService _wikiPageService;

}