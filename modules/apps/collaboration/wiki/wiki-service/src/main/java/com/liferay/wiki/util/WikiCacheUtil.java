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

package com.liferay.wiki.util;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.engine.impl.WikiEngineRenderer;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

import javax.portlet.PortletURL;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author     Jorge Ferrer
 * @deprecated As of 1.2.0, replaced by {@link WikiCacheHelper}
 */
@Deprecated
public class WikiCacheUtil {

	public static void clearCache(long nodeId) {
		_portalCache.removeAll();
	}

	public static void clearCache(long nodeId, String title) {
		clearCache(nodeId);
	}

	public static WikiPageDisplay getDisplay(
		long nodeId, String title, PortletURL viewPageURL,
		PortletURL editPageURL, String attachmentURLPrefix) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		String key = _encodeKey(nodeId, title, viewPageURL.toString());

		WikiPageDisplay pageDisplay = (WikiPageDisplay)_portalCache.get(key);

		if (pageDisplay == null) {
			pageDisplay = _getPageDisplay(
				nodeId, title, viewPageURL, editPageURL, attachmentURLPrefix);

			if (pageDisplay != null) {
				_portalCache.put(key, pageDisplay);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"getDisplay for {" + nodeId + ", " + title + ", " +
					viewPageURL + ", " + editPageURL + "} takes " +
						stopWatch.getTime() + " ms");
		}

		return pageDisplay;
	}

	public static Map<String, Boolean> getOutgoingLinks(
			WikiPage page, WikiEngineRenderer wikiEngineRenderer)
		throws PageContentException {

		String key = _encodeKey(
			page.getNodeId(), page.getTitle(), _OUTGOING_LINKS);

		Map<String, Boolean> links = (Map<String, Boolean>)_portalCache.get(
			key);

		if (links == null) {
			WikiEngine wikiEngine = wikiEngineRenderer.fetchWikiEngine(
				page.getFormat());

			if (wikiEngine != null) {
				links = wikiEngine.getOutgoingLinks(page);
			}
			else {
				links = Collections.emptyMap();
			}

			_portalCache.put(key, (Serializable)links);
		}

		return links;
	}

	private static String _encodeKey(
		long nodeId, String title, String postfix) {

		StringBundler sb = new StringBundler(6);

		sb.append(_CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(StringUtil.toHexString(nodeId));
		sb.append(title);

		if (postfix != null) {
			sb.append(StringPool.POUND);
			sb.append(postfix);
		}

		return sb.toString();
	}

	private static WikiPageDisplay _getPageDisplay(
		long nodeId, String title, PortletURL viewPageURL,
		PortletURL editPageURL, String attachmentURLPrefix) {

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Get page display for {" + nodeId + ", " + title + ", " +
						viewPageURL + ", " + editPageURL + "}");
			}

			return WikiPageLocalServiceUtil.getPageDisplay(
				nodeId, title, viewPageURL, editPageURL, attachmentURLPrefix);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get page display for {" + nodeId + ", " + title +
						", " + viewPageURL + ", " + editPageURL + "}");
			}

			return null;
		}
	}

	private static final String _CACHE_NAME = WikiCacheUtil.class.getName();

	private static final String _OUTGOING_LINKS = "OUTGOING_LINKS";

	private static final Log _log = LogFactoryUtil.getLog(WikiCacheUtil.class);

	private static final PortalCache<String, Serializable> _portalCache =
		MultiVMPoolUtil.getPortalCache(_CACHE_NAME);

	static {
		_portalCache.removeAll();
	}

}