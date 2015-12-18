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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.application.list.PanelApp" %><%@
page import="com.liferay.application.list.PanelAppRegistry" %><%@
page import="com.liferay.application.list.PanelCategory" %><%@
page import="com.liferay.application.list.PanelCategoryRegistry" %><%@
page import="com.liferay.application.list.constants.ApplicationListWebKeys" %><%@
page import="com.liferay.application.list.constants.PanelCategoryKeys" %><%@
page import="com.liferay.application.list.display.context.logic.PanelCategoryHelper" %><%@
page import="com.liferay.portal.DuplicateRoleException" %><%@
page import="com.liferay.portal.NoSuchRoleException" %><%@
page import="com.liferay.portal.RequiredRoleException" %><%@
page import="com.liferay.portal.RoleAssignmentException" %><%@
page import="com.liferay.portal.RoleNameException" %><%@
page import="com.liferay.portal.RolePermissionsException" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.SystemException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandler" %><%@
page import="com.liferay.portal.kernel.template.comparator.TemplateHandlerComparator" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.model.Company" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.model.Organization" %><%@
page import="com.liferay.portal.model.OrganizationConstants" %><%@
page import="com.liferay.portal.model.Permission" %><%@
page import="com.liferay.portal.model.PermissionDisplay" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.model.PortletCategory" %><%@
page import="com.liferay.portal.model.PortletCategoryConstants" %><%@
page import="com.liferay.portal.model.Resource" %><%@
page import="com.liferay.portal.model.ResourceConstants" %><%@
page import="com.liferay.portal.model.Role" %><%@
page import="com.liferay.portal.model.RoleConstants" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.model.UserGroupRole" %><%@
page import="com.liferay.portal.model.UserPersonalSite" %><%@
page import="com.liferay.portal.model.impl.ResourceImpl" %><%@
page import="com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.membershippolicy.RoleMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.membershippolicy.SiteMembershipPolicyUtil" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.security.permission.PermissionConverterUtil" %><%@
page import="com.liferay.portal.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.security.permission.RolePermissions" %><%@
page import="com.liferay.portal.security.permission.comparator.ActionComparator" %><%@
page import="com.liferay.portal.security.permission.comparator.ModelResourceWeightComparator" %><%@
page import="com.liferay.portal.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.service.GroupServiceUtil" %><%@
page import="com.liferay.portal.service.OrganizationLocalServiceUtil" %><%@
page import="com.liferay.portal.service.OrganizationServiceUtil" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.service.ResourceBlockLocalServiceUtil" %><%@
page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil" %><%@
page import="com.liferay.portal.service.ResourceTypePermissionLocalServiceUtil" %><%@
page import="com.liferay.portal.service.RoleLocalServiceUtil" %><%@
page import="com.liferay.portal.service.RoleServiceUtil" %><%@
page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil" %><%@
page import="com.liferay.portal.service.permission.PortalPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.RolePermissionUtil" %><%@
page import="com.liferay.portal.theme.ThemeDisplay" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PortletKeys" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portal.util.WebAppPool" %><%@
page import="com.liferay.portal.util.comparator.PortletTitleComparator" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.display.template.PortletDisplayTemplateUtil" %><%@
page import="com.liferay.portlet.rolesadmin.search.GroupRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.OrganizationRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.ResourceActionRowChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.RoleSearch" %><%@
page import="com.liferay.portlet.rolesadmin.search.RoleSearchTerms" %><%@
page import="com.liferay.portlet.rolesadmin.search.UserGroupRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.search.UserRoleChecker" %><%@
page import="com.liferay.portlet.rolesadmin.util.RolesAdminUtil" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupDisplayTerms" %><%@
page import="com.liferay.portlet.usergroupsadmin.search.UserGroupSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.OrganizationSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.search.UserSearch" %><%@
page import="com.liferay.portlet.usersadmin.search.UserSearchTerms" %><%@
page import="com.liferay.portlet.usersadmin.util.UsersAdmin" %><%@
page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil" %><%@
page import="com.liferay.roles.admin.web.search.RoleChecker" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Collections" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.ResourceURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);
String currentURL = currentURLObj.toString();

boolean filterManageableGroups = true;
boolean filterManageableOrganizations = true;
boolean filterManageableRoles = true;

if (permissionChecker.isCompanyAdmin()) {
	filterManageableGroups = false;
	filterManageableOrganizations = false;
}
%>

<%@ include file="/init-ext.jsp" %>

<%!
private String _getActionLabel(HttpServletRequest request, ThemeDisplay themeDisplay, String resourceName, String actionId) throws SystemException {
	String actionLabel = null;

	if (actionId.equals(ActionKeys.ACCESS_IN_CONTROL_PANEL)) {
		PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), resourceName);

		if (panelCategoryHelper.containsPortlet(portlet.getPortletId(), PanelCategoryKeys.SITE_ADMINISTRATION)) {
			actionLabel = LanguageUtil.get(request, "access-in-site-administration");
		}
		else if (panelCategoryHelper.containsPortlet(portlet.getPortletId(), PanelCategoryKeys.USER)) {
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

private boolean _isShowScope(HttpServletRequest request, Role role, String curModelResource, String curPortletResource) throws SystemException {
	boolean showScope = true;

	if (curPortletResource.equals(PortletKeys.PORTAL)) {
		showScope = false;
	}
	else if (role.getType() != RoleConstants.TYPE_REGULAR) {
		showScope = false;
	}
	else if (Validator.isNotNull(curPortletResource)) {
		Portlet curPortlet = PortletLocalServiceUtil.getPortletById(role.getCompanyId(), curPortletResource);

		PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);

		if ((curPortlet != null) && panelCategoryHelper.containsPortlet(curPortlet.getPortletId(), PanelCategoryKeys.SITE_ADMINISTRATION)) {
			showScope = false;
		}
	}

	if (Validator.isNotNull(curModelResource) && curModelResource.equals(Group.class.getName())) {
		showScope = true;
	}

	return showScope;
}
%>