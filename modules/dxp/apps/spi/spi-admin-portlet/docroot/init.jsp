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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.petra.content.ContentUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.servlet.ServletContextPool" %><%@
page import="com.liferay.portal.kernel.util.CharPool" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePairComparator" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropertiesParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Tuple" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.resiliency.spi.exception.DuplicateSPIDefinitionConnectorException" %><%@
page import="com.liferay.portal.resiliency.spi.exception.DuplicateSPIDefinitionException" %><%@
page import="com.liferay.portal.resiliency.spi.exception.InvalidDatabaseConfigurationException" %><%@
page import="com.liferay.portal.resiliency.spi.exception.InvalidSPIDefinitionConnectorException" %><%@
page import="com.liferay.portal.resiliency.spi.exception.SPIDefinitionActiveException" %><%@
page import="com.liferay.portal.resiliency.spi.model.SPIDefinition" %><%@
page import="com.liferay.portal.resiliency.spi.service.SPIDefinitionServiceUtil" %><%@
page import="com.liferay.portal.resiliency.spi.service.permission.SPIDefinitionPermissionUtil" %><%@
page import="com.liferay.portal.resiliency.spi.util.ActionKeys" %><%@
page import="com.liferay.portal.resiliency.spi.util.PortletPropsValues" %><%@
page import="com.liferay.portal.resiliency.spi.util.SPIAdminConstants" %><%@
page import="com.liferay.portal.resiliency.spi.util.SPIAdminUtil" %><%@
page import="com.liferay.portal.resiliency.spi.util.SPIConfigurationTemplate" %><%@
page import="com.liferay.portal.resiliency.spi.util.WebKeys" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

String currentURL = PortalUtil.getCurrentURL(request);
%>