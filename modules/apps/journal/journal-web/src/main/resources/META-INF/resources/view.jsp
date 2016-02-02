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
ArticleSearch articleSearchContainer = journalDisplayContext.getSearchContainer();
%>

<portlet:actionURL name="restoreTrashEntries" var="restoreTrashEntriesURL" />

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<%
Map<String, Object> data = new HashMap<>();

data.put("qa-id", "navigation");
%>

<aui:nav-bar cssClass="collapse-basic-search" data="<%= data %>" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="web-content" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= journalDisplayContext.isShowSearch() %>">
		<aui:nav-bar-search>

			<%
			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("folderId", String.valueOf(journalDisplayContext.getFolderId()));
			portletURL.setParameter("showEditActions", String.valueOf(journalDisplayContext.isShowEditActions()));
			%>

			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="articles" />
</liferay-util:include>

<div id="<portlet:namespace />journalContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= journalDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/journal/info_panel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="articles"
			>
				<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<div class="journal-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<c:if test="<%= journalDisplayContext.isShowBreadcrumb() %>">
					<liferay-util:include page="/breadcrumb.jsp" servletContext="<%= application %>" />
				</c:if>
			</div>

			<%
			PortletURL portletURL = journalDisplayContext.getPortletURL();
			%>

			<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
				<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />
				<aui:input name="newFolderId" type="hidden" />

				<div class="journal-container" id="<portlet:namespace />entriesContainer">
					<c:choose>
						<c:when test="<%= !journalDisplayContext.isSearch() || (!journalDisplayContext.hasResults() && !journalDisplayContext.hasCommentsResults()) %>">
							<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>">
								<liferay-util:param name="searchContainerId" value="articles" />
							</liferay-util:include>
						</c:when>
						<c:otherwise>

							<%
							String[] tabsNames = new String[0];

							if (journalDisplayContext.hasResults()) {
								String tabName = StringUtil.appendParentheticalSuffix(LanguageUtil.get(request, "web-content"), journalDisplayContext.getTotal());

								tabsNames = ArrayUtil.append(tabsNames, tabName);
							}

							if (journalDisplayContext.hasCommentsResults()) {
								String tabName = StringUtil.appendParentheticalSuffix(LanguageUtil.get(request, "comments"), journalDisplayContext.getCommentsTotal());

								tabsNames = ArrayUtil.append(tabsNames, tabName);
							}
							%>

							<liferay-ui:tabs
								names="<%= StringUtil.merge(tabsNames) %>"
								portletURL="<%= portletURL %>"
								type="tabs nav-tabs-default"
							>
								<c:if test="<%= journalDisplayContext.hasResults() %>">
									<liferay-ui:section>
										<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>">
											<liferay-util:param name="searchContainerId" value="articles" />
										</liferay-util:include>
									</liferay-ui:section>
								</c:if>

								<c:if test="<%= journalDisplayContext.hasCommentsResults() %>">
									<liferay-ui:section>
										<liferay-util:include page="/view_comments.jsp" servletContext="<%= application %>">
											<liferay-util:param name="searchContainerId" value="comments" />
										</liferay-util:include>
									</liferay-ui:section>
								</c:if>
							</liferay-ui:tabs>
						</c:otherwise>
					</c:choose>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<c:if test="<%= !journalDisplayContext.isSearch() %>">
	<liferay-util:include page="/add_button.jsp" servletContext="<%= application %>" />
</c:if>

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

<aui:script use="liferay-journal-navigation">
	var journalNavigation = new Liferay.Portlet.JournalNavigation(
		{
			editEntryUrl: '<portlet:actionURL />',
			form: {
					method: 'POST',
					node: A.one(document.<portlet:namespace />fm)
			},
			moveEntryUrl: '<portlet:renderURL><portlet:param name="mvcPath" value="/move_entries.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>',
			namespace: '<portlet:namespace />',
			searchContainerId: 'articles'
		}
	);

	var clearJournalNavigationHandles = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			journalNavigation.destroy();

			Liferay.detach('destroyPortlet', clearJournalNavigationHandles);
		}
	};

	Liferay.on('destroyPortlet', clearJournalNavigationHandles);
</aui:script>