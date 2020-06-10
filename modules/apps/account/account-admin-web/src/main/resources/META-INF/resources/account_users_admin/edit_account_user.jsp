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
	headerContainerCssClass=""
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
%>

<c:if test="<%= Objects.equals(AccountScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL, screenNavigationCategoryKey) && Objects.equals(AccountScreenNavigationEntryConstants.ENTRY_KEY_INFORMATION, screenNavigationEntryKey) %>">

	<%
	AccountUserDisplay accountUserDisplay = AccountUserDisplay.of(selUser);

	PortletURL viewValidDomainsURL = renderResponse.createRenderURL();

	viewValidDomainsURL.setParameter("mvcPath", "/account_users_admin/account_user/view_valid_domains.jsp");
	viewValidDomainsURL.setParameter("validDomains", accountUserDisplay.getAccountValidDomains());
	viewValidDomainsURL.setWindowState(LiferayWindowState.POP_UP);

	Map<String, Object> componentContext = HashMapBuilder.<String, Object>put(
			"validDomains", accountUserDisplay.getAccountValidDomains()
	).put(
			"viewValidDomainsURL", viewValidDomainsURL.toString()
	).build();
	%>

	<liferay-frontend:component
		componentId="AccountUserEmailDomainValidator"
		context="<%= componentContext %>"
		module="account_users_admin/js/AccountUserEmailDomainValidator.es"
	/>
</c:if>