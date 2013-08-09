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

<%@ include file="/html/portlet/mobile_device_rules/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MDRRuleGroup ruleGroup = (MDRRuleGroup)row.getObject();
%>

<liferay-ui:icon-menu>
	<c:if test="<%= MDRRuleGroupPermissionUtil.contains(permissionChecker, ruleGroup.getRuleGroupId(), ActionKeys.UPDATE) %>">
		<liferay-portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule_group" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="ruleGroupId" value="<%= String.valueOf(ruleGroup.getRuleGroupId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon image="edit" url="<%= editURL %>" />
	</c:if>

	<c:if test="<%= MDRRuleGroupPermissionUtil.contains(permissionChecker, ruleGroup.getRuleGroupId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= MDRRuleGroup.class.getName() %>"
			modelResourceDescription="<%= ruleGroup.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(ruleGroup.getRuleGroupId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			image="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= MDRRuleGroupPermissionUtil.contains(permissionChecker, ruleGroup.getRuleGroupId(), ActionKeys.VIEW) && MDRPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP) %>">
		<portlet:renderURL var="editRulesURL">
			<portlet:param name="struts_action" value="/mobile_device_rules/view_rules" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="ruleGroupId" value="<%= String.valueOf(ruleGroup.getRuleGroupId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="manage_nodes" message="manage-classification-rules" url="<%= editRulesURL.toString() %>" />

		<portlet:actionURL var="copyURL">
			<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule_group" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.COPY %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="ruleGroupId" value="<%= String.valueOf(ruleGroup.getRuleGroupId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon image="copy" url="<%= copyURL.toString() %>" />
	</c:if>

	<c:if test="<%= MDRRuleGroupPermissionUtil.contains(permissionChecker, ruleGroup.getRuleGroupId(), ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule_group" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="ruleGroupId" value="<%= String.valueOf(ruleGroup.getRuleGroupId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>