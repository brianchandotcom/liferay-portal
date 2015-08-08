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
String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/mobile_device_rules/view");
portletURL.setParameter("groupId", String.valueOf(groupId));
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="ruleGroupIds" type="hidden" />

	<%
	RuleGroupSearch ruleGroupSearch = new RuleGroupSearch(liferayPortletRequest, portletURL);

	RowChecker rowChecker = new RuleGroupChecker(renderResponse);
	%>

	<liferay-ui:search-container
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= ruleGroupSearch %>"
	>
		<aui:nav-bar>
			<c:if test="<%= MDRPermission.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP) %>">
				<portlet:renderURL var="viewRulesURL">
					<portlet:param name="mvcRenderCommandName" value="/view.jsp" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="className" value="<%= className %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
				</portlet:renderURL>

				<liferay-portlet:renderURL var="addRuleGroupURL">
					<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_rule_group" />
					<portlet:param name="redirect" value="<%= viewRulesURL %>" />
					<portlet:param name="backURL" value="<%= viewRulesURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				</liferay-portlet:renderURL>

				<aui:nav cssClass="navbar-nav" searchContainer="<%= ruleGroupSearch %>">
					<aui:nav-item href="<%= addRuleGroupURL %>" iconCssClass="icon-plus" label="add-device-family" />
				</aui:nav>
			</c:if>

			<aui:nav-bar-search searchContainer="<%= searchContainer %>">

				<%
				request.setAttribute("liferay-ui:search:searchContainer", searchContainer);
				%>

				<liferay-util:include page="/rule_group_search.jsp" servletContext="<%= application %>" />
			</aui:nav-bar-search>
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
			<%@ include file="/rule_group_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.mobile.device.rules.model.MDRRuleGroup"
			escapedModel="<%= true %>"
			keyProperty="ruleGroupId"
			modelVar="ruleGroup"
		>
			<%@ include file="/rule_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<c:if test="<%= total > 0 %>">
			<aui:button-row>
				<aui:button cssClass="delete-rules-button" disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteRules();" %>' value="delete" />
			</aui:button-row>

			<div class="separator"><!-- --></div>
		</c:if>

		<liferay-ui:search-iterator type="more" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

	function <portlet:namespace />deleteRules() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('ruleGroupIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="/mobile_device_rules/edit_rule_group"><portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_rule_group" /></portlet:actionURL>');
		}
	}
</aui:script>