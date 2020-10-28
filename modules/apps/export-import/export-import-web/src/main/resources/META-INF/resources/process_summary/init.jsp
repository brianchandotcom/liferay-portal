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

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.exportimport.changeset.constants.ChangesetPortletKeys" %><%@
page import="com.liferay.exportimport.constants.ExportImportBackgroundTaskContextMapConstants" %><%@
page import="com.liferay.exportimport.web.internal.display.context.ProcessSummaryDisplayContext" %><%@
page import="com.liferay.portal.kernel.model.LayoutSetBranch" %><%@
page import="com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatConstants" %><%@
page import="com.liferay.portal.kernel.util.LongWrapper" %>

<liferay-staging:defineObjects />

<%
long backgroundTaskId = GetterUtil.getLong(request.getAttribute("backgroundTaskId"), ParamUtil.getLong(request, "backgroundTaskId"));

BackgroundTask backgroundTask = BackgroundTaskManagerUtil.fetchBackgroundTask(backgroundTaskId);

Map<String, ?> taskContextMap = backgroundTask.getTaskContextMap();

long exportImportConfigurationId = GetterUtil.getLong(String.valueOf(taskContextMap.get("exportImportConfigurationId")));

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

Map<String, Serializable> exportImportConfigurationSettingsMap = exportImportConfiguration.getSettingsMap();

Map<String, Serializable> parameterMap = (Map<String, Serializable>)exportImportConfigurationSettingsMap.get("parameterMap");

String processCmd = MapUtil.getString(parameterMap, "cmd");

Map<String, LongWrapper> modelDeletionCounters = (Map<String, LongWrapper>)taskContextMap.get(ExportImportBackgroundTaskContextMapConstants.MODEL_DELETION_COUNTERS);
%>