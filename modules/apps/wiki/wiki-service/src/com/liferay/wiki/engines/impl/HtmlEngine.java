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

package com.liferay.wiki.engines.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.engines.WikiEngine;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 * @author Zsigmond Rab
 */
@Component(
	service = WikiEngine.class,
	property = {
		"enabled=true", "format=html",
		"edit.page=/html/portlet/wiki/edit/html.jsp"
	}
)
public class HtmlEngine implements WikiEngine {

	public HtmlEngine() {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			WikiPortletKeys.WIKI);

		_friendlyURLMapping =
			Portal.FRIENDLY_URL_SEPARATOR + portlet.getFriendlyURLMapping();

		FriendlyURLMapper friendlyURLMapper =
			portlet.getFriendlyURLMapperInstance();

		_router = friendlyURLMapper.getRouter();
	}

	@Override
	public String convert(
		WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
		String attachmentURLPrefix) {

		return page.getContent();
	}

	@Override
	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		try {
			return _getOutgoingLinks(page);
		}
		catch (Exception e) {
			throw new PageContentException(e);
		}
	}

	@Override
	public boolean validate(long nodeId, String newContent) {
		return true;
	}

	private Map<String, Boolean> _getOutgoingLinks(WikiPage page)
		throws Exception {

		if (Validator.isNull(page.getContent())) {
			return Collections.emptyMap();
		}

		Map<String, Boolean> links = new HashMap<String, Boolean>();

		Source source = new Source(page.getContent());

		List<StartTag> startTags = source.getAllStartTags("a");

		for (StartTag startTag : startTags) {
			String href = startTag.getAttributeValue("href");

			if (Validator.isNull(href)) {
				continue;
			}

			int pos = href.lastIndexOf(_friendlyURLMapping);

			if (pos == -1) {
				continue;
			}

			String friendlyURL = href.substring(
				pos + _friendlyURLMapping.length());

			if (friendlyURL.endsWith(StringPool.SLASH)) {
				friendlyURL = friendlyURL.substring(
					0, friendlyURL.length() - 1);
			}

			Map<String, String> routeParameters = new HashMap<String, String>();

			if (!_router.urlToParameters(friendlyURL, routeParameters)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No route could be found to match URL " + friendlyURL);
				}

				continue;
			}

			String title = routeParameters.get("title");
			String nodeName = routeParameters.get("nodeName");

			if (Validator.isNull(title) || Validator.isNull(nodeName)) {
				continue;
			}

			try {
				WikiNodeLocalServiceUtil.getNode(page.getGroupId(), nodeName);

				links.put(StringUtil.toLowerCase(title), Boolean.TRUE);
			}
			catch (NoSuchNodeException nsne) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsne.getMessage());
				}
			}
		}

		return links;
	}

	private static final Log _log = LogFactoryUtil.getLog(HtmlEngine.class);

	private final String _friendlyURLMapping;
	private final Router _router;

}