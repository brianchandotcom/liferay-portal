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

package com.liferay.wiki.web.internal.display.context;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.trash.TrashHelper;
import com.liferay.wiki.display.context.WikiDisplayContextFactory;
import com.liferay.wiki.display.context.WikiEditPageDisplayContext;
import com.liferay.wiki.display.context.WikiListPagesDisplayContext;
import com.liferay.wiki.display.context.WikiNodeInfoPanelDisplayContext;
import com.liferay.wiki.display.context.WikiPageInfoPanelDisplayContext;
import com.liferay.wiki.display.context.WikiViewPageDisplayContext;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
@Component(service = WikiDisplayContextProvider.class)
public class WikiDisplayContextProvider {

	public WikiEditPageDisplayContext getWikiEditPageDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, WikiPage wikiPage) {

		WikiEditPageDisplayContext wikiEditPageDisplayContext =
			new DefaultWikiEditPageDisplayContext(
				httpServletRequest, httpServletResponse, wikiPage);

		for (WikiDisplayContextFactory wikiDisplayContextFactory :
				_serviceTrackerList) {

			wikiEditPageDisplayContext =
				wikiDisplayContextFactory.getWikiEditPageDisplayContext(
					wikiEditPageDisplayContext, httpServletRequest,
					httpServletResponse, wikiPage);
		}

		return wikiEditPageDisplayContext;
	}

	public WikiListPagesDisplayContext getWikiListPagesDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, WikiNode wikiNode) {

		WikiListPagesDisplayContext wikiListPagesDisplayContext =
			new DefaultWikiListPagesDisplayContext(
				httpServletRequest, httpServletResponse, wikiNode,
				_trashHelper);

		for (WikiDisplayContextFactory wikiDisplayContextFactory :
				_serviceTrackerList) {

			wikiListPagesDisplayContext =
				wikiDisplayContextFactory.getWikiListPagesDisplayContext(
					wikiListPagesDisplayContext, httpServletRequest,
					httpServletResponse, wikiNode);
		}

		return wikiListPagesDisplayContext;
	}

	public WikiNodeInfoPanelDisplayContext getWikiNodeInfoPanelDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		WikiNodeInfoPanelDisplayContext wikiNodeInfoPanelDisplayContext =
			new DefaultWikiNodeInfoPanelDisplayContext(
				httpServletRequest, httpServletResponse);

		for (WikiDisplayContextFactory wikiDisplayContextFactory :
				_serviceTrackerList) {

			wikiNodeInfoPanelDisplayContext =
				wikiDisplayContextFactory.getWikiNodeInfoPanelDisplayContext(
					wikiNodeInfoPanelDisplayContext, httpServletRequest,
					httpServletResponse);
		}

		return wikiNodeInfoPanelDisplayContext;
	}

	public WikiPageInfoPanelDisplayContext getWikiPageInfoPanelDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		WikiPageInfoPanelDisplayContext wikiPageInfoPanelDisplayContext =
			new DefaultWikiPageInfoPanelDisplayContext(
				httpServletRequest, httpServletResponse);

		for (WikiDisplayContextFactory wikiDisplayContextFactory :
				_serviceTrackerList) {

			wikiPageInfoPanelDisplayContext =
				wikiDisplayContextFactory.getWikiPageInfoPanelDisplayContext(
					wikiPageInfoPanelDisplayContext, httpServletRequest,
					httpServletResponse);
		}

		return wikiPageInfoPanelDisplayContext;
	}

	public WikiViewPageDisplayContext getWikiViewPageDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, WikiPage wikiPage) {

		WikiViewPageDisplayContext wikiViewPageDisplayContext =
			new DefaultWikiViewPageDisplayContext(
				httpServletRequest, httpServletResponse, wikiPage);

		for (WikiDisplayContextFactory wikiDisplayContextFactory :
				_serviceTrackerList) {

			wikiViewPageDisplayContext =
				wikiDisplayContextFactory.getWikiViewPageDisplayContext(
					wikiViewPageDisplayContext, httpServletRequest,
					httpServletResponse, wikiPage);
		}

		return wikiViewPageDisplayContext;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, WikiDisplayContextFactory.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private ServiceTrackerList<WikiDisplayContextFactory> _serviceTrackerList;

	@Reference
	private TrashHelper _trashHelper;

}