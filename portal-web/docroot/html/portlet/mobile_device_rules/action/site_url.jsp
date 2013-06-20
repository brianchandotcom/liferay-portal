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
long actionGroupId = GetterUtil.getLong(typeSettingsProperties.getProperty("groupId"));
%>

<aui:select label="site" name="groupId" onChange='<%= liferayPortletResponse.getNamespace() + "changeDisplay();" %>'>
	<aui:option disabled="<%= true %>" label="select-a-site" selected="<%= actionGroupId == 0 %>" value="0" />

	<%
	int count = 0;

	for (Group group : GroupServiceUtil.getUserPlacesGroups()) {
		if (!group.isUser() && !group.isControlPanel()) {
			count++;
	%>

			<aui:option label="<%= group.getName() %>" selected="<%= group.getGroupId() == actionGroupId %>" value="<%= group.getGroupId() %>" />

	<%
		}
	}
	%>

	<c:if test="<%= count == 0 %>">
		<aui:option disabled="<%= true %>" label="no-available-sites" value="0" />
	</c:if>
</aui:select>

<div id="<portlet:namespace />layouts">
	<liferay-util:include page="/html/portlet/mobile_device_rules/action/site_url_layouts.jsp" />
</div>