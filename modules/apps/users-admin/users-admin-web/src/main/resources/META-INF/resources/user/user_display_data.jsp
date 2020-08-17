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
User selUser = (User)request.getAttribute(UsersAdminWebKeys.SELECTED_USER);
%>

<aui:model-context bean="<%= selUser %>" model="<%= User.class %>" />

<clay:row>
	<clay:col
		md="6"
	>
		<liferay-ui:success key="verificationEmailSent" message="your-email-verification-code-has-been-sent-and-the-new-email-address-will-be-applied-to-your-account-once-it-has-been-verified" />

		<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-create-user-account-because-the-maximum-number-of-users-has-been-reached" />

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

			StringBundler sb = new StringBundler(2 * fields.size() - 1);

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

		<c:if test="<%= !PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) || (selUser != null) %>">
			<c:choose>
				<c:when test='<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) || !UsersAdminUtil.hasUpdateFieldPermission(permissionChecker, user, selUser, "screenName") %>'>
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
			<c:when test='<%= !UsersAdminUtil.hasUpdateFieldPermission(permissionChecker, user, selUser, "emailAddress") %>'>
				<aui:input disabled="<%= true %>" name="emailAddress" />
			</c:when>
			<c:otherwise>

				<%
				User displayEmailAddressUser = null;

				if (selUser != null) {
					displayEmailAddressUser = (User)selUser.clone();

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

		<c:if test="<%= selUser != null %>">
			<liferay-ui:error exception="<%= UserIdException.MustNotBeNull.class %>" message="please-enter-a-user-id" />
			<liferay-ui:error exception="<%= UserIdException.MustNotBeReserved.class %>" message="the-user-id-you-requested-is-reserved" />

			<aui:input cssClass="disabled" name="userId" readonly="true" type="text" value="<%= String.valueOf(selUser.getUserId()) %>" />
		</c:if>
	</clay:col>

	<clay:col
		md="5"
	>
		<div align="middle">
			<c:if test="<%= selUser != null %>">
				<c:choose>
					<c:when test='<%= UsersAdminUtil.hasUpdateFieldPermission(permissionChecker, user, selUser, "portrait") %>'>

						<%
						UserFileUploadsConfiguration userFileUploadsConfiguration = ConfigurationProviderUtil.getSystemConfiguration(UserFileUploadsConfiguration.class);
						%>

						<label class="control-label"></label>

						<liferay-ui:logo-selector
							aspectRatio="<%= 1 %>"
							currentLogoURL="<%= selUser.getPortraitURL(themeDisplay) %>"
							defaultLogo="<%= selUser.getPortraitId() == 0 %>"
							defaultLogoURL="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), selUser.isMale(), 0, null) %>"
							logoDisplaySelector=".user-logo"
							maxFileSize="<%= userFileUploadsConfiguration.imageMaxSize() %>"
							preserveRatio="<%= true %>"
							tempImageFileName="<%= String.valueOf(selUser.getUserId()) %>"
						/>
					</c:when>
					<c:otherwise>
						<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="portrait" />" src="<%= selUser.getPortraitURL(themeDisplay) %>" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</clay:col>
</clay:row>

<portlet:renderURL var="verifyPasswordURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/user/password_verification.jsp" />
</portlet:renderURL>

<c:if test="<%= selUser != null %>">
	<aui:script use="liferay-form">
		Liferay.once('<portlet:namespace/>formReady', function () {
			var form = Liferay.Form.get('<portlet:namespace/>fm');

			form.set('onSubmit', function (event) {
				event.preventDefault();

				var emailAddressInput = document.getElementById(
					'<portlet:namespace/>emailAddress'
				);
				var screenNameInput = document.getElementById(
					'<portlet:namespace/>screenName'
				);

				if (
					emailAddressInput.value != '<%= selUser.getEmailAddress() %>' ||
					screenNameInput.value != '<%= selUser.getScreenName() %>'
				) {
					Liferay.Util.openModal({
						height: '320px',
						id: 'password-verification-dialog',
						onSelect: function () {
							submitForm(form.form);
						},
						selectEventName: '<portlet:namespace/>verifyPassword',
						size: 'md',
						title: '<%= LanguageUtil.get(request, "confirm-password") %>',
						url: '<%= verifyPasswordURL %>',
					});
				}
				else {
					submitForm(form.form);
				}
			});
		});
	</aui:script>
</c:if>