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

<%@ page import="com.liferay.portal.LARFileException" %><%@
page import="com.liferay.portal.LARFileSizeException" %><%@
page import="com.liferay.portal.LARTypeException" %><%@
page import="com.liferay.portal.NoSuchPortletItemException" %><%@
page import="com.liferay.portal.NoSuchResourceException" %><%@
page import="com.liferay.portal.PortletIdException" %><%@
page import="com.liferay.portal.PortletItemNameException" %><%@
page import="com.liferay.portal.ResourcePrimKeyException" %><%@
page import="com.liferay.portal.kernel.lar.ExportImportHelper" %><%@
page import="com.liferay.portal.kernel.lar.ExportImportHelperUtil" %><%@
page import="com.liferay.portal.kernel.lar.ManifestSummary" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataContext" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataException" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandler" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerBoolean" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerControl" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerKeys" %><%@
page import="com.liferay.portal.kernel.lar.UserIdStrategy" %><%@
page import="com.liferay.portal.kernel.portlet.PortletModeFactory" %><%@
page import="com.liferay.portal.kernel.util.DateRange" %><%@
page import="com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil" %><%@
page import="com.liferay.portal.lar.backgroundtask.PortletExportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.PortletImportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.PortletStagingBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.service.permission.TeamPermissionUtil" %><%@
page import="com.liferay.portal.util.ResourcePermissionUtil" %><%@
page import="com.liferay.portlet.PortletQNameUtil" %><%@
page import="com.liferay.portlet.backgroundtask.util.comparator.BackgroundTaskComparatorFactoryUtil" %><%@
page import="com.liferay.portlet.dynamicdatalists.RecordSetDuplicateRecordSetKeyException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException" %><%@
page import="com.liferay.portlet.portletconfiguration.action.ActionUtil" %><%@
page import="com.liferay.portlet.portletconfiguration.action.ExportImportAction" %><%@
page import="com.liferay.portlet.portletconfiguration.util.PublicRenderParameterConfiguration" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/portlet_configuration/init-ext.jsp" %>