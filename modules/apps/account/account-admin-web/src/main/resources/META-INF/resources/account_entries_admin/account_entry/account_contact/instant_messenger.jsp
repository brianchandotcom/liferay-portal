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

Contact accountEntryContact = accountEntry.fetchContact();
%>

<aui:model-context bean="<%= accountEntryContact %>" model="<%= Contact.class %>" />

<div class="instant-messenger">
	<aui:input label="jabber" name="jabberSn" />
</div>

<div class="instant-messenger">
	<aui:input label="skype" name="skypeSn" />

	<c:if test="<%= (accountEntryContact != null) && Validator.isNotNull(accountEntryContact.getSkypeSn()) %>">
		<div class="form-feedback-group">
			<div class="form-text">
				<clay:link
					decoration="underline"
					href='<%= "skype:" + HtmlUtil.escapeAttribute(accountEntryContact.getSkypeSn()) + "?call" %>'
					label='<%= LanguageUtil.get(request, "call-this-user") %>'
				/>
			</div>
		</div>
	</c:if>
</div>