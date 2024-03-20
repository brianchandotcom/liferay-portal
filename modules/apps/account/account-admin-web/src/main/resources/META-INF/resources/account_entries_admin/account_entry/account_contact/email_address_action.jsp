<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

EmailAddress emailAddress = (EmailAddress)row.getObject();

ContactInformationActionDropdownItemsProvider contactInformationActionDropdownItemsProvider = new ContactInformationActionDropdownItemsProvider(request, ListTypeConstants.EMAIL_ADDRESS, "/account_entries_admin/account_entry/account_contact/edit_email_address.jsp", emailAddress.getEmailAddressId(), renderResponse, emailAddress.getClassName(), emailAddress.getClassPK());
%>

<clay:dropdown-actions
	aria-label='<%= LanguageUtil.get(request, "edit-email-address") %>'
	dropdownItems="<%= contactInformationActionDropdownItemsProvider.getActionDropdownItems() %>"
/>