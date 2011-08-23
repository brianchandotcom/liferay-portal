<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/common/init.jsp" %>

<%@ page import="com.liferay.portal.DuplicateUserEmailAddressException" %>
<%@ page import="com.liferay.portal.LayoutFriendlyURLException" %>
<%@ page import="com.liferay.portal.LayoutPermissionException" %>
<%@ page import="com.liferay.portal.NoSuchResourceException" %>
<%@ page import="com.liferay.portal.PortletActiveException" %>
<%@ page import="com.liferay.portal.RequiredLayoutException" %>
<%@ page import="com.liferay.portal.RequiredRoleException" %>
<%@ page import="com.liferay.portal.ReservedUserEmailAddressException" %>
<%@ page import="com.liferay.portal.UserActiveException" %>
<%@ page import="com.liferay.portal.UserEmailAddressException" %>
<%@ page import="com.liferay.portal.UserPasswordException" %>
<%@ page import="com.liferay.portal.UserReminderQueryException" %>
<%@ page import="com.liferay.portal.kernel.templateparser.TransformException" %>
<%@ page import="com.liferay.portal.service.permission.GroupPermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.OrganizationPermissionUtil" %>
<%@ page import="com.liferay.portal.struts.PortletRequestProcessor" %>
<%@ page import="com.liferay.portal.upload.LiferayFileUpload" %>
<%@ page import="com.liferay.portal.util.LayoutLister" %>
<%@ page import="com.liferay.portal.util.LayoutView" %>
<%@ page import="com.liferay.portlet.journalcontent.util.JournalContentUtil" %>
<%@ page import="com.liferay.portlet.layoutconfiguration.util.RuntimePortletUtil" %>
<%@ page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil" %>

<%@ page import="org.apache.struts.action.ActionMapping" %>
<%@ page import="org.apache.struts.taglib.tiles.ComponentConstants" %>
<%@ page import="org.apache.struts.tiles.ComponentDefinition" %>
<%@ page import="org.apache.struts.tiles.TilesUtil" %>