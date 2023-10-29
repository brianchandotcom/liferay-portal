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
		<c:choose>
			<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-194763") %>'>
				<clay:row>
					<clay:col
						lg="3"
					>
						<clay:vertical-nav
							verticalNavItems="<%= journalDisplayContext.getVerticalNavItemList() %>"
						/>

						<c:if test="<%= ListUtil.isNotEmpty(journalDisplayContext.getVerticalNavigationDDMStructures()) %>">
							<span class="c-mb-1 c-mt-3 sheet-tertiary-title text-2 text-secondary">
								<liferay-ui:message key="highlighted-structures" />
							</span>

							<clay:vertical-nav
								verticalNavItems="<%= journalDisplayContext.getVerticalNavDDMStructureList() %>"
							/>
						</c:if>
					</clay:col>

					<clay:col
						lg="9"
					>
						<clay:sheet
							size="full"
						>
							<h2 class="sheet-title"><%= journalDisplayContext.getTitle() %></h2>

							<aui:form action="<%= journalDisplayContext.getPortletURL(null) %>" method="get" name="fm">
								<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" />
								<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
								<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />
								<aui:input name="newFolderId" type="hidden" />

								<c:choose>
									<c:when test="<%= !journalDisplayContext.isSearch() %>">
										<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>" />
									</c:when>
									<c:otherwise>
										<clay:navigation-bar
											cssClass="mt-4"
											navigationItems="<%= journalDisplayContext.getSearchNavigationItems() %>"
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
						</clay:sheet>
					</clay:col>
				</clay:row>
			</c:when>
			<c:otherwise>
				<c:if test="<%= !journalDisplayContext.isNavigationMine() && !journalDisplayContext.isNavigationRecent() && !journalDisplayContext.isSearch() %>">
					<liferay-site-navigation:breadcrumb
						breadcrumbEntries="<%= JournalPortletUtil.getPortletBreadcrumbEntries(journalDisplayContext.getFolder(), request, false, liferayPortletResponse) %>"
					/>
				</c:if>

				<aui:form action="<%= journalDisplayContext.getPortletURL(null) %>" method="get" name="fm">
					<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />
					<aui:input name="newFolderId" type="hidden" />

					<c:choose>
						<c:when test="<%= !journalDisplayContext.isSearch() %>">
							<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>" />
						</c:when>
						<c:otherwise>
							<clay:navigation-bar
								cssClass="mt-4"
								navigationItems="<%= journalDisplayContext.getSearchNavigationItems() %>"
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
			</c:otherwise>
		</c:choose>
	</clay:container-fluid>
</div>

<%@ include file="/friendly_url_changed_message.jspf" %>