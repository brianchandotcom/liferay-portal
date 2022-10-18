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
	backURL = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCRenderCommandName(
		"/account_admin/edit_account_entry"
	).setParameter(
		"accountEntryId", accountEntryDisplay.getAccountEntryId()
	).setParameter(
		"screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_USERS
	).buildString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.format(request, "add-new-user-to-x", accountEntryDisplay.getName(), false));
%>

<portlet:actionURL name="/account_admin/add_account_user" var="addAccountUsersURL" />

<liferay-frontend:edit-form
	action="<%= addAccountUsersURL %>"
>
	<liferay-frontend:edit-form-body>
		<portlet:renderURL var="defaultRedirect">
			<portlet:param name="mvcPath" value="/account_users_admin/edit_account_user.jsp" />
		</portlet:renderURL>

		<aui:input name="redirect" type="hidden" value='<%= ParamUtil.getString(request, "redirect", defaultRedirect) %>' />
		<aui:input name="accountEntryId" type="hidden" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />

		<h2 class="sheet-title">
			<liferay-ui:message key="information" />
		</h2>

		<clay:sheet-section>
			<h3 class="sheet-subtitle">
				<liferay-ui:message key="user-display-data" />
			</h3>

			<clay:row>
				<clay:col
					md="6"
				>
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

					<aui:model-context model="<%= User.class %>" />

					<aui:input name="screenName">

						<%
						ScreenNameValidator screenNameValidator = ScreenNameValidatorFactory.getInstance();
						%>

						<c:if test="<%= Validator.isNotNull(screenNameValidator.getAUIValidatorJS()) %>">
							<aui:validator errorMessage="<%= screenNameValidator.getDescription(locale) %>" name="custom">
								<%= screenNameValidator.getAUIValidatorJS() %>
							</aui:validator>
						</c:if>
					</aui:input>

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
				</clay:col>

				<clay:col
					md="6"
				>
					<div class="text-center">
						<liferay-ui:logo-selector
							currentLogoURL='<%= themeDisplay.getPathImage() + "/user_portrait?img_id=0" %>'
							defaultLogo="<%= true %>"
							defaultLogoURL='<%= themeDisplay.getPathImage() + "/user_portrait?img_id=0" %>'
							tempImageFileName="0"
						/>
					</div>

					<aui:input label="job-title" maxlength='<%= ModelHintsUtil.getMaxLength(Contact.class.getName(), "jobTitle") %>' name="jobTitle" type="text">
					</aui:input>
				</clay:col>
			</clay:row>
		</clay:sheet-section>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= backURL %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<c:if test="<%= !Objects.equals(accountEntryDisplay.getType(), AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON) && (accountEntryDisplay.isValidateUserEmailAddress() || Validator.isNotNull(AccountUserDisplay.getBlockedDomains(themeDisplay.getCompanyId()))) %>">

	<%
	Map<String, Object> context = HashMapBuilder.<String, Object>put(
		"accountEntryNames", accountEntryDisplay.getName()
	).build();

	if (Validator.isNotNull(AccountUserDisplay.getBlockedDomains(themeDisplay.getCompanyId()))) {
		context.put("blockedDomains", AccountUserDisplay.getBlockedDomains(themeDisplay.getCompanyId()));
	}

	if (accountEntryDisplay.isValidateUserEmailAddress()) {
		context.put("validDomains", accountEntryDisplay.getDomains());

		PortletURL viewValidDomainsURL = PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/account_users_admin/account_user/view_valid_domains.jsp"
		).setParameter(
			"validDomains", accountEntryDisplay.getDomains()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildPortletURL();

		context.put("viewValidDomainsURL", viewValidDomainsURL.toString());
	}
	%>

	<liferay-frontend:component
		componentId="AccountUserEmailDomainValidator"
		context="<%= context %>"
		module="account_users_admin/js/AccountUserEmailDomainValidator.es"
	/>
</c:if>