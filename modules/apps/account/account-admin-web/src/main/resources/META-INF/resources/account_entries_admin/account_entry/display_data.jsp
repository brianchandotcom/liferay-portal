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
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);
%>

<clay:sheet-section>
	<h3 class="sheet-subtitle">
		<%= LanguageUtil.get(request, "account-display-data") %>
	</h3>

	<clay:row>
		<clay:col
			md="6"
		>
			<aui:input label="account-name" name="name" required="<%= true %>" type="text" value="<%= accountEntryDisplay.getName() %>">
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(AccountEntry.class.getName(), "name") %></aui:validator>
			</aui:input>

			<aui:select disabled="<%= accountEntryDisplay.getAccountEntryId() > 0 %>" label="type" name="type">

				<%
				for (String type : AccountConstants.ACCOUNT_ENTRY_TYPES) {
				%>

					<aui:option label="<%= LanguageUtil.get(request, type) %>" selected="<%= type.equals(accountEntryDisplay.getType()) %>" value="<%= type %>" />

				<%
				}
				%>

				<c:if test="<%= Objects.equals(accountEntryDisplay.getType(), AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST) %>">
					<aui:option label="<%= LanguageUtil.get(request, accountEntryDisplay.getType()) %>" selected="<%= true %>" value="<%= accountEntryDisplay.getType() %>" />
				</c:if>
			</aui:select>

			<aui:input helpMessage="tax-id-help" label="tax-id" name="taxIdNumber" type="text" value="<%= accountEntryDisplay.getTaxIdNumber() %>">
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(AccountEntry.class.getName(), "taxIdNumber") %></aui:validator>
			</aui:input>

			<c:if test="<%= accountEntryDisplay.getAccountEntryId() > 0 %>">
				<aui:input cssClass="disabled" label="account-id" name="accountEntryId" readonly="true" type="text" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
			</c:if>
		</clay:col>

		<clay:col
			md="5"
		>
			<div align="middle">
				<label class="control-label"></label>

				<liferay-ui:logo-selector
					currentLogoURL="<%= (accountEntryDisplay.getLogoId() == 0) ? accountEntryDisplay.getDefaultLogoURL(liferayPortletRequest) : accountEntryDisplay.getLogoURL(themeDisplay) %>"
					defaultLogo="<%= accountEntryDisplay.getLogoId() == 0 %>"
					defaultLogoURL="<%= accountEntryDisplay.getDefaultLogoURL(liferayPortletRequest) %>"
					tempImageFileName="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>"
				/>
			</div>
		</clay:col>
	</clay:row>

	<aui:field-wrapper cssClass="form-group lfr-input-text-container">
		<aui:input name="description" type="textarea" value="<%= accountEntryDisplay.getDescription() %>" />
	</aui:field-wrapper>

	<aui:field-wrapper cssClass="form-group lfr-input-text-container">
		<aui:input label="" labelOff="inactive" labelOn="active" name="active" type="toggle-switch" value="<%= accountEntryDisplay.isActive() %>" />
	</aui:field-wrapper>
</clay:sheet-section>