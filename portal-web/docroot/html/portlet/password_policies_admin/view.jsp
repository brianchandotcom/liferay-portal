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

<%@ include file="/html/portlet/password_policies_admin/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/password_policies_admin/view");

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();

boolean passwordPolicyEnabled = LDAPSettingsUtil.isPasswordPolicyEnabled(company.getCompanyId());
%>

<aui:form action="<%= portletURLString %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<c:if test="<%= passwordPolicyEnabled %>">
		<div class="alert alert-info">
			<liferay-ui:message key="you-are-using-ldaps-password-policy" />
		</div>
	</c:if>

	<%
	PasswordPolicySearch searchContainer = new PasswordPolicySearch(renderRequest, portletURL);

	List headerNames = searchContainer.getHeaderNames();

	headerNames.add(StringPool.BLANK);
	%>

	<aui:nav-bar>
		<aui:nav>
			<portlet:renderURL var="viewPasswordPoliciesURL">
				<portlet:param name="struts_action" value="/password_policies_admin/view" />
			</portlet:renderURL>

			<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_PASSWORD_POLICY) %>">
				<portlet:renderURL var="addPasswordPolicyURL">
					<portlet:param name="struts_action" value="/password_policies_admin/edit_password_policy" />
					<portlet:param name="redirect" value="<%= viewPasswordPoliciesURL %>" />
				</portlet:renderURL>

				<aui:nav-item href="<%= addPasswordPolicyURL %>" iconCssClass="icon-plus" label="add" />
			</c:if>
		</aui:nav>

		<c:if test="<%= !passwordPolicyEnabled %>">
			<aui:nav-bar-search cssClass="pull-right" file="/html/portlet/password_policies_admin/password_policy_search.jsp" searchContainer="<%= searchContainer %>" />
		</c:if>
	</aui:nav-bar>

	<div class="alert alert-info">
		<liferay-ui:message key="when-no-password-policy-is-assigned-to-a-user,-either-explicitly-or-through-an-organization,-the-default-password-policy-will-be-used" />
	</div>

	<c:if test="<%= !passwordPolicyEnabled && windowState.equals(WindowState.MAXIMIZED) %>">

		<%
		PasswordPolicyDisplayTerms searchTerms = (PasswordPolicyDisplayTerms)searchContainer.getSearchTerms();

		int total = PasswordPolicyLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName());

		searchContainer.setTotal(total);

		List results = PasswordPolicyLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

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

			rowURL.setParameter("struts_action", "/password_policies_admin/edit_password_policy");
			rowURL.setParameter("redirect", searchContainer.getIteratorURL().toString());
			rowURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));

			// Name

			row.addText(HtmlUtil.escape(passwordPolicy.getName()), rowURL);

			// Description

			row.addText(HtmlUtil.escape(passwordPolicy.getDescription()), rowURL);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/password_policies_admin/password_policy_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:if>
</aui:form>