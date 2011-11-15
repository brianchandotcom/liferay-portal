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

package com.liferay.portlet.wiki.engines;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

/**
 * @author Jorge Ferrer
 * @author Zsigmond Rab
 */
public class HtmlEngine implements WikiEngine {

	public String convert(
		WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
		String attachmentURLPrefix) {

		return page.getContent();
	}

	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		if (Validator.isNull(page.getContent())) {
			return Collections.emptyMap();
		}

		try {
			_check(page);

			Source source = new Source(page.getContent());

			List<StartTag> tags = source.getAllStartTags("a");

			Map<String, Boolean> links = new HashMap<String, Boolean>();

			for (StartTag tag : tags) {
				String targetUri = tag.getAttributeValue("href");

				if (Validator.isNotNull(targetUri)) {
					int indexOfFriendlyURLPath = targetUri.lastIndexOf(
						_friendlyURLMapping);

					String friendlyURLPath = targetUri.substring(
						indexOfFriendlyURLPath + _friendlyURLMapping.length());

					if (friendlyURLPath.endsWith(StringPool.SLASH)) {
						friendlyURLPath = friendlyURLPath.substring(
							0, friendlyURLPath.length() - 1);
					}

					Map<String, String> routeParameters =
						new HashMap<String, String>();

					if (!_router.urlToParameters(
							friendlyURLPath, routeParameters)) {

						if (_log.isWarnEnabled()) {
							_log.warn(
								"No route could be found to match URL " +
									friendlyURLPath);
						}
					}
					else {
						String title = routeParameters.get("title");
						String nodeName = routeParameters.get("nodeName");

						if (Validator.isNotNull(title) &&
							Validator.isNotNull(nodeName)) {

							try {
								WikiNodeLocalServiceUtil.getNode(
									page.getGroupId(), nodeName);

								links.put(title.toLowerCase(), Boolean.TRUE);
							}
							catch (NoSuchNodeException nsne) {
								if (_log.isWarnEnabled()) {
									_log.warn(
										"There is no wiki node with groupId " +
											page.getGroupId() +
												" and nodeName " + nodeName);
								}
							}
						}
					}
				}
			}

			return links;
		}
		catch (Exception e) {
			throw new PageContentException(e);
		}
	}

	public void setInterWikiConfiguration(String interWikiConfiguration) {
	}

	public void setMainConfiguration(String mainConfiguration) {
	}

	public boolean validate(long nodeId, String newContent) {
		return true;
	}

	private void _check(WikiPage page) throws SystemException {
		if (Validator.isNull(_friendlyURLMapping) ||
			Validator.isNull(_router)) {

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				page.getCompanyId(), PortletKeys.WIKI);

			_friendlyURLMapping = Portal.FRIENDLY_URL_SEPARATOR +
				portlet.getFriendlyURLMapping();

			_router = portlet.getFriendlyURLMapperInstance().getRouter();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(HtmlEngine.class);

	private String _friendlyURLMapping;
	private Router _router;

}