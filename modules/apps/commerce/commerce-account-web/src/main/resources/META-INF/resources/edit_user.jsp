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
CommerceAccountDisplayContext commerceAccountDisplayContext = (CommerceAccountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAccount commerceAccount = commerceAccountDisplayContext.getCurrentCommerceAccount();

User selectedUser = commerceAccountDisplayContext.getSelectedUser();

PasswordPolicy passwordPolicy = selectedUser.getPasswordPolicy();

boolean passwordReset = false;
boolean passwordResetDisabled = false;

if ((selectedUser.getLastLoginDate() == null) && ((passwordPolicy == null) || (passwordPolicy.isChangeable() && passwordPolicy.isChangeRequired()))) {
	passwordReset = true;
	passwordResetDisabled = true;
}
else {
	passwordReset = BeanParamUtil.getBoolean(selectedUser, request, "passwordReset");

	if ((passwordPolicy != null) && !passwordPolicy.isChangeable()) {
		passwordResetDisabled = true;
	}
}

boolean canEditUser = (selectedUser.getUserId() == user.getUserId()) || commerceAccountDisplayContext.hasCommerceAccountModelPermissions(commerceAccount.getCommerceAccountId(), CommerceAccountActionKeys.MANAGE_MEMBERS);
%>

<portlet:actionURL name="editCommerceAccountUser" var="editCommerceAccountUserActionURL" />

<div class="account-management">
	<aui:form action="<%= editCommerceAccountUserActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceAccountId" type="hidden" value="<%= (commerceAccount == null) ? 0 : commerceAccount.getCommerceAccountId() %>" />
		<aui:input name="userId" type="hidden" value="<%= (selectedUser == null) ? 0 : selectedUser.getUserId() %>" />

		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="details"
		/>

		<aui:model-context bean="<%= selectedUser %>" model="<%= User.class %>" />

		<liferay-ui:error exception="<%= GroupFriendlyURLException.class %>" focusField="screenName">

			<%
			GroupFriendlyURLException gfurle = (GroupFriendlyURLException)errorException;
			%>

			<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.DUPLICATE %>">
				<liferay-ui:message key="the-screen-name-you-requested-is-associated-with-an-existing-friendly-url" />
			</c:if>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= UserFieldException.class %>">

			<%
			UserFieldException ufe = (UserFieldException)errorException;

			List<String> fields = ufe.getFields();

			StringBundler sb = new StringBundler((2 * fields.size()) - 1);

			for (int i = 0; i < fields.size(); i++) {
				String field = fields.get(i);

				sb.append(LanguageUtil.get(request, TextFormatter.format(field, TextFormatter.K)));

				if ((i + 1) < fields.size()) {
					sb.append(StringPool.COMMA_AND_SPACE);
				}
			}
			%>

			<liferay-ui:message arguments="<%= sb.toString() %>" key="your-portal-administrator-has-disabled-the-ability-to-modify-the-following-fields" translateArguments="<%= false %>" />
		</liferay-ui:error>

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

		<section class="panel panel-secondary">
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-4 account-management__thumbnail-container">
						<aui:fieldset>
							<c:choose>
								<c:when test="<%= canEditUser %>">

									<%
									UserFileUploadsConfiguration userFileUploadsConfiguration = commerceAccountDisplayContext.getUserFileUploadsConfiguration();
									%>

									<liferay-ui:logo-selector
										currentLogoURL="<%= selectedUser.getPortraitURL(themeDisplay) %>"
										defaultLogo="<%= selectedUser.getPortraitId() == 0 %>"
										defaultLogoURL="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), selectedUser.isMale(), 0, null) %>"
										logoDisplaySelector=".user-logo"
										maxFileSize="<%= userFileUploadsConfiguration.imageMaxSize() %>"
										tempImageFileName="<%= String.valueOf(selectedUser.getUserId()) %>"
									/>
								</c:when>
								<c:otherwise>
									<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="portrait" />" src="<%= selectedUser.getPortraitURL(themeDisplay) %>" />
								</c:otherwise>
							</c:choose>
						</aui:fieldset>
					</div>

					<div class="col-lg-4">
						<c:if test="<%= !PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) || (selectedUser != null) %>">
							<c:choose>
								<c:when test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) || !canEditUser %>">
									<aui:input disabled="<%= true %>" name="screenName" />
								</c:when>
								<c:otherwise>
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
								</c:otherwise>
							</c:choose>
						</c:if>

						<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeDuplicate.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-already-taken" />
						<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeNull.class %>" focusField="emailAddress" message="please-enter-an-email-address" />
						<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBePOP3User.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-reserved" />
						<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeReserved.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-reserved" />
						<liferay-ui:error exception="<%= UserEmailAddressException.MustNotUseCompanyMx.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-not-valid-because-its-domain-is-reserved" />
						<liferay-ui:error exception="<%= UserEmailAddressException.MustValidate.class %>" focusField="emailAddress" message="please-enter-a-valid-email-address" />

						<c:choose>
							<c:when test="<%= !canEditUser %>">
								<aui:input disabled="<%= true %>" name="emailAddress" />
							</c:when>
							<c:otherwise>

								<%
								User displayEmailAddressUser = null;

								if (selectedUser != null) {
									displayEmailAddressUser = (User)selectedUser.clone();

									displayEmailAddressUser.setEmailAddress(displayEmailAddressUser.getDisplayEmailAddress());
								}
								%>

								<aui:input bean="<%= displayEmailAddressUser %>" model="<%= User.class %>" name="emailAddress">
									<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_EMAIL_ADDRESS_REQUIRED) %>">
										<aui:validator name="required" />
									</c:if>
								</aui:input>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="col-lg-4">
						<c:choose>
							<c:when test="<%= !canEditUser %>">
								<aui:input disabled="<%= true %>" name="firstName" />
							</c:when>
							<c:otherwise>
								<aui:input bean="<%= selectedUser %>" model="<%= User.class %>" name="firstName" required="<%= true %>" />
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="<%= !canEditUser %>">
								<aui:input disabled="<%= true %>" name="lastName" />
							</c:when>
							<c:otherwise>
								<aui:input bean="<%= selectedUser %>" model="<%= User.class %>" name="lastName" required="<%= true %>" />
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</section>

		<c:if test="<%= canEditUser %>">
			<section class="panel panel-secondary">
				<div class="panel-body">
					<div class="row">
						<liferay-ui:error exception="<%= UserPasswordException.MustBeLonger.class %>">

							<%
							UserPasswordException.MustBeLonger upe = (UserPasswordException.MustBeLonger)errorException;
							%>

							<liferay-ui:message arguments="<%= String.valueOf(upe.minLength) %>" key="that-password-is-too-short" translateArguments="<%= false %>" />
						</liferay-ui:error>

						<liferay-ui:error exception="<%= UserPasswordException.MustComplyWithModelListeners.class %>" message="that-password-is-invalid-please-enter-a-different-password" />

						<liferay-ui:error exception="<%= UserPasswordException.MustComplyWithRegex.class %>">

							<%
							UserPasswordException.MustComplyWithRegex upe = (UserPasswordException.MustComplyWithRegex)errorException;
							%>

							<liferay-ui:message arguments="<%= upe.regex %>" key="that-password-does-not-comply-with-the-regular-expression" translateArguments="<%= false %>" />
						</liferay-ui:error>

						<liferay-ui:error exception="<%= UserPasswordException.MustMatch.class %>" message="the-passwords-you-entered-do-not-match" />
						<liferay-ui:error exception="<%= UserPasswordException.MustMatchCurrentPassword.class %>" message="the-password-you-entered-for-the-current-password-does-not-match-your-current-password" />
						<liferay-ui:error exception="<%= UserPasswordException.MustNotBeChanged.class %>" message="passwords-may-not-be-changed-under-the-current-password-policy" />

						<liferay-ui:error exception="<%= UserPasswordException.MustNotBeChangedYet.class %>">

							<%
							UserPasswordException.MustNotBeChangedYet upe = (UserPasswordException.MustNotBeChangedYet)errorException;

							Format dateFormat = FastDateFormatFactoryUtil.getDateTime(FastDateFormatConstants.SHORT, FastDateFormatConstants.LONG, locale, TimeZone.getTimeZone(upe.timeZoneId));
							%>

							<liferay-ui:message arguments="<%= dateFormat.format(upe.changeableDate) %>" key="you-cannot-change-your-password-yet" translateArguments="<%= false %>" />
						</liferay-ui:error>

						<liferay-ui:error exception="<%= UserPasswordException.MustNotBeEqualToCurrent.class %>" message="your-new-password-cannot-be-the-same-as-your-old-password-please-enter-a-different-password" />
						<liferay-ui:error exception="<%= UserPasswordException.MustNotBeNull.class %>" message="the-password-cannot-be-blank" />
						<liferay-ui:error exception="<%= UserPasswordException.MustNotBeRecentlyUsed.class %>" message="that-password-has-already-been-used-please-enter-a-different-password" />
						<liferay-ui:error exception="<%= UserPasswordException.MustNotBeTrivial.class %>" message="that-password-uses-common-words-please-enter-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
						<liferay-ui:error exception="<%= UserPasswordException.MustNotContainDictionaryWords.class %>" message="that-password-uses-common-dictionary-words" />

						<aui:fieldset>
							<div class="col-lg-4">
								<aui:input autocomplete="off" label="new-password" name="password1" size="30" type="password" />
							</div>

							<div class="col-lg-4">
								<aui:input autocomplete="off" label="enter-again" name="password2" size="30" type="password">
									<aui:validator name="equalTo">
										'#<portlet:namespace />password1'
									</aui:validator>
								</aui:input>
							</div>

							<c:if test="<%= user.getUserId() != selectedUser.getUserId() %>">
								<aui:input disabled="<%= passwordResetDisabled %>" label="require-password-reset" name="passwordReset" type="hidden" value="<%= passwordReset %>" />
							</c:if>
						</aui:fieldset>
					</div>
				</div>
			</section>
		</c:if>

		<c:if test="<%= canEditUser %>">
			<div class="commerce-cta is-visible">
				<c:if test="<%= Validator.isNotNull(backURL) %>">
					<aui:button cssClass="btn-lg btn-secondary" href="<%= backURL %>" value="cancel" />
				</c:if>

				<aui:button cssClass="btn-lg" primary="<%= true %>" type="submit" />
			</div>
		</c:if>
	</aui:form>
</div>