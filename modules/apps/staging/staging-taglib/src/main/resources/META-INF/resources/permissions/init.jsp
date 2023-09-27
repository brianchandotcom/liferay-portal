<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String action = GetterUtil.getString(request.getAttribute("liferay-staging:permissions:action"));
boolean disableInputs = GetterUtil.getBoolean(request.getAttribute("liferay-staging:permissions:disableInputs"));
long exportImportConfigurationId = GetterUtil.getLong(request.getAttribute("liferay-staging:permissions:exportImportConfigurationId"));
boolean global = GetterUtil.getBoolean(request.getAttribute("liferay-staging:permissions:global"));

Map<String, Serializable> settingsMap = Collections.emptyMap();
Map<String, String[]> parameterMap = Collections.emptyMap();

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.fetchExportImportConfiguration(exportImportConfigurationId);

if (exportImportConfiguration != null) {
	settingsMap = exportImportConfiguration.getSettingsMap();

	parameterMap = (Map<String, String[]>)settingsMap.get("parameterMap");
}

String inputDescription = StringPool.BLANK;

if (global) {
	inputDescription = "publish-global-permissions-help";
}
else {
	inputDescription = "export-import-permissions-help";
}

String inputTitle = StringPool.BLANK;

if (action.equals("export")) {
	inputTitle = "export-permissions";
}
else if (action.equals("publish")) {
	inputTitle = "publish-permissions";
}
else {
	inputTitle = "import-permissions";
}
%>