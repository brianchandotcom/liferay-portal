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

<%
List<MFAFIDO2CredentialEntry> mfaIDO2CredentialEntries = MFAFIDO2CredentialEntryLocalServiceUtil.getMFAFIDO2CredentialEntriesByUserId(themeDisplay.getUserId());
%>

<div id="<portlet:namespace />messageContainer"></div>

<c:choose>
	<c:when test="<%= mfaIDO2CredentialEntries.size() < mfaFIDO2Configuration.allowedCredentialsPerUser() %>">
		<aui:button-row>
			<clay:button
				additionalProps='<%=
					HashMapBuilder.put(
						"pkccOptions", request.getAttribute(MFAFIDO2WebKeys.MFA_FIDO2_PKCC_OPTIONS)
					).build()
				%>'
				label="register-a-fido2-authenticator"
				propsTransformer="js/RegistrationTransformer"
			/>
		</aui:button-row>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="you-have-registered-the-maximum-number-of-allowed-authenticators" />

		<aui:input name="mfaRemoveExistingSetup" type="hidden" value="<%= true %>" />

		<button class="btn btn-danger" type="submit">
			<liferay-ui:message key="remove-all-registered-fido2-authenticators" />
		</button>
	</c:otherwise>
</c:choose>

<aui:input name="responseJSON" showRequiredLabel="yes" type="hidden" />

<liferay-ui:search-container
	iteratorURL="<%= renderResponse.createRenderURL() %>"
	total="<%= mfaIDO2CredentialEntries.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= mfaIDO2CredentialEntries.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="MFAFIDO2CredentialEntry"
		modelVar="mfaFIDO2CredentialEntry"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="authenticator-id"
			value="<%= String.valueOf(mfaFIDO2CredentialEntry.getPrimaryKey()) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="registered-date"
			value="<%= String.valueOf(mfaFIDO2CredentialEntry.getCreateDate()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<portlet:actionURL name="/multi-factor-authentication-fido2/remove_mfa_fido2_credential_entry" var="removeMFAFIDO2CredentialEntryURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="mfaFIDO2CredentialEntryId" value="<%= String.valueOf(mfaFIDO2CredentialEntry.getPrimaryKey()) %>" />
				<portlet:param name="setupMFACheckerServiceId" value="<%= String.valueOf(request.getAttribute(MFAFIDO2WebKeys.SETUP_MFA_CHECKER_SERVICE_ID)) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				confirmation="are-you-sure-you-want-to-remove-this-authenticator"
				icon="times-circle"
				message="remove"
				showIcon="<%= true %>"
				url="<%= removeMFAFIDO2CredentialEntryURL %>"
			/>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>