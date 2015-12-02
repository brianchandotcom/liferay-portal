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
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

List<WikiNode> nodes = wikiPortletInstanceSettingsHelper.getAllPermittedNodes();

boolean print = ParamUtil.getString(request, "viewMode").equals(Constants.PRINT);

WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);
WikiVisualizationHelper wikiVisualizationHelper = new WikiVisualizationHelper(wikiRequestHelper, wikiPortletInstanceSettingsHelper, wikiGroupServiceConfiguration);
%>

<c:if test="<%= wikiVisualizationHelper.isUndoTrashControlVisible() %>">

	<%
	PortletURL undoTrashURL = wikiURLHelper.getUndoTrashURL();
	%>

	<liferay-ui:trash-undo portletURL="<%= undoTrashURL.toString() %>" />
</c:if>

<%
boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

PortletURL backToNodeURL = wikiURLHelper.getBackToNodeURL(node);

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backToNodeURL.toString());

	renderResponse.setTitle(node.getName());
}
%>

<c:if test="<%= !print %>">
	<c:if test="<%= wikiVisualizationHelper.isNodeNavigationVisible() %>">
		<aui:nav cssClass="nav-tabs">

			<%
			for (WikiNode curNode: nodes) {
				String cssClass = StringPool.BLANK;

				if (curNode.getNodeId() == node.getNodeId()) {
					cssClass = "active";
				}

				PortletURL viewPageURL = wikiURLHelper.getViewPageURL(curNode);
			%>

				<aui:nav-item cssClass="<%= cssClass %>" href="<%= viewPageURL.toString() %>" label="<%= HtmlUtil.escape(curNode.getName()) %>" />

			<%
			}
			%>

		</aui:nav>
	</c:if>

	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav">

			<%
			PortletURL frontPageURL = wikiURLHelper.getFrontPageURL(node);

			String label = wikiGroupServiceConfiguration.frontPageName();
			boolean selected = wikiVisualizationHelper.isFrontPageNavItemSelected();
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

		<%
		PortletURL searchURL = wikiURLHelper.getSearchURL();
		%>

		<aui:nav-bar-search>
			<div class="form-search">
				<aui:form action="<%= searchURL %>" method="get" name="searchFm">
					<liferay-portlet:renderURLParams portletURL="<%= searchURL %>" />
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