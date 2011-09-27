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

<%@ include file="/html/portlet/mobile_device_rules_admin/action/init.jsp" %>

<%
long actionGroupId = 0L;
long actionLayoutId = 0L;

if (!isAdd) {
	actionGroupId = GetterUtil.getLong(actionTypeSettings.getProperty("groupId"));
	actionLayoutId = GetterUtil.get(actionTypeSettings.getProperty("layoutId"), 0L);
}

request.setAttribute("actionGroupId", actionGroupId);
request.setAttribute("actionLayoutId", actionLayoutId);

String namespace = StringPool.BLANK;

List<Group> groups = GroupServiceUtil.getUserSites();

if (resourceResponse != null) {
	namespace = resourceResponse.getNamespace();
}
else if (renderResponse != null) {
	namespace = renderResponse.getNamespace();
}
%>

<aui:select label="site" name="groupId" onChange='<%= namespace + "changeDisplay();" %>'>
	<aui:option label="select-a-site" value="0" selected="<%= actionGroupId == 0%>" disabled="<%= true %>"/>
	<%
	int groupCount = 0;
	for (Group selGroup : groups) {
		if (!selGroup.isUser() && !selGroup.isControlPanel()) {
			groupCount++;
	%>
		<aui:option label="<%= selGroup.getName() %>" value="<%= selGroup.getGroupId() %>" selected="<%= selGroup.getGroupId() == actionGroupId %>" />
	<%
		}
	}
	%>

	<c:if test="<%= groupCount == 0 %>">
		<aui:option label="no-available-sites" value="0" disabled="<%= true %>" />
	</c:if>
</aui:select>

<aui:input name="originalLayoutId" value="<%=actionLayoutId%>" type="hidden"/>

<div id="<%= namespace %>layouts">
	<c:import url="/html/portlet/mobile_device_rules_admin/action/site_url_action_layouts.jsp" />
</div>
