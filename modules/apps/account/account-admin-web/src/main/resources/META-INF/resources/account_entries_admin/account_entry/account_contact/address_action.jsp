<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Address address = AddressServiceUtil.getAddress(ParamUtil.getLong(request, "addressId"));

ContactInformationActionDropdownItemsProvider contactInformationActionDropdownItemsProvider = new ContactInformationActionDropdownItemsProvider(request, ListTypeConstants.ADDRESS, "/account_entries_admin/account_entry/account_contact/edit_address.jsp", ParamUtil.getLong(request, "addressId"), renderResponse, address.getClassName(), address.getClassPK());
%>

<clay:dropdown-actions
	aria-label='<%= LanguageUtil.get(request, "edit-address") %>'
	dropdownItems="<%= contactInformationActionDropdownItemsProvider.getActionDropdownItems() %>"
/>