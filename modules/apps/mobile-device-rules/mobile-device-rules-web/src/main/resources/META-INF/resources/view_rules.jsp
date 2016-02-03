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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL");

if (Validator.isNull(redirect) && Validator.isNull(backURL)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcPath", "/view.jsp");
	portletURL.setParameter("groupId", String.valueOf(groupId));

	backURL = portletURL.toString();
}

long ruleGroupId = ParamUtil.getLong(request, "ruleGroupId");

MDRRuleGroup ruleGroup = MDRRuleGroupLocalServiceUtil.getRuleGroup(ruleGroupId);

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_rules.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("ruleGroupId", String.valueOf(ruleGroupId));
portletURL.setParameter("groupId", String.valueOf(groupId));

SearchContainer rulesSearchContainer = new SearchContainer(renderRequest, portletURL, null, "no-classification-rules-are-configured-for-this-device-family");

String orderByCol = ParamUtil.getString(request, "orderByCol", "create-date");

rulesSearchContainer.setOrderByCol(orderByCol);

boolean orderByAsc = false;

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

if (orderByType.equals("asc")) {
	orderByAsc = true;
}

OrderByComparator<MDRRule> orderByComparator = new RuleCreateDateComparator(orderByAsc);

rulesSearchContainer.setOrderByComparator(orderByComparator);

rulesSearchContainer.setOrderByType(orderByType);

int rulesCount = MDRRuleLocalServiceUtil.getRulesCount(ruleGroupId);
List<MDRRule> rules = MDRRuleLocalServiceUtil.getRules(ruleGroupId, rulesSearchContainer.getStart(), rulesSearchContainer.getEnd(), rulesSearchContainer.getOrderByComparator());

rulesSearchContainer.setResults(rules);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.format(request, "classification-rules-for-x", ruleGroup.getName(locale), false));
%>

<aui:nav-bar markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="classification-rules" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= rulesCount <= 0 %>"
>

	<%
	PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, renderResponse);
	%>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<%
	PortletURL iteratorURL = PortletURLUtil.clone(portletURL, renderResponse);

	iteratorURL.setParameter("displayStyle", displayStyle);
	%>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= iteratorURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= iteratorURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		searchContainer="<%= rulesSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.mobile.device.rules.model.MDRRule"
			escapedModel="<%= true %>"
			keyProperty="ruleId"
			modelVar="rule"
		>
			<liferay-portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_rule" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="ruleId" value="<%= String.valueOf(rule.getRuleId()) %>" />
			</liferay-portlet:renderURL>

			<%@ include file="/rule_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" type="more" />
	</liferay-ui:search-container>
</div>

<liferay-portlet:renderURL var="addURL">
	<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_rule" />
	<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
	<portlet:param name="ruleGroupId" value="<%= String.valueOf(ruleGroupId) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-classification-rule") %>' url="<%= addURL.toString() %>" />
</liferay-frontend:add-menu>