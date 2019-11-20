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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.announcements.constants.AnnouncementsPortletKeys" %><%@
page import="com.liferay.announcements.kernel.exception.EntryContentException" %><%@
page import="com.liferay.announcements.kernel.exception.EntryDisplayDateException" %><%@
page import="com.liferay.announcements.kernel.exception.EntryExpirationDateException" %><%@
page import="com.liferay.announcements.kernel.exception.EntryTitleException" %><%@
page import="com.liferay.announcements.kernel.exception.EntryURLException" %><%@
page import="com.liferay.announcements.kernel.exception.NoSuchEntryException" %><%@
page import="com.liferay.announcements.kernel.exception.NoSuchFlagException" %><%@
page import="com.liferay.announcements.kernel.model.AnnouncementsEntry" %><%@
page import="com.liferay.announcements.kernel.model.AnnouncementsEntryConstants" %><%@
page import="com.liferay.announcements.kernel.model.AnnouncementsFlagConstants" %><%@
page import="com.liferay.announcements.kernel.service.AnnouncementsEntryLocalServiceUtil" %><%@
page import="com.liferay.announcements.kernel.service.AnnouncementsFlagLocalServiceUtil" %><%@
page import="com.liferay.announcements.web.internal.configuration.AnnouncementsPortletInstanceConfiguration" %><%@
page import="com.liferay.announcements.web.internal.constants.AnnouncementsWebKeys" %><%@
page import="com.liferay.announcements.web.internal.display.context.AnnouncementsAdminViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.announcements.web.internal.display.context.AnnouncementsDisplayContext" %><%@
page import="com.liferay.announcements.web.internal.display.context.DefaultAnnouncementsDisplayContext" %><%@
page import="com.liferay.announcements.web.internal.display.context.util.AnnouncementsRequestHelper" %><%@
page import="com.liferay.announcements.web.internal.search.AnnouncementsEntryChecker" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPNavigationItemList" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.configuration.metatype.util.ParameterMapUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.Organization" %><%@
page import="com.liferay.portal.kernel.model.Role" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.model.UserGroup" %><%@
page import="com.liferay.portal.kernel.security.auth.PrincipalException" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.OrganizationLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserGroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.PortalPermissionUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.PortletPermissionUtil" %><%@
page import="com.liferay.portal.kernel.servlet.HttpHeaders" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Time" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portlet.announcements.service.permission.AnnouncementsEntryPermission" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
AnnouncementsRequestHelper announcementsRequestHelper = new AnnouncementsRequestHelper(request);

AnnouncementsDisplayContext announcementsDisplayContext = new DefaultAnnouncementsDisplayContext(announcementsRequestHelper);
%>

<%@ include file="/init-ext.jsp" %>