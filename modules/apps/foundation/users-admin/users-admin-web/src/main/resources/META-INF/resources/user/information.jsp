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

UserDisplayContext userDisplayContext = new UserDisplayContext(request, initDisplayContext);

List<Group> allGroups = userDisplayContext.getAllGroups();
List<Group> groups = userDisplayContext.getGroups();
List<UserGroupGroupRole> inheritedSiteRoles = userDisplayContext.getInheritedSiteRoles();
List<Group> inheritedSites = userDisplayContext.getInheritedSites();
List<UserGroupRole> organizationRoles = userDisplayContext.getOrganizationRoles();
List<Organization> organizations = userDisplayContext.getOrganizations();
PasswordPolicy passwordPolicy = userDisplayContext.getPasswordPolicy();
List<Group> roleGroups = userDisplayContext.getRoleGroups();
List<Role> roles = userDisplayContext.getRoles();
Contact selContact = userDisplayContext.getContact();
User selUser = userDisplayContext.getSelectedUser();
List<UserGroupRole> siteRoles = userDisplayContext.getSiteRoles();
List<UserGroup> userGroups = userDisplayContext.getUserGroups();
%>

<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-create-user-account-because-the-maximum-number-of-users-has-been-reached" />

<c:if test="<%= !portletName.equals(myAccountPortletId) %>">

	<%
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);

	renderResponse.setTitle((selUser == null) ? LanguageUtil.get(request, "add-user") : LanguageUtil.format(request, "edit-user-x", selUser.getFullName(), false));
	%>

</c:if>

<portlet:actionURL name="/users_admin/edit_user" var="editUserActionURL" />

<portlet:renderURL var="editUserRenderURL">
	<portlet:param name="mvcRenderCommandName" value="/users_admin/edit_user" />
	<portlet:param name="backURL" value="<%= backURL %>" />
</portlet:renderURL>

<aui:form action="<%= editUserActionURL %>" cssClass="container-fluid-1280 portlet-users-admin-edit-user" data-senna-off="true" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (selUser == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= editUserRenderURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="p_u_i_d" type="hidden" value="<%= (selUser != null) ? selUser.getUserId() : 0 %>" />

	<%
	request.setAttribute("user.allGroups", allGroups);
	request.setAttribute("user.groups", groups);
	request.setAttribute("user.inheritedSiteRoles", inheritedSiteRoles);
	request.setAttribute("user.inheritedSites", inheritedSites);
	request.setAttribute("user.organizationRoles", organizationRoles);
	request.setAttribute("user.organizations", organizations);
	request.setAttribute("user.passwordPolicy", passwordPolicy);
	request.setAttribute("user.roleGroups", roleGroups);
	request.setAttribute("user.roles", roles);
	request.setAttribute("user.selContact", selContact);
	request.setAttribute("user.selUser", selUser);
	request.setAttribute("user.siteRoles", siteRoles);
	request.setAttribute("user.userGroups", userGroups);

	request.setAttribute("emailAddresses.className", Contact.class.getName());
	request.setAttribute("phones.className", Contact.class.getName());
	request.setAttribute("websites.className", Contact.class.getName());

	if (selContact != null) {
		request.setAttribute("emailAddresses.classPK", selContact.getContactId());
		request.setAttribute("phones.classPK", selContact.getContactId());
		request.setAttribute("websites.classPK", selContact.getContactId());
	}
	else {
		request.setAttribute("emailAddresses.classPK", 0L);
		request.setAttribute("phones.classPK", 0L);
		request.setAttribute("websites.classPK", 0L);
	}
	%>

	<liferay-ui:form-navigator
		backURL="<%= backURL %>"
		formModelBean="<%= selUser %>"
		id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_USERS %>"
		markupView="lexicon"
	/>
</aui:form>

<%
if (selUser != null) {
	PortalUtil.setPageSubtitle(selUser.getFullName(), request);
}
%>

<aui:script>
	function <portlet:namespace />createURL(href, value, onclick) {
		return '<a href="' + href + '"' + (onclick ? ' onclick="' + onclick + '" ' : '') + '>' + value + '</a>';
	}

	function <portlet:namespace />saveUser(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>