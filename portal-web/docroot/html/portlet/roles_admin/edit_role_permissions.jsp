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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

String tabs1 = "roles";
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Role role = (Role)request.getAttribute(WebKeys.ROLE);

String portletResource = ParamUtil.getString(request, "portletResource");

String portletResourceLabel = null;

if (Validator.isNotNull(portletResource)) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	if (portlet.getPortletId().equals(PortletKeys.PORTAL)) {
		portletResourceLabel = LanguageUtil.get(pageContext, "general");
	}
	else {
		portletResourceLabel = PortalUtil.getPortletLongTitle(portlet, application, locale);
	}
}

List modelResources = null;

if (Validator.isNotNull(portletResource)) {
	modelResources = ResourceActionsUtil.getPortletModelResources(portletResource);
}

boolean showModelResources = ParamUtil.getBoolean(request, "showModelResources", true);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/roles_admin/edit_role_permissions");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("backURL", backURL);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));

PortletURL editPermissionsURL = renderResponse.createRenderURL();

editPermissionsURL.setParameter("struts_action", "/roles_admin/edit_role_permissions");
editPermissionsURL.setParameter(Constants.CMD, Constants.EDIT);
editPermissionsURL.setParameter("tabs1", "roles");
editPermissionsURL.setParameter("redirect", backURL);
editPermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));
%>

<c:choose>
	<c:when test="<%= !portletName.equals(PortletKeys.ADMIN_SERVER) %>">
		<liferay-util:include page="/html/portlet/roles_admin/toolbar.jsp">
			<liferay-util:param name="toolbarItem" value='<%= (role == null) ? "add" : "view-all" %>' />
		</liferay-util:include>

		<liferay-ui:header
			backURL="<%= backURL %>"
			localizeTitle="<%= false %>"
			title="<%= role.getTitle(locale) %>"
		/>

		<liferay-util:include page="/html/portlet/roles_admin/edit_role_tabs.jsp">
			<liferay-util:param name="tabs1" value="define-permissions" />
			<liferay-util:param name="backURL" value="<%= backURL %>" />
		</liferay-util:include>
	</c:when>
	<c:otherwise>

		<%
		request.setAttribute("edit_role_permissions.jsp-role", role);

		request.setAttribute("edit_role_permissions.jsp-portletResource", portletResource);
		%>

	</c:otherwise>
</c:choose>

<liferay-ui:success key="permissionDeleted" message="the-permission-was-deleted" />
<liferay-ui:success key="permissionsUpdated" message="the-role-permissions-were-updated" />

<c:if test="<%= !portletName.equals(PortletKeys.ADMIN_SERVER) %>">
	<%@ include file="/html/portlet/roles_admin/edit_role_permissions_navigation.jspf" %>
</c:if>

<c:choose>
	<c:when test="<%= cmd.equals(Constants.VIEW) %>">
		<liferay-util:include page="/html/portlet/roles_admin/edit_role_permissions_summary.jsp" />

		<c:if test="<%= portletName.equals(PortletKeys.ADMIN_SERVER) %>">
			<br />

			<aui:button href="<%= redirect %>" type="cancel" />
		</c:if>
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="editRolePermissionsURL">
			<portlet:param name="struts_action" value="/roles_admin/edit_role_permissions" />
		</portlet:actionURL>

		<aui:form action="<%= editRolePermissionsURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
			<aui:input name="redirect" type="hidden" />
			<aui:input name="roleId" type="hidden" value="<%= role.getRoleId() %>" />
			<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
			<aui:input name="modelResources" type="hidden" value='<%= (modelResources == null) ? "" : StringUtil.merge(modelResources) %>' />
			<aui:input name="showModelResources" type="hidden" value="<%= String.valueOf(showModelResources) %>" />
			<aui:input name="selectedTargets" type="hidden" />

			<c:choose>
				<c:when test="<%= !showModelResources %>">
					<h3><%= portletResourceLabel %></h3>

					<%
					request.setAttribute("edit_role_permissions.jsp-curPortletResource", portletResource);
					%>

					<liferay-util:include page="/html/portlet/roles_admin/edit_role_permissions_resource.jsp" />
				</c:when>
				<c:when test="<%= (modelResources != null) && !modelResources.isEmpty() %>">

					<%
					modelResources = ListUtil.sort(modelResources, new ModelResourceComparator(locale));

					for (int i = 0; i < modelResources.size(); i++) {
						String curModelResource = (String)modelResources.get(i);

						String curModelResourceName = ResourceActionsUtil.getModelResource(pageContext, curModelResource);
						%>

						<h3><%= curModelResourceName %></h3>

						<%
						request.removeAttribute("edit_role_permissions.jsp-curPortletResource");

						request.setAttribute("edit_role_permissions.jsp-curModelResource", curModelResource);
						request.setAttribute("edit_role_permissions.jsp-curModelResourceName", curModelResourceName);
						%>

						<liferay-util:include page="/html/portlet/roles_admin/edit_role_permissions_resource.jsp" />

					<%
					}
					%>

				</c:when>
			</c:choose>

			<aui:button-row>
				<aui:button onClick='<%= renderResponse.getNamespace() + "updateActions();" %>' value="save" />

				<aui:button href="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</aui:form>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />addPermissions(field) {
		var permissionsURL = field.value;

		if (permissionsURL == '') {

			<%
			PortletURL viewPermissionsURL = renderResponse.createRenderURL();

			viewPermissionsURL.setParameter("struts_action", "/roles_admin/edit_role_permissions");
			viewPermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
			viewPermissionsURL.setParameter("tabs1", "roles");
			viewPermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));
			%>

			permissionsURL = '<%= viewPermissionsURL %>';
		}

		location.href = permissionsURL;
	}

	function <portlet:namespace />removeGroup(pos, target) {
		var selectedGroupIds = document.<portlet:namespace />fm['<portlet:namespace />groupIds' + target].value.split(",");
		var selectedGroupNames = document.<portlet:namespace />fm['<portlet:namespace />groupNames' + target].value.split("@@");

		selectedGroupIds.splice(pos, 1);
		selectedGroupNames.splice(pos, 1);

		<portlet:namespace />updateGroups(selectedGroupIds, selectedGroupNames, target);
	}

	Liferay.on(
		'<portlet:namespace />selectGroup',
		function(event) {
			var selectedGroupIds = [];

			var selectedGroupIdsField = document.<portlet:namespace />fm['<portlet:namespace />groupIds' + event.grouptarget].value;

			if (selectedGroupIdsField) {
				selectedGroupIds = selectedGroupIdsField.split(',');
			}

			var selectedGroupNames = [];
			var selectedGroupNamesField = document.<portlet:namespace />fm['<portlet:namespace />groupNames' + event.grouptarget].value;

			if (selectedGroupNamesField) {
				selectedGroupNames = selectedGroupNamesField.split('@@');
			}

			if (AUI().Array.indexOf(selectedGroupIds, event.groupid) == -1) {
				selectedGroupIds.push(event.groupid);
				selectedGroupNames.push(event.groupname);
			}

			<portlet:namespace />updateGroups(selectedGroupIds, selectedGroupNames, event.grouptarget);
		}
	);

	function <portlet:namespace />selectOrganization(organizationId, groupId, name, type, target) {
		<portlet:namespace />selectGroup(groupId, name, target);
	}

	function <portlet:namespace />updateGroups(selectedGroupIds, selectedGroupNames, target) {
		document.<portlet:namespace />fm['<portlet:namespace />groupIds' + target].value = selectedGroupIds.join(',');
		document.<portlet:namespace />fm['<portlet:namespace />groupNames' + target].value = selectedGroupNames.join('@@');

		var nameEl = document.getElementById("<portlet:namespace />groupHTML" + target);

		var groupsHTML = '';

		for (var i = 0; i < selectedGroupIds.length; i++) {
			var id = selectedGroupIds[i];
			var name = selectedGroupNames[i];

			groupsHTML += '<span class="lfr-token"><span class="lfr-token-text">' + name + '</span><a class="icon icon-remove lfr-token-close" href="javascript:<portlet:namespace />removeGroup(' + i + ', \'' + target + '\' );"></a></span>';
		}

		if (groupsHTML == '') {
			groupsHTML = '<%= UnicodeLanguageUtil.get(pageContext, "portal") %>';
		}

		nameEl.innerHTML = groupsHTML;
	}

	Liferay.provide(
		window,
		'<portlet:namespace />updateActions',
		function() {
			var selectedTargets = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "actions";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<%= portletURL.toString() %>";
			document.<portlet:namespace />fm.<portlet:namespace />selectedTargets.value = selectedTargets;
			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>

<%
PortletURL definePermissionsURL = renderResponse.createRenderURL();

definePermissionsURL.setParameter("struts_action", "/roles_admin/edit_role_permissions");
definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
definePermissionsURL.setParameter("redirect", backURL);
definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "define-permissions"), definePermissionsURL.toString());

if (!cmd.equals(Constants.VIEW) && Validator.isNotNull(portletResource)) {
	PortletURL resourceURL = renderResponse.createRenderURL();

	resourceURL.setParameter("struts_action", "/roles_admin/edit_role");
	resourceURL.setParameter(Constants.CMD, Constants.EDIT);
	resourceURL.setParameter("tabs1", tabs1);
	resourceURL.setParameter("portletResource", portletResource);

	PortalUtil.addPortletBreadcrumbEntry(request, portletResourceLabel, resourceURL.toString());
}
%>