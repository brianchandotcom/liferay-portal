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

<%@ include file="/wiki/init.jsp" %>

<%
WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);

WikiNodesManagementToolbarDisplayContext wikiNodesManagementToolbarDisplayContext = new WikiNodesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, renderRequest, trashHelper);

request.setAttribute("view.jsp-orderByCol", wikiNodesManagementToolbarDisplayContext.getOrderByCol());
request.setAttribute("view.jsp-orderByType", wikiNodesManagementToolbarDisplayContext.getOrderByType());
%>

<portlet:actionURL name="/wiki/edit_node" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= wikiNodesManagementToolbarDisplayContext.getActionDropdownItems() %>"
	additionalProps="<%= wikiNodesManagementToolbarDisplayContext.getAdditionalProps() %>"
	creationMenu="<%= wikiNodesManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= wikiNodesManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= wikiNodesManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	itemsTotal="<%= wikiNodesManagementToolbarDisplayContext.getTotalItems() %>"
	propsTransformer="wiki_admin/js/WikiNodesManagementToolbarPropsTransformer"
	searchContainerId="wikiNodes"
	selectable="<%= wikiNodesManagementToolbarDisplayContext.isSelectable() %>"
	showInfoButton="<%= true %>"
	showSearch="<%= wikiNodesManagementToolbarDisplayContext.isShowSearch() %>"
	sortingOrder="<%= wikiNodesManagementToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= String.valueOf(wikiNodesManagementToolbarDisplayContext.getSortingURL()) %>"
	viewTypeItems="<%= wikiNodesManagementToolbarDisplayContext.getViewTypes() %>"
/>

<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/wiki/node_info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="wikiNodes"
	>

		<%
		request.removeAttribute(WikiWebKeys.WIKI_NODE);
		%>

		<liferay-util:include page="/wiki_admin/node_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<clay:container-fluid
			cssClass="container-view"
		>

			<%
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "wiki"), String.valueOf(wikiNodesManagementToolbarDisplayContext.getPortletURL()));
			%>

			<liferay-ui:breadcrumb
				showCurrentGroup="<%= false %>"
				showGuestGroup="<%= false %>"
				showLayout="<%= false %>"
				showParentGroups="<%= false %>"
			/>

			<liferay-trash:undo
				portletURL="<%= restoreTrashEntriesURL %>"
			/>

			<liferay-ui:error exception="<%= RequiredNodeException.class %>" message="the-last-main-node-is-required-and-cannot-be-deleted" />

			<aui:form action="<%= wikiURLHelper.getSearchURL() %>" method="get" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<liferay-ui:search-container
					id="wikiNodes"
					searchContainer="<%= wikiNodesManagementToolbarDisplayContext.getSearchContainer() %>"
					total="<%= wikiNodesManagementToolbarDisplayContext.getSearchContainerTotal() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.wiki.model.WikiNode"
						keyProperty="nodeId"
						modelVar="node"
					>

						<%
						row.setData(
							HashMapBuilder.<String, Object>put(
								"actions", StringUtil.merge(wikiNodesManagementToolbarDisplayContext.getAvailableActions(node))
							).build());

						PortletURL rowURL = PortletURLBuilder.createRenderURL(
							renderResponse
						).setMVCRenderCommandName(
							"/wiki/view_pages"
						).setRedirect(
							currentURL
						).setNavigation(
							"all-pages"
						).setParameter(
							"nodeId", node.getNodeId()
						).buildPortletURL();
						%>

						<c:choose>
							<c:when test='<%= Objects.equals(wikiNodesManagementToolbarDisplayContext.getDisplayStyle(), "descriptive") %>'>
								<liferay-ui:search-container-column-icon
									icon="wiki"
									toggleRowChecker="<%= true %>"
								/>

								<liferay-ui:search-container-column-text
									colspan="<%= 2 %>"
								>
									<p class="h5">
										<aui:a href="<%= rowURL.toString() %>">
											<%= HtmlUtil.escape(node.getName()) %>
										</aui:a>
									</p>

									<%
									Date lastPostDate = node.getLastPostDate();
									%>

									<c:if test="<%= lastPostDate != null %>">
										<span class="text-default">
											<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - lastPostDate.getTime(), true) %>" key="last-post-x-ago" />
										</span>
									</c:if>

									<span class="text-default">
										<liferay-ui:message arguments="<%= String.valueOf(WikiPageServiceUtil.getPagesCount(scopeGroupId, node.getNodeId(), true)) %>" key="x-pages" />
									</span>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-jsp
									path="/wiki/node_action.jsp"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand table-cell-minw-200 table-title"
									href="<%= rowURL %>"
									name="wiki"
									value="<%= HtmlUtil.escape(node.getName()) %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-small"
									name="num-of-pages"
									value="<%= String.valueOf(WikiPageServiceUtil.getPagesCount(scopeGroupId, node.getNodeId(), true)) %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-smaller table-cell-ws-nowrap"
									name="last-post-date"
									value='<%= (node.getLastPostDate() == null) ? LanguageUtil.get(request, "never") : dateFormatDateTime.format(node.getLastPostDate()) %>'
								/>

								<liferay-ui:search-container-column-jsp
									path="/wiki/node_action.jsp"
								/>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="<%= wikiNodesManagementToolbarDisplayContext.getDisplayStyle() %>"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</aui:form>
		</clay:container-fluid>
	</div>
</div>