<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String cmd = GetterUtil.getString(request.getAttribute("liferay-staging:deletions:cmd"));
boolean disableInputs = GetterUtil.getBoolean(request.getAttribute("liferay-staging:deletions:disableInputs"));
long exportImportConfigurationId = GetterUtil.getLong(request.getAttribute("liferay-staging:deletions:exportImportConfigurationId"));

Map<String, Serializable> settingsMap = Collections.emptyMap();
Map<String, String[]> parameterMap = Collections.emptyMap();

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.fetchExportImportConfiguration(exportImportConfigurationId);

if (exportImportConfiguration != null) {
	settingsMap = exportImportConfiguration.getSettingsMap();

	parameterMap = (Map<String, String[]>)settingsMap.get("parameterMap");
}

String individualDeletionsDescription = StringPool.BLANK;
String individualDeletionsTitle = StringPool.BLANK;

if (cmd.equals(Constants.EXPORT)) {
	individualDeletionsDescription = "deletions-help-export";
	individualDeletionsTitle = "export-individual-deletions";
}
else {
	individualDeletionsDescription = LanguageUtil.format(request, "x-deletions-help", StringUtil.toLowerCase(group.getScopeSimpleName(themeDisplay)), false);
	individualDeletionsTitle = "replicate-individual-deletions";
}
%>