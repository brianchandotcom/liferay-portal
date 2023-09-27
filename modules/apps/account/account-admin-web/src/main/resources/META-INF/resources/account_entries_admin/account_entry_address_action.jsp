<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AddressDisplay addressDisplay = (AddressDisplay)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= AccountEntryPermission.contains(permissionChecker, accountEntryDisplay.getAccountEntryId(), AccountActionKeys.MANAGE_ADDRESSES) %>">
		<portlet:renderURL var="editAccountEntryAddressURL">
			<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_entry_address" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="accountEntryAddressId" value="<%= String.valueOf(addressDisplay.getAddressId()) %>" />
			<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editAccountEntryAddressURL %>"
		/>

		<portlet:actionURL name="/account_admin/delete_account_entry_addresses" var="deleteAccounEntryAddressURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="accountEntryAddressIds" value="<%= String.valueOf(addressDisplay.getAddressId()) %>" />
			<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteAccounEntryAddressURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>