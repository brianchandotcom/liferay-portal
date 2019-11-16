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

<div class="sheet">
	<div class="sheet-header">
		<h3 class="sheet-title"><liferay-ui:message key="summary" /></h3>
	</div>

	<div class="sheet-section">

		<%
		String redirect = ParamUtil.getString(request, "redirect");

		String backURL = ParamUtil.getString(request, "backURL", redirect);

		Role role = (Role)request.getAttribute("edit_role_permissions.jsp-role");

		PortletURL permissionsAllURL = liferayPortletResponse.createRenderURL();

		permissionsAllURL.setParameter("mvcPath", "/edit_role_permissions.jsp");
		permissionsAllURL.setParameter(Constants.CMD, Constants.VIEW);
		permissionsAllURL.setParameter("tabs1", "define-permissions");
		permissionsAllURL.setParameter("tabs2", "roles");
		permissionsAllURL.setParameter("backURL", backURL);
		permissionsAllURL.setParameter("roleId", String.valueOf(role.getRoleId()));

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("permissions");

		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			headerNames.add("sites");
		}

		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 50, permissionsAllURL, headerNames, "this-role-does-not-have-any-permissions");

		List<Permission> permissions = PermissionConverterUtil.convertPermissions(role);

		List<PermissionDisplay> permissionDisplays = new ArrayList<PermissionDisplay>(permissions.size());

		for (int i = 0; i < permissions.size(); i++) {
			Permission permission = permissions.get(i);

			Resource resource = new ResourceImpl();

			resource.setCompanyId(themeDisplay.getCompanyId());
			resource.setName(permission.getName());
			resource.setScope(permission.getScope());
			resource.setPrimKey(permission.getPrimKey());

			String curPortletName = null;
			String curPortletLabel = null;
			String curModelName = null;
			String curModelLabel = null;

			String actionId = permission.getActionId();

			String actionLabel = _getActionLabel(request, themeDisplay, resource.getName(), actionId);

			if (PortletLocalServiceUtil.hasPortlet(company.getCompanyId(), resource.getName())) {
				curPortletName = resource.getName();
				curModelName = StringPool.BLANK;
				curModelLabel = StringPool.BLANK;
			}
			else {
				curModelName = resource.getName();
				curModelLabel = ResourceActionsUtil.getModelResource(request, curModelName);

				List portletResources = ResourceActionsUtil.getModelPortletResources(curModelName);

				if (!portletResources.isEmpty()) {
					curPortletName = (String)portletResources.get(0);
				}
			}

			if (curPortletName == null) {
				continue;
			}

			Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), curPortletName);

			curPortletLabel = PortalUtil.getPortletLongTitle(portlet, application, locale);

			PermissionDisplay permissionDisplay = new PermissionDisplay(permission, resource, curPortletName, curPortletLabel, curModelName, curModelLabel, actionId, actionLabel);

			if (!permissionDisplays.contains(permissionDisplay)) {
				permissionDisplays.add(permissionDisplay);
			}
		}

		permissionDisplays = ListUtil.sort(permissionDisplays);

		int total = permissionDisplays.size();

		searchContainer.setTotal(total);

		List results = ListUtil.subList(permissionDisplays, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			PermissionDisplay permissionDisplay = (PermissionDisplay)results.get(i);

			Permission permission = permissionDisplay.getPermission();

			Resource resource = permissionDisplay.getResource();

			String curResource = resource.getName();

			String curPortletName = permissionDisplay.getPortletName();
			String curPortletLabel = permissionDisplay.getPortletLabel();
			String curModelLabel = permissionDisplay.getModelLabel();
			String actionId = permissionDisplay.getActionId();
			String actionLabel = permissionDisplay.getActionLabel();

			ResultRow row = new ResultRow(new Object[] {permission, role}, actionId, i);

			List groups = Collections.emptyList();

			int scope;

			if (role.getType() == RoleConstants.TYPE_REGULAR) {
				LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

				RolePermissions rolePermissions = new RolePermissions(curResource, ResourceConstants.SCOPE_GROUP, actionId, role.getRoleId());

				groupParams.put("rolePermissions", rolePermissions);

				groups = GroupLocalServiceUtil.search(company.getCompanyId(), new long[] {PortalUtil.getClassNameId(Company.class), PortalUtil.getClassNameId(Group.class), PortalUtil.getClassNameId(Organization.class), PortalUtil.getClassNameId(UserPersonalSite.class)}, null, null, groupParams, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				if (groups.isEmpty()) {
					scope = ResourceConstants.SCOPE_COMPANY;
				}
				else {
					scope = ResourceConstants.SCOPE_GROUP;
				}
			}
			else {
				scope = ResourceConstants.SCOPE_GROUP_TEMPLATE;
			}

			boolean selected = ResourcePermissionLocalServiceUtil.hasScopeResourcePermission(company.getCompanyId(), curResource, scope, role.getRoleId(), actionId);

			if (!selected) {
				continue;
			}

			ResourceURL editPermissionsResourceURL = liferayPortletResponse.createResourceURL();

			editPermissionsResourceURL.setParameter("mvcPath", "/view_resources.jsp");
			editPermissionsResourceURL.setParameter(Constants.CMD, Constants.EDIT);
			editPermissionsResourceURL.setParameter("tabs2", "roles");
			editPermissionsResourceURL.setParameter("roleId", String.valueOf(role.getRoleId()));
			editPermissionsResourceURL.setParameter("redirect", permissionsAllURL.toString());
			editPermissionsResourceURL.setParameter("portletResource", curPortletName);

			PortletURL editPermissionsURL = liferayPortletResponse.createRenderURL();

			editPermissionsURL.setParameter("mvcPath", "/edit_role_permissions.jsp");
			editPermissionsURL.setParameter(Constants.CMD, Constants.EDIT);
			editPermissionsURL.setParameter("tabs1", "define-permissions");
			editPermissionsURL.setParameter("tabs2", "roles");
			editPermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));
			editPermissionsURL.setParameter("redirect", permissionsAllURL.toString());
			editPermissionsURL.setParameter("portletResource", curPortletName);

			StringBundler sb = new StringBundler(17);

			sb.append("<a class=\"permission-navigation-link\" data-resource-href=\"");
			sb.append(editPermissionsResourceURL);
			sb.append(StringPool.POUND);
			sb.append(_getResourceHtmlId(curResource));
			sb.append("\" href=\"");
			sb.append(editPermissionsURL);
			sb.append(StringPool.POUND);
			sb.append(_getResourceHtmlId(curResource));
			sb.append("\">");
			sb.append(curPortletLabel);

			if (Validator.isNotNull(curModelLabel)) {
				sb.append(StringPool.SPACE);
				sb.append(StringPool.GREATER_THAN);
				sb.append(StringPool.SPACE);
				sb.append(curModelLabel);
			}

			sb.append("</a>: <strong>");
			sb.append(actionLabel);
			sb.append("</strong>");

			row.addText(sb.toString());

			if (scope == ResourceConstants.SCOPE_COMPANY) {
				row.addText(LanguageUtil.get(request, _isShowScope(request, role, curResource, curPortletName) ? "all-sites" : StringPool.BLANK));
			}
			else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
			}
			else if (scope == ResourceConstants.SCOPE_GROUP) {
				sb = new StringBundler(groups.size() * 3 - 2);

				for (int j = 0; j < groups.size(); j++) {
					Group group = (Group)groups.get(j);

					sb.append(HtmlUtil.escape(group.getDescriptiveName(locale)));

					if (j < (groups.size() - 1)) {
						sb.append(StringPool.COMMA);
						sb.append(StringPool.SPACE);
					}
				}

				row.addText(sb.toString());
			}

			// Action

			row.addJSP("/permission_action.jsp", "entry-action", application, request, response);

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator
			searchContainer="<%= searchContainer %>"
			searchResultCssClass="show-quick-actions-on-hover table table-autofit"
		/>
	</div>
</div>