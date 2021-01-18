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
User selUser = PortalUtil.getSelectedUser(request, false);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
portletURL.setParameter("mvcPath", "/account_users_admin/edit_account_user.jsp");
%>

<liferay-frontend:screen-navigation
	containerCssClass="col-lg-8"
	containerWrapperCssClass="container-fluid container-fluid-max-xl container-form-lg"
	context="<%= selUser %>"
	key="<%= AccountScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ACCOUNT_USER %>"
	menubarCssClass="menubar menubar-transparent menubar-vertical-expand-lg"
	navCssClass="col-lg-3"
	portletURL="<%= portletURL %>"
/>

<%
String screenNavigationCategoryKey = ParamUtil.getString(request, "screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL);

String screenNavigationEntryKey = ParamUtil.getString(request, "screenNavigationEntryKey");

if (Validator.isNull(screenNavigationEntryKey)) {
	screenNavigationEntryKey = AccountScreenNavigationEntryConstants.ENTRY_KEY_INFORMATION;
}

AccountUserDisplay accountUserDisplay = AccountUserDisplay.of(selUser);
%>

<c:if test="<%= Objects.equals(AccountScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL, screenNavigationCategoryKey) && Objects.equals(AccountScreenNavigationEntryConstants.ENTRY_KEY_INFORMATION, screenNavigationEntryKey) %>">
	<c:if test="<%= accountUserDisplay.isValidateEmailAddress() || Validator.isNotNull(AccountUserDisplay.getBlockedDomains(themeDisplay.getCompanyId())) %>">

		<%
		Map<String, Object> context = HashMapBuilder.<String, Object>put(
			"accountEntryNames", accountUserDisplay.getAccountEntryNamesString(request)
		).build();

		if (Validator.isNotNull(AccountUserDisplay.getBlockedDomains(themeDisplay.getCompanyId()))) {
			context.put("blockedDomains", AccountUserDisplay.getBlockedDomains(themeDisplay.getCompanyId()));
		}

		if (accountUserDisplay.isValidateEmailAddress()) {
			context.put("validDomains", accountUserDisplay.getValidDomainsString());

			PortletURL viewValidDomainsURL = renderResponse.createRenderURL();

			viewValidDomainsURL.setParameter("mvcPath", "/account_users_admin/account_user/view_valid_domains.jsp");
			viewValidDomainsURL.setParameter("validDomains", accountUserDisplay.getValidDomainsString());
			viewValidDomainsURL.setWindowState(LiferayWindowState.POP_UP);

			context.put("viewValidDomainsURL", viewValidDomainsURL.toString());
		}
		%>

		<liferay-frontend:component
			componentId="AccountUserEmailDomainValidator"
			context="<%= context %>"
			module="account_users_admin/js/AccountUserEmailDomainValidator.es"
		/>
	</c:if>
</c:if>