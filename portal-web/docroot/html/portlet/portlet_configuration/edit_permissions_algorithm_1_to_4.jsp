<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
String tabs2 = ParamUtil.getString(request, "tabs2", "users");
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceDescription = ParamUtil.getString(request, "modelResourceDescription");
String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, modelResource);

String resourcePrimKey = ParamUtil.getString(request, "resourcePrimKey");

if (Validator.isNull(resourcePrimKey)) {
	throw new ResourcePrimKeyException();
}

String selResource = modelResource;
String selResourceDescription = modelResourceDescription;
String selResourceName = modelResourceName;

if (Validator.isNull(modelResource)) {
	PortletURL portletURL = new PortletURLImpl(request, portletResource, plid, PortletRequest.RENDER_PHASE);

	portletURL.setWindowState(WindowState.NORMAL);
	portletURL.setPortletMode(PortletMode.VIEW);

	redirect = portletURL.toString();

	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	selResource = portlet.getRootPortletId();
	selResourceDescription = PortalUtil.getPortletTitle(portlet, application, locale);
	selResourceName = LanguageUtil.get(pageContext, "portlet");
}

PortalUtil.addPortletBreadcrumbEntry(request, selResourceDescription, null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "permissions"), currentURL);

Group group = themeDisplay.getScopeGroup();
long groupId = group.getGroupId();

Layout selLayout = null;

if (modelResource.equals(Layout.class.getName())) {
	selLayout = LayoutLocalServiceUtil.getLayout(GetterUtil.getLong(resourcePrimKey));

	group = selLayout.getGroup();
	groupId = group.getGroupId();
}

Resource resource = null;

try {
	resource = ResourceLocalServiceUtil.getResource(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
}
catch (NoSuchResourceException nsre) {
	boolean portletActions = Validator.isNull(modelResource);

	ResourceLocalServiceUtil.addResources(company.getCompanyId(), groupId, 0, selResource, resourcePrimKey, portletActions, true, true);

	resource = ResourceLocalServiceUtil.getResource(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/portlet_configuration/edit_permissions");
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);
portletURL.setParameter("modelResource", modelResource);
portletURL.setParameter("modelResourceDescription", modelResourceDescription);
portletURL.setParameter("resourcePrimKey", resourcePrimKey);

request.setAttribute("edit_permissions_algorithm_1_to_4.jsp-tabs2", tabs2);
request.setAttribute("edit_permissions_algorithm_1_to_4.jsp-tabs3", tabs3);

request.setAttribute("edit_permissions_algorithm_1_to_4.jsp-portletResource", portletResource);
request.setAttribute("edit_permissions_algorithm_1_to_4.jsp-modelResource", modelResource);
request.setAttribute("edit_permissions_algorithm_1_to_4.jsp-group", group);
request.setAttribute("edit_permissions_algorithm_1_to_4.jsp-groupId", groupId);
request.setAttribute("edit_permissions_algorithm_1_to_4.jsp-resource", resource);

request.setAttribute("edit_permissions_algorithm_1_to_4.jsp-portletURL", portletURL);
%>

<div class="edit-permissions">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="permissionsRedirect" type="hidden" />
		<aui:input name="cur" type="hidden" value="<%= cur %>" />
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
					title="<%= selResourceDescription %>"
				/>
			</c:otherwise>
		</c:choose>

		<%
		String tabs2Names = "users,organizations,user-groups,regular-roles,site-roles,site,guest";

		if (ResourceActionsUtil.isPortalModelResource(modelResource)) {
			tabs2Names = StringUtil.replace(tabs2Names, "site-roles,", StringPool.BLANK);
			tabs2Names = StringUtil.replace(tabs2Names, "site,", StringPool.BLANK);
			tabs2Names = StringUtil.replace(tabs2Names, ",guest", StringPool.BLANK);
		}
		else if (modelResource.equals(Layout.class.getName())) {

			// User layouts should not have site assignments

			if (group.isUser()) {
				tabs2Names = StringUtil.replace(tabs2Names, "site,", StringPool.BLANK);
				tabs2Names = StringUtil.replace(tabs2Names, "site-roles,", StringPool.BLANK);
			}
			else if (group.isOrganization()) {
				tabs2Names = StringUtil.replace(tabs2Names, "site,", "site,organization,");
				tabs2Names = StringUtil.replace(tabs2Names, "site-roles,", "site-roles,organization-roles,");
			}

			// Private layouts should not have guest assignments

			if (selLayout.isPrivateLayout() && !selLayout.getGroup().isLayoutSetPrototype()) {
				tabs2Names = StringUtil.replace(tabs2Names, ",guest", StringPool.BLANK);
			}
		}
		else {
			if (group.isUser()) {
				tabs2Names = StringUtil.replace(tabs2Names, "site,", StringPool.BLANK);
				tabs2Names = StringUtil.replace(tabs2Names, "site-roles,", StringPool.BLANK);
			}
			else if (group.isOrganization()) {
				tabs2Names = StringUtil.replace(tabs2Names, "site,", "site,organization,");
				tabs2Names = StringUtil.replace(tabs2Names, "site-roles,", "site-roles,organization-roles,");
			}
		}
		%>

		<c:choose>
			<c:when test="<%= Validator.isNull(modelResource) %>">
				<liferay-ui:tabs
					names="<%= tabs2Names %>"
					param="tabs2"
					url="<%= portletURL.toString() %>"
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:tabs
					names="<%= tabs2Names %>"
					param="tabs2"
					url="<%= portletURL.toString() %>"
					backURL="<%= redirect %>"
				/>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test='<%= tabs2.equals("users") %>'>
				<liferay-util:include page="/html/portlet/portlet_configuration/edit_permissions_algorithm_1_to_4_users.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("organizations") %>'>
				<liferay-util:include page="/html/portlet/portlet_configuration/edit_permissions_algorithm_1_to_4_organizations.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("user-groups") %>'>
				<liferay-util:include page="/html/portlet/portlet_configuration/edit_permissions_algorithm_1_to_4_user_groups.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("regular-roles") || tabs2.equals("site-roles") || tabs2.equals("organization-roles") %>'>
				<liferay-util:include page="/html/portlet/portlet_configuration/edit_permissions_algorithm_1_to_4_roles.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("site") || tabs2.equals("organization") %>'>
				<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
				<aui:input name="groupIdActionIds" type="hidden" />

				<%
				List permissions = PermissionLocalServiceUtil.getGroupPermissions(groupId, resource.getResourceId());

				List actions1 = ResourceActionsUtil.getResourceActions(portletResource, modelResource);
				List actions2 = ResourceActionsUtil.getActions(permissions);

				// Left list

				List leftList = new ArrayList();

				for (int i = 0; i < actions2.size(); i++) {
					String actionId = (String)actions2.get(i);

					leftList.add(new KeyValuePair(actionId, ResourceActionsUtil.getAction(pageContext, actionId)));
				}

				leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

				// Right list

				List rightList = new ArrayList();

				for (int i = 0; i < actions1.size(); i++) {
					String actionId = (String)actions1.get(i);

					if (!actions2.contains(actionId)) {
						rightList.add(new KeyValuePair(actionId, ResourceActionsUtil.getAction(pageContext, actionId)));
					}
				}

				rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
				%>

				<div class="assign-permissions">
					<liferay-ui:input-move-boxes
						leftTitle="what-they-can-do"
						rightTitle="what-they-cant-do"
						leftBoxName="current_actions"
						rightBoxName="available_actions"
						leftList="<%= leftList %>"
						rightList="<%= rightList %>"
					/>

					<aui:button-row>
						<aui:button onClick='<%= renderResponse.getNamespace() + "saveGroupPermissions();" %>' value="save" />
					</aui:button-row>
				</div>
			</c:when>
			<c:when test='<%= tabs2.equals("guest") %>'>
				<input name="<portlet:namespace />guestActionIds" type="hidden" value="" />

				<%
				User guestUser = UserLocalServiceUtil.getDefaultUser(company.getCompanyId());

				List permissions = PermissionLocalServiceUtil.getUserPermissions(guestUser.getUserId(), resource.getResourceId());

				List actions1 = ResourceActionsUtil.getResourceActions(portletResource, modelResource);
				List actions2 = ResourceActionsUtil.getActions(permissions);

				List guestUnsupportedActions = ResourceActionsUtil.getResourceGuestUnsupportedActions(portletResource, modelResource);

				// Left list

				List leftList = new ArrayList();

				for (int i = 0; i < actions2.size(); i++) {
					String actionId = (String)actions2.get(i);

					if (!guestUnsupportedActions.contains(actionId) && !actionId.equals(ActionKeys.ACCESS_IN_CONTROL_PANEL)) {
						leftList.add(new KeyValuePair(actionId, ResourceActionsUtil.getAction(pageContext, actionId)));
					}
				}

				leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

				// Right list

				List rightList = new ArrayList();

				for (int i = 0; i < actions1.size(); i++) {
					String actionId = (String)actions1.get(i);

					if (!guestUnsupportedActions.contains(actionId) && !actionId.equals(ActionKeys.ACCESS_IN_CONTROL_PANEL)) {
						if (!actions2.contains(actionId)) {
							rightList.add(new KeyValuePair(actionId, ResourceActionsUtil.getAction(pageContext, actionId)));
						}
					}
				}

				rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
				%>

				<div class="assign-permissions">
					<liferay-ui:input-move-boxes
						leftTitle="what-they-can-do"
						rightTitle="what-they-cant-do"
						leftBoxName="current_actions"
						rightBoxName="available_actions"
						leftList="<%= leftList %>"
						rightList="<%= rightList %>"
					/>

					<aui:button-row>
						<aui:button onClick='<%= renderResponse.getNamespace() + "saveGuestPermissions();" %>' value="save" />
					</aui:button-row>
				</div>
			</c:when>
		</c:choose>
	</aui:form>
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveGroupPermissions',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "group_permissions";
			document.<portlet:namespace />fm.<portlet:namespace />permissionsRedirect.value = "<%= portletURL.toString() %>";
			document.<portlet:namespace />fm.<portlet:namespace />groupIdActionIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />current_actions);
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/portlet_configuration/edit_permissions" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />saveGuestPermissions',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "guest_permissions";
			document.<portlet:namespace />fm.<portlet:namespace />permissionsRedirect.value = "<%= portletURL.toString() %>";
			document.<portlet:namespace />fm.<portlet:namespace />guestActionIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />current_actions);
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/portlet_configuration/edit_permissions" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />saveOrganizationPermissions',
		function(organizationIdsPos, organizationIdsPosValue) {

			<%
			PortletURL saveOrganizationPermissionsRedirectURL = PortletURLUtil.clone(portletURL, renderResponse);

			new OrganizationSearch(renderRequest, saveOrganizationPermissionsRedirectURL);
			%>

			var organizationIds = document.<portlet:namespace />fm.<portlet:namespace />organizationIds.value;

			if (organizationIdsPos == -1) {
				organizationIds = "";
				organizationIdsPos = 0;
			}

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "organization_permissions";
			document.<portlet:namespace />fm.<portlet:namespace />permissionsRedirect.value = "<%= saveOrganizationPermissionsRedirectURL.toString() %>&<portlet:namespace />cur=<%= cur %>&<portlet:namespace />organizationIds=" + organizationIds + "&<portlet:namespace />organizationIdsPos=" + organizationIdsPos;
			document.<portlet:namespace />fm.<portlet:namespace />organizationIds.value = organizationIds;
			document.<portlet:namespace />fm.<portlet:namespace />organizationIdsPosValue.value = organizationIdsPosValue;
			document.<portlet:namespace />fm.<portlet:namespace />organizationIdActionIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />current_actions);
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/portlet_configuration/edit_permissions" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />saveRolePermissions',
		function(roleIdsPos, roleIdsPosValue) {

			<%
			PortletURL saveRolePermissionsRedirectURL = PortletURLUtil.clone(portletURL, renderResponse);

			new RoleSearch(renderRequest, saveRolePermissionsRedirectURL);
			%>

			var roleIds = document.<portlet:namespace />fm.<portlet:namespace />roleIds.value;

			if (roleIdsPos == -1) {
				roleIds = "";
				roleIdsPos = 0;
			}

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "role_permissions";
			document.<portlet:namespace />fm.<portlet:namespace />permissionsRedirect.value = "<%= saveRolePermissionsRedirectURL.toString() %>&<portlet:namespace />cur=<%= cur %>&<portlet:namespace />roleIds=" + roleIds + "&<portlet:namespace />roleIdsPos=" + roleIdsPos;
			document.<portlet:namespace />fm.<portlet:namespace />roleIds.value = roleIds;
			document.<portlet:namespace />fm.<portlet:namespace />roleIdsPosValue.value = roleIdsPosValue;
			document.<portlet:namespace />fm.<portlet:namespace />roleIdActionIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />current_actions);
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/portlet_configuration/edit_permissions" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />saveUserGroupPermissions',
		function(userGroupIdsPos, userGroupIdsPosValue) {

			<%
			PortletURL saveUserGroupPermissionsRedirectURL = PortletURLUtil.clone(portletURL, renderResponse);

			new UserGroupSearch(renderRequest, saveUserGroupPermissionsRedirectURL);
			%>

			var userGroupIds = document.<portlet:namespace />fm.<portlet:namespace />userGroupIds.value;

			if (userGroupIdsPos == -1) {
				userGroupIds = "";
				userGroupIdsPos = 0;
			}

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_group_permissions";
			document.<portlet:namespace />fm.<portlet:namespace />permissionsRedirect.value = "<%= saveUserGroupPermissionsRedirectURL.toString() %>&<portlet:namespace />cur=<%= cur %>&<portlet:namespace />userGroupIds=" + userGroupIds + "&<portlet:namespace />userGroupIdsPos=" + userGroupIdsPos;
			document.<portlet:namespace />fm.<portlet:namespace />userGroupIds.value = userGroupIds;
			document.<portlet:namespace />fm.<portlet:namespace />userGroupIdsPosValue.value = userGroupIdsPosValue;
			document.<portlet:namespace />fm.<portlet:namespace />userGroupIdActionIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />current_actions);
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/portlet_configuration/edit_permissions" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />saveUserPermissions',
		function(userIdsPos, userIdsPosValue) {

			<%
			PortletURL saveUserPermissionsRedirectURL = PortletURLUtil.clone(portletURL, renderResponse);

			new UserSearch(renderRequest, saveUserPermissionsRedirectURL);
			%>

			var userIds = document.<portlet:namespace />fm.<portlet:namespace />userIds.value;

			if (userIdsPos == -1) {
				userIds = "";
				userIdsPos = 0;
			}

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_permissions";
			document.<portlet:namespace />fm.<portlet:namespace />permissionsRedirect.value = "<%= saveUserPermissionsRedirectURL.toString() %>&<portlet:namespace />cur=<%= cur %>&<portlet:namespace />userIds=" + userIds + "&<portlet:namespace />userIdsPos=" + userIdsPos;
			document.<portlet:namespace />fm.<portlet:namespace />userIds.value = userIds;
			document.<portlet:namespace />fm.<portlet:namespace />userIdsPosValue.value = userIdsPosValue;
			document.<portlet:namespace />fm.<portlet:namespace />userIdActionIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />current_actions);
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/portlet_configuration/edit_permissions" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateOrganizationPermissions',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />organizationIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateRolePermissions',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />roleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateUserGroupPermissions',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />userGroupIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateUserPermissions',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />userIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>