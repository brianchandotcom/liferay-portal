<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

AccountEntry accountEntry = AccountEntryLocalServiceUtil.getAccountEntry(accountEntryDisplay.getAccountEntryId());

request.setAttribute("contact_information.jsp-className", AccountEntry.class.getName());
request.setAttribute("contact_information.jsp-classPK", accountEntry.getAccountEntryId());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())));
%>

<portlet:actionURL name="/account_admin/edit_account_entry_contact" var="editAccountEntryContactURL" />

<liferay-frontend:edit-form
	action="<%= editAccountEntryContactURL %>"
>
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(accountEntry.getAccountEntryId()) %>" />

	<liferay-frontend:edit-form-body>
		<liferay-util:include page="/common/addresses.jsp" servletContext="<%= application %>">
			<liferay-util:param name="emptyResultsMessage" value="this-account-does-not-have-any-addresses" />
		</liferay-util:include>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>