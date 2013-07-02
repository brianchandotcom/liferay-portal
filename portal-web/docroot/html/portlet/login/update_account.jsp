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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

User selUser = null;

Contact selContact = null;

long userId = ParamUtil.getLong(request, "userId");

if (userId > 0) {
	selUser = UserLocalServiceUtil.getUser(userId);

	if (selUser.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) {
		throw new PrincipalException();
	}

	selContact = selUser.getContact();
}

String screenName = BeanParamUtil.getString(selUser, request, "screenName");
String emailAddress = BeanParamUtil.getString(selUser, request, "emailAddress");
String openId = BeanParamUtil.getString(selUser, request, "openId");
String firstName = BeanParamUtil.getString(selUser, request, "firstName");
String middleName = BeanParamUtil.getString(selUser, request, "middleName");
String lastName = BeanParamUtil.getString(selUser, request, "lastName");
int prefixId = BeanParamUtil.getInteger(selContact, request, "prefixId");
int suffixId = BeanParamUtil.getInteger(selContact, request, "suffixId");

Calendar birthdayCalendar = CalendarFactoryUtil.getCalendar();

int birthdayDay = ParamUtil.getInteger(request, "birthdayDay");
int birthdayMonth = ParamUtil.getInteger(request, "birthdayMonth");
int birthdayYear = ParamUtil.getInteger(request, "birthdayYear");

Date birthdayDate = PortalUtil.getDate(birthdayMonth, birthdayDay, birthdayYear);

if (birthdayDate != null) {
	birthdayCalendar.setTime(birthdayDate);
}
else if (selUser != null) {
	birthdayCalendar.setTime(selContact.getBirthday());
}

boolean male = BeanParamUtil.getBoolean(selUser, request, "male", true);
String jobTitle = BeanParamUtil.getString(selUser, request, "jobTitle");
%>

<div class="anonymous-account">
	<portlet:actionURL var="createAccountURL">
		<portlet:param name="struts_action" value="/login/create_account" />
	</portlet:actionURL>

	<aui:form action="<%= createAccountURL %>" method="post" name="fm">
		<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
		<aui:input name="<%= Constants.CMD %>" type="hidden" />

		<c:if test="<%= Validator.isNotNull(redirect) %>">
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		</c:if>

		<aui:input name="screenName" type="hidden" value="<%= screenName %>" />
		<aui:input name="emailAddress" type="hidden" value="<%= emailAddress %>" />
		<aui:input name="openId" type="hidden" value="<%= openId %>" />
		<aui:input name="firstName" type="hidden" value="<%= firstName %>" />
		<aui:input name="middleName" type="hidden" value="<%= middleName %>" />
		<aui:input name="lastName" type="hidden" value="<%= lastName %>" />
		<aui:input name="prefixId" type="hidden" value="<%= String.valueOf(prefixId) %>" />
		<aui:input name="suffixId" type="hidden" value="<%= String.valueOf(suffixId) %>" />
		<aui:input name="male" type="hidden" value="<%= String.valueOf(male) %>" />
		<aui:input name="birthdayDay" type="hidden" value="<%= String.valueOf(birthdayCalendar.get(Calendar.DAY_OF_MONTH)) %>" />
		<aui:input name="birthdayMonth" type="hidden" value="<%= String.valueOf(birthdayCalendar.get(Calendar.MONTH)) %>" />
		<aui:input name="birthdayYear" type="hidden" value="<%= String.valueOf(birthdayCalendar.get(Calendar.YEAR)) %>" />
		<aui:input name="jobTitle" type="hidden" value="<%= jobTitle %>" />
	</aui:form>

	<div class="alert alert-block">
		<liferay-ui:message arguments="<%= emailAddress %>" key="an-account-with-x-as-the-email-address-already-exists-in-the-portal.-do-you-want-to-associate-this-activity-with-that-account" />
	</div>

	<aui:button name="updateUser" onClick='<%= renderResponse.getNamespace() + "updateUser();" %>' value="associate-account" />

	<aui:button name="resetUser" onClick='<%= renderResponse.getNamespace() + "resetUser();" %>' value="create-new-account" />
</div>

<aui:script>
	function <portlet:namespace />resetUser() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.RESET %>";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateUser() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>