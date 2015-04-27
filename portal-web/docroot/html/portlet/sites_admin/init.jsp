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

<%@ page import="com.liferay.portal.DuplicateGroupException" %><%@
page import="com.liferay.portal.GroupInheritContentException" %><%@
page import="com.liferay.portal.GroupKeyException" %><%@
page import="com.liferay.portal.GroupParentException" %><%@
page import="com.liferay.portal.LayoutSetVirtualHostException" %><%@
page import="com.liferay.portal.NoSuchGroupException" %><%@
page import="com.liferay.portal.NoSuchLayoutSetException" %><%@
page import="com.liferay.portal.PendingBackgroundTaskException" %><%@
page import="com.liferay.portal.RemoteExportException" %><%@
page import="com.liferay.portal.RemoteOptionsException" %><%@
page import="com.liferay.portal.RequiredGroupException" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants" %><%@
page import="com.liferay.portal.kernel.staging.StagingConstants" %><%@
page import="com.liferay.portal.lar.LayoutExporter" %><%@
page import="com.liferay.portal.liveusers.LiveUsers" %><%@
page import="com.liferay.portal.security.auth.RemoteAuthException" %><%@
page import="com.liferay.portal.util.CustomJspRegistryUtil" %><%@
page import="com.liferay.portal.util.RobotsUtil" %><%@
page import="com.liferay.portlet.backgroundtask.util.comparator.BackgroundTaskCreateDateComparator" %><%@
page import="com.liferay.portlet.ratings.display.context.CompanyPortletRatingsDefinitionDisplayContext" %><%@
page import="com.liferay.portlet.ratings.display.context.GroupPortletRatingsDefinitionDisplayContext" %><%@
page import="com.liferay.portlet.rolesadmin.search.GroupRoleChecker" %><%@
page import="com.liferay.portlet.sitesadmin.search.SiteChecker" %><%@
page import="com.liferay.portlet.sitesadmin.search.TeamDisplayTerms" %><%@
page import="com.liferay.portlet.sitesadmin.search.TeamSearch" %>

<%
boolean filterManageableGroups = true;

if (permissionChecker.isCompanyAdmin()) {
	filterManageableGroups = false;
}

if (GroupPermissionUtil.contains(permissionChecker, ActionKeys.VIEW)) {
	filterManageableGroups = false;
}

long[] classNameIds = new long[] {PortalUtil.getClassNameId(Company.class), PortalUtil.getClassNameId(Group.class), PortalUtil.getClassNameId(Organization.class)};
%>

<%@ include file="/html/portlet/sites_admin/init-ext.jsp" %>