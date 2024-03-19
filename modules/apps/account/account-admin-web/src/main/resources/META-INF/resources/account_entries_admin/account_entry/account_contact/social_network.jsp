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
%>

<aui:model-context bean="<%= accountEntry.fetchContact() %>" model="<%= Contact.class %>" />

<div class="social-network">
	<aui:input label="facebook" name="facebookSn" />

	<i class="icon-facebook-sign"></i>
</div>

<div class="social-network">
	<aui:input label="twitter" name="twitterSn" />

	<i class="icon-twitter-sign"></i>
</div>