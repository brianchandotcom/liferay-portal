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
String redirect = ParamUtil.getString(request, "redirect");

long serviceAccessPolicyId = ParamUtil.getLong(request, "serviceAccessPolicyId");

ServiceAccessPolicy serviceAccessPolicy = null;

if (serviceAccessPolicyId > 0) {
	serviceAccessPolicy = ServiceAccessPolicyServiceUtil.getServiceAccessPolicy(serviceAccessPolicyId);
}

boolean defaultServiceAccessPolicy = false;

if (serviceAccessPolicy != null) {
	defaultServiceAccessPolicy = serviceAccessPolicy.isDefaultServiceAccessPolicy();
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title='<%= (serviceAccessPolicy != null) ? serviceAccessPolicy.getTitle(locale) : "new-service-access-policy" %>'
/>

<portlet:actionURL name="updateServiceAccessPolicy" var="updateServiceAccessPolicyURL">
	<portlet:param name="mvcPath" value="/edit_entry.jsp" />
</portlet:actionURL>

<aui:form action="<%= updateServiceAccessPolicyURL %>">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="serviceAccessPolicyId" type="hidden" value="<%= serviceAccessPolicyId %>" />

	<liferay-ui:error exception="<%= DuplicateServiceAccessPolicyNameException.class %>" message="please-enter-a-unique-service-access-policy-name" />
	<liferay-ui:error exception="<%= ServiceAccessPolicyNameException.class %>" message="service-access-policy-name-is-required" />
	<liferay-ui:error exception="<%= ServiceAccessPolicyTitleException.class %>" message="service-access-policy-title-is-required" />

	<aui:model-context bean="<%= serviceAccessPolicy %>" model="<%= ServiceAccessPolicy.class %>" />

	<aui:input disabled="<%= defaultServiceAccessPolicy %>" name="name" required="<%= true %>">
		<aui:validator errorMessage="this-field-is-required-and-must-contain-only-following-characters" name="custom">
			function(val, fieldNode, ruleValue) {
				var allowedCharacters = '<%= HtmlUtil.escapeJS(ServiceAccessPolicyConstants.NAME_ALLOWED_CHARACTERS) %>';

				val = val.trim();

				var regex = new RegExp('[^' + allowedCharacters + ']');

				return !regex.test(val);
			}
		</aui:validator>
	</aui:input>

	<aui:input name="title" required="<%= true %>" />

	<aui:input helpMessage="allowed-service-signatures-help" name="allowedServiceSignatures" />

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>