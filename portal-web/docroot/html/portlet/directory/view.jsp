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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/directory/view");
portletURL.setParameter("tabs1", tabs1);

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();

request.setAttribute("view.jsp-portletURL", portletURL);
request.setAttribute("view.jsp-portletURLString", portletURLString);
%>

<aui:form action="<%= portletURLString %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="redirect" type="hidden" value="<%= portletURLString %>" />

	<c:if test="<%= !portletName.equals(PortletKeys.FRIENDS_DIRECTORY) %>">
		<liferay-util:include page="/html/portlet/directory/tabs1.jsp" />
	</c:if>

	<c:choose>
		<c:when test='<%= tabs1.equals("users") %>'>
			<liferay-util:include page="/html/portlet/directory/view_users.jsp" />
		</c:when>
		<c:when test='<%= tabs1.equals("organizations") %>'>
			<liferay-util:include page="/html/portlet/directory/view_organizations.jsp" />
		</c:when>
		<c:when test='<%= tabs1.equals("user-groups") %>'>
			<liferay-util:include page="/html/portlet/directory/view_user_groups.jsp" />
		</c:when>
	</c:choose>
</aui:form>