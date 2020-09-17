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
%>

<portlet:actionURL name="<%= organizationScreenNavigationDisplayContext.getActionName() %>" var="editOrganizationActionURL" />

<aui:form action="<%= editOrganizationActionURL %>" cssClass="portlet-users-admin-edit-organization" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= organizationScreenNavigationDisplayContext.getRedirect() %>" />
	<aui:input name="backURL" type="hidden" value="<%= organizationScreenNavigationDisplayContext.getBackURL() %>" />
	<aui:input name="organizationId" type="hidden" value="<%= organizationScreenNavigationDisplayContext.getOrganizationId() %>" />

	<clay:sheet>
		<c:if test="<%= organizationScreenNavigationDisplayContext.isShowTitle() %>">
			<clay:sheet-header>
				<h2 class="sheet-title"><%= organizationScreenNavigationDisplayContext.getFormLabel() %></h2>
			</clay:sheet-header>
		</c:if>

		<clay:sheet-section>
			<liferay-util:include page="<%= organizationScreenNavigationDisplayContext.getJspPath() %>" servletContext="<%= application %>" />
		</clay:sheet-section>

		<c:if test="<%= organizationScreenNavigationDisplayContext.isShowControls() %>">
			<clay:sheet-footer>
				<aui:button primary="<%= true %>" type="submit" />

				<aui:button href="<%= organizationScreenNavigationDisplayContext.getBackURL() %>" type="cancel" />
			</clay:sheet-footer>
		</c:if>
	</clay:sheet>
</aui:form>