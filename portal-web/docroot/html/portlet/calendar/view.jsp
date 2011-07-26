<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/calendar/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", tabs1Default);

String eventType = ParamUtil.getString(request, "eventType");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/calendar/view");
portletURL.setParameter("tabs1", tabs1);

String[] array = PortalUtil.stripURLAnchor(portletURL.toString(), "&#");

String urlAnchor = array[1];
String strippedURL = array[0];
%>

<aui:form method="post" name="fm">
	<liferay-util:include page="/html/portlet/calendar/tabs1.jsp" />

	<c:choose>
		<c:when test='<%= tabs1.equals("summary") %>'>
			<%@ include file="/html/portlet/calendar/summary.jspf" %>
		</c:when>
		<c:when test='<%= tabs1.equals("day") %>'>
			<%@ include file="/html/portlet/calendar/day.jspf" %>
		</c:when>
		<c:when test='<%= tabs1.equals("week") %>'>
			<%@ include file="/html/portlet/calendar/week.jspf" %>
		</c:when>
		<c:when test='<%= tabs1.equals("month") %>'>
			<%@ include file="/html/portlet/calendar/month.jspf" %>
		</c:when>
		<c:when test='<%= tabs1.equals("year") %>'>
			<%@ include file="/html/portlet/calendar/year.jspf" %>
		</c:when>
		<c:when test='<%= tabs1.equals("events") %>'>
			<%@ include file="/html/portlet/calendar/events.jspf" %>
		</c:when>
		<c:when test='<%= tabs1.equals("export-import") %>'>
			<%@ include file="/html/portlet/calendar/export_import.jspf" %>
		</c:when>
	</c:choose>
</aui:form>

<%
if (!tabs1.equals("summary")) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs1), currentURL);
}
%>