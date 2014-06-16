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
String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectRuleGroup");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/mobile_device_rules/view");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("eventName", eventName);
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectRuleGroupFm">

	<%
	RuleGroupSearch ruleGroupSearch = new RuleGroupSearch(liferayPortletRequest, portletURL);
	%>

	<liferay-ui:search-container
		searchContainer="<%= ruleGroupSearch %>"
	>
		<aui:nav-bar>
			<c:if test="<%= MDRPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP) %>">
				<liferay-portlet:renderURL var="addRuleGroupURL">
					<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule_group" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				</liferay-portlet:renderURL>

				<aui:nav cssClass="navbar-nav" searchContainer="<%= ruleGroupSearch %>">
					<aui:nav-item href="<%= addRuleGroupURL %>" iconCssClass="icon-plus" label="add-device-family" />
				</aui:nav>
			</c:if>

			<aui:nav-bar-search cssClass="navbar-search-advanced" file="/html/portlet/mobile_device_rules/rule_group_search.jsp" searchContainer="<%= ruleGroupSearch %>" />
		</aui:nav-bar>

		<%
		RuleGroupDisplayTerms displayTerms = (RuleGroupDisplayTerms)searchContainer.getDisplayTerms();
		RuleGroupSearchTerms searchTerms = (RuleGroupSearchTerms)searchContainer.getSearchTerms();

		if (displayTerms.getGroupId() == 0) {
			displayTerms.setGroupId(groupId);
			searchTerms.setGroupId(groupId);
		}
		%>

		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/mobile_device_rules/rule_group_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup"
			escapedModel="<%= true %>"
			keyProperty="ruleGroupId"
			modelVar="ruleGroup"
		>

			<liferay-ui:search-container-column-text
				name="name"
				value="<%= ruleGroup.getName(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= ruleGroup.getDescription(locale) %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				MDRRuleGroupInstance ruleGroupInstance = MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(className, classPK, ruleGroup.getRuleGroupId());
				%>

				<c:if test="<%= (ruleGroupInstance == null) %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("rulegroupid", ruleGroup.getRuleGroupId());
					data.put("rulegroupname", ruleGroup.getName());
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator type="more" />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectRuleGroupFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>