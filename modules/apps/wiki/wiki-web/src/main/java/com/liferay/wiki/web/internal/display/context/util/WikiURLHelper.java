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
		PortletURL viewPagesURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/wiki/view_pages"
		).setParameter(
			"navigation", "all-pages"
		).setParameter(
			"nodeId", String.valueOf(node.getNodeId())
		).build();

		return viewPagesURL;
	}

	public PortletURL getFrontPageURL(WikiNode wikiNode) {
		PortletURL frontPageURL = PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view"
		).setParameter(
			"title", _wikiGroupServiceConfiguration.frontPageName()
		).setParameter(
			"tag", StringPool.BLANK
		).build();

		return frontPageURL;
	}

	public PortletURL getSearchURL() {
		PortletURL searchURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/wiki/search"
		).build();

		return searchURL;
	}

	public PortletURL getUndoTrashURL() {
		PortletURL undoTrashURL = PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/wiki/edit_page"
		).setParameter(
			Constants.CMD, Constants.RESTORE
		).build();

		return undoTrashURL;
	}

	public PortletURL getViewDraftPagesURL(WikiNode wikiNode) {
		PortletURL viewDraftPagesURL = PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view_draft_pages"
		).build();

		return viewDraftPagesURL;
	}

	public PortletURL getViewFrontPagePageURL(WikiNode wikiNode) {
		return getViewPageURL(
			wikiNode, _wikiGroupServiceConfiguration.frontPageName());
	}

	public PortletURL getViewOrphanPagesURL(WikiNode wikiNode) {
		PortletURL viewOrphanPagesURL = PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view_orphan_pages"
		).build();

		return viewOrphanPagesURL;
	}

	public PortletURL getViewPagesURL(WikiNode wikiNode) {
		PortletURL viewPagesURL = PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view_pages"
		).build();

		return viewPagesURL;
	}

	public PortletURL getViewPageURL(WikiNode wikiNode, String title) {
		PortletURL viewPageURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/wiki/view"
		).setParameter(
			"nodeName", wikiNode.getName()
		).setParameter(
			"title", title
		).build();

		return viewPageURL;
	}

	public PortletURL getViewRecentChangesURL(WikiNode wikiNode) {
		PortletURL viewRecentChangesURL = PortletURLBuilder.create(
			getWikiNodeBaseURL(wikiNode)
		).setMVCRenderCommandName(
			"/wiki/view_recent_changes"
		).build();

		return viewRecentChangesURL;
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