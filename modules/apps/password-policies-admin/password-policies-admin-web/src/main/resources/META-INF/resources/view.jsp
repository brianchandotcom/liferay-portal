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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

PortletURL portletURL = renderResponse.createRenderURL();

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();

boolean passwordPolicyEnabled = LDAPSettingsUtil.isPasswordPolicyEnabled(company.getCompanyId());

PasswordPolicySearch searchContainer = new PasswordPolicySearch(renderRequest, portletURL);

searchContainer.setId("passwordPolicy");
searchContainer.setRowChecker(new PasswordPolicyChecker(renderResponse));
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="password-policies" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= !passwordPolicyEnabled %>">

		<%
		PortletURL searchURL = renderResponse.createRenderURL();
		%>

		<aui:nav-bar-search searchContainer="<%= searchContainer %>">
			<aui:form action="<%= searchURL %>" name="searchFm">
				<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="passwordPolicy"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= searchContainer.getOrderByCol() %>"
			orderByType="<%= searchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<aui:a cssClass="btn" href="javascript:;" iconCssClass="icon-trash" id="deletePasswordPolicies" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURLString %>" cssClass="container-fluid-1280" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="passwordPolicyIds" type="hidden" />

	<c:if test="<%= passwordPolicyEnabled %>">
		<div class="alert alert-info">
			<liferay-ui:message key="you-are-using-ldaps-password-policy" />
		</div>
	</c:if>

	<%
	List headerNames = searchContainer.getHeaderNames();

	headerNames.add(StringPool.BLANK);
	%>

	<div class="alert alert-info">
		<liferay-ui:message key="when-no-password-policy-is-assigned-to-a-user,-either-explicitly-or-through-an-organization,-the-default-password-policy-is-used" />
	</div>

	<c:if test="<%= !passwordPolicyEnabled && windowState.equals(WindowState.MAXIMIZED) %>">

		<%
		PasswordPolicyDisplayTerms searchTerms = (PasswordPolicyDisplayTerms)searchContainer.getSearchTerms();

		int total = PasswordPolicyLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords());

		searchContainer.setTotal(total);

		List results = PasswordPolicyLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

		searchContainer.setResults(results);

		PortletURL passwordPoliciesRedirectURL = PortletURLUtil.clone(portletURL, renderResponse);

		passwordPoliciesRedirectURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));
		%>

		<aui:input name="passwordPoliciesRedirect" type="hidden" value="<%= passwordPoliciesRedirectURL.toString() %>" />

		<%
		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			PasswordPolicy passwordPolicy = (PasswordPolicy)results.get(i);

			ResultRow row = new ResultRow(passwordPolicy, passwordPolicy.getPasswordPolicyId(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("mvcPath", "/edit_password_policy.jsp");
			rowURL.setParameter("redirect", searchContainer.getIteratorURL().toString());
			rowURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));

			// Name

			row.addText(HtmlUtil.escape(passwordPolicy.getName()), rowURL);

			// Description

			row.addText(HtmlUtil.escape(passwordPolicy.getDescription()), rowURL);

			// Action

			row.addJSP("/password_policy_action.jsp", "list-group-item-field", application, request, response);

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= searchContainer %>" />
	</c:if>
</aui:form>

<aui:script>
	$('#<portlet:namespace />deletePasswordPolicies').on(
		'click',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
				var form = AUI.$(document.<portlet:namespace />fm);

				var passwordPolicyIds = Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds');

				form.attr('method', 'post');
				form.fm('passwordPolicyIds').val(passwordPolicyIds);

				submitForm(form, '<portlet:actionURL name="deletePasswordPolicies" />');
			}
		}
	);
</aui:script>

<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_PASSWORD_POLICY) %>">
	<portlet:renderURL var="viewPasswordPoliciesURL" />

	<portlet:renderURL var="addPasswordPolicyURL">
		<portlet:param name="mvcPath" value="/edit_password_policy.jsp" />
		<portlet:param name="redirect" value="<%= viewPasswordPoliciesURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addPasswordPolicyURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>