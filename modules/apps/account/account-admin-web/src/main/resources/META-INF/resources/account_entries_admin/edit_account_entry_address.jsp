<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long accountEntryAddressId = ParamUtil.getLong(renderRequest, "accountEntryAddressId");

Address address = AddressLocalServiceUtil.fetchAddress(accountEntryAddressId);

long accountEntryId = ParamUtil.getLong(request, "accountEntryId");
%>

<liferay-frontend:screen-navigation
	key="<%= AccountScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ACCOUNT_ADDRESS %>"
	modelBean="<%= address %>"
	portletURL='<%=
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/account_admin/edit_account_entry_address"
		).setParameter(
			"accountEntryAddressId", accountEntryAddressId
		).setParameter(
			"accountEntryId", accountEntryId
		).buildPortletURL()
	%>'
/>