<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
int birthdayDay = ParamUtil.getInteger(request, "birthdayDay");
int birthdayMonth = ParamUtil.getInteger(request, "birthdayMonth");
int birthdayYear = ParamUtil.getInteger(request, "birthdayYear");
String emailAddress = ParamUtil.getString(request, "emailAddress");
long facebookId = ParamUtil.getLong(request, "facebookId");
String firstName = ParamUtil.getString(request, "firstName");
String jobTitle = ParamUtil.getString(request, "jobTitle");
String lastName = ParamUtil.getString(request, "lastName");
boolean male = ParamUtil.getBoolean(request, "male", true);
String middleName = ParamUtil.getString(request, "middleName");
String openId = ParamUtil.getString(request, "openId");
int prefixId = ParamUtil.getInteger(request, "prefixId");
String screenName = ParamUtil.getString(request, "screenName");
int suffixId = ParamUtil.getInteger(request, "suffixId");
%>

<div class="anonymous-account">
	<portlet:actionURL var="createAccountURL">
		<portlet:param name="struts_action" value="/login/create_account" />
	</portlet:actionURL>

	<aui:form action="<%= createAccountURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="birthdayDay" type="hidden" value="<%= String.valueOf(birthdayDay) %>" />
		<aui:input name="birthdayMonth" type="hidden" value="<%= String.valueOf(birthdayMonth) %>" />
		<aui:input name="birthdayYear" type="hidden" value="<%= String.valueOf(birthdayYear) %>" />
		<aui:input name="emailAddress" type="hidden" value="<%= emailAddress %>" />
		<aui:input name="facebookId" type="hidden" value="<%= String.valueOf(facebookId) %>" />
		<aui:input name="firstName" type="hidden" value="<%= firstName %>" />
		<aui:input name="jobTitle" type="hidden" value="<%= jobTitle %>" />
		<aui:input name="lastName" type="hidden" value="<%= lastName %>" />
		<aui:input name="male" type="hidden" value="<%= String.valueOf(male) %>" />
		<aui:input name="middleName" type="hidden" value="<%= middleName %>" />
		<aui:input name="openId" type="hidden" value="<%= openId %>" />
		<aui:input name="prefixId" type="hidden" value="<%= String.valueOf(prefixId) %>" />
		<aui:input name="screenName" type="hidden" value="<%= screenName %>" />
		<aui:input name="suffixId" type="hidden" value="<%= String.valueOf(suffixId) %>" />
	</aui:form>

	<div class="portlet-msg-alert">
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