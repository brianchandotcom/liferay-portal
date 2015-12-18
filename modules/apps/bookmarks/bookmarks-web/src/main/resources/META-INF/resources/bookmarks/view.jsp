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
String navigation = ParamUtil.getString(request, "navigation", "home");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", rootFolderId);

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

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());

request.setAttribute("view.jsp-displayStyle", displayStyle);

request.setAttribute("view.jsp-useAssetEntryQuery", String.valueOf(useAssetEntryQuery));

BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
%>

<portlet:actionURL name="/bookmarks/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-util:include page="/bookmarks/navigation.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/bookmarks/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="entries" />
</liferay-util:include>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<div class="sidenav-menu-slider">
		<div class="sidebar sidebar-default sidenav-menu">
			<liferay-util:include page="/bookmarks/info_panel.jsp" servletContext="<%= application %>" />
		</div>
	</div>
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
if (navigation.equals("home") && !defaultFolderView && (folder != null) && (portletName.equals(BookmarksPortletKeys.BOOKMARKS) || portletName.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN))) {
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