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
String tabs1 = ParamUtil.getString(request, "tabs1", "settings");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL settingsURL = PortletURLUtil.clone(portletURL, renderResponse);

		settingsURL.setParameter("tabs1", "settings");
		%>

		<aui:nav-item href="<%= settingsURL.toString() %>" label="settings" selected='<%= tabs1.equals("settings") %>' />

		<%
		PortletURL sitesURL = PortletURLUtil.clone(portletURL, renderResponse);

		sitesURL.setParameter("tabs1", "sites");
		%>

		<aui:nav-item href="<%= sitesURL.toString() %>" label="sites" selected='<%= tabs1.equals("sites") %>' />
	</aui:nav>

	<c:choose>
		<c:when test='<%= tabs1.equals("sites") %>'>
			<aui:nav-bar-search>
				<aui:form action="<%= portletURL.toString() %>" method="get" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="portletURL" />

					<liferay-ui:input-search markupView="lexicon" />
				</aui:form>
			</aui:nav-bar-search>
		</c:when>
	</c:choose>
</aui:nav-bar>

<c:choose>
	<c:when test='<%= tabs1.equals("settings") %>'>
		<liferay-util:include page="/settings.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/sites.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>