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
String tabs2 = ParamUtil.getString(request, "tabs2", "regular-roles");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);

String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceDescription = ParamUtil.getString(request, "modelResourceDescription");

long resourceGroupId = ParamUtil.getLong(request, "resourceGroupId");

String resourcePrimKey = ParamUtil.getString(request, "resourcePrimKey");

if (Validator.isNull(resourcePrimKey)) {
	throw new ResourcePrimKeyException();
}

String selResource = modelResource;
String selResourceDescription = modelResourceDescription;

if (Validator.isNull(modelResource)) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	selResource = portlet.getRootPortletId();
	selResourceDescription = PortalUtil.getPortletTitle(portlet, application, locale);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.unescape(selResourceDescription), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "permissions"), currentURL);
}

if (resourceGroupId == 0) {
	resourceGroupId = themeDisplay.getScopeGroupId();
}

long groupId = resourceGroupId;

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

LiferayPortletURL definePermissionsURL = (LiferayPortletURL)PortletProviderUtil.getPortletURL(request, Role.class.getName(), PortletProvider.Action.MANAGE);

definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
definePermissionsURL.setParameter("backURL", currentURL);
definePermissionsURL.setPortletMode(PortletMode.VIEW);
definePermissionsURL.setRefererPlid(plid);
definePermissionsURL.setWindowState(LiferayWindowState.POP_UP);

PortletURL iteratorURL = PortletURLFactoryUtil.create(renderRequest, PortletConfigurationPortletKeys.PORTLET_CONFIGURATION, PortletRequest.RENDER_PHASE);

iteratorURL.setParameter("mvcPath", "/edit_permissions.jsp");
iteratorURL.setParameter("returnToFullPageURL", returnToFullPageURL);
iteratorURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
iteratorURL.setParameter("portletResource", portletResource);
iteratorURL.setParameter("resourcePrimKey", resourcePrimKey);
iteratorURL.setWindowState(LiferayWindowState.POP_UP);

SearchContainer roleSearchContainer = new RoleSearch(renderRequest, iteratorURL);

RoleSearchTerms searchTerms = (RoleSearchTerms)roleSearchContainer.getSearchTerms();
%>

<div class="edit-permissions portlet-configuration-edit-permissions">
	<div class="portlet-configuration-body-content">
		<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="permissions" selected="<%= true %>" />
			</aui:nav>

			<aui:nav-bar-search>
				<aui:form action="<%= iteratorURL.toString() %>" name="searchFm">
					<liferay-ui:input-search markupView="lexicon" />
				</aui:form>
			</aui:nav-bar-search>
		</aui:nav-bar>

		<portlet:actionURL name="updateRolePermissions" var="updateRolePermissionsURL">
			<portlet:param name="mvcPath" value="/edit_permissions.jsp" />
			<portlet:param name="tabs2" value="<%= tabs2 %>" />
			<portlet:param name="cur" value="<%= String.valueOf(cur) %>" />
			<portlet:param name="delta" value="<%= String.valueOf(delta) %>" />
			<portlet:param name="returnToFullPageURL" value="<%= returnToFullPageURL %>" />
			<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
			<portlet:param name="portletResource" value="<%= portletResource %>" />
			<portlet:param name="modelResource" value="<%= modelResource %>" />
			<portlet:param name="modelResourceDescription" value="<%= modelResourceDescription %>" />
			<portlet:param name="resourceGroupId" value="<%= String.valueOf(resourceGroupId) %>" />
			<portlet:param name="resourcePrimKey" value="<%= resourcePrimKey %>" />
			<portlet:param name="roleTypes" value="<%= roleTypesParam %>" />
		</portlet:actionURL>

		<aui:form action="<%= updateRolePermissionsURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
			<aui:input name="resourceId" type="hidden" value="<%= resource.getResourceId() %>" />

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

			if (roleTypes == null) {
				roleTypes = RoleConstants.TYPES_REGULAR_AND_SITE;

				if (ResourceActionsUtil.isPortalModelResource(modelResource)) {
					if (modelResource.equals(Organization.class.getName()) || modelResource.equals(User.class.getName())) {
						roleTypes = RoleConstants.TYPES_ORGANIZATION_AND_REGULAR;
					}
					else {
						roleTypes = RoleConstants.TYPES_REGULAR;
					}
				}
				else {
					if (group != null) {
						Group parentGroup = null;

						if (group.isLayout()) {
							parentGroup = GroupLocalServiceUtil.fetchGroup(group.getParentGroupId());
						}

						if (parentGroup == null) {
							if (group.isOrganization()) {
								roleTypes = RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE;
							}
							else if (group.isUser()) {
								roleTypes = RoleConstants.TYPES_REGULAR;
							}
						}
						else {
							if (parentGroup.isOrganization()) {
								roleTypes = RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE;
							}
							else if (parentGroup.isUser()) {
								roleTypes = RoleConstants.TYPES_REGULAR;
							}
						}
					}
				}
			}

			long modelResourceRoleId = 0;

			if (modelResource.equals(Role.class.getName())) {
				modelResourceRoleId = GetterUtil.getLong(resourcePrimKey);
			}

			boolean filterGuestRole = false;

			if (Objects.equals(modelResource, Layout.class.getName())) {
				Layout resourceLayout = LayoutLocalServiceUtil.getLayout(GetterUtil.getLong(resourcePrimKey));

				if (resourceLayout.isPrivateLayout()) {
					Group resourceLayoutGroup = resourceLayout.getGroup();

					if (!resourceLayoutGroup.isLayoutSetPrototype()) {
						filterGuestRole = true;
					}
				}
			}
			else if (Validator.isNotNull(portletResource)) {
				int pos = resourcePrimKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

				if (pos > 0) {
					long resourcePlid = GetterUtil.getLong(resourcePrimKey.substring(0, pos));

					Layout resourceLayout = LayoutLocalServiceUtil.getLayout(resourcePlid);

					if (resourceLayout.isPrivateLayout()) {
						Group resourceLayoutGroup = resourceLayout.getGroup();

						if (!resourceLayoutGroup.isLayoutPrototype() && !resourceLayoutGroup.isLayoutSetPrototype()) {
							filterGuestRole = true;
						}
					}
				}
			}

			List<String> excludedRoleNames = new ArrayList<>();

			excludedRoleNames.add(RoleConstants.ADMINISTRATOR);

			if (filterGroupRoles) {
				excludedRoleNames.add(RoleConstants.ORGANIZATION_ADMINISTRATOR);
				excludedRoleNames.add(RoleConstants.ORGANIZATION_OWNER);
				excludedRoleNames.add(RoleConstants.SITE_ADMINISTRATOR);
				excludedRoleNames.add(RoleConstants.SITE_OWNER);
			}

			if (filterGuestRole) {
				excludedRoleNames.add(RoleConstants.GUEST);
			}

			long teamGroupId = group.getGroupId();

			if (group.isLayout()) {
				teamGroupId = group.getParentGroupId();
			}

			int count = RoleLocalServiceUtil.getGroupRolesAndTeamRolesCount(company.getCompanyId(), searchTerms.getKeywords(), excludedRoleNames, roleTypes, modelResourceRoleId, teamGroupId);

			roleSearchContainer.setTotal(count);

			List<Role> roles = RoleLocalServiceUtil.getGroupRolesAndTeamRoles(company.getCompanyId(), searchTerms.getKeywords(), excludedRoleNames, roleTypes, modelResourceRoleId, teamGroupId, roleSearchContainer.getStart(), roleSearchContainer.getResultEnd());

			roleSearchContainer.setResults(roles);
			%>

			<liferay-ui:search-container
				searchContainer="<%= roleSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Role"
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
						value="<%= role.getTitle(locale) %>"
					/>

					<%

					// Actions

					List<String> currentIndividualActions = new ArrayList<String>();
					List<String> currentGroupActions = new ArrayList<String>();
					List<String> currentGroupTemplateActions = new ArrayList<String>();
					List<String> currentCompanyActions = new ArrayList<String>();

					ResourcePermissionUtil.populateResourcePermissionActionIds(groupId, role, resource, actions, currentIndividualActions, currentGroupActions, currentGroupTemplateActions, currentCompanyActions);

					List<String> guestUnsupportedActions = ResourceActionsUtil.getResourceGuestUnsupportedActions(portletResource, modelResource);

					// LPS-32515

					if ((selLayout != null) && group.isGuest() && SitesUtil.isFirstLayout(selLayout.getGroupId(), selLayout.isPrivateLayout(), selLayout.getLayoutId())) {
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
							continue;
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

							<input <%= checked ? "checked" : StringPool.BLANK %> class="<%= Validator.isNotNull(preselectedMsg) ? "lfr-checkbox-preselected" : StringPool.BLANK %>" data-message="<%= dataMessage %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= FriendlyURLNormalizerUtil.normalize(role.getName()) + actionSeparator + action %>" name="<%= renderResponse.getNamespace() + role.getRoleId() + actionSeparator + action %>" onclick="<%= Validator.isNotNull(preselectedMsg) ? "return false;" : StringPool.BLANK %>" type="checkbox" />
						</liferay-ui:search-container-column-text>

					<%
					}
					%>

				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator markupView="lexicon" />
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="saveButton" type="submit" />

		<aui:button cssClass="btn-lg" type="cancel" />
	</aui:button-row>
</div>

<aui:script sandbox="<%= true %>">
	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />fm').on(
		'mouseover',
		'.lfr-checkbox-preselected',
		function(event) {
			var currentTarget = $(event.currentTarget);

			Liferay.Portal.ToolTip.show(currentTarget, currentTarget.data('message'));
		}
	);

	$('#<portlet:namespace />saveButton').on(
		'click',
		function(event) {
			event.preventDefault();

			if (<%= roleSearchContainer.getTotal() != 0 %>) {
				submitForm(form);
			}
		}
	);
</aui:script>