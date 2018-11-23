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

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Website website = (Website)row.getObject();

long websiteId = website.getWebsiteId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-ui:icon
		cssClass="modify-website-link"
		data="<%=
			new HashMap<String, Object>() {
				{
					put("title", LanguageUtil.get(request, "edit-website"));
					put("primary-key", String.valueOf(websiteId));
				}
			}
		%>"
		message="edit"
		url="javascript:;"
	/>

	<portlet:actionURL name="/users_admin/update_organization_contact_information" var="makePrimaryURL">
		<portlet:param name="<%= Constants.CMD %>" value="makePrimary" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="listType" value="<%= ListTypeConstants.WEBSITE %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
		<portlet:param name="primaryKey" value="<%= String.valueOf(websiteId) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="make-primary"
		url="<%= makePrimaryURL %>"
	/>

	<portlet:actionURL name="/users_admin/update_organization_contact_information" var="removeWebsiteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="listType" value="<%= ListTypeConstants.WEBSITE %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
		<portlet:param name="primaryKey" value="<%= String.valueOf(websiteId) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="remove"
		url="<%= removeWebsiteURL %>"
	/>
</liferay-ui:icon-menu>