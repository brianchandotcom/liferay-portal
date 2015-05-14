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
long sacpEntryId = ParamUtil.getLong(request, "sacpEntryId");

SACPEntry sacpEntry = null;

if (sacpEntryId > 0) {
	sacpEntry = SACPEntryServiceUtil.getSACPEntry(sacpEntryId);
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title='<%= (sacpEntry != null) ? sacpEntry.getTitle(locale) : "new-service-access-control-profile" %>'
/>

<portlet:actionURL name="updateSACPEntry" var="updateSACPEntryURL">
	<portlet:param name="mvcPath" value="/edit_entry.jsp" />
</portlet:actionURL>

<aui:form action="<%= updateSACPEntryURL %>">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error exception="<%= DuplicateSACPEntryNameException.class %>" message="please-enter-a-unique-service-access-control-profile-name" />
	<liferay-ui:error exception="<%= SACPEntryNameException.class %>" message="service-access-control-profile-name-is-required" />
	<liferay-ui:error exception="<%= SACPEntryTitleException.class %>" message="service-access-control-profile-title-is-required" />

	<aui:model-context bean="<%= sacpEntry %>" model="<%= SACPEntry.class %>" />

	<aui:input name="sacpEntryId" type="hidden" />

	<aui:input name="name" required="<%= true %>">
		<aui:validator errorMessage="this-field-is-required-and-must-contain-only-following-characters" name="custom">
			function(val, fieldNode, ruleValue) {
				var allowedCharacters = '<%= HtmlUtil.escapeJS(SACPConstants.SACP_ENTRY_NAME_ALLOWED_CHARACTERS) %>';

				val = val.trim();

				var regex = new RegExp('[^' + allowedCharacters + ']');

				return !regex.test(val);
			}
		</aui:validator>
	</aui:input>

	<aui:input name="title" required="<%= true %>" />

	<aui:input helpMessage="allowed-services-help" name="allowedServices" />

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>