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

Role role = (Role)request.getAttribute("edit_role_permissions.jsp-role");

PortletURL permissionsAllURL = PortletURLBuilder.createRenderURL(
	liferayPortletResponse
).setMVCPath(
	"/edit_role_permissions.jsp"
).setCMD(
	Constants.VIEW
).setBackURL(
	backURL
).setTabs1(
	roleDisplayContext.getEditRolePermissionsTabs1()
).setTabs2(
	"roles"
).setParameter(
	"accountRoleGroupScope", roleDisplayContext.isAccountRoleGroupScope()
).setParameter(
	"roleId", role.getRoleId()
).buildPortletURL();

List<String> headerNames = new ArrayList<String>();

headerNames.add("permissions");

if (roleDisplayContext.isAllowGroupScope()) {
	headerNames.add("sites-and-asset-libraries");
}

headerNames.add(StringPool.BLANK);

SearchContainer<PermissionDisplay> searchContainer = new SearchContainer(liferayPortletRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 50, permissionsAllURL, headerNames, "this-role-does-not-have-any-permissions");

List<Permission> permissions = PermissionConverterUtil.convertPermissions(role);

List<PermissionDisplay> permissionDisplays = new ArrayList<PermissionDisplay>(permissions.size());

for (int i = 0; i < permissions.size(); i++) {
	Permission permission = permissions.get(i);

	if (!roleDisplayContext.isValidPermission(role, permission)) {
		continue;
	}

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

		List<String> portletResources = ResourceActionsUtil.getModelPortletResources(curModelName);

		if (!portletResources.isEmpty()) {
			curPortletName = portletResources.get(0);
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

searchContainer.setTotal(permissionDisplays.size());

List<PermissionDisplay> results = ListUtil.subList(permissionDisplays, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

List<com.liferay.portal.kernel.dao.search.ResultRow> resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	PermissionDisplay permissionDisplay = results.get(i);

	Permission permission = permissionDisplay.getPermission();

	Resource resource = permissionDisplay.getResource();

	String curResource = resource.getName();

	String curPortletName = permissionDisplay.getPortletName();
	String curPortletLabel = permissionDisplay.getPortletLabel();
	String curModelLabel = permissionDisplay.getModelLabel();

	String actionId = permissionDisplay.getActionId();

	ResultRow row = new ResultRow(new Object[] {permission, role}, actionId, i);

	List<Group> groups = Collections.emptyList();

	int scope;

	if (roleDisplayContext.isAllowGroupScope()) {
		RolePermissions rolePermissions = new RolePermissions(curResource, ResourceConstants.SCOPE_GROUP, actionId, role.getRoleId());

		groups = GroupLocalServiceUtil.search(
			company.getCompanyId(), GroupTypeContributorUtil.getClassNameIds(), null, null,
			LinkedHashMapBuilder.<String, Object>put(
				"rolePermissions", rolePermissions
			).build(),
			true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

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
	editPermissionsResourceURL.setParameter("accountRoleGroupScope", String.valueOf(roleDisplayContext.isAccountRoleGroupScope()));

	PortletURL editPermissionsURL = PortletURLBuilder.createRenderURL(
		liferayPortletResponse
	).setMVCPath(
		"/edit_role_permissions.jsp"
	).setCMD(
		Constants.EDIT
	).setRedirect(
		permissionsAllURL
	).setPortletResource(
		curPortletName
	).setTabs1(
		roleDisplayContext.getEditRolePermissionsTabs1()
	).setTabs2(
		"roles"
	).setParameter(
		"accountRoleGroupScope", roleDisplayContext.isAccountRoleGroupScope()
	).setParameter(
		"roleId", role.getRoleId()
	).buildPortletURL();

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
	sb.append(permissionDisplay.getActionLabel());
	sb.append("</strong>");

	row.addText(sb.toString());

	if (scope == ResourceConstants.SCOPE_COMPANY) {
		row.addText(LanguageUtil.get(request, _isShowScope(request, role, curResource, curPortletName, roleDisplayContext) ? "all-sites-and-asset-libraries" : StringPool.BLANK));
	}
	else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
	}
	else if (scope == ResourceConstants.SCOPE_GROUP) {
		sb = new StringBundler((groups.size() * 3) - 2);

		for (int j = 0; j < groups.size(); j++) {
			Group group = groups.get(j);

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

<clay:sheet>
	<clay:sheet-header>
		<h3 class="sheet-title"><liferay-ui:message key="summary" /></h3>
	</clay:sheet-header>

	<clay:sheet-section>
		<liferay-ui:search-iterator
			paginate="<%= false %>"
			searchContainer="<%= searchContainer %>"
			searchResultCssClass="show-quick-actions-on-hover table table-autofit"
		/>
	</clay:sheet-section>

	<c:if test="<%= searchContainer.getTotal() > 0 %>">
		<clay:sheet-footer>
			<clay:content-row>
				<clay:content-col
					cssClass="taglib-search-iterator-page-iterator-bottom"
					expand="<%= true %>"
				>
					<liferay-ui:search-paginator
						markupView="lexicon"
						searchContainer="<%= searchContainer %>"
					/>
				</clay:content-col>
			</clay:content-row>
		</clay:sheet-footer>
	</c:if>
</clay:sheet>