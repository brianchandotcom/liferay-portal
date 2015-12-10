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

<liferay-util:buffer var="navigation">
	<liferay-ui:icon-list>
		<liferay-util:dynamic-include key="com.liferay.login.web:/navigation.jsp#pre" />

		<%
		for (String section : PropsValues.LOGIN_FORM_NAVIGATION_PRE) {
		%>

			<liferay-util:include page='<%= "/navigation/" + _getSectionJsp(section) + ".jsp" %>' portletId="<%= portletDisplay.getRootPortletId() %>" servletContext="<%= application %>" />

		<%
		}

		for (String section : PropsValues.LOGIN_FORM_NAVIGATION_POST) {
		%>

			<liferay-util:include page='<%= "/navigation/" + _getSectionJsp(section) + ".jsp" %>' portletId="<%= portletDisplay.getRootPortletId() %>" servletContext="<%= application %>" />

		<%
		}
		%>

		<liferay-util:dynamic-include key="com.liferay.login.web:/navigation.jsp#post" />
	</liferay-ui:icon-list>
</liferay-util:buffer>

<%
navigation = navigation.trim();
%>

<c:if test="<%= Validator.isNotNull(navigation) %>">
	<div class="navigation">
		<%= navigation %>
	</div>
</c:if>

<%!
private String _getSectionJsp(String name) {
	return TextFormatter.format(name, TextFormatter.N);
}
%>