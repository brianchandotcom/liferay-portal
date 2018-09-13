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

<portlet:actionURL name="<%= organizationScreenNavigationDisplayContext.getActionCommandName() %>" var="editOrganizationActionURL" />

<portlet:renderURL var="editOrganizationRenderURL">
	<portlet:param name="mvcRenderCommandName" value="/users_admin/edit_organization" />
	<portlet:param name="backURL" value="<%= organizationScreenNavigationDisplayContext.getBackURL() %>" />
	<portlet:param name="organizationId" value="<%= String.valueOf(organizationScreenNavigationDisplayContext.getOrganizationId()) %>" />
	<portlet:param name="screenNavigationCategoryKey" value="<%= organizationScreenNavigationDisplayContext.getScreenNavigationCategoryKey() %>" />
	<portlet:param name="screenNavigationEntryKey" value="<%= organizationScreenNavigationDisplayContext.getScreenNavigationEntryKey() %>" />
</portlet:renderURL>

<aui:form action="<%= editOrganizationActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= editOrganizationRenderURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= organizationScreenNavigationDisplayContext.getBackURL() %>" />
	<aui:input name="organizationId" type="hidden" value="<%= organizationScreenNavigationDisplayContext.getOrganizationId() %>" />

	<aui:fieldset-group markupView="lexicon">
		<div class="sheet">
			<h2 class="sheet-title"><%= organizationScreenNavigationDisplayContext.getFormLabel() %></h2>

			<div class="sheet-section">
				<liferay-util:include page="<%= organizationScreenNavigationDisplayContext.getJspPath() %>" servletContext="<%= application %>" />
			</div>

			<div class="sheet-footer">
				<aui:button primary="<%= true %>" type="submit" />

				<aui:button href="<%= organizationScreenNavigationDisplayContext.getBackURL() %>" type="cancel" />
			</div>
		</div>
	</aui:fieldset-group>
</aui:form>