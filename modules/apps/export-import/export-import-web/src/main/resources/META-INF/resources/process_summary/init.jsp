<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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