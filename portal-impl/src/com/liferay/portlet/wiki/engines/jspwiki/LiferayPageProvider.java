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

package com.liferay.portlet.wiki.engines.jspwiki;

import com.ecyrd.jspwiki.QueryItem;
import com.ecyrd.jspwiki.WikiEngine;
import com.ecyrd.jspwiki.WikiPage;
import com.ecyrd.jspwiki.providers.ProviderException;
import com.ecyrd.jspwiki.providers.WikiPageProvider;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Jorge Ferrer
 */
public class LiferayPageProvider implements WikiPageProvider {

	public static com.ecyrd.jspwiki.WikiPage toJSPWikiPage(
		com.liferay.portlet.wiki.model.WikiPage page, WikiEngine engine) {

		com.ecyrd.jspwiki.WikiPage jspWikiPage = new com.ecyrd.jspwiki.WikiPage(
			engine, page.getTitle());

		jspWikiPage.setAuthor(page.getUserName());
		jspWikiPage.setVersion((int)(page.getVersion() * 10));
		jspWikiPage.setLastModified(page.getCreateDate());

		return jspWikiPage;
	}

	@Override
	public void deletePage(String name) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deletePage(" + name + ")");
		}
	}

	@Override
	public void deleteVersion(String title, int version) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Invoking deleteVersion(" + title + ", " + version + ")");
		}
	}

	@Override
	public Collection<WikiPage> findPages(QueryItem[] query) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking findPages(" + Arrays.toString(query) + ")");
		}

		return Collections.emptyList();
	}

	@Override
	public Collection<WikiPage> getAllChangedSince(Date date) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getAllChangedSince(" + date + ")");
		}

		try {
			return getAllPages();
		}
		catch (ProviderException pe) {
			_log.error("Could not get changed pages", pe);

			return Collections.emptyList();
		}
	}

	@Override
	public Collection<WikiPage> getAllPages() throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getAllPages()");
		}

		List<WikiPage> jspWikiPages = new ArrayList<WikiPage>();

		try {
			int count = WikiPageLocalServiceUtil.getPagesCount(_nodeId, true);

			List<com.liferay.portlet.wiki.model.WikiPage> pages =
				WikiPageLocalServiceUtil.getPages(_nodeId, true, 0, count);

			for (com.liferay.portlet.wiki.model.WikiPage page : pages) {
				jspWikiPages.add(toJSPWikiPage(page, _engine));
			}
		}
		catch (SystemException se) {
			throw new ProviderException(se.toString());
		}

		return jspWikiPages;
	}

	@Override
	public int getPageCount() throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getPageCount()");
		}

		try {
			return WikiPageLocalServiceUtil.getPagesCount(_nodeId);
		}
		catch (SystemException se) {
			throw new ProviderException(se.toString());
		}
	}

	@Override
	public com.ecyrd.jspwiki.WikiPage getPageInfo(String title, int version)
		throws ProviderException {

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getPageInfo(" + title + ", " + version + ")");
		}

		try {
			com.liferay.portlet.wiki.model.WikiPage page =
				WikiPageLocalServiceUtil.getPage(_nodeId, title);

			return toJSPWikiPage(page, _engine);
		}
		catch (NoSuchPageException nspe) {
			return null;
		}
		catch (Exception e) {
			throw new ProviderException(e.toString());
		}
	}

	@Override
	public String getPageText(String title, int version)
		throws ProviderException {

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getPageText(" + title + ", " + version + ")");
		}

		try {
			com.liferay.portlet.wiki.model.WikiPage page =
				WikiPageLocalServiceUtil.getPage(_nodeId, title);

			return page.getContent();
		}
		catch (Exception e) {
			throw new ProviderException(e.toString());
		}
	}

	@Override
	public String getProviderInfo() {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getProviderInfo()");
		}

		return LiferayPageProvider.class.getName();
	}

	@Override
	public List<WikiPage> getVersionHistory(String title) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking getVersionHistory(" + title + ")");
		}

		return Collections.emptyList();
	}

	@Override
	public void initialize(WikiEngine engine, Properties props) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking initialize(" + engine + ", " + props + ")");
		}

		_engine = engine;
		_nodeId = GetterUtil.getLong(props.getProperty("nodeId"));
	}

	@Override
	public void movePage(String from, String to) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking movePage(" + from + ", " + to + ")");
		}
	}

	@Override
	public boolean pageExists(String title) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking pageExists(" + title + ")");
		}

		try {
			int count = WikiPageLocalServiceUtil.getPagesCount(
				_nodeId, JSPWikiEngine.decodeJSPWikiName(title), true);

			if (count > 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	public void putPageText(com.ecyrd.jspwiki.WikiPage page, String text) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking putPageText(" + page + ", " + text + ")");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayPageProvider.class);

	private WikiEngine _engine;
	private long _nodeId;

}