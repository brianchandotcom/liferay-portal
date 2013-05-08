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
String redirect = ParamUtil.getString(request, "redirect");

long ruleGroupInstanceId = ParamUtil.getLong(request, "ruleGroupInstanceId");

MDRRuleGroupInstance ruleGroupInstance = MDRRuleGroupInstanceLocalServiceUtil.getRuleGroupInstance(ruleGroupInstanceId);

MDRRuleGroup ruleGroup = ruleGroupInstance.getRuleGroup();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/mobile_device_rules/view_actions");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("ruleGroupInstanceId", String.valueOf(ruleGroupInstanceId));
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title='<%= LanguageUtil.format(pageContext, "actions-for-x", ruleGroup.getName(locale), false) %>'
/>

<c:if test="<%= MDRPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP) %>">
	<liferay-portlet:renderURL var="addURL">
		<portlet:param name="struts_action" value="/mobile_device_rules/edit_action" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="ruleGroupInstanceId" value="<%= String.valueOf(ruleGroupInstanceId) %>" />
	</liferay-portlet:renderURL>

	<aui:nav-bar>
		<aui:nav>
			<aui:nav-item href="<%= addURL %>" iconClass="icon-plus" label="add-action" />
		</aui:nav>
	</aui:nav-bar>
</c:if>

<div class="separator"><!-- --></div>

<liferay-ui:search-container
	delta="<%= 5 %>"
	deltaConfigurable="<%= false %>"
	emptyResultsMessage="no-actions-are-configured-for-this-rule-group-instance"
	headerNames="name,description,type"
	iteratorURL="<%= portletURL %>"
>
	<liferay-ui:search-container-results
		results="<%= MDRActionLocalServiceUtil.getActions(ruleGroupInstanceId, searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= MDRActionLocalServiceUtil.getActionsCount(ruleGroupInstanceId) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.mobiledevicerules.model.MDRAction"
		escapedModel="<%= true %>"
		keyProperty="actionId"
		modelVar="action"
	>
		<liferay-portlet:renderURL var="rowURL">
			<portlet:param name="struts_action" value="/mobile_device_rules/edit_action" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="actionId" value="<%= String.valueOf(action.getActionId()) %>" />
		</liferay-portlet:renderURL>

		<%@ include file="/html/portlet/mobile_device_rules/action_columns.jspf" %>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator type="more" />
</liferay-ui:search-container>