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
String backURL = ParamUtil.getString(request, "backURL", themeDisplay.getURLHome());

String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL loginURL = PortletURLBuilder.createRenderURL(
		liferayPortletResponse, LoginPortletKeys.LOGIN
	).setMVCRenderCommandName(
		"/login/login"
	).setRedirect(
		themeDisplay.getURLHome()
	).setPortletMode(
		PortletMode.VIEW
	).setWindowState(
		WindowState.MAXIMIZED
	).buildPortletURL();

	redirect = loginURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL name="/account_admin/create_account_user" var="createAccountUsersURL" />

<liferay-frontend:edit-form
	action="<%= createAccountUsersURL %>"
	cssClass="pt-0"
>
	<liferay-frontend:edit-form-body>
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

		<h2 class="sheet-title">
			<liferay-ui:message key="create-account" />
		</h2>

		<clay:sheet-section>
			<div class="form-group">
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

						<aui:input cssClass="form-control-plaintext" label="email-address" name="emailAddress" readonly="<%= true %>" required="<%= true %>" type="text" value='<%= ParamUtil.getString(request, "emailAddress") %>' />
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
					</clay:col>
				</clay:row>
			</div>

			<div class="form-group">
				<h3 class="sheet-subtitle">
					<liferay-ui:message key="personal-information" />
				</h3>

				<clay:row>
					<clay:col
						md="6"
					>
						<liferay-ui:user-name-fields />
					</clay:col>

					<clay:col
						md="6"
					>
						<aui:input label="job-title" maxlength='<%= ModelHintsUtil.getMaxLength(Contact.class.getName(), "jobTitle") %>' name="jobTitle" type="text" />
					</clay:col>
				</clay:row>
			</div>

			<div class="form-group">
				<h3 class="sheet-subtitle">
					<liferay-ui:message key="password" />
				</h3>

				<clay:row>
					<clay:col
						md="6"
					>
						<c:if test="<%= PropsValues.LOGIN_CREATE_ACCOUNT_ALLOW_CUSTOM_PASSWORD %>">
							<aui:input label="password" name="password1" size="30" type="password" value="">
								<aui:validator name="required" />
							</aui:input>

							<aui:input label="enter-again" name="password2" size="30" type="password" value="">
								<aui:validator name="equalTo">
									'#<portlet:namespace />password1'
								</aui:validator>

								<aui:validator name="required" />
							</aui:input>
						</c:if>
					</clay:col>
				</clay:row>
			</div>
		</clay:sheet-section>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= backURL %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>