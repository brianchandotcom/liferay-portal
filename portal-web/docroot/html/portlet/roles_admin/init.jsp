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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.DuplicateRoleException" %><%@
page import="com.liferay.portal.RequiredRoleException" %><%@
page import="com.liferay.portal.RoleAssignmentException" %><%@
page import="com.liferay.portal.RoleNameException" %><%@
page import="com.liferay.portal.RolePermissionsException" %><%@
page import="com.liferay.portal.kernel.template.comparator.TemplateHandlerComparator" %><%@
page import="com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.membershippolicy.RoleMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.permission.PermissionConverterUtil" %><%@
page import="com.liferay.portal.security.permission.comparator.ActionComparator" %><%@
page import="com.liferay.portal.security.permission.comparator.ModelResourceWeightComparator" %><%@
page import="com.liferay.portlet.rolesadmin.search.ResourceActionRowChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.RoleSearch" %><%@
page import="com.liferay.portlet.rolesadmin.search.RoleSearchTerms" %><%@
page import="com.liferay.portlet.sites.action.ActionUtil" %>

<%
boolean filterManageableGroups = true;
boolean filterManageableOrganizations = true;
boolean filterManageableRoles = true;

if (permissionChecker.isCompanyAdmin()) {
	filterManageableGroups = false;
	filterManageableOrganizations = false;
}
%>

<%@ include file="/html/portlet/roles_admin/init-ext.jsp" %>

<%!
private String _getActionLabel(HttpServletRequest request, ThemeDisplay themeDisplay, String resourceName, String actionId) throws SystemException {
	String actionLabel = null;

	if (actionId.equals(ActionKeys.ACCESS_IN_CONTROL_PANEL)) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), resourceName);

		String controlPanelCategory = portlet.getControlPanelEntryCategory();

		if (Validator.isNull(controlPanelCategory)) {
		}
		else if (controlPanelCategory.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION)) {
			actionLabel = LanguageUtil.get(request, "access-in-site-administration");
		}
		else if (controlPanelCategory.equals(PortletCategoryKeys.MY)) {
			actionLabel = LanguageUtil.get(request, "access-in-my-account");
		}
	}

	if (actionLabel == null) {
		actionLabel = ResourceActionsUtil.getAction(request, actionId);
	}

	return actionLabel;
}

private StringBundler _getResourceHtmlId(String resource) {
	StringBundler sb = new StringBundler(2);

	sb.append("resource_");
	sb.append(resource.replace('.', '_'));

	return sb;
}

private boolean _isShowScope(Role role, String curModelResource, String curPortletResource) throws SystemException {
	boolean showScope = true;

	Portlet curPortlet = null;
	String curPortletControlPanelEntryCategory = StringPool.BLANK;

	if (Validator.isNotNull(curPortletResource)) {
		curPortlet = PortletLocalServiceUtil.getPortletById(role.getCompanyId(), curPortletResource);
		curPortletControlPanelEntryCategory = curPortlet.getControlPanelEntryCategory();
	}

	if (curPortletResource.equals(PortletKeys.PORTAL)) {
		showScope = false;
	}
	else if (role.getType() != RoleConstants.TYPE_REGULAR) {
		showScope = false;
	}
	else if (Validator.isNotNull(curPortletControlPanelEntryCategory) && !curPortletControlPanelEntryCategory.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION)) {
		showScope = false;
	}

	if (Validator.isNotNull(curModelResource) && curModelResource.equals(Group.class.getName())) {
		showScope = true;
	}

	return showScope;
}
%>