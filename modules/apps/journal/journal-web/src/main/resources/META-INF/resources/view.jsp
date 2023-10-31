<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalManagementToolbarDisplayContext journalManagementToolbarDisplayContext = null;

if (!journalDisplayContext.isSearch() || journalDisplayContext.isWebContentTabSelected()) {
	journalManagementToolbarDisplayContext = new JournalManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, journalDisplayContext, trashHelper);
}
else if (journalDisplayContext.isIndexAllArticleVersions() && journalDisplayContext.isVersionsTabSelected()) {
	journalManagementToolbarDisplayContext = new JournalArticleVersionsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, journalDisplayContext, trashHelper);
}
else if (journalDisplayContext.isCommentsTabSelected()) {
	journalManagementToolbarDisplayContext = new JournalArticleCommentsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, journalDisplayContext, trashHelper);
}
else {
	journalManagementToolbarDisplayContext = new JournalManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, journalDisplayContext, trashHelper);
}
%>

<liferay-ui:success key='<%= portletDisplay.getId() + "requestProcessed" %>' message="your-request-completed-successfully" />

<portlet:actionURL name="/journal/restore_trash_entries" var="restoreTrashEntriesURL" />

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= journalDisplayContext.getNavigationItems("web-content") %>'
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= journalManagementToolbarDisplayContext %>"
	propsTransformer="js/ManagementToolbarPropsTransformer"
/>

<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
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

	<clay:container-fluid
		cssClass="container-view sidenav-content"
	>

		<%
		VerticalNavItemList verticalNavItemList = journalDisplayContext.getVerticalNavDDMStructureList();
		%>

		<c:choose>
			<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-194763") && ListUtil.isNotEmpty(verticalNavItemList) %>'>
				<clay:row>
					<clay:col
						lg="3"
					>
						<clay:vertical-nav
							verticalNavItems="<%= journalDisplayContext.getVerticalNavItemList() %>"
						/>

						<span class="c-mb-1 c-mt-3 sheet-tertiary-title text-2 text-secondary">
							<liferay-ui:message key="highlighted-structures" />
						</span>

						<clay:vertical-nav
							verticalNavItems="<%= verticalNavItemList %>"
						/>
					</clay:col>

					<clay:col
						lg="9"
					>
						<clay:sheet
							size="full"
						>
							<h2 class="sheet-title"><%= journalDisplayContext.getTitle() %></h2>

							<%@ include file="/view_form.jspf" %>
						</clay:sheet>
					</clay:col>
				</clay:row>
			</c:when>
			<c:otherwise>
				<%@ include file="/view_form.jspf" %>
			</c:otherwise>
		</c:choose>
	</clay:container-fluid>
</div>

<%@ include file="/friendly_url_changed_message.jspf" %>