<%--
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
--%>

<%@ include file="/bookmarks/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "all");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", rootFolderId);

String keywords = ParamUtil.getString(request, "keywords");

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (Validator.isNotNull(keywords) && portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);

	String redirect = ParamUtil.getString(request, "redirect");

	portletDisplay.setURLBack(redirect);

	String headerTitle = LanguageUtil.get(resourceBundle, "search");

	renderResponse.setTitle(headerTitle);
}

boolean defaultFolderView = false;

if ((folder == null) && (folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	defaultFolderView = true;
}

if (defaultFolderView) {
	try {
		folder = BookmarksFolderServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = (assetCategoryId > 0) || Validator.isNotNull(assetTagName);

String displayStyle = ParamUtil.getString(request, "displayStyle");

String[] displayViews = new String[] {"descriptive", "list"};

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(BookmarksPortletKeys.BOOKMARKS, "display-style", "descriptive");
}
else {
	if (ArrayUtil.contains(displayViews, displayStyle)) {
		portalPreferences.setValue(BookmarksPortletKeys.BOOKMARKS, "display-style", displayStyle);

		request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
	}
}

if (!ArrayUtil.contains(displayViews, displayStyle)) {
	displayStyle = displayViews[0];
}

PortletURL portletURL = renderResponse.createRenderURL();

if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	portletURL.setParameter("mvcRenderCommandName", "/bookmarks/view");
}
else {
	portletURL.setParameter("mvcRenderCommandName", "/bookmarks/view_folder");
	portletURL.setParameter("folderId", String.valueOf(folderId));
}

portletURL.setParameter("navigation", navigation);

SearchContainer bookmarksSearchContainer = new SearchContainer(liferayPortletRequest, null, null, "curEntry", SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-bookmarks-in-this-folder");

int total = 0;

if (Validator.isNotNull(keywords)) {
	Indexer<?> indexer = BookmarksSearcher.getInstance();

	SearchContext searchContext = SearchContextFactory.getInstance(request);

	searchContext.setAttribute("paginationType", "more");
	searchContext.setEnd(bookmarksSearchContainer.getEnd());
	searchContext.setFolderIds(new long[] {folderId});
	searchContext.setKeywords(keywords);
	searchContext.setStart(bookmarksSearchContainer.getStart());

	Hits hits = indexer.search(searchContext);

	total = hits.getLength();

	bookmarksSearchContainer.setTotal(total);
	bookmarksSearchContainer.setResults(BookmarksUtil.getEntries(hits));
}
else if (navigation.equals("mine") || navigation.equals("recent")) {
	long groupEntriesUserId = 0;

	if (navigation.equals("mine") && themeDisplay.isSignedIn()) {
		groupEntriesUserId = user.getUserId();
	}

	total = BookmarksEntryServiceUtil.getGroupEntriesCount(scopeGroupId, groupEntriesUserId);

	bookmarksSearchContainer.setTotal(total);
	bookmarksSearchContainer.setResults(BookmarksEntryServiceUtil.getGroupEntries(scopeGroupId, groupEntriesUserId, bookmarksSearchContainer.getStart(), bookmarksSearchContainer.getEnd()));
}
else if (useAssetEntryQuery) {
	AssetEntryQuery assetEntryQuery = new AssetEntryQuery(BookmarksEntry.class.getName(), bookmarksSearchContainer);

	assetEntryQuery.setEnablePermissions(true);
	assetEntryQuery.setExcludeZeroViewCount(false);
	assetEntryQuery.setEnd(bookmarksSearchContainer.getEnd());
	assetEntryQuery.setStart(bookmarksSearchContainer.getStart());

	if (Validator.isNotNull(keywords)) {
		assetEntryQuery.setKeywords(keywords);
	}

	total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

	bookmarksSearchContainer.setTotal(total);
	bookmarksSearchContainer.setResults(AssetEntryServiceUtil.getEntries(assetEntryQuery));
}
else {
	total = BookmarksFolderServiceUtil.getFoldersAndEntriesCount(scopeGroupId, folderId);

	bookmarksSearchContainer.setTotal(total);
	bookmarksSearchContainer.setResults(BookmarksFolderServiceUtil.getFoldersAndEntries(scopeGroupId, folderId, WorkflowConstants.STATUS_APPROVED, bookmarksSearchContainer.getStart(), bookmarksSearchContainer.getEnd()));
}

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-displayStyle", displayStyle);

request.setAttribute("view.jsp-bookmarksSearchContainer", bookmarksSearchContainer);

request.setAttribute("view.jsp-total", String.valueOf(total));

BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
%>

<portlet:actionURL name="/bookmarks/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-util:include page="/bookmarks/navigation.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/bookmarks/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="entries" />
</liferay-util:include>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<portlet:resourceURL id="/bookmarks/info_panel" var="sidebarPanelURL">
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
	</portlet:resourceURL>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="entries"
	>
		<liferay-util:include page="/bookmarks/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<div class="bookmakrs-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
			<c:if test='<%= !navigation.equals("recent") && !navigation.equals("mine") %>'>
				<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />
			</c:if>
		</div>

		<liferay-portlet:actionURL varImpl="editEntryURL">
			<portlet:param name="mvcRenderCommandName" value="/bookmarks/edit_entry" />
		</liferay-portlet:actionURL>

		<aui:form action="<%= editEntryURL.toString() %>" method="get" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="newFolderId" type="hidden" />

			<liferay-util:include page="/bookmarks/view_entries.jsp" servletContext="<%= application %>">
				<liferay-util:param name="searchContainerId" value="entries" />
			</liferay-util:include>
		</aui:form>
	</div>
</div>

<c:if test="<%= portletName.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN) %>">
	<liferay-util:include page="/bookmarks/add_button.jsp" servletContext="<%= application %>" />
</c:if>

<%
if (navigation.equals("all") && !defaultFolderView && (folder != null) && (portletName.equals(BookmarksPortletKeys.BOOKMARKS) || portletName.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN))) {
	PortalUtil.setPageSubtitle(folder.getName(), request);
	PortalUtil.setPageDescription(folder.getDescription(), request);
}
else {
	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, navigation), currentURL);
	}

	PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(navigation, StringPool.UNDERLINE, StringPool.DASH)), request);
}
%>

<aui:script>
	$('#<portlet:namespace />infoPanelId').sideNavigation(
		{
			gutter: 15,
			position: 'right',
			toggler: '.infoPanelToggler',
			type: 'relative',
			typeMobile: 'fixed',
			width: 320
		}
	);
</aui:script>

<aui:script use="liferay-bookmarks">
	var bookmarks = new Liferay.Portlet.Bookmarks(
		{
			editEntryUrl: '<portlet:actionURL name="/bookmarks/edit_entry" />',
			form: {
				method: 'POST',
				node: A.one(document.<portlet:namespace />fm)
			},
			moveEntryUrl: '<portlet:renderURL><portlet:param name="mvcRenderCommandName" value="/bookmarks/move_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>',
			namespace: '<portlet:namespace />',
			searchContainerId: 'entries'
		}
	);
</aui:script>