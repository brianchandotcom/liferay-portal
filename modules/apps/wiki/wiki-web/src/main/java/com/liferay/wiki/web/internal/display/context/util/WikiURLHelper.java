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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.model.WikiNode;

import javax.portlet.ActionRequest;
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

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			portletResponse);
		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	public WikiURLHelper(
		WikiRequestHelper wikiRequestHelper, RenderResponse renderResponse,
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiRequestHelper = wikiRequestHelper;

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			renderResponse);
		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	public PortletURL getBackToNodeURL(WikiNode wikiNode) {
		return getWikiNodeBaseURL(wikiNode);
	}

	public PortletURL getBackToViewPagesURL(WikiNode node) {
		PortletURL viewPagesURL = _liferayPortletResponse.createRenderURL();

		viewPagesURL.setParameter("mvcRenderCommandName", "/wiki/view_pages");
		viewPagesURL.setParameter("navigation", "all-pages");
		viewPagesURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

		return viewPagesURL;
	}

	public PortletURL getFrontPageURL(WikiNode wikiNode) {
		PortletURL frontPageURL = getWikiNodeBaseURL(wikiNode);

		frontPageURL.setParameter("mvcRenderCommandName", "/wiki/view");
		frontPageURL.setParameter(
			"title", _wikiGroupServiceConfiguration.frontPageName());
		frontPageURL.setParameter("tag", StringPool.BLANK);

		return frontPageURL;
	}

	public PortletURL getSearchURL() {
		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter("mvcRenderCommandName", "/wiki/search");

		return searchURL;
	}

	public PortletURL getUndoTrashURL() {
		PortletURL undoTrashURL = _liferayPortletResponse.createActionURL();

		undoTrashURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/edit_page");
		undoTrashURL.setParameter(Constants.CMD, Constants.RESTORE);

		return undoTrashURL;
	}

	public PortletURL getViewDraftPagesURL(WikiNode wikiNode) {
		PortletURL viewDraftPagesURL = getWikiNodeBaseURL(wikiNode);

		viewDraftPagesURL.setParameter(
			"mvcRenderCommandName", "/wiki/view_draft_pages");

		return viewDraftPagesURL;
	}

	public PortletURL getViewFrontPagePageURL(WikiNode wikiNode) {
		return getViewPageURL(
			wikiNode, _wikiGroupServiceConfiguration.frontPageName());
	}

	public PortletURL getViewOrphanPagesURL(WikiNode wikiNode) {
		PortletURL viewOrphanPagesURL = getWikiNodeBaseURL(wikiNode);

		viewOrphanPagesURL.setParameter(
			"mvcRenderCommandName", "/wiki/view_orphan_pages");

		return viewOrphanPagesURL;
	}

	public PortletURL getViewPagesURL(WikiNode wikiNode) {
		PortletURL viewPagesURL = getWikiNodeBaseURL(wikiNode);

		viewPagesURL.setParameter("mvcRenderCommandName", "/wiki/view_pages");

		return viewPagesURL;
	}

	public PortletURL getViewPageURL(WikiNode wikiNode, String title) {
		PortletURL viewPageURL = _liferayPortletResponse.createRenderURL();

		viewPageURL.setParameter("mvcRenderCommandName", "/wiki/view");
		viewPageURL.setParameter("nodeName", wikiNode.getName());
		viewPageURL.setParameter("title", title);

		return viewPageURL;
	}

	public PortletURL getViewRecentChangesURL(WikiNode wikiNode) {
		PortletURL viewRecentChangesURL = getWikiNodeBaseURL(wikiNode);

		viewRecentChangesURL.setParameter(
			"mvcRenderCommandName", "/wiki/view_recent_changes");

		return viewRecentChangesURL;
	}

	protected PortletURL getWikiNodeBaseURL(WikiNode node) {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("nodeName", node.getName());

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