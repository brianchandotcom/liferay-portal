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

<%@ page import="com.liferay.portal.LARFileSizeException" %><%@
page import="com.liferay.portal.NoSuchPortletItemException" %><%@
page import="com.liferay.portal.NoSuchResourceException" %><%@
page import="com.liferay.portal.PortletItemNameException" %><%@
page import="com.liferay.portal.ResourcePrimKeyException" %><%@
page import="com.liferay.portal.kernel.portlet.PortletModeFactory" %><%@
page import="com.liferay.portal.kernel.settings.ArchivedSettings" %><%@
page import="com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil" %><%@
page import="com.liferay.portal.service.permission.TeamPermissionUtil" %><%@
page import="com.liferay.portal.util.ResourcePermissionUtil" %><%@
page import="com.liferay.portlet.PortletQNameUtil" %><%@
page import="com.liferay.portlet.portletconfiguration.action.ActionUtil" %><%@
page import="com.liferay.portlet.portletconfiguration.util.PublicRenderParameterConfiguration" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);
%>

<%@ include file="/html/portlet/portlet_configuration/init-ext.jsp" %>