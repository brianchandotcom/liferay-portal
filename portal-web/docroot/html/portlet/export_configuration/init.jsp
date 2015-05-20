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

<%@ page import="com.liferay.portal.DuplicateLockException" %><%@
page import="com.liferay.portal.LARFileException" %><%@
page import="com.liferay.portal.LARFileNameException" %><%@
page import="com.liferay.portal.LARFileSizeException" %><%@
page import="com.liferay.portal.LARTypeException" %><%@
page import="com.liferay.portal.LayoutImportException" %><%@
page import="com.liferay.portal.LayoutPrototypeException" %><%@
page import="com.liferay.portal.NoSuchGroupException" %><%@
page import="com.liferay.portal.RemoteOptionsException" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil" %><%@
page import="com.liferay.portal.kernel.lar.ExportImportHelper" %><%@
page import="com.liferay.portal.kernel.lar.ExportImportHelperUtil" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerChoice" %><%@
page import="com.liferay.portal.kernel.lar.UserIdStrategy" %><%@
page import="com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationConstants" %><%@
page import="com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil" %><%@
page import="com.liferay.portal.kernel.scheduler.StorageType" %><%@
page import="com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse" %><%@
page import="com.liferay.portal.lar.LayoutExporter" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutExportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutImportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutRemoteStagingBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.security.auth.RemoteAuthException" %><%@
page import="com.liferay.portlet.backgroundtask.util.comparator.BackgroundTaskComparatorFactoryUtil" %><%@
page import="com.liferay.portlet.dynamicdatalists.RecordSetDuplicateRecordSetKeyException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException" %><%@
page import="com.liferay.portlet.exportconfiguration.search.ExportImportConfigurationDisplayTerms" %><%@
page import="com.liferay.portlet.exportconfiguration.search.ExportImportConfigurationSearchTerms" %><%@
page import="com.liferay.portlet.layoutsadmin.context.LayoutsAdminDisplayContext" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

LayoutsAdminDisplayContext layoutsAdminDisplayContext = new LayoutsAdminDisplayContext(request, liferayPortletResponse);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/export_configuration/init-ext.jsp" %>