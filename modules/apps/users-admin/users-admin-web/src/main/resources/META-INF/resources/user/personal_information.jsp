<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User selUser = (User)request.getAttribute(UsersAdminWebKeys.SELECTED_USER);

PasswordPolicy passwordPolicy = userDisplayContext.getPasswordPolicy();
Contact selContact = userDisplayContext.getContact();

Calendar birthday = CalendarFactoryUtil.getCalendar();

birthday.set(Calendar.MONTH, Calendar.JANUARY);
birthday.set(Calendar.DATE, 1);
birthday.set(Calendar.YEAR, 1970);

if (selContact != null) {
	birthday.setTime(selContact.getBirthday());
}

String organizationIdsString = ParamUtil.getString(request, "organizationsSearchContainerPrimaryKeys");

if (Validator.isNull(organizationIdsString)) {
	organizationIdsString = ParamUtil.getString(request, "addOrganizationIds");
}
%>

<aui:model-context bean="<%= selUser %>" model="<%= User.class %>" />

<clay:row>
	<clay:col
		md="6"
	>
		<liferay-user:user-name-fields
			contact="<%= selContact %>"
			user="<%= selUser %>"
		/>
	</clay:col>

	<clay:col
		md="5"
	>
		<aui:input disabled='<%= !UsersAdminUtil.hasUpdateFieldPermission(permissionChecker, user, selUser, "jobTitle") %>' name="jobTitle" />

		<c:choose>
			<c:when test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_CONTACT_BIRTHDAY) %>">
				<liferay-ui:error exception="<%= ContactBirthdayException.class %>" message="please-enter-a-valid-date" />

				<aui:input bean="<%= selContact %>" cssClass="modify-link" disabled='<%= !UsersAdminUtil.hasUpdateFieldPermission(permissionChecker, user, selUser, "birthday") %>' model="<%= Contact.class %>" name="birthday" value="<%= birthday %>" />
			</c:when>
			<c:otherwise>
				<aui:input name="birthdayMonth" type="hidden" value="<%= Calendar.JANUARY %>" />
				<aui:input name="birthdayDay" type="hidden" value="1" />
				<aui:input name="birthdayYear" type="hidden" value="1970" />
			</c:otherwise>
		</c:choose>

		<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_CONTACT_MALE) %>">
			<aui:select bean="<%= selContact %>" disabled='<%= !UsersAdminUtil.hasUpdateFieldPermission(permissionChecker, user, selUser, "gender") %>' label="gender" model="<%= Contact.class %>" name="male">
				<aui:option label="male" value="<%= true %>" />
				<aui:option label="female" value="<%= false %>" />
			</aui:select>
		</c:if>

		<c:if test="<%= (selUser == null) && Validator.isNotNull(organizationIdsString) %>">
			<aui:input name="addOrganizationIds" type="hidden" value="<%= organizationIdsString %>" />
		</c:if>

		<%
		boolean lockedOut = false;

		if ((selUser != null) && (passwordPolicy != null)) {
			try {
				UserLocalServiceUtil.checkLockout(selUser);
			}
			catch (UserLockoutException.PasswordPolicyLockout ule) {
				lockedOut = true;
			}
		}
		%>

		<c:if test="<%= lockedOut %>">
			<aui:button-row>
				<clay:alert
					displayType="warning"
					message="this-user-account-has-been-locked-due-to-excessive-failed-login-attempts"
				/>

				<%
				String taglibOnClick = liferayPortletResponse.getNamespace() + "saveUser('unlock');";
				%>

				<aui:button onClick="<%= taglibOnClick %>" value="unlock" />
			</aui:button-row>
		</c:if>
	</clay:col>
</clay:row>

<aui:script>
	function <portlet:namespace />saveUser(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>