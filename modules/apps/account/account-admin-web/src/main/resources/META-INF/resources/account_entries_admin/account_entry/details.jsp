<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((accountEntryDisplay.getAccountEntryId() == 0) ? LanguageUtil.get(request, "add-account") : LanguageUtil.format(request, "edit-x", accountEntryDisplay.getName(), false));
%>

<portlet:actionURL name="/account_admin/edit_account_entry" var="editAccountURL" />

<liferay-frontend:edit-form
	action="<%= editAccountURL %>"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (accountEntryDisplay.getAccountEntryId() == 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-frontend:edit-form-body>
		<h2 class="sheet-title">
			<liferay-ui:message key="information" />
		</h2>

		<liferay-util:include page="/account_entries_admin/account_entry/display_data.jsp" servletContext="<%= application %>" />

		<c:choose>
			<c:when test="<%= Objects.equals(AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS, accountEntryDisplay.getType()) && accountEntryDisplay.isEmailAddressDomainValidationEnabled() %>">
				<div class="business-account-only">
					<liferay-util:include page="/account_entries_admin/account_entry/domains.jsp" servletContext="<%= application %>" />
				</div>
			</c:when>
			<c:when test="<%= Objects.equals(AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON, accountEntryDisplay.getType()) %>">
				<div class="person-account-only">
					<liferay-util:include page="/account_entries_admin/account_entry/person_account_entry_user.jsp" servletContext="<%= application %>" />
				</div>
			</c:when>
		</c:choose>

		<c:if test="<%= accountEntryDisplay.getAccountEntryId() > 0 %>">
			<liferay-util:include page="/account_entries_admin/account_entry/default_addresses.jsp" servletContext="<%= application %>" />
		</c:if>

		<liferay-util:include page="/account_entries_admin/account_entry/categorization.jsp" servletContext="<%= application %>" />

		<liferay-util:include page="/account_entries_admin/account_entry/custom_fields.jsp" servletContext="<%= application %>" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= backURL %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<c:if test="<%= accountEntryDisplay.getAccountEntryId() == 0 %>">
	<liferay-frontend:component
		componentId="AccountEntriesAdminPortlet"
		module="account_entries_admin/js/AccountEntriesAdminPortlet.es"
	/>
</c:if>