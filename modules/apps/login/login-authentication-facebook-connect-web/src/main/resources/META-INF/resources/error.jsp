<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="sheet">
	<h2 class="sheet-title">
		<liferay-ui:message key="failed-to-sign-in-using-this-facebook-account" />
	</h2>

	<liferay-ui:error key="<%= MustVerifyEmailAddressException.class.getSimpleName() %>" message="you-need-to-verify-your-email-address-on-facebook-first" />
	<liferay-ui:error key="<%= StrangersNotAllowedException.class.getSimpleName() %>" message="only-known-users-are-allowed-to-sign-in-using-facebook" />
	<liferay-ui:error key="<%= UnknownErrorException.class.getSimpleName() %>" message="there-was-an-unknown-error" />
	<liferay-ui:error key="<%= UserEmailAddressException.MustNotUseCompanyMx.class.getSimpleName() %>" message="this-facebook-account-cannot-be-used-to-register-a-new-user-because-its-email-domain-is-reserved" />

	<aui:button-row>
		<aui:button onClick="window.close();" value="close" />
	</aui:button-row>
</div>