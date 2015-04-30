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

package com.liferay.wiki.engine.html;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.Portal;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.engine.BaseWikiEngine;
import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Zsigmond Rab
 */
@Component(service = WikiEngine.class)
public class HtmlEngine extends BaseWikiEngine {

	@Override
	public String getFormat() {
		return "html";
	}

	@Override
	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		try {
			return _getOutgoingLinks(page);
		}
		catch (PortalException pe) {
			throw new PageContentException(pe);
		}
	}

	@Reference(target = "(javax.portlet.name=" + WikiPortletKeys.WIKI + ")")
	protected void setFriendlyURLMapper(FriendlyURLMapper friendlyURLMapper) {
		_friendlyURLMapping =
			Portal.FRIENDLY_URL_SEPARATOR + friendlyURLMapper.getMapping();

		_router = friendlyURLMapper.getRouter();
	}

	private Map<String, Boolean> _getOutgoingLinks(WikiPage page)
		throws PortalException {

		if (Validator.isNull(page.getContent())) {
			return Collections.emptyMap();
		}

		Map<String, Boolean> links = new HashMap<>();

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

			Map<String, String> routeParameters = new HashMap<>();

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

	private String _friendlyURLMapping;
	private Router _router;

}