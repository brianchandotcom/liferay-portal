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

String[] types = GetterUtil.getStringValues(request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_ALLOWED_TYPES), AccountConstants.ACCOUNT_ENTRY_TYPES);
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

			<c:choose>
				<c:when test="<%= accountEntryDisplay.getAccountEntryId() > 0 %>">
					<aui:input disabled="<%= true %>" label="type" name="type" value="<%= LanguageUtil.get(request, accountEntryDisplay.getType()) %>" />
				</c:when>
				<c:otherwise>
					<aui:select label="type" name="type">

						<%
						for (String type : types) {
						%>

							<aui:option label="<%= LanguageUtil.get(request, type) %>" value="<%= type %>" />

						<%
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>

			<aui:input helpMessage="tax-id-help" label="tax-id" name="taxIdNumber" type="text" value="<%= accountEntryDisplay.getTaxIdNumber() %>">
				<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(AccountEntry.class.getName(), "taxIdNumber") %></aui:validator>
			</aui:input>

			<liferay-ui:error embed="<%= false %>" key="<%= DuplicateAccountEntryExternalReferenceCodeException.class.getName() %>" message="the-given-external-reference-code-belongs-to-another-account" />

			<aui:input label="external-reference-code" name="externalReferenceCode" type="text" value="<%= accountEntryDisplay.getExternalReferenceCode() %>" />

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