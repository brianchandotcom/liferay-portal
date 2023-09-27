<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="sheet-section">
	<liferay-ui:message key="you-can-only-have-one-timebased-otp-password-configured-remove-your-existing-timebased-otp-password-to-generate-new-timebased-otp-password" />

	<aui:input name="removeExistingSetup" type="hidden" value="<%= true %>" />
</div>

<div class="sheet-footer">
	<button class="btn btn-danger" type="submit">
		<liferay-ui:message key="button-remove-configured-timebased-otp" />
	</button>
</div>