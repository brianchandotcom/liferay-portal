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

package com.liferay.wiki.web.display.context.util;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.model.WikiNode;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author Adolfo Pérez
 */
public class WikiURLHelper {

	public WikiURLHelper(
		WikiRequestHelper wikiRequestHelper, RenderResponse renderResponse,
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiRequestHelper = wikiRequestHelper;
		_renderResponse = renderResponse;
		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	public PortletURL getBackToNodeURL(WikiNode wikiNode) {
		return getWikiNodeBaseURL(wikiNode);
	}

	public PortletURL getFrontPageURL(WikiNode wikiNode) {
		PortletURL frontPageURL = getWikiNodeBaseURL(wikiNode);

		frontPageURL.setParameter("struts_action", "/wiki/view");
		frontPageURL.setParameter(
			"title", _wikiGroupServiceConfiguration.frontPageName());
		frontPageURL.setParameter("tag", StringPool.BLANK);

		return frontPageURL;
	}

	public PortletURL getSearchURL() {
		PortletURL searchURL = _renderResponse.createRenderURL();

		searchURL.setParameter("struts_action", "/wiki/search");

		return searchURL;
	}

	public PortletURL getUndoTrashURL() {
		PortletURL undoTrashURL = _renderResponse.createActionURL();

		undoTrashURL.setParameter("struts_action", "/wiki/edit_page");
		undoTrashURL.setParameter(Constants.CMD, Constants.RESTORE);

		return undoTrashURL;
	}

	public PortletURL getViewAllPagesURL(WikiNode wikiNode) {
		PortletURL viewAllPagesURL = getWikiNodeBaseURL(wikiNode);

		viewAllPagesURL.setParameter("struts_action", "/wiki/view_all_pages");

		return viewAllPagesURL;
	}

	public PortletURL getViewDraftPagesURL(WikiNode wikiNode) {
		PortletURL viewDraftPagesURL = getWikiNodeBaseURL(wikiNode);

		viewDraftPagesURL.setParameter(
			"struts_action", "/wiki/view_draft_pages");

		return viewDraftPagesURL;
	}

	public PortletURL getViewOrphanPagesURL(WikiNode wikiNode) {
		PortletURL viewOrphanPagesURL = getWikiNodeBaseURL(wikiNode);

		viewOrphanPagesURL.setParameter(
			"struts_action", "/wiki/view_orphan_pages");

		return viewOrphanPagesURL;
	}

	public PortletURL getViewPageURL(WikiNode wikiNode) {
		PortletURL viewPageURL = _renderResponse.createRenderURL();

		viewPageURL.setParameter("struts_action", "/wiki/view");
		viewPageURL.setParameter("nodeName", wikiNode.getName());
		viewPageURL.setParameter(
			"title", _wikiGroupServiceConfiguration.frontPageName());

		return viewPageURL;
	}

	public PortletURL getViewRecentChangesURL(WikiNode wikiNode) {
		PortletURL viewRecentChangesURL = getWikiNodeBaseURL(wikiNode);

		viewRecentChangesURL.setParameter(
			"struts_action", "/wiki/view_recent_changes");

		return viewRecentChangesURL;
	}

	protected PortletURL getWikiNodeBaseURL(WikiNode node) {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("nodeName", node.getName());

		long categoryId = _wikiRequestHelper.getCategoryId();

		if (categoryId > 0) {
			portletURL.setParameter("categoryId", "0");
		}

		return portletURL;
	}

	private final RenderResponse _renderResponse;
	private final WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;
	private final WikiRequestHelper _wikiRequestHelper;

}