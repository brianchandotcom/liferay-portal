<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

<portlet:actionURL name="/account_admin/edit_account_group" var="editAccountGroupURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountGroupId" value="<%= String.valueOf(accountGroupDisplay.getAccountGroupId()) %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editAccountGroupURL %>"
	cssClass="container-form-lg"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (accountGroupDisplay.getAccountGroupId() == 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-frontend:edit-form-body>
		<h2 class="sheet-title">
			<%= LanguageUtil.get(request, "information") %>
		</h2>

		<aui:input label="account-group-name" name="name" required="<%= true %>" type="text" value="<%= accountGroupDisplay.getName() %>">
			<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(AccountGroup.class.getName(), "name") %></aui:validator>
		</aui:input>

		<aui:field-wrapper cssClass="form-group lfr-input-text-container">
			<aui:input name="description" type="textarea" value="<%= accountGroupDisplay.getDescription() %>" />
		</aui:field-wrapper>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>