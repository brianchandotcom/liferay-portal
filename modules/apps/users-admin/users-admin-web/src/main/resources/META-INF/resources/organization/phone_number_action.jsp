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
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

long organizationId = organizationScreenNavigationDisplayContext.getOrganizationId();

Organization organization = organizationScreenNavigationDisplayContext.getOrganization();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Phone phone = (Phone)row.getObject();

long phoneId = phone.getPhoneId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>

	<%
	Map<String, Object> data = new HashMap<String, Object>();

	data.put("phone-id", String.valueOf(phoneId));
	%>

	<liferay-ui:icon
		data="<%= data %>"
		linkCssClass="edit-phone"
		message="edit"
		url="javascript:;"
	/>

	<portlet:actionURL name="/users_admin/update_organization_contact_information" var="makePrimaryURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= UsersAdminWebKeys.CMD_MAKE_PRIMARY %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="entryId" value="<%= String.valueOf(phoneId) %>" />
		<portlet:param name="listType" value="<%= ListTypeConstants.PHONE %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="make-primary"
		url="<%= makePrimaryURL %>"
	/>

	<portlet:actionURL name="/users_admin/update_organization_contact_information" var="removePhoneURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="entryId" value="<%= String.valueOf(phoneId) %>" />
		<portlet:param name="listType" value="<%= ListTypeConstants.PHONE %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="remove"
		url="<%= removePhoneURL %>"
	/>
</liferay-ui:icon-menu>