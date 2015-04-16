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

<%@ page import="com.liferay.portal.DuplicateTeamException" %><%@
page import="com.liferay.portal.TeamNameException" %><%@
page import="com.liferay.portal.service.permission.TeamPermissionUtil" %><%@
page import="com.liferay.portlet.sites.action.ActionUtil" %><%@
page import="com.liferay.portlet.sitesadmin.search.TeamDisplayTerms" %><%@
page import="com.liferay.portlet.sitesadmin.search.TeamSearch" %><%@
page import="com.liferay.portlet.sitesadmin.search.UserGroupTeamChecker" %><%@
page import="com.liferay.portlet.sitesadmin.search.UserTeamChecker" %>

<%@ include file="/html/portlet/site_teams/init-ext.jsp" %>