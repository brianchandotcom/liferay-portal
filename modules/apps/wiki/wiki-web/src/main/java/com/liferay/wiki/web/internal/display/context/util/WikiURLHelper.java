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

package com.liferay.wiki.web.internal.display.context.util;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.model.WikiNode;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author Adolfo Pérez
 */
public class WikiURLHelper {

	public WikiURLHelper(
		WikiRequestHelper wikiRequestHelper, PortletResponse portletResponse,
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiRequestHelper = wikiRequestHelper;
		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			portletResponse);
	}

	public WikiURLHelper(
		WikiRequestHelper wikiRequestHelper, RenderResponse renderResponse,
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiRequestHelper = wikiRequestHelper;
		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			renderResponse);
	}

	public PortletURL getBackToNodeURL(WikiNode wikiNode) {
		return getWikiNodeBaseURL(wikiNode);
	}

	public PortletURL getBackToViewPagesURL(WikiNode node) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/wiki/view_pages"
		).setParameter(
			"navigation", "all-pages"
		).setParameter(
			"nodeId", node.getNodeId()
		).build();
	}

	public PortletURL getFrontPageURL(WikiNode wikiNode) {
		return PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view"
		).setParameter(
			"title", _wikiGroupServiceConfiguration.frontPageName()
		).setParameter(
			"tag", StringPool.BLANK
		).build();
	}

	public PortletURL getSearchURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/wiki/search"
		).build();
	}

	public PortletURL getUndoTrashURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/wiki/edit_page"
		).setParameter(
			Constants.CMD, Constants.RESTORE
		).build();
	}

	public PortletURL getViewDraftPagesURL(WikiNode wikiNode) {
		return PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view_draft_pages"
		).build();
	}

	public PortletURL getViewFrontPagePageURL(WikiNode wikiNode) {
		return getViewPageURL(
			wikiNode, _wikiGroupServiceConfiguration.frontPageName());
	}

	public PortletURL getViewOrphanPagesURL(WikiNode wikiNode) {
		return PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view_orphan_pages"
		).build();
	}

	public PortletURL getViewPagesURL(WikiNode wikiNode) {
		return PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view_pages"
		).build();
	}

	public PortletURL getViewPageURL(WikiNode wikiNode, String title) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/wiki/view"
		).setParameter(
			"nodeName", wikiNode.getName()
		).setParameter(
			"title", title
		).build();
	}

	public PortletURL getViewRecentChangesURL(WikiNode wikiNode) {
		return PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view_recent_changes"
		).build();
	}

	protected PortletURL getWikiNodeBaseURL(WikiNode node) {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setParameter(
			"nodeName", node.getName()
		).build();

		long categoryId = _wikiRequestHelper.getCategoryId();

		if (categoryId > 0) {
			portletURL.setParameter("categoryId", "0");
		}

		return portletURL;
	}

	private final LiferayPortletResponse _liferayPortletResponse;
	private final WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;
	private final WikiRequestHelper _wikiRequestHelper;

}