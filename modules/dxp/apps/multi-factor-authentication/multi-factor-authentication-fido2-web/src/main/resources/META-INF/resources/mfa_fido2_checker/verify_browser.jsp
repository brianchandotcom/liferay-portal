<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="portlet-msg-info">
	<liferay-ui:message key="verify-identity-using-a-registered-fido2-authenticator" />
</div>

<aui:button-row>
	<clay:button
		additionalProps='<%=
			HashMapBuilder.put(
				"assertionRequest", request.getAttribute(MFAFIDO2WebKeys.MFA_FIDO2_ASSERTION_REQUEST)
			).build()
		%>'
		label="verify"
		propsTransformer="js/AuthenticationTransformer"
	/>
</aui:button-row>

<div id="<portlet:namespace />messageContainer"></div>

<aui:input name="responseJSON" showRequiredLabel="yes" type="hidden" />