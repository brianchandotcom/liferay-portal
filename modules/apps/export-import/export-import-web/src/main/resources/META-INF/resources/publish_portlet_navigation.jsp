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
String tabs3 = ParamUtil.getString(request, "tabs3", "new-publish-process");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/export_import/publish_portlet");
portletURL.setParameter("portletResource", portletResource);
%>

<c:if test="<%= (themeDisplay.getURLPublishToLive() != null) || layout.isTypeControlPanel() %>">
	<aui:nav-bar cssClass="navbar-collapse-absolute navbar-expand-md navbar-underline navigation-bar navigation-bar-light" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">

			<%
			portletURL.setParameter("tabs3", "new-publish-process");
			%>

			<aui:nav-item href="<%= portletURL.toString() %>" label="new-publish-process" selected='<%= tabs3.equals("new-publish-process") %>' />

			<%
			Group scopeGroup = themeDisplay.getScopeGroup();
			%>

			<c:if test="<%= !scopeGroup.isStagedRemotely() %>">

				<%
				portletURL.setParameter("tabs3", "copy-from-live");
				%>

				<aui:nav-item href="<%= portletURL.toString() %>" label="copy-from-live" selected='<%= tabs3.equals("copy-from-live") %>' />
			</c:if>

			<%
			portletURL.setParameter("tabs3", "current-and-previous");
			%>

			<aui:nav-item href="<%= portletURL.toString() %>" label="current-and-previous" selected='<%= tabs3.equals("current-and-previous") %>' />
		</aui:nav>
	</aui:nav-bar>
</c:if>