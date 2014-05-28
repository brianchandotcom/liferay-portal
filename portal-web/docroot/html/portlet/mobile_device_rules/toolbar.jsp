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

<%@ include file="/html/portlet/mobile_device_rules/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "add");
%>

<c:if test="<%= MDRPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP) %>">
	<portlet:renderURL var="viewRulesURL">
		<portlet:param name="struts_action" value="/mobile_device_rules/view" />
	</portlet:renderURL>

	<liferay-portlet:renderURL var="addRuleGroupURL">
		<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule_group" />
		<portlet:param name="redirect" value="<%= viewRulesURL %>" />
		<portlet:param name="backURL" value="<%= viewRulesURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	</liferay-portlet:renderURL>

	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav">
			<aui:nav-item href="<%= addRuleGroupURL %>" iconCssClass="icon-plus" label="add-device-family" selected='<%= toolbarItem.equals("add") %>' />
		</aui:nav>

		<aui:nav-bar-search cssClass="pull-right" file="/html/portlet/mobile_device_rules/rule_group_search.jsp" />
	</aui:nav-bar>
</c:if>