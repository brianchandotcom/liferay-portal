<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.service.permission.OrganizationPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.RolePermissionUtil" %><%@
page import="com.liferay.portal.service.permission.UserGroupPermissionUtil" %><%@
page import="com.liferay.portlet.announcements.EntryContentException" %><%@
page import="com.liferay.portlet.announcements.EntryDisplayDateException" %><%@
page import="com.liferay.portlet.announcements.EntryExpirationDateException" %><%@
page import="com.liferay.portlet.announcements.EntryTitleException" %><%@
page import="com.liferay.portlet.announcements.EntryURLException" %><%@
page import="com.liferay.portlet.announcements.NoSuchEntryException" %><%@
page import="com.liferay.portlet.announcements.NoSuchFlagException" %><%@
page import="com.liferay.portlet.announcements.model.AnnouncementsEntry" %><%@
page import="com.liferay.portlet.announcements.model.AnnouncementsEntryConstants" %><%@
page import="com.liferay.portlet.announcements.model.AnnouncementsFlagConstants" %><%@
page import="com.liferay.portlet.announcements.service.AnnouncementsEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.announcements.service.AnnouncementsFlagLocalServiceUtil" %><%@
page import="com.liferay.portlet.announcements.service.permission.AnnouncementsEntryPermission" %><%@
page import="com.liferay.portlet.announcements.util.AnnouncementsUtil" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

int pageDelta = GetterUtil.getInteger(preferences.getValue("pageDelta", String.valueOf(SearchContainer.DEFAULT_DELTA)));

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/announcements/init-ext.jsp" %>