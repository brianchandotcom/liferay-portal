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

<div id="<portlet:namespace />messageContainer"></div>

<aui:button-row>
	<clay:button
		additionalProps='<%=
			HashMapBuilder.put(
				"pkccOptions", request.getAttribute(MFAFIDO2WebKeys.MFA_FIDO2_PKCC_OPTIONS)
			).build()
		%>'
		label="button-register-a-fido2-authenticator"
		propsTransformer="js/RegistrationTransformer"
	/>
</aui:button-row>

<aui:input name="responseJSON" showRequiredLabel="yes" type="hidden" />