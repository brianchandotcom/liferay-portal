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

String[] mainSections = PropsValues.ORGANIZATIONS_FORM_ADD_MAIN;
String[] identificationSections = PropsValues.ORGANIZATIONS_FORM_ADD_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.ORGANIZATIONS_FORM_ADD_MISCELLANEOUS;

if (organization != null) {
	mainSections = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_FORM_UPDATE_MAIN, new Filter(organization.getType()));
	identificationSections = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_FORM_UPDATE_IDENTIFICATION, new Filter(organization.getType()));
	miscellaneousSections = PropsUtil.getArray(PropsKeys.ORGANIZATIONS_FORM_UPDATE_MISCELLANEOUS, new Filter(organization.getType()));
}

String[][] categorySections = {mainSections, identificationSections, miscellaneousSections};
%>

<aui:nav-bar>
	<liferay-util:include page="/html/portlet/users_admin/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value='<%= (organization == null) ? "add" : "view" %>' />
	</liferay-util:include>
</aui:nav-bar>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= (organization == null) %>"
	title='<%= (organization == null) ? "new-organization" : organization.getName() %>'
/>

<portlet:actionURL var="editOrganizationURL">
	<portlet:param name="struts_action" value="/users_admin/edit_organization" />
</portlet:actionURL>

<aui:form action="<%= editOrganizationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveOrganization();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" />
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

	function <portlet:namespace />saveOrganization() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (organization == null) ? Constants.ADD : Constants.UPDATE %>";

		submitForm(document.<portlet:namespace />fm);
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>

<%
if (organization != null) {
	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-organization"), currentURL);
}
%>

<%!
private static final String[] _CATEGORY_NAMES = {"organization-information", "identification", "miscellaneous"};
%>