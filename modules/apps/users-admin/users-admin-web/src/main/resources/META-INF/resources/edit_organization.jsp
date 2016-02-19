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

String backURL = ParamUtil.getString(request, "backURL", redirect);

long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);

long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationSearchContainerPrimaryKeys", (organization != null) ? organization.getParentOrganizationId() : OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
String type = BeanParamUtil.getString(organization, request, "type");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

String headerTitle = null;

if (organization != null) {
	headerTitle = LanguageUtil.format(request, "edit-x", organization.getName(), false);
}
else if (Validator.isNotNull(type)) {
	headerTitle = LanguageUtil.format(request, "add-x", type);
}
else {
	headerTitle = LanguageUtil.get(request, "add-organization");
}

renderResponse.setTitle(headerTitle);
%>

<portlet:actionURL name="/users_admin/edit_organization" var="editOrganizationActionURL" />

<portlet:renderURL var="editOrganizationRenderURL">
	<portlet:param name="mvcRenderCommandName" value="/users_admin/edit_organization" />
	<portlet:param name="backURL" value="<%= backURL %>" />
</portlet:renderURL>

<aui:form action="<%= editOrganizationActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (organization == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= editOrganizationRenderURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="organizationId" type="hidden" value="<%= organizationId %>" />

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

	<liferay-util:buffer var="htmlTop">
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
		backURL="<%= backURL %>"
		formModelBean="<%= organization %>"
		htmlTop="<%= htmlTop %>"
		id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_ORGANIZATIONS %>"
		markupView="lexicon"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />createURL(href, value, onclick) {
		return '<a href="' + href + '"' + (onclick ? ' onclick="' + onclick + '" ' : '') + '>' + value + '</a>';
	}
</aui:script>