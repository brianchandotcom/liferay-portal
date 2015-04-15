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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "regular-roles");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceDescription = ParamUtil.getString(request, "modelResourceDescription");
String modelResourceName = ResourceActionsUtil.getModelResource(request, modelResource);

long resourceGroupId = ParamUtil.getLong(request, "resourceGroupId");

String resourcePrimKey = ParamUtil.getString(request, "resourcePrimKey");

if (Validator.isNull(resourcePrimKey)) {
	throw new ResourcePrimKeyException();
}

String selResource = modelResource;
String selResourceDescription = modelResourceDescription;
String selResourceName = modelResourceName;

if (Validator.isNull(modelResource)) {
	PortletURL portletURL = new PortletURLImpl(request, portletResource, plid, PortletRequest.ACTION_PHASE);

	portletURL.setPortletMode(PortletMode.VIEW);
	portletURL.setWindowState(WindowState.NORMAL);

	redirect = portletURL.toString();

	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	selResource = portlet.getRootPortletId();
	selResourceDescription = PortalUtil.getPortletTitle(portlet, application, locale);
	selResourceName = LanguageUtil.get(request, "portlet");
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.unescape(selResourceDescription), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "permissions"), currentURL);
}

long groupId = themeDisplay.getScopeGroupId();

if (resourceGroupId > 0) {
	groupId = resourceGroupId;
}

Group group = GroupLocalServiceUtil.getGroup(groupId);

Layout selLayout = null;

if (modelResource.equals(Layout.class.getName())) {
	selLayout = LayoutLocalServiceUtil.getLayout(GetterUtil.getLong(resourcePrimKey));

	group = selLayout.getGroup();
	groupId = group.getGroupId();
}

Resource resource = null;

try {
	if (ResourceBlockLocalServiceUtil.isSupported(selResource)) {
		ResourceBlockLocalServiceUtil.verifyResourceBlockId(company.getCompanyId(), selResource, Long.valueOf(resourcePrimKey));
	}
	else {
		if (ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey) == 0) {
			throw new NoSuchResourceException();
		}
	}
	resource = ResourceLocalServiceUtil.getResource(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
}
catch (NoSuchResourceException nsre) {
	boolean portletActions = Validator.isNull(modelResource);

	ResourceLocalServiceUtil.addResources(company.getCompanyId(), groupId, 0, selResource, resourcePrimKey, portletActions, true, true);

	resource = ResourceLocalServiceUtil.getResource(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
}

String roleTypesParam = ParamUtil.getString(request, "roleTypes");

int[] roleTypes = null;

if (Validator.isNotNull(roleTypesParam)) {
	roleTypes = StringUtil.split(roleTypesParam, 0);
}

PortletURL actionPortletURL = renderResponse.createActionURL();

actionPortletURL.setParameter("struts_action", "/portlet_configuration/edit_permissions");
actionPortletURL.setParameter("tabs2", tabs2);
actionPortletURL.setParameter("redirect", redirect);
actionPortletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
actionPortletURL.setParameter("portletResource", portletResource);
actionPortletURL.setParameter("modelResource", modelResource);
actionPortletURL.setParameter("modelResourceDescription", modelResourceDescription);
actionPortletURL.setParameter("resourceGroupId", String.valueOf(resourceGroupId));
actionPortletURL.setParameter("resourcePrimKey", resourcePrimKey);
actionPortletURL.setParameter("roleTypes", roleTypesParam);

PortletURL renderPortletURL = renderResponse.createRenderURL();

renderPortletURL.setParameter("struts_action", "/portlet_configuration/edit_permissions");
renderPortletURL.setParameter("tabs2", tabs2);
renderPortletURL.setParameter("redirect", redirect);
renderPortletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
renderPortletURL.setParameter("portletResource", portletResource);
renderPortletURL.setParameter("modelResource", modelResource);
renderPortletURL.setParameter("modelResourceDescription", modelResourceDescription);
renderPortletURL.setParameter("resourceGroupId", String.valueOf(resourceGroupId));
renderPortletURL.setParameter("resourcePrimKey", resourcePrimKey);
renderPortletURL.setParameter("roleTypes", roleTypesParam);

long controlPanelPlid = PortalUtil.getControlPanelPlid(company.getCompanyId());

PortletURLImpl definePermissionsURL = new PortletURLImpl(request, PortletKeys.ROLES_ADMIN, controlPanelPlid, PortletRequest.RENDER_PHASE);

definePermissionsURL.setParameter("struts_action", "/roles_admin/edit_role_permissions");
definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
definePermissionsURL.setPortletMode(PortletMode.VIEW);
definePermissionsURL.setRefererPlid(plid);
%>

<div class="edit-permissions">
	<aui:form action="<%= actionPortletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="role_permissions" />
		<aui:input name="resourceId" type="hidden" value="<%= resource.getResourceId() %>" />

		<c:choose>
			<c:when test="<%= Validator.isNull(modelResource) %>">
				<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
					<liferay-util:param name="tabs1" value="permissions" />
				</liferay-util:include>
			</c:when>
			<c:otherwise>
				<liferay-ui:header
						backURL="<%= redirect %>"
						localizeTitle="<%= false %>"
						title="<%= HtmlUtil.unescape(selResourceDescription) %>"
						/>
			</c:otherwise>
		</c:choose>

		<%
		boolean filterGroupRoles = !ResourceActionsUtil.isPortalModelResource(modelResource);

		List<String> actions = ResourceActionsUtil.getResourceActions(portletResource, modelResource);

		if (modelResource.equals(Group.class.getName())) {
			long modelResourceGroupId = GetterUtil.getLong(resourcePrimKey);

			Group modelResourceGroup = GroupLocalServiceUtil.getGroup(modelResourceGroupId);

			if (modelResourceGroup.isLayoutPrototype() || modelResourceGroup.isLayoutSetPrototype() || modelResourceGroup.isUserGroup()) {
				actions = new ArrayList<String>(actions);

				actions.remove(ActionKeys.ADD_LAYOUT_BRANCH);
				actions.remove(ActionKeys.ADD_LAYOUT_SET_BRANCH);
				actions.remove(ActionKeys.ASSIGN_MEMBERS);
				actions.remove(ActionKeys.ASSIGN_USER_ROLES);
				actions.remove(ActionKeys.MANAGE_ANNOUNCEMENTS);
				actions.remove(ActionKeys.MANAGE_STAGING);
				actions.remove(ActionKeys.MANAGE_TEAMS);
				actions.remove(ActionKeys.PUBLISH_STAGING);
				actions.remove(ActionKeys.PUBLISH_TO_REMOTE);
				actions.remove(ActionKeys.VIEW_MEMBERS);
				actions.remove(ActionKeys.VIEW_STAGING);
			}
		}
		else if (modelResource.equals(Role.class.getName())) {
			long modelResourceRoleId = GetterUtil.getLong(resourcePrimKey);

			Role modelResourceRole = RoleLocalServiceUtil.getRole(modelResourceRoleId);

			String name = modelResourceRole.getName();

			if (name.equals(RoleConstants.GUEST) || name.equals(RoleConstants.USER)) {
				actions = new ArrayList<String>(actions);

				actions.remove(ActionKeys.ASSIGN_MEMBERS);
				actions.remove(ActionKeys.DEFINE_PERMISSIONS);
				actions.remove(ActionKeys.DELETE);
				actions.remove(ActionKeys.PERMISSIONS);
				actions.remove(ActionKeys.UPDATE);
				actions.remove(ActionKeys.VIEW);
			}

			if ((modelResourceRole.getType() == RoleConstants.TYPE_ORGANIZATION) || (modelResourceRole.getType() == RoleConstants.TYPE_SITE)) {
				filterGroupRoles = true;
			}
		}

		List<Role> roles = ListUtil.copy(ResourceActionsUtil.getRoles(company.getCompanyId(), group, modelResource, roleTypes));

		Role administratorRole = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		roles.remove(administratorRole);

		if (filterGroupRoles) {
			Role organizationAdministratorRole = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.ORGANIZATION_ADMINISTRATOR);

			roles.remove(organizationAdministratorRole);

			Role organizationOwnerRole = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

			roles.remove(organizationOwnerRole);

			Role siteAdministratorRole = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

			roles.remove(siteAdministratorRole);

			Role siteOwnerRole = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.SITE_OWNER);

			roles.remove(siteOwnerRole);
		}

		long modelResourceRoleId = 0;

		if (modelResource.equals(Role.class.getName())) {
			modelResourceRoleId = GetterUtil.getLong(resourcePrimKey);
		}

		roles.addAll(RoleLocalServiceUtil.getTeamRoles(groupId, new long[] {modelResourceRoleId}));

		Iterator<Role> itr = roles.iterator();

		while (itr.hasNext()) {
			Role role = itr.next();

			String name = role.getName();

			if (!name.equals(RoleConstants.GUEST) && !RolePermissionUtil.contains(permissionChecker, groupId, role.getRoleId(), ActionKeys.VIEW) && (!role.isTeam() || !TeamPermissionUtil.contains(permissionChecker, role.getClassPK(), ActionKeys.PERMISSIONS))) {
				itr.remove();
			}
		}
		%>

		<liferay-ui:search-container>
			<liferay-ui:search-container-results
				results="<%= roles %>"
				total="<%= roles.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.Role"
				escapedModel="<%= true %>"
				keyProperty="roleId"
				modelVar="role"
			>

				<%
				String definePermissionsHREF = null;

				String name = role.getName();

				if (!name.equals(RoleConstants.ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_OWNER) && !name.equals(RoleConstants.OWNER) && !name.equals(RoleConstants.SITE_ADMINISTRATOR) && !name.equals(RoleConstants.SITE_OWNER) && !role.isTeam() && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DEFINE_PERMISSIONS)) {
					definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

					definePermissionsHREF = definePermissionsURL.toString();
				}
				%>

				<liferay-ui:search-container-column-text
					href="<%= definePermissionsHREF %>"
					name="role"
				>
					<liferay-ui:icon
						iconCssClass="<%= RolesAdminUtil.getIconCssClass(role) %>"
						label="<%= true %>"
						message="<%= role.getTitle(locale) %>"
					/>
				</liferay-ui:search-container-column-text>

				<%

				// Actions

				List<String> currentIndividualActions = new ArrayList<String>();
				List<String> currentGroupActions = new ArrayList<String>();
				List<String> currentGroupTemplateActions = new ArrayList<String>();
				List<String> currentCompanyActions = new ArrayList<String>();

				ResourcePermissionUtil.populateResourcePermissionActionIds(groupId, role, resource, actions, currentIndividualActions, currentGroupActions, currentGroupTemplateActions, currentCompanyActions);

				List<String> guestUnsupportedActions = ResourceActionsUtil.getResourceGuestUnsupportedActions(portletResource, modelResource);

				// LPS-32515

				if ((selLayout != null) && group.isGuest() && SitesUtil.isFirstLayout(selLayout.getGroupId(), selLayout.getLayoutId())) {
					guestUnsupportedActions = new ArrayList<String>(guestUnsupportedActions);

					guestUnsupportedActions.add(ActionKeys.VIEW);
				}

				for (String action : actions) {
					boolean checked = false;
					boolean disabled = false;
					String preselectedMsg = StringPool.BLANK;

					if (currentIndividualActions.contains(action)) {
						checked = true;
					}

					if (currentGroupActions.contains(action) || currentGroupTemplateActions.contains(action)) {
						checked = true;
						preselectedMsg = "x-is-allowed-to-do-action-x-in-all-items-of-type-x-in-x";
					}

					if (currentCompanyActions.contains(action)) {
						checked = true;
						preselectedMsg = "x-is-allowed-to-do-action-x-in-all-items-of-type-x-in-this-portal-instance";
					}

					if (name.equals(RoleConstants.GUEST) && guestUnsupportedActions.contains(action)) {
						disabled = true;
					}

					if (action.equals(ActionKeys.ACCESS_IN_CONTROL_PANEL)) {
						disabled = true;
					}
				%>

					<liferay-ui:search-container-column-text
						name="<%= ResourceActionsUtil.getAction(request, action) %>"
					>

						<%
						String dataMessage = StringPool.BLANK;

						if (Validator.isNotNull(preselectedMsg)) {
							dataMessage = HtmlUtil.escapeAttribute(LanguageUtil.format(request, preselectedMsg, new Object[] {role.getTitle(locale), ResourceActionsUtil.getAction(request, action), Validator.isNull(modelResource) ? selResourceDescription : ResourceActionsUtil.getModelResource(locale, resource.getName()), HtmlUtil.escape(group.getDescriptiveName(locale))}, false));
						}

						String actionSeparator = Validator.isNotNull(preselectedMsg) ? ActionUtil.PRESELECTED : ActionUtil.ACTION;
						%>

						<c:if test="<%= disabled && checked %>">
							<input name="<%= renderResponse.getNamespace() + role.getRoleId() + actionSeparator + action %>" type="hidden" value="<%= true %>" />
						</c:if>

						<input <%= checked ? "checked" : StringPool.BLANK %> class="<%= Validator.isNotNull(preselectedMsg) ? "lfr-checkbox-preselected" : StringPool.BLANK %>" data-message="<%= dataMessage %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= FriendlyURLNormalizerUtil.normalize(role.getName()) + actionSeparator + action %>" name="<%= renderResponse.getNamespace() + role.getRoleId() + actionSeparator + action %>" type="checkbox" />
					</liferay-ui:search-container-column-text>

				<%
				}
				%>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />
		</liferay-ui:search-container>

		<aui:button-row>
			<aui:button type="submit" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />fm').on(
		'mouseover',
		'.lfr-checkbox-preselected',
		function(event) {
			var currentTarget = $(event.currentTarget);

			Liferay.Portal.ToolTip.show(currentTarget, currentTarget.data('message'));
		}
	);
</aui:script>