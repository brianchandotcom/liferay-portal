<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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