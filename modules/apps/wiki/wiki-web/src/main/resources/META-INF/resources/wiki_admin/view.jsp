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

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/wiki_admin/view");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(WikiPortletKeys.WIKI_ADMIN, "nodes-display-style", "descriptive");
}
else {
	portalPreferences.setValue(WikiPortletKeys.WIKI_ADMIN, "nodes-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol)) {
	portalPreferences.setValue(WikiPortletKeys.WIKI_ADMIN, "nodes-order-by-col", orderByCol);
}
else {
	orderByCol = portalPreferences.getValue(WikiPortletKeys.WIKI_ADMIN, "nodes-order-by-col", "lastPostDate");
}

if (Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(WikiPortletKeys.WIKI_ADMIN, "nodes-order-by-type", orderByType);
}
else {
	orderByType = portalPreferences.getValue(WikiPortletKeys.WIKI_ADMIN, "nodes-order-by-type", "desc");
}

request.setAttribute("view.jsp-orderByCol", orderByCol);
request.setAttribute("view.jsp-orderByType", orderByType);
%>

<portlet:actionURL name="/wiki/edit_node" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<%
SearchContainer wikiNodesSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-wikis");

NodesChecker nodesChecker = new NodesChecker(liferayPortletRequest, liferayPortletResponse);

wikiNodesSearchContainer.setRowChecker(nodesChecker);
wikiNodesSearchContainer.setOrderByCol(orderByCol);
wikiNodesSearchContainer.setOrderByComparator(WikiPortletUtil.getNodeOrderByComparator(orderByCol, orderByType));
wikiNodesSearchContainer.setOrderByType(orderByType);
wikiNodesSearchContainer.setTotal(WikiNodeServiceUtil.getNodesCount(scopeGroupId));

wikiNodesSearchContainer.setResults(WikiNodeServiceUtil.getNodes(scopeGroupId, WorkflowConstants.STATUS_APPROVED, wikiNodesSearchContainer.getStart(), wikiNodesSearchContainer.getEnd(), wikiNodesSearchContainer.getOrderByComparator()));

WikiNodesManagementToolbarDisplayContext wikiNodesManagementToolbarDisplayContext = new WikiNodesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, displayStyle, wikiNodesSearchContainer, trashHelper);
%>

<clay:management-toolbar
	actionDropdownItems="<%= wikiNodesManagementToolbarDisplayContext.getActionDropdownItems() %>"
	componentId="wikiNodesManagementToolbar"
	creationMenu="<%= wikiNodesManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= wikiNodesManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= wikiNodesManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	itemsTotal="<%= wikiNodesManagementToolbarDisplayContext.getTotalItems() %>"
	searchContainerId="wikiNodes"
	selectable="<%= wikiNodesManagementToolbarDisplayContext.isSelectable() %>"
	showInfoButton="<%= true %>"
	showSearch="<%= wikiNodesManagementToolbarDisplayContext.isShowSearch() %>"
	sortingOrder="<%= wikiNodesManagementToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= String.valueOf(wikiNodesManagementToolbarDisplayContext.getSortingURL()) %>"
	viewTypeItems="<%= wikiNodesManagementToolbarDisplayContext.getViewTypes() %>"
/>

<div class="closed container-fluid container-fluid-max-xl sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
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

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "wiki"), portletURL.toString());
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
				searchContainer="<%= wikiNodesSearchContainer %>"
				total="<%= wikiNodesSearchContainer.getTotal() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.wiki.model.WikiNode"
					keyProperty="nodeId"
					modelVar="node"
				>

					<%
					Map<String, Object> rowData = new HashMap<>();

					rowData.put("actions", StringUtil.merge(wikiNodesManagementToolbarDisplayContext.getAvailableActions(node)));

					row.setData(rowData);

					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcRenderCommandName", "/wiki/view_pages");
					rowURL.setParameter("navigation", "all-pages");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
					%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("descriptive") %>'>
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
										<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - lastPostDate.getTime(), true)} %>" key="last-post-x-ago" />
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
					displayStyle="<%= displayStyle %>"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>

<script>
	var deleteNodes = function() {
		if (
			<%= trashHelper.isTrashEnabled(scopeGroupId) %> ||
			confirm(
				' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>'
			)
		) {
			var form = document.<portlet:namespace />fm;

			Liferay.Util.postForm(form, {
				data: {
					<%= Constants.CMD %>:
						'<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>'
				},
				url: '<portlet:actionURL name="/wiki/edit_node" />'
			});
		}
	};

	var ACTIONS = {
		deleteNodes: deleteNodes
	};

	Liferay.componentReady('wikiNodesManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</script>