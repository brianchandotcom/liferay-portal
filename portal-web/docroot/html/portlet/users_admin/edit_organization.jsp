<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

long organizationId = BeanParamUtil.getLong(organization, request, "organizationId");

long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationSearchContainerPrimaryKeys", (organization != null) ? organization.getParentOrganizationId() : OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
String type = BeanParamUtil.getString(organization, request, "type");

String[] mainSections = PropsValues.ORGANIZATIONS_FORM_ADD_MAIN;
String[] identificationSections = PropsValues.ORGANIZATIONS_FORM_ADD_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.ORGANIZATIONS_FORM_ADD_MISCELLANEOUS;

if (organization != null) {
	mainSections = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_FORM_UPDATE_MAIN, new Filter(organization.getType()));
	identificationSections = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_FORM_UPDATE_IDENTIFICATION, new Filter(organization.getType()));
	miscellaneousSections = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_FORM_UPDATE_MISCELLANEOUS, new Filter(organization.getType()));
}

String[][] categorySections = {mainSections, identificationSections, miscellaneousSections};

if (organization != null) {
	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
else if (parentOrganizationId > 0) {
	Organization parentOrganization = OrganizationServiceUtil.getOrganization(parentOrganizationId);

	UsersAdminUtil.addPortletBreadcrumbEntries(parentOrganization, request, renderResponse);
}
%>

<aui:nav-bar>
	<liferay-util:include page="/html/portlet/users_admin/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value='<%= (organization == null) ? "add" : "view" %>' />
	</liferay-util:include>
</aui:nav-bar>

<div id="breadcrumb">
	<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showCurrentPortlet="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
</div>

<%
String headerTitle = null;

if (organization != null) {
	headerTitle = LanguageUtil.format(pageContext, "edit-x", organization.getName(), false);
}
else if (Validator.isNotNull(type)) {
	headerTitle = LanguageUtil.format(pageContext, "add-x", type, false);
}
else {
	headerTitle = LanguageUtil.get(pageContext, "add-organization");
}
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= (organization == null) %>"
	title="<%= headerTitle %>"
/>

<portlet:actionURL var="editOrganizationActionURL">
	<portlet:param name="struts_action" value="/users_admin/edit_organization" />
</portlet:actionURL>

<portlet:renderURL var="editOrganizationRenderURL">
	<portlet:param name="struts_action" value="/users_admin/edit_organization" />
	<portlet:param name="backURL" value="<%= backURL %>" />
</portlet:renderURL>

<aui:form action="<%= editOrganizationActionURL %>" method="post" name="fm">
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
					<img alt="<%= HtmlUtil.escape(organization.getName()) %>" class="organization-logo" src="<%= themeDisplay.getPathImage() %>/organization_logo?img_id=<%= logoId %>&t=<%= WebServerServletTokenUtil.getToken(logoId) %>" />

					<span class="organization-name"><%= HtmlUtil.escape(organization.getName()) %></span>
				</div>
			</div>
		</c:if>
	</liferay-util:buffer>

	<liferay-ui:form-navigator
		backURL="<%= backURL %>"
		categoryNames="<%= _CATEGORY_NAMES %>"
		categorySections="<%= categorySections %>"
		htmlTop="<%= htmlTop %>"
		jspPath="/html/portlet/users_admin/organization/"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />createURL(href, value, onclick) {
		return '<a href="' + href + '"' + (onclick ? ' onclick="' + onclick + '" ' : '') + '>' + value + '</a>';
	};
</aui:script>

<%!
private static final String[] _CATEGORY_NAMES = {"organization-information", "identification", "miscellaneous"};
%>