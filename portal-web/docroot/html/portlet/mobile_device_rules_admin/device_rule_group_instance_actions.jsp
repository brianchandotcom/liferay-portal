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

<%@ include file="/html/portlet/mobile_device_rules_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MDRRuleGroupInstance ruleGroupInstance = (MDRRuleGroupInstance)row.getObject();
MDRRuleGroup ruleGroup = MDRRuleGroupLocalServiceUtil.getMDRRuleGroup(ruleGroupInstance.getRuleGroupId());

groupId = ruleGroupInstance.getGroupId();

String redirect = (String)request.getAttribute("view.jsp-redirect");
%>

<liferay-ui:icon-menu>
	<c:if test="<%= MDRRuleGroupInstancePermissionUtil.contains(permissionChecker, ruleGroupInstance.getRuleGroupInstanceId(), ActionKeys.UPDATE) %>">
		<liferay-portlet:renderURL portletName="<%= PortletKeys.MOBILE_DEVICE_SITE_ADMIN %>" varImpl="viewRuleGroupInstanceActionsURL">
			<portlet:param name="struts_action" value="/mobile_device_rules_admin/view_actions" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="ruleGroupInstanceId" value="<%= String.valueOf(ruleGroupInstance.getRuleGroupInstanceId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon image="manage_nodes" message="manage-actions" url="<%= viewRuleGroupInstanceActionsURL.toString() %>" />
	</c:if>

	<c:if test="<%= MDRRuleGroupInstancePermissionUtil.contains(permissionChecker, ruleGroupInstance.getRuleGroupInstanceId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= MDRRuleGroupInstance.class.getName() %>"
			modelResourceDescription="<%= ruleGroup.getName(themeDisplay.getLocale()) %>"
			resourcePrimKey="<%= String.valueOf(ruleGroupInstance.getRuleGroupInstanceId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon image="permissions" url="<%= permissionsURL %>" />
	</c:if>

	<c:if
		test="<%= MDRRuleGroupInstancePermissionUtil.contains(permissionChecker, ruleGroupInstance.getRuleGroupInstanceId(), ActionKeys.DELETE) %>">
		<liferay-portlet:actionURL portletName="<%= PortletKeys.MOBILE_DEVICE_SITE_ADMIN %>" var="deleteURL">
			<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule_group_instance" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="ruleGroupInstanceId" value="<%= Long.toString(ruleGroupInstance.getRuleGroupInstanceId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>