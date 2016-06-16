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

package com.liferay.wiki.web.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.DeleteMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.search.ResultRow;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.trash.kernel.util.TrashUtil;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.display.context.WikiListPagesDisplayContext;
import com.liferay.wiki.display.context.WikiUIItemKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;
import com.liferay.wiki.util.comparator.PageVersionComparator;
import com.liferay.wiki.web.display.context.util.WikiRequestHelper;
import com.liferay.wiki.web.util.WikiPortletUtil;
import com.liferay.wiki.web.util.WikiWebComponentProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DefaultWikiListPagesDisplayContext
	implements WikiListPagesDisplayContext {

	public DefaultWikiListPagesDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		WikiNode wikiNode) {

		_request = request;
		_wikiNode = wikiNode;

		_wikiRequestHelper = new WikiRequestHelper(request);
	}

	@Override
	public String getEmptyResultsMessage() {
		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			return LanguageUtil.format(
				_request, "no-pages-were-found-that-matched-the-keywords-x",
				"<strong>" + HtmlUtil.escape(keywords) + "</strong>", false);
		}

		String navigation = ParamUtil.getString(_request, "navigation");

		if (navigation.equals("categorized-pages")) {
			return "there-are-no-pages-with-this-category";
		}
		else if (navigation.equals("draft-pages")) {
			return "there-are-no-drafts";
		}
		else if (navigation.equals("frontpage")) {
			WikiWebComponentProvider wikiWebComponentProvider =
				WikiWebComponentProvider.getWikiWebComponentProvider();

			WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
				wikiWebComponentProvider.getWikiGroupServiceConfiguration();

			return LanguageUtil.format(
				_request, "there-is-no-x",
				new String[] {wikiGroupServiceConfiguration.frontPageName()},
				false);
		}
		else if (navigation.equals("incoming-links")) {
			return "there-are-no-pages-that-link-to-this-page";
		}
		else if (navigation.equals("orphan-pages")) {
			return "there-are-no-orphan-pages";
		}
		else if (navigation.equals("outgoing-links")) {
			return "this-page-has-no-links";
		}
		else if (navigation.equals("pending-pages")) {
			return "there-are-no-pages-submitted-by-you-pending-approval";
		}
		else if (navigation.equals("recent-changes")) {
			return "there-are-no-recent-changes";
		}
		else if (navigation.equals("tagged-pages")) {
			return "there-are-no-pages-with-this-tag";
		}

		return "there-are-no-pages";
	}

	@Override
	public Menu getMenu(WikiPage wikiPage) throws PortalException {
		Menu menu = new Menu();

		menu.setDirection("left-side");
		menu.setMarkupView("lexicon");
		menu.setScroll(false);

		List<MenuItem> menuItems = new ArrayList<>();

		addEditMenuItem(menuItems, wikiPage);

		addPermissionsMenuItem(menuItems, wikiPage);

		addCopyMenuItem(menuItems, wikiPage);

		addMoveMenuItem(menuItems, wikiPage);

		addChildPageMenuItem(menuItems, wikiPage);

		addSubscriptionMenuItem(menuItems, wikiPage);

		addPrintPageMenuItem(menuItems, wikiPage);

		addDeleteMenuItem(menuItems, wikiPage);

		menu.setMenuItems(menuItems);

		return menu;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public void populateResultsAndTotal(SearchContainer searchContainer)
		throws PortalException {

		WikiPage page = (WikiPage)_request.getAttribute(WikiWebKeys.WIKI_PAGE);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String navigation = ParamUtil.getString(
			_request, "navigation", "all-pages");

		String keywords = ParamUtil.getString(_request, "keywords");

		int total = 0;
		List<WikiPage> results = new ArrayList<>();

		if (Validator.isNotNull(keywords)) {
			Indexer<WikiPage> indexer = IndexerRegistryUtil.getIndexer(
				WikiPage.class);

			SearchContext searchContext = SearchContextFactory.getInstance(
				_request);

			searchContext.setAttribute("paginationType", "more");
			searchContext.setEnd(searchContainer.getEnd());
			searchContext.setIncludeAttachments(true);
			searchContext.setIncludeDiscussions(true);
			searchContext.setKeywords(keywords);
			searchContext.setNodeIds(new long[] {_wikiNode.getNodeId()});
			searchContext.setStart(searchContainer.getStart());

			Hits hits = indexer.search(searchContext);

			searchContainer.setTotal(hits.getLength());

			List<SearchResult> searchResults =
				SearchResultUtil.getSearchResults(
					hits, themeDisplay.getLocale());

			for (SearchResult searchResult : searchResults) {
				WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(
					searchResult.getClassPK());

				results.add(wikiPage);
			}
		}
		else if (navigation.equals("all-pages")) {
			total = WikiPageServiceUtil.getPagesCount(
				themeDisplay.getScopeGroupId(), _wikiNode.getNodeId(), true,
				themeDisplay.getUserId(), true,
				WorkflowConstants.STATUS_APPROVED);

			searchContainer.setTotal(total);

			OrderByComparator<WikiPage> obc =
				WikiPortletUtil.getPageOrderByComparator(
					searchContainer.getOrderByCol(),
					searchContainer.getOrderByType());

			results = WikiPageServiceUtil.getPages(
				themeDisplay.getScopeGroupId(), _wikiNode.getNodeId(), true,
				themeDisplay.getUserId(), true,
				WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(),
				searchContainer.getEnd(), obc);
		}
		else if (navigation.equals("categorized-pages") ||
				 navigation.equals("tagged-pages")) {

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(
				WikiPage.class.getName(), searchContainer);

			assetEntryQuery.setEnablePermissions(true);

			total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

			searchContainer.setTotal(total);

			assetEntryQuery.setEnd(searchContainer.getEnd());
			assetEntryQuery.setStart(searchContainer.getStart());

			List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(
				assetEntryQuery);

			for (AssetEntry assetEntry : assetEntries) {
				WikiPageResource pageResource =
					WikiPageResourceLocalServiceUtil.getPageResource(
						assetEntry.getClassPK());

				WikiPage assetPage = WikiPageLocalServiceUtil.getPage(
					pageResource.getNodeId(), pageResource.getTitle());

				results.add(assetPage);
			}
		}
		else if (navigation.equals("draft-pages") ||
				 navigation.equals("pending-pages")) {

			long draftUserId = themeDisplay.getUserId();

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			if (permissionChecker.isContentReviewer(
					themeDisplay.getCompanyId(),
					themeDisplay.getScopeGroupId())) {

				draftUserId = 0;
			}

			int status = WorkflowConstants.STATUS_DRAFT;

			if (navigation.equals("pending-pages")) {
				status = WorkflowConstants.STATUS_PENDING;
			}

			total = WikiPageServiceUtil.getPagesCount(
				themeDisplay.getScopeGroupId(), draftUserId,
				_wikiNode.getNodeId(), status);

			searchContainer.setTotal(total);

			results = WikiPageServiceUtil.getPages(
				themeDisplay.getScopeGroupId(), draftUserId,
				_wikiNode.getNodeId(), status, searchContainer.getStart(),
				searchContainer.getEnd());
		}
		else if (navigation.equals("frontpage")) {
			WikiWebComponentProvider wikiWebComponentProvider =
				WikiWebComponentProvider.getWikiWebComponentProvider();

			WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
				wikiWebComponentProvider.getWikiGroupServiceConfiguration();

			WikiPage wikiPage = WikiPageServiceUtil.getPage(
				themeDisplay.getScopeGroupId(), _wikiNode.getNodeId(),
				wikiGroupServiceConfiguration.frontPageName());

			searchContainer.setTotal(1);

			results.add(wikiPage);
		}
		else if (navigation.equals("history")) {
			total = WikiPageLocalServiceUtil.getPagesCount(
				page.getNodeId(), page.getTitle());

			searchContainer.setTotal(total);

			results = WikiPageLocalServiceUtil.getPages(
				page.getNodeId(), page.getTitle(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new PageVersionComparator());
		}
		else if (navigation.equals("incoming-links")) {
			List<WikiPage> links = WikiPageLocalServiceUtil.getIncomingLinks(
				page.getNodeId(), page.getTitle());

			total = links.size();

			searchContainer.setTotal(total);

			results = ListUtil.subList(
				links, searchContainer.getStart(), searchContainer.getEnd());
		}
		else if (navigation.equals("orphan-pages")) {
			List<WikiPage> orphans = WikiPageServiceUtil.getOrphans(
				themeDisplay.getScopeGroupId(), _wikiNode.getNodeId());

			total = orphans.size();

			searchContainer.setTotal(total);

			results = ListUtil.subList(
				orphans, searchContainer.getStart(), searchContainer.getEnd());
		}
		else if (navigation.equals("outgoing-links")) {
			List<WikiPage> links = WikiPageLocalServiceUtil.getOutgoingLinks(
				page.getNodeId(), page.getTitle());

			total = links.size();

			searchContainer.setTotal(total);

			results = ListUtil.subList(
				links, searchContainer.getStart(), searchContainer.getEnd());
		}
		else if (navigation.equals("recent-changes")) {
			total = WikiPageServiceUtil.getRecentChangesCount(
				themeDisplay.getScopeGroupId(), _wikiNode.getNodeId());

			searchContainer.setTotal(total);

			results = WikiPageServiceUtil.getRecentChanges(
				themeDisplay.getScopeGroupId(), _wikiNode.getNodeId(),
				searchContainer.getStart(), searchContainer.getEnd());
		}

		searchContainer.setResults(results);
	}

	protected void addChildPageMenuItem(
			List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		if (!WikiNodePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage.getNodeId(),
				ActionKeys.ADD_PAGE)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setKey(WikiUIItemKeys.ADD_CHILD_PAGE);
		urlMenuItem.setLabel("add-child-page");

		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
		portletURL.setParameter("redirect", _wikiRequestHelper.getCurrentURL());
		portletURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
		portletURL.setParameter("title", StringPool.BLANK);
		portletURL.setParameter("editTitle", "1");
		portletURL.setParameter("parentTitle", wikiPage.getTitle());

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addCopyMenuItem(List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		if (!isCopyPasteEnabled(wikiPage)) {
			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setKey(WikiUIItemKeys.COPY);
		urlMenuItem.setLabel("copy");

		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
		portletURL.setParameter("redirect", _wikiRequestHelper.getCurrentURL());
		portletURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
		portletURL.setParameter("title", StringPool.BLANK);
		portletURL.setParameter("editTitle", "1");
		portletURL.setParameter(
			"templateNodeId", String.valueOf(wikiPage.getNodeId()));
		portletURL.setParameter(
			"templateTitle", HtmlUtil.unescape(wikiPage.getTitle()));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addDeleteMenuItem(
			List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		if (!wikiPage.isDraft() &&
			WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage.getNodeId(),
				HtmlUtil.unescape(wikiPage.getTitle()), ActionKeys.DELETE)) {

			DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

			deleteMenuItem.setKey(WikiUIItemKeys.DELETE);
			deleteMenuItem.setTrash(
				TrashUtil.isTrashEnabled(_wikiRequestHelper.getScopeGroupId()));

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createActionURL();

			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/wiki/edit_page");

			String cmd = Constants.DELETE;

			if (TrashUtil.isTrashEnabled(
					_wikiRequestHelper.getScopeGroupId())) {

				cmd = Constants.MOVE_TO_TRASH;
			}

			portletURL.setParameter(Constants.CMD, cmd);

			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"title", HtmlUtil.unescape(wikiPage.getTitle()));

			deleteMenuItem.setURL(portletURL.toString());

			menuItems.add(deleteMenuItem);
		}

		if (wikiPage.isDraft() &&
			WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.DELETE)) {

			URLMenuItem urlMenuItem = new URLMenuItem();

			urlMenuItem.setKey(WikiUIItemKeys.DELETE);
			urlMenuItem.setLabel("discard-draft");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createActionURL();

			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/wiki/edit_page");
			portletURL.setParameter(Constants.CMD, Constants.DELETE);
			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"title", HtmlUtil.unescape(wikiPage.getTitle()));
			portletURL.setParameter(
				"version", String.valueOf(wikiPage.getVersion()));

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
	}

	protected void addEditMenuItem(
		List<MenuItem> menuItems, WikiPage wikiPage) {

		if (!WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.UPDATE)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setKey(WikiUIItemKeys.EDIT);
		urlMenuItem.setLabel("edit");

		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
		portletURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
		portletURL.setParameter(
			"title", HtmlUtil.unescape(wikiPage.getTitle()));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addMoveMenuItem(List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		if (!isCopyPasteEnabled(wikiPage)) {
			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setKey(WikiUIItemKeys.MOVE);
		urlMenuItem.setLabel("move");

		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/wiki/move_page");
		portletURL.setParameter("redirect", _wikiRequestHelper.getCurrentURL());
		portletURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
		portletURL.setParameter(
			"title", HtmlUtil.unescape(wikiPage.getTitle()));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addPermissionsMenuItem(
		List<MenuItem> menuItems, WikiPage wikiPage) {

		if (!WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.PERMISSIONS)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setKey(WikiUIItemKeys.PERMISSIONS);
		urlMenuItem.setLabel("permissions");
		urlMenuItem.setMethod("get");
		urlMenuItem.setUseDialog(true);

		String url = null;

		try {
			url = PermissionsURLTag.doTag(
				null, WikiPage.class.getName(), wikiPage.getTitle(), null,
				String.valueOf(wikiPage.getResourcePrimKey()),
				LiferayWindowState.POP_UP.toString(), null, _request);
		}
		catch (Exception e) {
			throw new SystemException("Unable to create permissions URL", e);
		}

		urlMenuItem.setURL(url);

		menuItems.add(urlMenuItem);
	}

	protected void addPrintPageMenuItem(
			List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		try {
			JavaScriptMenuItem javascriptMenuItem = new JavaScriptMenuItem();

			javascriptMenuItem.setKey(WikiUIItemKeys.PRINT);
			javascriptMenuItem.setLabel("print");

			StringBundler sb = new StringBundler(5);

			sb.append("window.open('");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			WikiNode wikiNode = wikiPage.getNode();

			portletURL.setParameter("mvcRenderCommandName", "/wiki/view");
			portletURL.setParameter("nodeName", wikiNode.getName());
			portletURL.setParameter("title", wikiPage.getTitle());
			portletURL.setParameter("viewMode", Constants.PRINT);
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			sb.append(portletURL.toString());

			sb.append("', '', 'directories=0,height=480,left=80,location=1,");
			sb.append("menubar=1,resizable=1,scrollbars=yes,status=0,");
			sb.append("toolbar=0,top=180,width=640');");

			javascriptMenuItem.setOnClick(sb.toString());

			menuItems.add(javascriptMenuItem);
		}
		catch (WindowStateException wse) {
		}
	}

	protected void addSubscriptionMenuItem(
		List<MenuItem> menuItems, WikiPage wikiPage) {

		ResultRow row = (ResultRow)_request.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		if (row == null) {
			return;
		}

		WikiGroupServiceOverriddenConfiguration
			wikiGroupServiceOverriddenConfiguration =
				_wikiRequestHelper.getWikiGroupServiceOverriddenConfiguration();

		if (!WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.SUBSCRIBE) ||
			(!wikiGroupServiceOverriddenConfiguration.emailPageAddedEnabled() &&
			 !wikiGroupServiceOverriddenConfiguration.
				 emailPageUpdatedEnabled())) {

			return;
		}

		User user = _wikiRequestHelper.getUser();

		if (SubscriptionLocalServiceUtil.isSubscribed(
				user.getCompanyId(), user.getUserId(), WikiPage.class.getName(),
				wikiPage.getResourcePrimKey())) {

			URLMenuItem urlMenuItem = new URLMenuItem();

			urlMenuItem.setKey(WikiUIItemKeys.UNSUBSCRIBE);
			urlMenuItem.setLabel("unsubscribe");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createActionURL();

			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/wiki/edit_page");
			portletURL.setParameter(Constants.CMD, Constants.UNSUBSCRIBE);
			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"title", HtmlUtil.unescape(wikiPage.getTitle()));

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
		else {
			URLMenuItem urlMenuItem = new URLMenuItem();

			urlMenuItem.setKey(WikiUIItemKeys.SUBSCRIBE);
			urlMenuItem.setLabel("subscribe");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createActionURL();

			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/wiki/edit_page");
			portletURL.setParameter(Constants.CMD, Constants.SUBSCRIBE);
			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"title", HtmlUtil.unescape(wikiPage.getTitle()));

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
	}

	protected boolean isCopyPasteEnabled(WikiPage wikiPage)
		throws PortalException {

		if (!WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.UPDATE)) {

			return false;
		}

		if (!WikiNodePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage.getNodeId(),
				ActionKeys.ADD_PAGE)) {

			return false;
		}

		return true;
	}

	private static final UUID _UUID = UUID.fromString(
		"628C435B-DB39-4E46-91DF-CEA763CF79F5");

	private final HttpServletRequest _request;
	private final WikiNode _wikiNode;
	private final WikiRequestHelper _wikiRequestHelper;

}