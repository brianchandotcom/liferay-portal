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

Organization organization = organizationScreenNavigationDisplayContext.getOrganization();

String xml = LocalizationUtil.getLocalizationXmlFromPreferences(organization.getPreferences(), renderRequest, "reminderQueries");
%>

<div class="sheet-text">
	<liferay-ui:message key="specify-custom-security-questions-for-the-users-of-this-organization" />
</div>

<aui:input id="reminderQueries" label="security-questions" localized="<%= true %>" name="reminderQueries" type="textarea" value="<%= xml %>" />