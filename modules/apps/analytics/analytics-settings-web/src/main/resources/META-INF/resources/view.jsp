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
String tabs1 = ParamUtil.getString(request, "tabs1", "analytics-cloud-connection");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL analyticsCloudConnectionURL = PortletURLUtil.clone(portletURL, renderResponse);

		analyticsCloudConnectionURL.setParameter("tabs1", "analytics-cloud-connection");
		%>

		<aui:nav-item href="<%= analyticsCloudConnectionURL.toString() %>" label="analytics-cloud-connection" selected='<%= tabs1.equals("analytics-cloud-connection") %>' />

		<%
		PortletURL syncedContactsURL = PortletURLUtil.clone(portletURL, renderResponse);

		syncedContactsURL.setParameter("tabs1", "synced-contacts");
		%>

		<aui:nav-item href="<%= syncedContactsURL.toString() %>" label="synced-contacts" selected='<%= tabs1.equals("synced-contacts") %>' />

		<%
		PortletURL syncedSitesURL = PortletURLUtil.clone(portletURL, renderResponse);

		syncedSitesURL.setParameter("tabs1", "synced-sites");
		%>

		<aui:nav-item href="<%= syncedSitesURL.toString() %>" label="synced-sites" selected='<%= tabs1.equals("synced-sites") %>' />
	</aui:nav>
</aui:nav-bar>

<c:choose>
	<c:when test='<%= tabs1.equals("analytics-cloud-connection") %>'>
		<liferay-util:include page="/edit_workspace_connection.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("synced-contacts") %>'>
		<liferay-util:include page="/edit_synced_contacts.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/edit_synced_sites.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>