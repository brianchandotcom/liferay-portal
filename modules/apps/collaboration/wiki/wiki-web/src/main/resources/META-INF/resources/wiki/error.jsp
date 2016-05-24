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

String title = ParamUtil.getString(request, "title");

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);

	WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);

	PortletURL backToViewPagesURL = wikiURLHelper.getBackToViewPagesURL(node);

	portletDisplay.setURLBack(backToViewPagesURL.toString());

	renderResponse.setTitle(title);
}
%>

<c:if test="<%= !portletTitleBasedNavigation %>">
	<liferay-ui:error-header />
</c:if>

<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="please-enter-a-valid-page-title" />

<c:if test="<%= SessionErrors.contains(renderRequest, NoSuchPageException.class.getName()) %>">

	<%
	long nodeId = ParamUtil.getLong(request, "nodeId");

	if (nodeId == 0) {
		if (node != null) {
			nodeId = node.getNodeId();
		}
	}

	PortletURL editPageURL = renderResponse.createRenderURL();

	editPageURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
	editPageURL.setParameter("redirect", currentURL);
	editPageURL.setParameter("nodeId", String.valueOf(nodeId));
	editPageURL.setParameter("title", title);

	PortletURL searchURL = renderResponse.createRenderURL();

	searchURL.setParameter("mvcRenderCommandName", "/wiki/search");
	searchURL.setParameter("redirect", currentURL);
	searchURL.setParameter("nodeId", String.valueOf(nodeId));
	searchURL.setParameter("keywords", title);
	%>

	<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
		<div class="main-content-card panel">
			<div class="panel-body">
				<div class="alert alert-info">
					<liferay-ui:message key="this-page-is-empty.-use-the-buttons-below-to-create-it-or-to-search-for-the-words-in-the-title" />
				</div>

				<div class="btn-toolbar">

					<%
					String taglibEditPage = "location.href = '" + editPageURL.toString() + "';";
					%>

					<aui:button cssClass="btn-lg" onClick="<%= taglibEditPage %>" primary="<%= true %>" value='<%= LanguageUtil.format(request, "create-page-x", HtmlUtil.escapeAttribute(title), false) %>' />

					<%
					String taglibSearch = "location.href = '" + searchURL.toString() + "';";
					%>

					<aui:button cssClass="btn-default btn-lg" onClick="<%= taglibSearch %>" value='<%= LanguageUtil.format(request, "search-for-x", HtmlUtil.escapeAttribute(title), false) %>' />
				</div>
			</div>
		</div>
	</div>
</c:if>

<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-page-title" />

<liferay-ui:error-principal />