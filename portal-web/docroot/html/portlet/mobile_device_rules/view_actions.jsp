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
String redirect = ParamUtil.getString(request, "redirect");
boolean showBackURL = ParamUtil.getBoolean(request, "showBackURL", true);

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
	showBackURL="<%= showBackURL %>"
	title='<%= LanguageUtil.format(pageContext, "actions-for-x", ruleGroup.getName(locale), false) %>'
/>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="actionIds" type="hidden" />

	<liferay-ui:search-container
		delta="<%= 5 %>"
		deltaConfigurable="<%= false %>"
		emptyResultsMessage="no-actions-are-configured-for-this-device-family"
		headerNames="name,description,type"
		iteratorURL="<%= portletURL %>"
		rowChecker="<%= new RowChecker(renderResponse) %>"
		total="<%= MDRActionLocalServiceUtil.getActionsCount(ruleGroupInstanceId) %>"
	>
		<liferay-ui:search-container-results
			results="<%= MDRActionLocalServiceUtil.getActions(ruleGroupInstanceId, searchContainer.getStart(), searchContainer.getEnd()) %>"
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

		<c:if test="<%= MDRPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP) %>">
			<liferay-portlet:renderURL var="addURL">
				<portlet:param name="struts_action" value="/mobile_device_rules/edit_action" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="ruleGroupInstanceId" value="<%= String.valueOf(ruleGroupInstanceId) %>" />
			</liferay-portlet:renderURL>

			<aui:nav-bar>
				<aui:nav cssClass="navbar-nav">
					<aui:nav-item href="<%= addURL %>" iconCssClass="icon-plus" label="add-action" />
				</aui:nav>
			</aui:nav-bar>
		</c:if>
		<c:if test="<%= total > 0 %>">
			<aui:button-row>
				<aui:button cssClass="delete-rule-actions-button" disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteActions();" %>' value="delete" />
			</aui:button-row>

			<div class="separator"><!-- --></div>
		</c:if>

		<liferay-ui:search-iterator type="more" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

	Liferay.provide(
		window,
		'<portlet:namespace />deleteActions',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this") %>')) {
				document.<portlet:namespace />fm.method = 'post';
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>';
				document.<portlet:namespace />fm.<portlet:namespace />actionIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

				submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/mobile_device_rules/edit_action" /></portlet:actionURL>');
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>