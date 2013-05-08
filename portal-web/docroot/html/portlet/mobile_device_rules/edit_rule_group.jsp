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
String backURL = ParamUtil.getString(request, "backURL");

MDRRuleGroup ruleGroup = (MDRRuleGroup)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP);

long ruleGroupId = BeanParamUtil.getLong(ruleGroup, request, "ruleGroupId");
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= (ruleGroup == null) %>"
	title='<%= (ruleGroup == null) ? "new-rule-group" : ruleGroup.getName(locale) %>'
/>

<portlet:actionURL var="editRuleGroupURL">
	<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule_group" />
</portlet:actionURL>

<aui:form action="<%= editRuleGroupURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (ruleGroup == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="ruleGroupId" type="hidden" value="<%= ruleGroupId %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />

	<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="rule-group-does-not-exist" />

	<aui:model-context bean="<%= ruleGroup %>" model="<%= MDRRuleGroup.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="description" />
	</aui:fieldset>

	<c:if test="<%= ruleGroup != null %>">
		<aui:fieldset>
			<c:if test="<%= MDRRuleLocalServiceUtil.getRulesCount(ruleGroupId) == 0 %>">
				<div class="alert alert-info">
					<liferay-ui:message key="no-rules-are-configured-for-this-rule-group" />
				</div>
			</c:if>

			<liferay-portlet:renderURL var="editRulesURL">
				<portlet:param name="struts_action" value="/mobile_device_rules/view_rules" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="ruleGroupId" value="<%= String.valueOf(ruleGroupId) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				image="manage_nodes"
				label="<%= true %>"
				message="manage-rules"
				url="<%= editRulesURL.toString() %>"
			/>
		</aui:fieldset>
	</c:if>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>