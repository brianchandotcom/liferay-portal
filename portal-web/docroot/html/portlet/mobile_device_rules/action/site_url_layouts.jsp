<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/mobile_device_rules/action/init.jsp" %>

<%
long actionGroupId = 0;

if (action != null) {
	actionGroupId = GetterUtil.getLong(typeSettingsProperties.getProperty("groupId"));
}
else {
	actionGroupId = ParamUtil.getLong(request, "actionGroupId");
}

long actionPlid = 0;

if (action != null) {
	actionPlid = GetterUtil.getLong(typeSettingsProperties.getProperty("plid"));
}
else {
	actionPlid = ParamUtil.getLong(request, "actionPlid");
}
%>

<aui:input name="actionPlid" type="hidden" value="<%= actionPlid %>" />

<aui:select label="page" name="plid" required="<%= true %>">
	<aui:option disabled="<%= true %>" label="select-a-page" selected="<%= actionPlid == 0 %>" value="" />

	<%
	List<Layout> publicLayouts = LayoutServiceUtil.getLayouts(actionGroupId, false);
	%>

	<c:if test="<%= !publicLayouts.isEmpty() %>">
		<aui:option disabled="<%= true %>" label="public-pages" value="0" />

		<%
		for (Layout publicLayout : publicLayouts) {
		%>

			<aui:option label="<%= HtmlUtil.escape(publicLayout.getName(locale)) %>" selected="<%= publicLayout.getPlid() == actionPlid %>" value="<%= publicLayout.getPlid() %>" />

		<%
		}
		%>

	</c:if>

	<%
	List<Layout> privateLayouts = LayoutServiceUtil.getLayouts(actionGroupId, true);
	%>

	<c:if test="<%= !privateLayouts.isEmpty() %>">
		<aui:option disabled="<%= true %>" label="private-pages" value="0" />

		<%
		for (Layout privateLayout : privateLayouts) {
		%>

			<aui:option label="<%= HtmlUtil.escape(privateLayout.getName(locale)) %>" selected="<%= privateLayout.getPlid() == actionPlid %>" value="<%= privateLayout.getPlid() %>" />

		<%
		}
		%>

	</c:if>

	<c:if test="<%= publicLayouts.isEmpty() && privateLayouts.isEmpty() %>">
		<aui:option disabled="<%= true %>" label="no-available-pages" value="0" />
	</c:if>
</aui:select>