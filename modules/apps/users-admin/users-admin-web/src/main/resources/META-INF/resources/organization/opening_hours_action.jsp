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

OrgLabor orgLabor = (OrgLabor)row.getObject();

long orgLaborId = orgLabor.getOrgLaborId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-ui:icon
		cssClass="modify-opening-hours-link"
		data="<%=
			new HashMap<String, Object>() {
				{
					put("title", LanguageUtil.get(request, "edit-opening-hours"));
					put("entry-id", String.valueOf(orgLaborId));
				}
			}
		%>"
		message="edit"
		url="javascript:;"
	/>

	<portlet:actionURL name="/users_admin/update_organization_contact_information" var="removeOpeningHoursUrl">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="entryId" value="<%= String.valueOf(orgLaborId) %>" />
		<portlet:param name="listType" value="<%= ListTypeConstants.ORGANIZATION_SERVICE %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="remove"
		url="<%= removeOpeningHoursUrl %>"
	/>
</liferay-ui:icon-menu>