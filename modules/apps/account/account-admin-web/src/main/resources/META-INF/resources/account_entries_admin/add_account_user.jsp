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

String backURL = ParamUtil.getString(request, "backURL");

if (Validator.isNull(backURL)) {
	PortletURL viewAccountUserURL = renderResponse.createRenderURL();

	viewAccountUserURL.setParameter("mvcRenderCommandName", "/account_admin/edit_account_entry");
	viewAccountUserURL.setParameter("screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_USERS);
	viewAccountUserURL.setParameter("accountEntryId", String.valueOf(accountEntryDisplay.getAccountEntryId()));

	backURL = viewAccountUserURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.format(request, "add-new-user-to-x", accountEntryDisplay.getName(), false));
%>

<portlet:renderURL var="redirect">
	<portlet:param name="mvcPath" value="/account_users_admin/edit_account_user.jsp" />
</portlet:renderURL>

<portlet:actionURL name="/account_admin/add_account_user" var="addAccountUsersURL" />

<liferay-frontend:edit-form
	action="<%= addAccountUsersURL %>"
>
	<liferay-frontend:edit-form-body>
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="accountEntryId" type="hidden" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />

		<h2 class="sheet-title">
			<%= LanguageUtil.get(request, "information") %>
		</h2>

		<div class="sheet-section">
			<h3 class="sheet-subtitle">
				<%= LanguageUtil.get(request, "user-display-data") %>
			</h3>

			<aui:row>
				<aui:col width="<%= 50 %>">
					<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeDuplicate.class %>" focusField="screenName" message="the-screen-name-you-requested-is-already-taken" />
					<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeNull.class %>" focusField="screenName" message="the-screen-name-cannot-be-blank" />
					<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeNumeric.class %>" focusField="screenName" message="the-screen-name-cannot-contain-only-numeric-values" />
					<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeReserved.class %>" focusField="screenName" message="the-screen-name-you-requested-is-reserved" />
					<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeReservedForAnonymous.class %>" focusField="screenName" message="the-screen-name-you-requested-is-reserved-for-the-anonymous-user" />
					<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeUsedByGroup.class %>" focusField="screenName" message="the-screen-name-you-requested-is-already-taken-by-a-site" />
					<liferay-ui:error exception="<%= UserScreenNameException.MustProduceValidFriendlyURL.class %>" focusField="screenName" message="the-screen-name-you-requested-must-produce-a-valid-friendly-url" />

					<liferay-ui:error exception="<%= UserScreenNameException.MustValidate.class %>" focusField="screenName">

						<%
						UserScreenNameException.MustValidate usne = (UserScreenNameException.MustValidate)errorException;
						%>

						<liferay-ui:message key="<%= usne.screenNameValidator.getDescription(locale) %>" />
					</liferay-ui:error>

					<aui:input label="screen-name" name="screenName" required="<%= true %>" type="text" />

					<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeDuplicate.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-already-taken" />
					<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeNull.class %>" focusField="emailAddress" message="please-enter-an-email-address" />
					<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBePOP3User.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-reserved" />
					<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeReserved.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-reserved" />
					<liferay-ui:error exception="<%= UserEmailAddressException.MustNotUseCompanyMx.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-not-valid-because-its-domain-is-reserved" />
					<liferay-ui:error exception="<%= UserEmailAddressException.MustValidate.class %>" focusField="emailAddress" message="please-enter-a-valid-email-address" />

					<aui:input label="email-address" name="emailAddress" required="<%= true %>" type="text">
						<aui:validator name="email" />
					</aui:input>

					<liferay-ui:user-name-fields />
				</aui:col>

				<aui:col width="<%= 40 %>">
					<div class="text-center">

						<%
						UserFileUploadsConfiguration userFileUploadsConfiguration = (UserFileUploadsConfiguration)request.getAttribute(UserFileUploadsConfiguration.class.getName());
						%>

						<liferay-ui:logo-selector
							currentLogoURL='<%= themeDisplay.getPathImage() + "/user_portrait?img_id=0" %>'
							defaultLogo="<%= true %>"
							defaultLogoURL='<%= themeDisplay.getPathImage() + "/user_portrait?img_id=0" %>'
							maxFileSize="<%= userFileUploadsConfiguration.imageMaxSize() %>"
							tempImageFileName="0"
						/>
					</div>
				</aui:col>
			</aui:row>
		</div>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>