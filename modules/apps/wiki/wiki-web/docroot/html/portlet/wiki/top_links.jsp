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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

List<WikiNode> nodes = WikiUtil.getNodes(wikiPortletInstanceSettingsHelper.getAllNodes(), wikiPortletInstanceSettings.getHiddenNodes(), permissionChecker);

boolean print = ParamUtil.getString(request, "viewMode").equals(Constants.PRINT);

WikiVisualizationHelper wikiVisualizationHelper = new WikiVisualizationHelper(wikiRequestHelper, wikiServiceConfiguration);
WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiServiceConfiguration);
%>

<c:if test="<%= wikiVisualizationHelper.isUndoTrashControlVisible() %>">
	<portlet:actionURL var="undoTrashURL">
		<portlet:param name="struts_action" value="/wiki/edit_page" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
	</portlet:actionURL>

	<liferay-ui:trash-undo portletURL="<%= undoTrashURL %>" />
</c:if>

<c:if test="<%= wikiVisualizationHelper.isNodeNameVisible() %>">

	<%
	PortletURL backToNodeURL = wikiURLHelper.getBackToNodeURL(node);
	%>

	<liferay-ui:header
		backURL="<%= backToNodeURL.toString() %>"
		localizeTitle="<%= false %>"
		title="<%= node.getName() %>"
	/>
</c:if>

<c:if test="<%= !print %>">
	<c:if test="<%= wikiVisualizationHelper.isNodeNavigationVisible(nodes) %>">
		<aui:nav cssClass="nav-tabs">

			<%
			for (int i = 0; i < nodes.size(); i++) {
				WikiNode curNode = nodes.get(i);

				String cssClass = StringPool.BLANK;

				if (curNode.getNodeId() == node.getNodeId()) {
					cssClass = "active";
				}
			%>

				<portlet:renderURL var="viewPageURL">
					<portlet:param name="struts_action" value="/wiki/view" />
					<portlet:param name="nodeName" value="<%= curNode.getName() %>" />
					<portlet:param name="title" value="<%= wikiServiceConfiguration.frontPageName() %>" />
				</portlet:renderURL>

				<aui:nav-item cssClass="<%= cssClass %>" href="<%= viewPageURL %>" label="<%= HtmlUtil.escape(curNode.getName()) %>" />

			<%
			}
			%>

		</aui:nav>
	</c:if>

	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav">

			<%
			PortletURL frontPageURL = wikiURLHelper.getFrontPageURL(node);

			String label = wikiServiceConfiguration.frontPageName();
			boolean selected = wikiVisualizationHelper.isFrontPageNavItemSelected(wikiPage);
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= frontPageURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

			<%
			PortletURL viewRecentChangesURL = wikiURLHelper.getViewRecentChangesURL(node);

			label = "recent-changes";
			selected = wikiVisualizationHelper.isViewRecentChangesNavItemSelected();
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= viewRecentChangesURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

			<%
			PortletURL viewAllPagesURL = wikiURLHelper.getViewAllPagesURL(node);

			label = "all-pages";
			selected = wikiVisualizationHelper.isViewAllPagesNavItemSelected();
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= viewAllPagesURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

			<%
			PortletURL viewOrphanPagesURL = wikiURLHelper.getViewOrphanPagesURL(node);

			label = "orphan-pages";
			selected = wikiVisualizationHelper.isViewOrphanPagesNavItemSelected();
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= viewOrphanPagesURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

			<%
			PortletURL viewDraftPagesURL = wikiURLHelper.getViewDraftPagesURL(node);

			label = "draft-pages";
			selected = wikiVisualizationHelper.isViewDraftPagesNavItemSelected();
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= viewDraftPagesURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />
		</aui:nav>

		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="struts_action" value="/wiki/search" />
		</liferay-portlet:renderURL>

		<aui:nav-bar-search>
			<div class="form-search">
				<aui:form action="<%= searchURL %>" method="get" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />

					<liferay-ui:input-search id="keywords1" />
				</aui:form>
			</div>
		</aui:nav-bar-search>
	</aui:nav-bar>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<aui:script>
			Liferay.Util.focusFormField(document.getElementById('<portlet:namespace />keywords1'));
		</aui:script>
	</c:if>
</c:if>