<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

AccountGroupDisplay accountGroupDisplay = (AccountGroupDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_GROUP_DISPLAY);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((accountGroupDisplay.getAccountGroupId() == 0) ? LanguageUtil.get(request, "add-account-group") : LanguageUtil.format(request, "edit-x", accountGroupDisplay.getName(), false));
%>

<aui:model-context bean="<%= accountGroupDisplay.getAccountGroup() %>" model="<%= AccountGroup.class %>" />

<portlet:actionURL name="/account_admin/edit_account_group" var="editAccountGroupURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountGroupId" value="<%= String.valueOf(accountGroupDisplay.getAccountGroupId()) %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editAccountGroupURL %>"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (accountGroupDisplay.getAccountGroupId() == 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-frontend:edit-form-body>
		<h2 class="sheet-title">
			<liferay-ui:message key="information" />
		</h2>

		<aui:input label="account-group-name" name="name" />

		<liferay-ui:error embed="<%= false %>" key="<%= DuplicateAccountGroupExternalReferenceCodeException.class.getName() %>" message="the-given-external-reference-code-belongs-to-another-account-group" />

		<aui:input label="external-reference-code" name="externalReferenceCode" />

		<aui:field-wrapper cssClass="form-group lfr-input-text-container">
			<aui:input name="description" type="textarea" value="<%= accountGroupDisplay.getDescription() %>" />
		</aui:field-wrapper>

		<liferay-util:include page="/account_groups_admin/account_group/custom_fields.jsp" servletContext="<%= application %>" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= backURL %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>