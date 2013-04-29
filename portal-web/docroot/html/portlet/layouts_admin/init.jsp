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

<%@ page import="com.liferay.portal.DuplicateLockException" %><%@
page import="com.liferay.portal.ImageTypeException" %><%@
page import="com.liferay.portal.LARFileException" %><%@
page import="com.liferay.portal.LARFileSizeException" %><%@
page import="com.liferay.portal.LARTypeException" %><%@
page import="com.liferay.portal.LayoutFriendlyURLException" %><%@
page import="com.liferay.portal.LayoutImportException" %><%@
page import="com.liferay.portal.LayoutNameException" %><%@
page import="com.liferay.portal.LayoutPrototypeException" %><%@
page import="com.liferay.portal.LayoutTypeException" %><%@
page import="com.liferay.portal.LocaleException" %><%@
page import="com.liferay.portal.NoSuchGroupException" %><%@
page import="com.liferay.portal.NoSuchLayoutException" %><%@
page import="com.liferay.portal.NoSuchLayoutRevisionException" %><%@
page import="com.liferay.portal.NoSuchLayoutSetBranchException" %><%@
page import="com.liferay.portal.NoSuchRoleException" %><%@
page import="com.liferay.portal.RemoteExportException" %><%@
page import="com.liferay.portal.RemoteOptionsException" %><%@
page import="com.liferay.portal.RequiredLayoutException" %><%@
page import="com.liferay.portal.SitemapChangeFrequencyException" %><%@
page import="com.liferay.portal.SitemapIncludeException" %><%@
page import="com.liferay.portal.SitemapPagePriorityException" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataException" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandler" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerBoolean" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerChoice" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerControl" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerKeys" %><%@
page import="com.liferay.portal.kernel.lar.UserIdStrategy" %><%@
page import="com.liferay.portal.kernel.plugin.PluginPackage" %><%@
page import="com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil" %><%@
page import="com.liferay.portal.kernel.scheduler.StorageType" %><%@
page import="com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse" %><%@
page import="com.liferay.portal.kernel.staging.StagingConstants" %><%@
page import="com.liferay.portal.kernel.staging.StagingUtil" %><%@
page import="com.liferay.portal.lar.LayoutExporter" %><%@
page import="com.liferay.portal.layoutconfiguration.util.RuntimePageUtil" %><%@
page import="com.liferay.portal.plugin.PluginUtil" %><%@
page import="com.liferay.portal.util.LayoutLister" %><%@
page import="com.liferay.portal.util.LayoutView" %><%@
page import="com.liferay.portlet.documentlibrary.FileSizeException" %><%@
page import="com.liferay.portlet.dynamicdatalists.RecordSetDuplicateRecordSetKeyException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException" %><%@
page import="com.liferay.portlet.layoutsadmin.action.ImportLayoutsAction" %><%@
page import="com.liferay.portlet.layoutsadmin.util.LayoutsTreeUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup" %><%@
page import="com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.permission.MDRPermissionUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.service.permission.MDRRuleGroupInstancePermissionUtil" %><%@
page import="com.liferay.portlet.mobiledevicerules.util.RuleGroupInstancePriorityComparator" %>

<%
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/layouts_admin/init-ext.jsp" %>