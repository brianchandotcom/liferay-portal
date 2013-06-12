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

<%@ include file="/html/portlet/plugins_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String moduleId = ParamUtil.getString(request, "moduleId");
String pluginId = ParamUtil.getString(request, "pluginId");
String pluginType = ParamUtil.getString(request, "pluginType");
String title = ParamUtil.getString(request, "title", pluginType);

PluginSetting pluginSetting = PluginSettingLocalServiceUtil.getPluginSetting(company.getCompanyId(), pluginId, pluginType);

boolean active = pluginSetting.isActive();
String[] rolesArray = pluginSetting.getRolesArray();

Portlet portlet = null;

if (pluginType.equals(Plugin.TYPE_PORTLET)) {
	String portletId = pluginId;

	portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletId);

	active = portlet.isActive();
	rolesArray = portlet.getRolesArray();
}
%>

<portlet:actionURL var="editPluginURL">
	<portlet:param name="struts_action" value="/plugins_admin/edit_plugin" />
</portlet:actionURL>

<aui:form action="<%= editPluginURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="pluginId" type="hidden" value="<%= pluginId %>" />
	<aui:input name="pluginType" type="hidden" value="<%= pluginType %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= false %>"
		title="<%= title %>"
	/>

	<aui:fieldset>
		<aui:field-wrapper label="module-id">
			<%= HtmlUtil.escape(moduleId) %>
		</aui:field-wrapper>

		<aui:field-wrapper label="plugin-id">
			<%= HtmlUtil.escape(pluginId) %>
		</aui:field-wrapper>

		<aui:input name="active" type="checkbox" value="<%= active %>" />

		<c:choose>
			<c:when test="<%= pluginType.equals(Plugin.TYPE_PORTLET) %>">
				<aui:field-wrapper helpMessage="edit-plugin-permissions-help" label="permissions">

					<%
					List curActions = ResourceActionsUtil.getResourceActions(portlet.getPortletId(), null);

					int maxNumberOfRolesChecked = 500;

					List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), null, null, (Integer[])null, 0, maxNumberOfRolesChecked, new RoleRoleIdComparator(true));
					int rolesCount = RoleLocalServiceUtil.searchCount(company.getCompanyId(), null, null, (Integer[])null);

					List<Role> addToPageRoles = null;
					List<Role> accessInControlPanelRoles = null;

					if (curActions.contains(ActionKeys.ADD_TO_PAGE)) {
						addToPageRoles = _filterRoles(roles, portlet.getPortletId(), ActionKeys.ADD_TO_PAGE);
					}
					else {
						addToPageRoles = new ArrayList<Role>();
					}

					if (curActions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL)) {
						accessInControlPanelRoles = _filterRoles(roles, portlet.getPortletId(), ActionKeys.ACCESS_IN_CONTROL_PANEL);
					}
					else {
						accessInControlPanelRoles = new ArrayList<Role>();
					}
					%>

					<c:if test="<%= rolesCount > maxNumberOfRolesChecked %>">
						<div class="alert alert-block">
							<%= LanguageUtil.format(pageContext, "the-portal-has-more-roles-than-the-maximum-that-can-be-checked-x", maxNumberOfRolesChecked) %>
						</div>
					</c:if>

					<c:if test="<%= !addToPageRoles.isEmpty() %>">
						<div class="permission-group">
							<b><liferay-ui:message key="action.ADD_TO_PAGE" /></b>: <liferay-ui:message key="the-users-with-the-following-roles-can-add-this-portlet-to-the-pages-they-manage" />

							<liferay-ui:search-container >
								<liferay-ui:search-container-results
									results="<%= addToPageRoles %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.portal.model.Role"
									keyProperty="name"
									modelVar="role"

								>
									<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(role) %>" />
									<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(role) %>" />

									<liferay-ui:search-container-column-text
										name="name"
										property="name"
									/>

									<liferay-ui:search-container-column-text
										align="right"
									>
										<liferay-portlet:renderURL portletName="<%= PortletKeys.ROLES_ADMIN %>" var="editURL">
											<portlet:param name="struts_action" value="/roles_admin/edit_role_permissions" />
											<portlet:param name="<%= Constants.CMD %>" value="edit" />
											<portlet:param name="tabs1" value="roles" />
											<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
											<portlet:param name="portletResource" value="<%= portlet.getPortletId() %>" />
											<portlet:param name="showModelResources" value="0" />
										</liferay-portlet:renderURL>

										<liferay-ui:icon image="edit" label="<%= true %>" message="change" url="<%= editURL %>" />
									</liferay-ui:search-container-column-text>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator type="more" />
							</liferay-ui:search-container>
						</div>
					</c:if>

					<c:if test="<%= !accessInControlPanelRoles.isEmpty() %>">
						<div class="permission-group">
							<strong><liferay-ui:message key="action.ACCESS_IN_CONTROL_PANEL" /></strong>: <liferay-ui:message key="the-users-with-the-following-roles-can-access-this-portlet-in-the-control-panel" />

							<liferay-ui:search-container >
								<liferay-ui:search-container-results
									results="<%= accessInControlPanelRoles %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.portal.model.Role"
									keyProperty="name"
									modelVar="role"
								>
									<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(role) %>" />
									<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(role) %>" />

									<liferay-ui:search-container-column-text
										name="name"
										property="name"
									/>

									<liferay-ui:search-container-column-text
										align="right"
									>
										<liferay-portlet:renderURL portletName="<%= PortletKeys.ROLES_ADMIN %>" var="editURL">
											<portlet:param name="struts_action" value="/roles_admin/edit_role_permissions" />
											<portlet:param name="<%= Constants.CMD %>" value="edit" />
											<portlet:param name="tabs1" value="roles" />
											<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
											<portlet:param name="portletResource" value="<%= portlet.getPortletId() %>" />
											<portlet:param name="showModelResources" value="0" />
										</liferay-portlet:renderURL>

										<liferay-ui:icon image="edit" label="<%= true %>" message="change" url="<%= editURL %>" />
									</liferay-ui:search-container-column-text>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator type="more" />
							</liferay-ui:search-container>
						</div>
					</c:if>

					<c:if test="<%= addToPageRoles.isEmpty() && accessInControlPanelRoles.isEmpty() %>">
						<liferay-ui:message key="only-administrators-can-use-this-portlet" />
					</c:if>
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<aui:input cssClass="lfr-textarea-container" helpMessage="enter-one-role-name-per-line-a-user-must-belong-to-one-of-these-roles-in-order-to-add-this-plugin-to-a-page" name="roles" type="textarea" value='<%= StringUtil.merge(rolesArray, "\n") %>' />
			</c:otherwise>
		</c:choose>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%!
private List<Role> _filterRoles(List<Role> roles, String portletId, String actionId) throws Exception {
	List<Role> filteredRoles = new ArrayList<Role>(0);

	for (Role role : roles) {
		if ((role.getType() == RoleConstants.TYPE_REGULAR) && _hasPermission(role, actionId, portletId, ResourceConstants.SCOPE_COMPANY) || _hasPermission(role, actionId, portletId, ResourceConstants.SCOPE_GROUP)) {
			filteredRoles.add(role);
		}
		else if (_hasPermission(role, actionId, portletId, ResourceConstants.SCOPE_GROUP_TEMPLATE)) {
			filteredRoles.add(role);
		}
	}

	return filteredRoles;
}

private boolean _hasPermission(Role role, String actionId, String resourceName, Integer scope) throws Exception {
	return ResourcePermissionLocalServiceUtil.hasScopeResourcePermission(role.getCompanyId(), resourceName, scope, role.getRoleId(), actionId);
}
%>