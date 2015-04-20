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
page import="com.liferay.portal.ImageTypeException" %><%@
page import="com.liferay.portal.LARFileException" %><%@
page import="com.liferay.portal.LARFileNameException" %><%@
page import="com.liferay.portal.LARFileSizeException" %><%@
page import="com.liferay.portal.LARTypeException" %><%@
page import="com.liferay.portal.LayoutFriendlyURLException" %><%@
page import="com.liferay.portal.LayoutFriendlyURLsException" %><%@
page import="com.liferay.portal.LayoutImportException" %><%@
page import="com.liferay.portal.LayoutNameException" %><%@
page import="com.liferay.portal.LayoutPrototypeException" %><%@
page import="com.liferay.portal.LayoutTypeException" %><%@
page import="com.liferay.portal.NoSuchGroupException" %><%@
page import="com.liferay.portal.NoSuchLayoutSetBranchException" %><%@
page import="com.liferay.portal.RemoteExportException" %><%@
page import="com.liferay.portal.RemoteOptionsException" %><%@
page import="com.liferay.portal.RequiredLayoutException" %><%@
page import="com.liferay.portal.SitemapChangeFrequencyException" %><%@
page import="com.liferay.portal.SitemapIncludeException" %><%@
page import="com.liferay.portal.SitemapPagePriorityException" %><%@
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
page import="com.liferay.portal.kernel.servlet.DynamicServletRequest" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutExportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutImportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutRemoteStagingBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.security.auth.RemoteAuthException" %><%@
page import="com.liferay.portal.theme.NavItem" %><%@
page import="com.liferay.portal.util.LayoutTypeControllerTracker" %><%@
page import="com.liferay.portlet.backgroundtask.util.comparator.BackgroundTaskComparatorFactoryUtil" %><%@
page import="com.liferay.portlet.dynamicdatalists.RecordSetDuplicateRecordSetKeyException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException" %><%@
page import="com.liferay.portlet.layoutsadmin.context.LayoutsAdminDisplayContext" %><%@
page import="com.liferay.portlet.layoutsadmin.search.ExportImportConfigurationDisplayTerms" %><%@
page import="com.liferay.portlet.layoutsadmin.search.ExportImportConfigurationSearchTerms" %><%@
page import="com.liferay.portlet.mobiledevicerules.model.MDRAction" %><%@
page import="com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup" %><%@
page import="com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.permission.MDRPermissionUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.permission.MDRRuleGroupInstancePermissionUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.util.RuleGroupInstancePriorityComparator" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

LayoutsAdminDisplayContext layoutsAdminDisplayContext = new LayoutsAdminDisplayContext(request, liferayPortletResponse);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/layouts_admin/init-ext.jsp" %>