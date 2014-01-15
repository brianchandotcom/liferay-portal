<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");
Contact selContact = (Contact)request.getAttribute("user.selContact");

Calendar birthday = CalendarFactoryUtil.getCalendar();

birthday.set(Calendar.MONTH, Calendar.JANUARY);
birthday.set(Calendar.DATE, 1);
birthday.set(Calendar.YEAR, 1970);

if (selContact != null) {
	birthday.setTime(selContact.getBirthday());
}
%>

<liferay-ui:error-marker key="errorSection" value="details" />

<aui:model-context bean="<%= selUser %>" model="<%= User.class %>" />

<h3><liferay-ui:message key="details" /></h3>

<div class="row-fluid">
	<aui:fieldset cssClass="span6">
		<liferay-ui:success key="verificationEmailSent" message="your-email-verification-code-has-been-sent-and-the-new-email-address-will-be-applied-to-your-account-once-it-has-been-verified" />

		<liferay-ui:error exception="<%= DuplicateUserScreenNameException.class %>" focusField="screenName" message="the-screen-name-you-requested-is-already-taken" />

		<liferay-ui:error exception="<%= GroupFriendlyURLException.class %>" focusField="screenName">

			<%
			GroupFriendlyURLException gfurle = (GroupFriendlyURLException)errorException;
			%>

			<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.DUPLICATE %>">
				<liferay-ui:message key="the-screen-name-you-requested-is-associated-with-an-existing-friendly-url" />
			</c:if>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= ReservedUserScreenNameException.class %>" focusField="screenName" message="the-screen-name-you-requested-is-reserved" />

		<liferay-ui:error exception="<%= UserFieldException.class %>">

			<%
			UserFieldException ufe = (UserFieldException)errorException;

			List<String> fields = ufe.getFields();

			StringBundler sb = new StringBundler(2 * fields.size() - 1);

			for (int i = 0; i < fields.size(); i++) {
				String field = fields.get(i);

				sb.append(LanguageUtil.get(pageContext, TextFormatter.format(field, TextFormatter.K)));

				if ((i + 1) < fields.size()) {
					sb.append(StringPool.COMMA_AND_SPACE);
				}
			}
			%>

			<liferay-ui:message arguments="<%= sb.toString() %>" key="your-portal-administrator-has-disabled-the-ability-to-modify-the-following-fields" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= UserScreenNameException.class %>" focusField="screenName" message="please-enter-a-valid-screen-name" />

		<c:if test="<%= !PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) || (selUser != null) %>">
			<c:choose>
				<c:when test='<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) || !UsersAdminUtil.hasUpdateFieldPermission(selUser, "screenName") %>'>
					<aui:input disabled="<%= true %>" name="screenName" />
				</c:when>
				<c:otherwise>
					<aui:input name="screenName" />
				</c:otherwise>
			</c:choose>
		</c:if>

		<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" focusField="emailAddress" message="the-email-address-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= UserEmailAddressException.class %>" focusField="emailAddress" message="please-enter-a-valid-email-address" />

		<c:choose>
			<c:when test='<%= !UsersAdminUtil.hasUpdateFieldPermission(selUser, "emailAddress") %>'>
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

		<%@ include file="/html/portlet/users_admin/user/details_user_name.jspf" %>
	</aui:fieldset>

	<aui:fieldset cssClass="span5">
		<div>
			<c:if test="<%= selUser != null %>">
				<c:choose>
					<c:when test='<%= UsersAdminUtil.hasUpdateFieldPermission(selUser, "portrait") %>'>
						<liferay-ui:logo-selector
							currentLogoURL="<%= selUser.getPortraitURL(themeDisplay) %>"
							defaultLogo="<%= selUser.getPortraitId() == 0 %>"
							defaultLogoURL="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), selUser.isMale(), 0, null) %>"
							logoDisplaySelector=".user-logo"
							maxFileSize="<%= PrefsPropsUtil.getLong(PropsKeys.USERS_IMAGE_MAX_SIZE) / 1024 %>"
							tempImageFileName="<%= String.valueOf(selUser.getUserId()) %>"
						/>
					</c:when>
					<c:otherwise>
						<img src="<%= selUser.getPortraitURL(themeDisplay) %>" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>

		<c:if test="<%= selUser != null %>">
			<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="the-user-id-you-requested-is-already-taken" />
			<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="the-user-id-you-requested-is-reserved" />
			<liferay-ui:error exception="<%= UserIdException.class %>" message="please-enter-a-valid-user-id" />

			<aui:field-wrapper name="userId">
				<liferay-ui:input-resource url="<%= String.valueOf(selUser.getUserId()) %>" />

				<aui:input name="userId" type="hidden" value="<%= selUser.getUserId() %>" />
			</aui:field-wrapper>
		</c:if>

		<c:choose>
			<c:when test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY) %>">
				<liferay-ui:error exception="<%= ContactBirthdayException.class %>" message="please-enter-a-valid-date" />

				<aui:input bean="<%= selContact %>" cssClass="modify-link" disabled='<%= !UsersAdminUtil.hasUpdateFieldPermission(selUser, "birthday") %>' model="<%= Contact.class %>" name="birthday" value="<%= birthday %>" />
			</c:when>
			<c:otherwise>
				<aui:input name="birthdayMonth" type="hidden" value="<%= Calendar.JANUARY %>" />
				<aui:input name="birthdayDay" type="hidden" value="1" />
				<aui:input name="birthdayYear" type="hidden" value="1970" />
			</c:otherwise>
		</c:choose>

		<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE) %>">
			<aui:select bean="<%= selContact %>" disabled='<%= !UsersAdminUtil.hasUpdateFieldPermission(selUser, "gender") %>' label="gender" model="<%= Contact.class %>" name="male">
				<aui:option label="male" value="true" />
				<aui:option label="female" value="false" />
			</aui:select>
		</c:if>

		<aui:input disabled='<%= !UsersAdminUtil.hasUpdateFieldPermission(selUser, "jobTitle") %>' name="jobTitle" />
	</aui:fieldset>
</div>