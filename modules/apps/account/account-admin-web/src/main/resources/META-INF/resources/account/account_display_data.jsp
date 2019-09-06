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
AccountDisplay accountDisplay = (AccountDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_DISPLAY);
%>

<div class="sheet-section">
	<h3 class="sheet-subtitle">
		<%= LanguageUtil.get(request, "account-display-data") %>
	</h3>

	<aui:row>
		<aui:col width="<%= 50 %>">
			<aui:field-wrapper cssClass="form-group lfr-input-text-container">
				<aui:input label="account-name" name="name" required="<%= true %>" type="text" value="<%= (accountDisplay == null) ? StringPool.BLANK : accountDisplay.getName() %>">
					<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(AccountEntry.class.getName(), "name") %></aui:validator>
				</aui:input>
			</aui:field-wrapper>
		</aui:col>

		<aui:col width="<%= 40 %>">
			<div class="text-center">
				<liferay-ui:logo-selector
					currentLogoURL='<%= ((accountDisplay == null) || (accountDisplay.getLogoId() == 0)) ? themeDisplay.getPathImage() + "/organization_logo?img_id=0" : accountDisplay.getLogoURL(themeDisplay) %>'
					defaultLogo="<%= (accountDisplay == null) || (accountDisplay.getLogoId() == 0) %>"
					defaultLogoURL='<%= themeDisplay.getPathImage() + "/organization_logo?img_id=0" %>'
					tempImageFileName='<%= (accountDisplay == null) ? "0" : String.valueOf(accountDisplay.getAccountId()) %>'
				/>
			</div>
		</aui:col>
	</aui:row>

	<aui:field-wrapper cssClass="form-group lfr-input-text-container">
		<aui:input name="description" type="textarea" value="<%= (accountDisplay == null) ? StringPool.BLANK : accountDisplay.getDescription() %>" />
	</aui:field-wrapper>

	<aui:field-wrapper cssClass="form-group lfr-input-text-container">
		<aui:input label="" labelOff="inactive" labelOn="active" name="active" type="toggle-switch" value="<%= (accountDisplay == null) ? true : accountDisplay.isActive() %>" />
	</aui:field-wrapper>
</div>