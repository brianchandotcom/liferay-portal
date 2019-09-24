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

	<liferay-trash:undo
		portletURL="<%= undoTrashURL.toString() %>"
	/>
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
			for (WikiNode curNode : nodes) {
				String cssClass = StringPool.BLANK;

				if (curNode.getNodeId() == node.getNodeId()) {
					cssClass = "active";
				}

				PortletURL viewPageURL = wikiURLHelper.getViewFrontPagePageURL(curNode);
			%>

				<aui:nav-item cssClass="<%= cssClass %>" href="<%= viewPageURL.toString() %>" label="<%= HtmlUtil.escape(curNode.getName()) %>" />

			<%
			}
			%>

		</aui:nav>
	</c:if>

	<clay:navigation-bar
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					add(
						navigationItem -> {
							navigationItem.setActive(wikiVisualizationHelper.isFrontPageNavItemSelected());
							navigationItem.setHref(wikiURLHelper.getFrontPageURL(node));
							navigationItem.setLabel(wikiGroupServiceConfiguration.frontPageName());
						});

					add(
						navigationItem -> {
							navigationItem.setActive(wikiVisualizationHelper.isViewRecentChangesNavItemSelected());
							navigationItem.setHref(wikiURLHelper.getViewRecentChangesURL(node));
							navigationItem.setLabel(LanguageUtil.get(request, "recent-changes"));
						});

					add(
						navigationItem -> {
							navigationItem.setActive(wikiVisualizationHelper.isViewAllPagesNavItemSelected());
							navigationItem.setHref(wikiURLHelper.getViewPagesURL(node));
							navigationItem.setLabel(LanguageUtil.get(request, "all-pages"));
						});

					add(
						navigationItem -> {
							navigationItem.setActive(wikiVisualizationHelper.isViewOrphanPagesNavItemSelected());
							navigationItem.setHref(wikiURLHelper.getViewOrphanPagesURL(node));
							navigationItem.setLabel(LanguageUtil.get(request, "orphan-pages"));
						});

					add(
						navigationItem -> {
							navigationItem.setActive(wikiVisualizationHelper.isViewDraftPagesNavItemSelected());
							navigationItem.setHref(wikiURLHelper.getViewDraftPagesURL(node));
							navigationItem.setLabel(LanguageUtil.get(request, "draft-pages"));
						});
				}
			}
		%>'
	/>
</c:if>