<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

AccountEntry accountEntry = AccountEntryLocalServiceUtil.getAccountEntry(accountEntryDisplay.getAccountEntryId());

Contact accountEntryContact = accountEntry.fetchContact();

long contactId = (accountEntryContact != null) ? accountEntryContact.getContactId() : 0;

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "contact-information"));
%>

<portlet:actionURL name="/account_admin/edit_account_entry_contact" var="editAccountEntryContactURL" />

<liferay-frontend:edit-form
	action="<%= editAccountEntryContactURL %>"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (accountEntryContact == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="accountEntryId" type="hidden" value="<%= accountEntryDisplay.getAccountEntryId() %>" />
	<aui:input name="contactId" type="hidden" value="<%= contactId %>" />

	<liferay-frontend:edit-form-body>
		<clay:sheet-section>
			<liferay-util:include page="/account_entries_admin/account_entry/account_contact/phone_numbers.jsp" servletContext="<%= application %>">
				<liferay-util:param name="emptyResultsMessage" value="this-account-does-not-have-any-phone-numbers" />
			</liferay-util:include>
		</clay:sheet-section>

		<clay:sheet-section>
			<liferay-util:include page="/account_entries_admin/account_entry/account_contact/email_addresses.jsp" servletContext="<%= application %>">
				<liferay-util:param name="emptyResultsMessage" value="this-account-does-not-have-any-email-addresses" />
			</liferay-util:include>
		</clay:sheet-section>

		<clay:sheet-section>
			<liferay-util:include page="/account_entries_admin/account_entry/account_contact/websites.jsp" servletContext="<%= application %>">
				<liferay-util:param name="emptyResultsMessage" value="this-account-does-not-have-any-websites" />
			</liferay-util:include>
		</clay:sheet-section>

		<clay:sheet-section>
			<div class="sheet-subtitle"><liferay-ui:message key="instant-messenger" /></div>

			<liferay-util:include page="/account_entries_admin/account_entry/account_contact/instant_messenger.jsp" servletContext="<%= application %>" />
		</clay:sheet-section>

		<clay:sheet-section>
			<div class="sheet-subtitle"><liferay-ui:message key="sms" /></div>

			<liferay-util:include page="/account_entries_admin/account_entry/account_contact/sms.jsp" servletContext="<%= application %>" />
		</clay:sheet-section>

		<clay:sheet-section>
			<div class="sheet-subtitle"><liferay-ui:message key="social-network" /></div>

			<liferay-util:include page="/account_entries_admin/account_entry/account_contact/social_network.jsp" servletContext="<%= application %>" />
		</clay:sheet-section>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= backURL %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>