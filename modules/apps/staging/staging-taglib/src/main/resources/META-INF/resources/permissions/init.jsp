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