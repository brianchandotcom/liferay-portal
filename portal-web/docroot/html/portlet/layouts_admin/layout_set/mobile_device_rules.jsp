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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
LayoutSet selLayoutSet = ((LayoutSet)request.getAttribute("edit_pages.jsp-selLayoutSet"));

long groupId = selLayoutSet.getGroupId();
String className = LayoutSet.class.getName();
long classPK = selLayoutSet.getLayoutSetId();
%>

<%@ include file="/html/portlet/layouts_admin/layout/mobile_device_rules_header.jspf" %>

<div id="<portlet:namespace />uniqueRuleGroupInstancesContainer">
	<liferay-util:include page="/html/portlet/layouts_admin/layout/mobile_device_rules_rule_group_instances.jsp">
		<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<liferay-util:param name="className" value="<%= className %>" />
		<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	</liferay-util:include>
</div>