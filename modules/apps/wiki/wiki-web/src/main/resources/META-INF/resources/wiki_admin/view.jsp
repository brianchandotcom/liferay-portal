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

List<String> headerNames = new ArrayList<String>();

headerNames.add("wiki");
headerNames.add("num-of-pages");
headerNames.add("last-post-date");
headerNames.add(StringPool.BLANK);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(WikiPortletKeys.WIKI_ADMIN, "nodes-display-style", "descriptive");
}
else {
	portalPreferences.setValue(WikiPortletKeys.WIKI_ADMIN, "nodes-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}
%>

<portlet:actionURL name="/wiki/edit_node" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewNodesURL">
			<portlet:param name="mvcRenderCommandName" value="/wiki_admin/view" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewNodesURL %>"
			label="wikis"
			selected="<%= true %>"
		/>
	</aui:nav>
</aui:nav-bar>

<%
int nodesCount = WikiNodeServiceUtil.getNodesCount(scopeGroupId);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= nodesCount > 0 %>"
	searchContainerId="wikiNodes"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive", "list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:trash-undo
		portletURL="<%= restoreTrashEntriesURL %>"
	/>

	<liferay-ui:error exception="<%= RequiredNodeException.class %>" message="the-last-main-node-is-required-and-cannot-be-deleted" />

	<aui:form action="<%= wikiURLHelper.getSearchURL() %>" method="get" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<%
		SearchContainer wikiNodesSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-wikis");

		NodesChecker nodesChecker = new NodesChecker(liferayPortletRequest, liferayPortletResponse);

		wikiNodesSearchContainer.setRowChecker(nodesChecker);
		%>

		<liferay-ui:search-container
			id="wikiNodes"
			searchContainer="<%= wikiNodesSearchContainer %>"
			total="<%= nodesCount %>"
		>
			<liferay-ui:search-container-results
				results="<%= WikiNodeServiceUtil.getNodes(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.wiki.model.WikiNode"
				keyProperty="nodeId"
				modelVar="result"
			>
				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							icon="folder"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="2"
							path="/wiki_admin/view_node_descriptive.jsp"
						/>

						<liferay-ui:search-container-column-jsp
							cssClass="entry-action"
							path="/wiki/node_action.jsp"
						/>
					</c:when>
					<c:otherwise>

						<%
						WikiNode node = (WikiNode)result;

						PortletURL rowURL = renderResponse.createRenderURL();

						rowURL.setParameter("mvcRenderCommandName", "/wiki/view_all_pages");
						rowURL.setParameter("redirect", currentURL);
						rowURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
						%>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="wiki"
							value="<%= node.getName() %>"
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="num-of-pages"
							value="<%= String.valueOf(WikiPageServiceUtil.getPagesCount(scopeGroupId, node.getNodeId(), true)) %>"
						/>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="last-post-date"
							value='<%= (node.getLastPostDate() == null) ? LanguageUtil.get(request, "never") : dateFormatDateTime.format(node.getLastPostDate()) %>'
						/>

						<liferay-ui:search-container-column-jsp
							cssClass="entry-action"
							path="/wiki/node_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<%
boolean showAddNodeButton = WikiResourcePermissionChecker.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_NODE);
%>

<c:if test="<%= showAddNodeButton %>">
	<portlet:renderURL var="viewNodesURL">
		<portlet:param name="mvcRenderCommandName" value="/wiki_admin/view" />
	</portlet:renderURL>

	<portlet:renderURL var="addNodeURL">
		<portlet:param name="mvcRenderCommandName" value="/wiki/edit_node" />
		<portlet:param name="redirect" value="<%= viewNodesURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-wiki") %>' url="<%= addNodeURL %>" />
	</liferay-frontend:add-menu>
</c:if>