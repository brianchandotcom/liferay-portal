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

<%
long ruleGroupInstanceId = ParamUtil.getLong(request, "ruleGroupInstanceId");

MDRRuleGroupInstance ruleGroupInstance = MDRRuleGroupInstanceLocalServiceUtil.getRuleGroupInstance(ruleGroupInstanceId);

MDRRuleGroup ruleGroup = ruleGroupInstance.getRuleGroup();

MDRActionDisplayContext mdrActionDisplayContext = new MDRActionDisplayContext(renderRequest, renderResponse);

PortletURL portletURL = mdrActionDisplayContext.getPortletURL();
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label='<%= LanguageUtil.format(resourceBundle, "actions-for-x", ruleGroup.getName(locale), false) %>' selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="actionActions"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= mdrActionDisplayContext.getOrderByCol() %>"
			orderByType="<%= mdrActionDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="<%= mdrActionDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteActions" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="/mobile_device_rules/edit_action" var="deleteURL">
	<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_action" />
</portlet:actionURL>

<aui:form action="<%= deleteURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

	<c:if test="<%= MDRPermission.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP) %>">
		<liferay-portlet:renderURL var="addURL">
			<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_action" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="ruleGroupInstanceId" value="<%= String.valueOf(ruleGroupInstanceId) %>" />
		</liferay-portlet:renderURL>

		<div class="button-holder text-center">
			<aui:button href="<%= addURL.toString() %>" value="add-action" />
		</div>
	</c:if>

	<liferay-ui:search-container
		id="actionActions"
		searchContainer="<%= mdrActionDisplayContext.getActionSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.mobile.device.rules.model.MDRAction"
			escapedModel="<%= true %>"
			keyProperty="actionId"
			modelVar="action"
		>
			<liferay-portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_action" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="actionId" value="<%= String.valueOf(action.getActionId()) %>" />
			</liferay-portlet:renderURL>

			<%@ include file="/action_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	$('#<portlet:namespace />deleteActions').on(
		'click',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(resourceBundle, "are-you-sure-you-want-to-delete-this") %>')) {
				submitForm(document.<portlet:namespace />fm);
			}
		}
	);
</aui:script>