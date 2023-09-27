<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User selUser = PortalUtil.getSelectedUser(request, false);
%>

<liferay-frontend:screen-navigation
	containerCssClass="col-lg-8"
	containerWrapperCssClass="container-fluid container-fluid-max-xl container-form-lg"
	context="<%= selUser %>"
	key="<%= AccountScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ACCOUNT_USER %>"
	menubarCssClass="menubar menubar-transparent menubar-vertical-expand-lg"
	navCssClass="col-lg-3"
	portletURL='<%=
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/account_users_admin/edit_account_user.jsp"
		).setParameter(
			"p_u_i_d", selUser.getUserId()
		).buildPortletURL()
	%>'
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

			PortletURL viewValidDomainsURL = PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCPath(
				"/account_users_admin/account_user/view_valid_domains.jsp"
			).setParameter(
				"validDomains", accountUserDisplay.getValidDomainsString()
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildPortletURL();

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