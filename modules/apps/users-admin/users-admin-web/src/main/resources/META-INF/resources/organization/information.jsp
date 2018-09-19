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
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (organization == null) ? Constants.ADD : Constants.UPDATE %>" />

<%
request.setAttribute("addresses.className", Organization.class.getName());
request.setAttribute("addresses.classPK", organizationId);
request.setAttribute("emailAddresses.className", Organization.class.getName());
request.setAttribute("emailAddresses.classPK", organizationId);
request.setAttribute("phones.className", Organization.class.getName());
request.setAttribute("phones.classPK", organizationId);
request.setAttribute("websites.className", Organization.class.getName());
request.setAttribute("websites.classPK", organizationId);
%>

<liferay-util:buffer
	var="htmlTop"
>
	<c:if test="<%= organization != null %>">

		<%
		long logoId = organization.getLogoId();
		%>

		<div class="organization-info">
			<div class="float-container">
				<img alt="<%= HtmlUtil.escapeAttribute(organization.getName()) %>" class="organization-logo" src="<%= themeDisplay.getPathImage() %>/organization_logo?img_id=<%= logoId %>&t=<%= WebServerServletTokenUtil.getToken(logoId) %>" />

				<span class="organization-name"><%= HtmlUtil.escape(organization.getName()) %></span>
			</div>
		</div>
	</c:if>
</liferay-util:buffer>

<liferay-ui:form-navigator
	backURL="<%= organizationScreenNavigationDisplayContext.getBackURL() %>"
	formModelBean="<%= organization %>"
	htmlTop="<%= htmlTop %>"
	id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_ORGANIZATIONS %>"
	markupView="lexicon"
	showButtons="<%= false %>"
/>

<aui:script>
	function <portlet:namespace />createURL(href, value, onclick) {
		return '<a href="' + href + '"' + (onclick ? ' onclick="' + onclick + '" ' : '') + '>' + value + '</a>';
	}
</aui:script>