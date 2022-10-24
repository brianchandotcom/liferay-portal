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

boolean print = Objects.equals(ParamUtil.getString(request, "viewMode"), Constants.PRINT);

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
}
%>

<c:if test="<%= !print %>">
	<c:if test="<%= wikiVisualizationHelper.isNodeNavigationVisible() %>">
		<clay:navigation-bar
			navigationItems="<%=
				new JSPNavigationItemList(pageContext) {
					{
						for (WikiNode curNode : nodes) {
							PortletURL viewPageURL = wikiURLHelper.getViewFrontPagePageURL(curNode);

							add(
								navigationItem -> {
									navigationItem.setActive(curNode.getNodeId() == node.getNodeId());
									navigationItem.setHref(viewPageURL.toString());
									navigationItem.setLabel(HtmlUtil.escape(curNode.getName()));
								});
						}
					}
				}
			%>"
		/>
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
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "recent-changes"));
						});

					add(
						navigationItem -> {
							navigationItem.setActive(wikiVisualizationHelper.isViewAllPagesNavItemSelected());
							navigationItem.setHref(wikiURLHelper.getViewPagesURL(node));
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "all-pages"));
						});

					add(
						navigationItem -> {
							navigationItem.setActive(wikiVisualizationHelper.isViewOrphanPagesNavItemSelected());
							navigationItem.setHref(wikiURLHelper.getViewOrphanPagesURL(node));
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "orphan-pages"));
						});

					add(
						navigationItem -> {
							navigationItem.setActive(wikiVisualizationHelper.isViewDraftPagesNavItemSelected());
							navigationItem.setHref(wikiURLHelper.getViewDraftPagesURL(node));
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "draft-pages"));
						});
				}
			}
		%>'
	/>
</c:if>