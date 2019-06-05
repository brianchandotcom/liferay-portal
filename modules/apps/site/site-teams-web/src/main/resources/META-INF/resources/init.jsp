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
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.DuplicateTeamException" %><%@
page import="com.liferay.portal.kernel.exception.TeamNameException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.Team" %><%@
page import="com.liferay.portal.kernel.model.UserGroup" %><%@
page import="com.liferay.portal.kernel.model.role.RoleConstants" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.TeamLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.TeamPermissionUtil" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.site.teams.web.internal.constants.SiteTeamsWebKeys" %><%@
page import="com.liferay.site.teams.web.internal.display.context.EditSiteTeamAssignmentsDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.EditSiteTeamAssignmentsUserGroupsDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.EditSiteTeamAssignmentsUserGroupsManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.EditSiteTeamAssignmentsUsersDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.EditSiteTeamAssignmentsUsersManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.SelectTeamDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.SelectTeamManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.SelectUserGroupsDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.SelectUserGroupsManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.SelectUsersDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.SelectUsersManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.SiteTeamsDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.display.context.SiteTeamsManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.teams.web.internal.servlet.taglib.clay.SelectUserUserCard" %><%@
page import="com.liferay.site.teams.web.internal.servlet.taglib.clay.UserUserCard" %><%@
page import="com.liferay.site.teams.web.internal.servlet.taglib.util.UserActionDropdownItemsProvider" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%@ include file="/init-ext.jsp" %>