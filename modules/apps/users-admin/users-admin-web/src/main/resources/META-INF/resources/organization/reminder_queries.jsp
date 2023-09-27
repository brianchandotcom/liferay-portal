<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

Organization organization = organizationScreenNavigationDisplayContext.getOrganization();
%>

<div class="sheet-text">
	<liferay-ui:message key="specify-custom-security-questions-for-the-users-of-this-organization" />
</div>

<aui:input id="reminderQueries" label="security-questions" localized="<%= true %>" name="reminderQueries" type="textarea" value='<%= LocalizationUtil.getLocalizationXmlFromPreferences(organization.getPreferences(), renderRequest, "reminderQueries") %>' />