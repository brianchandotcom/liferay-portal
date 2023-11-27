<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long accountEntryId = 0;

AddressDisplay addressDisplay = (AddressDisplay)request.getAttribute(AccountWebKeys.ADDRESS_DISPLAY);

Address address = AddressLocalServiceUtil.fetchAddress(addressDisplay.getAddressId());

if (address != null) {
	accountEntryId = address.getClassPK();
}
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
			"accountEntryAddressId", addressDisplay.getAddressId()
		).setParameter(
			"accountEntryId", accountEntryId
		).buildPortletURL()
	%>'
/>