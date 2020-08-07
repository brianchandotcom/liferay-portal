<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<div class="sheet-section">
	<liferay-ui:message key="you-can-only-have-one-timebased-otp-password-configured-remove-your-existing-timebased-otp-password-to-generate-new-timebased-otp-password" />

	<aui:input name="mfaRemoveExistingSetup" type="hidden" value="<%= true %>" />
</div>

<div class="sheet-footer">
	<button class="btn btn-danger" type="submit">
		<liferay-ui:message key="button-remove-configured-timebased-otp" />
	</button>
</div>