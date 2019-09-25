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

<%@ include file="/init.jsp" %>

<%
JournalManagementToolbarDisplayContext journalManagementToolbarDisplayContext = null;

if (!journalDisplayContext.isSearch() || journalDisplayContext.isWebContentTabSelected()) {
	journalManagementToolbarDisplayContext = new JournalManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalDisplayContext, trashHelper);
}
else if (journalDisplayContext.isVersionsTabSelected()) {
	journalManagementToolbarDisplayContext = new JournalArticleVersionsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalDisplayContext, trashHelper);
}
else if (journalDisplayContext.isCommentsTabSelected()) {
	journalManagementToolbarDisplayContext = new JournalArticleCommentsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalDisplayContext, trashHelper);
}
else {
	journalManagementToolbarDisplayContext = new JournalManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalDisplayContext, trashHelper);
}

String title = journalDisplayContext.getFolderTitle();

if (Validator.isNotNull(title)) {
	renderResponse.setTitle(journalDisplayContext.getFolderTitle());
}
%>

<portlet:actionURL name="/journal/restore_trash_entries" var="restoreTrashEntriesURL" />

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= journalDisplayContext.getNavigationBarItems("web-content") %>'
/>

<clay:management-toolbar
	displayContext="<%= journalManagementToolbarDisplayContext %>"
/>

<liferay-frontend:component
	componentId="<%= journalManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context="<%= journalManagementToolbarDisplayContext.getComponentContext() %>"
	module="js/ManagementToolbarDefaultEventHandler.es"
/>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<c:if test="<%= journalDisplayContext.isShowInfoButton() %>">
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/journal/info_panel" var="sidebarPanelURL">
			<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
		</liferay-portlet:resourceURL>

		<liferay-frontend:sidebar-panel
			resourceURL="<%= sidebarPanelURL %>"
			searchContainerId="articles"
		>
			<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
		</liferay-frontend:sidebar-panel>
	</c:if>

	<div class="sidenav-content">
		<c:if test="<%= !journalDisplayContext.isNavigationMine() && !journalDisplayContext.isNavigationRecent() %>">
			<liferay-site-navigation:breadcrumb
				breadcrumbEntries="<%= JournalPortletUtil.getPortletBreadcrumbEntries(journalDisplayContext.getFolder(), request, journalDisplayContext.getPortletURL()) %>"
			/>
		</c:if>

		<aui:form action="<%= journalDisplayContext.getPortletURL() %>" method="get" name="fm">
			<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />
			<aui:input name="newFolderId" type="hidden" />

			<c:choose>
				<c:when test="<%= !journalDisplayContext.isSearch() %>">
					<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>

					<%
					String[] tabsNames = new String[0];
					String[] tabsValues = new String[0];

					if (journalDisplayContext.hasResults()) {
						String tabName = StringUtil.appendParentheticalSuffix(LanguageUtil.get(request, "web-content"), journalDisplayContext.getTotalItems());

						tabsNames = ArrayUtil.append(tabsNames, tabName);
						tabsValues = ArrayUtil.append(tabsValues, "web-content");
					}

					if (journalDisplayContext.hasVersionsResults()) {
						String tabName = StringUtil.appendParentheticalSuffix(LanguageUtil.get(request, "versions"), journalDisplayContext.getVersionsTotal());

						tabsNames = ArrayUtil.append(tabsNames, tabName);
						tabsValues = ArrayUtil.append(tabsValues, "versions");
					}

					if (journalDisplayContext.hasCommentsResults()) {
						String tabName = StringUtil.appendParentheticalSuffix(LanguageUtil.get(request, "comments"), journalDisplayContext.getCommentsTotal());

						tabsNames = ArrayUtil.append(tabsNames, tabName);
						tabsValues = ArrayUtil.append(tabsValues, "comments");
					}
					%>

					<liferay-ui:tabs
						names="<%= StringUtil.merge(tabsNames) %>"
						portletURL="<%= journalDisplayContext.getPortletURL() %>"
						tabsValues="<%= StringUtil.merge(tabsValues) %>"
					/>

					<c:choose>
						<c:when test="<%= journalDisplayContext.isWebContentTabSelected() %>">
							<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>" />
						</c:when>
						<c:when test="<%= journalDisplayContext.isVersionsTabSelected() %>">
							<liferay-util:include page="/view_versions.jsp" servletContext="<%= application %>" />
						</c:when>
						<c:when test="<%= journalDisplayContext.isCommentsTabSelected() %>">
							<liferay-util:include page="/view_comments.jsp" servletContext="<%= application %>" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</aui:form>
	</div>
</div>