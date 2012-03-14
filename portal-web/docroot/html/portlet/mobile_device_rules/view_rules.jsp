<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

long ruleGroupId = ParamUtil.getLong(request, "ruleGroupId");

MDRRuleGroup ruleGroup = MDRRuleGroupLocalServiceUtil.getRuleGroup(ruleGroupId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/mobile_device_rules/view_rules");
portletURL.setParameter("ruleGroupId", String.valueOf(ruleGroupId));
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("redirect", redirect);
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= false %>"
	title='<%= LanguageUtil.format(pageContext, "rules-for-x", ruleGroup.getName(locale), false) %>'
/>

<div class="lfr-portlet-toolbar">
	<liferay-portlet:renderURL var="addURL">
		<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule" />
		<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
		<portlet:param name="ruleGroupId" value="<%= String.valueOf(ruleGroupId) %>" />
	</liferay-portlet:renderURL>

	<span class="lfr-toolbar-button add-button">
		<a href="<%= addURL %>"><liferay-ui:message key="add-rule" /></a>
	</span>
</div>

<div class="separator"><!-- --></div>

<liferay-ui:search-container
	delta="<%= 5 %>"
	deltaConfigurable="<%= false %>"
	emptyResultsMessage="no-rules-are-configured-for-this-rule-group"
	headerNames="name,type"
	id="mdrRulesSearchContainer"
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
		<liferay-portlet:renderURL var="rowURL">
			<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="ruleId" value="<%= String.valueOf(rule.getRuleId()) %>" />
		</liferay-portlet:renderURL>

		<%@ include file="/html/portlet/mobile_device_rules/rule_columns.jspf" %>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator type="more" />
</liferay-ui:search-container>