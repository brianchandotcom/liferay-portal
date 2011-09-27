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
String redirect = ParamUtil.getString(request, "redirect");

MDRRuleGroup ruleGroup = (MDRRuleGroup) renderRequest.getAttribute(
	MDRPortletConstants.MDR_RULE_GROUP);

long ruleGroupId = 0;

boolean isAdd = Validator.isNull(ruleGroup);

if (!isAdd) {
	ruleGroupId = ruleGroup.getRuleGroupId();

	groupId = ruleGroup.getGroupId();
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter(
	"struts_action", "/mobile_device_rules_admin/edit_rule_group");
portletURL.setParameter(
	MDRPortletConstants.MDR_RULE_GROUP_ID, String.valueOf(ruleGroupId));
portletURL.setParameter(
	MDRPortletConstants.GROUP_ID, String.valueOf(groupId));
portletURL.setParameter("redirect", redirect);

request.setAttribute("view_rule_group_rules.jsp-portletURL", portletURL);
%>

<c:if test='<%=isAdd%>'>
	<liferay-ui:header title="add-rule-group" backURL="<%= redirect %>" />
</c:if>
<c:if test='<%=!isAdd%>'>
	<liferay-ui:header title="edit-rule-group" backURL="<%= redirect %>" />
</c:if>

<portlet:actionURL var="editRuleGroupURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule_group"/>
	<portlet:param name="redirect" value="<%= redirect %>"/>
</portlet:actionURL>

<aui:form action="<%= editRuleGroupURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveRuleGroup();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden"/>
	<aui:input name="<%= MDRPortletConstants.GROUP_ID %>" type="hidden" value="<%= groupId %>"/>
	<aui:input name="<%= MDRPortletConstants.MDR_RULE_GROUP_ID %>" type="hidden" value="<%= ruleGroupId %>"/>

	<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="rule-group-does-not-exist"/>

	<aui:model-context bean="<%= ruleGroup %>" model="<%= MDRRuleGroup.class %>"/>

	<aui:fieldset>
		<aui:input name="name"/>
		<aui:input name="description"/>
	</aui:fieldset>

	<c:if test="<%= !isAdd %>">

		<h4><%= LanguageUtil.get(pageContext, "rules") %></h4>

		<liferay-portlet:renderURL varImpl="addURL">
			<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule"/>
			<portlet:param name="<%= MDRPortletConstants.MDR_RULE_GROUP_ID %>" value="<%= String.valueOf(ruleGroupId) %>" />
			<portlet:param name="redirect" value="<%= portletURL.toString() %>"/>
		</liferay-portlet:renderURL>

		<liferay-ui:search-container
			curParam="cur1"
			delta="<%= 5 %>"
			deltaConfigurable="<%= false %>"
			emptyResultsMessage="no-rules-are-configured-for-this-rule-group"
			headerNames="name,type"
			iteratorURL="<%= portletURL %>"
		>

			<liferay-ui:search-container-results
				results="<%= MDRRuleLocalServiceUtil.getRules(ruleGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= MDRRuleLocalServiceUtil.getRulesCount(ruleGroupId) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.mobiledevicerules.model.MDRRule"
				escapedModel="<%= true %>"
				keyProperty="ruleId"
				modelVar="rule"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule"/>
					<portlet:param name="redirect" value="<%= portletURL.toString() %>"/>
					<portlet:param name="<%= MDRPortletConstants.MDR_RULE_ID %>" value="<%= String.valueOf(rule.getRuleId()) %>"/>
				</liferay-portlet:renderURL>

				<%@ include file="/html/portlet/mobile_device_rules_admin/device_rule_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator type="more"/>
		</liferay-ui:search-container>

		<liferay-ui:icon
			image="add"
			label="<%= true %>"
			message="add"
			url='<%= addURL.toString() %>'
		/>
	</c:if>

	<aui:button-row>
		<aui:button type="submit"/>
		<aui:button href="<%= redirect %>" value="cancel"/>
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace/>saveRuleGroup() {
		document.<portlet:namespace/>fm.<portlet:namespace/><%= Constants.CMD %>.value = "<%= isAdd ? Constants.ADD : Constants.EDIT %>";
		submitForm(document.<portlet:namespace/>fm);
	}
</aui:script>