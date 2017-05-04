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
String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PasswordPoliciesAdminPortletKeys.PASSWORD_POLICIES_ADMIN, "display-style", "list");
}
else {
	portalPreferences.setValue(PasswordPoliciesAdminPortletKeys.PASSWORD_POLICIES_ADMIN, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

PortletURL portletURL = renderResponse.createRenderURL();

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();

boolean passwordPolicyEnabled = LDAPSettingsUtil.isPasswordPolicyEnabled(company.getCompanyId());

PasswordPolicySearch searchContainer = new PasswordPolicySearch(renderRequest, portletURL);

searchContainer.setId("passwordPolicy");
searchContainer.setRowChecker(new PasswordPolicyChecker(renderResponse));

String description = LanguageUtil.get(request, "javax.portlet.description.com_liferay_password_policies_admin_web_portlet_PasswordPoliciesAdminPortlet") + " " + LanguageUtil.get(request, "when-no-password-policy-is-assigned-to-a-user,-either-explicitly-or-through-an-organization,-the-default-password-policy-is-used");

portletDisplay.setDescription(description);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "password-policies"), null);
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
	searchContainerId="passwordPolicies"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= searchContainer.getOrderByCol() %>"
			orderByType="<%= searchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive", "icon", "list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<aui:a cssClass="btn" href="javascript:;" iconCssClass="icon-trash" id="deletePasswordPolicies" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURLString %>" cssClass="container-fluid-1280" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="passwordPolicyIds" type="hidden" />

	<div id="breadcrumb">
		<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
	</div>

	<c:if test="<%= passwordPolicyEnabled %>">
		<div class="alert alert-info">
			<liferay-ui:message key="you-are-using-ldaps-password-policy" />
		</div>
	</c:if>

	<c:if test="<%= !passwordPolicyEnabled && windowState.equals(WindowState.MAXIMIZED) %>">
		<liferay-ui:search-container
			id="passwordPolicies"
			searchContainer="<%= searchContainer %>"
			var="passwordPolicySearchContainer"
		>

			<%
			PasswordPolicyDisplayTerms searchTerms = (PasswordPolicyDisplayTerms)passwordPolicySearchContainer.getSearchTerms();

			total = PasswordPolicyServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords());

			passwordPolicySearchContainer.setTotal(total);

			List results = PasswordPolicyServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), passwordPolicySearchContainer.getStart(), passwordPolicySearchContainer.getEnd(), passwordPolicySearchContainer.getOrderByComparator());

			passwordPolicySearchContainer.setResults(results);

			PortletURL passwordPoliciesRedirectURL = PortletURLUtil.clone(portletURL, renderResponse);

			passwordPoliciesRedirectURL.setParameter(passwordPolicySearchContainer.getCurParam(), String.valueOf(passwordPolicySearchContainer.getCur()));
			%>

			<aui:input name="passwordPoliciesRedirect" type="hidden" value="<%= passwordPoliciesRedirectURL.toString() %>" />

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.PasswordPolicy"
				escapedModel="<%= true %>"
				keyProperty="passwordPolicyId"
				modelVar="passwordPolicy"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcPath" value="/edit_password_policy.jsp" />
					<portlet:param name="redirect" value="<%= passwordPolicySearchContainer.getIteratorURL().toString() %>" />
					<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />
				</portlet:renderURL>

				<%@ include file="/search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= passwordPolicySearchContainer %>" />
		</liferay-ui:search-container>
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

				document.<portlet:namespace />fm.p_p_lifecycle.value = '1';

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