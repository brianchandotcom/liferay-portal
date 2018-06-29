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
String action = GetterUtil.getString(request.getAttribute("liferay-staging:select-pages:action"));
boolean disableInputs = GetterUtil.getBoolean(request.getAttribute("liferay-staging:select-pages:disableInputs"));
long exportImportConfigurationId = GetterUtil.getLong(request.getAttribute("liferay-staging:select-pages:exportImportConfigurationId"));
long groupIdAttribute = GetterUtil.getLong(request.getAttribute("liferay-staging:select-pages:groupId"));
boolean privateLayoutAttribute = GetterUtil.getBoolean(request.getAttribute("liferay-staging:select-pages:privateLayout"));
String treeId = GetterUtil.getString(request.getAttribute("liferay-staging:select-pages:treeId"));

Group groupAttribute = group;

if (groupId != groupIdAttribute) {
	groupAttribute = GroupLocalServiceUtil.fetchGroup(groupId);

	if (groupAttribute == null) {
		groupAttribute = group;
	}
}

Map<String, Serializable> settingsMap = Collections.emptyMap();
Map<String, String[]> parameterMap = Collections.emptyMap();

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.fetchExportImportConfiguration(exportImportConfigurationId);

long[] selectedLayoutIdsArray = null;

if (exportImportConfiguration != null) {
	settingsMap = exportImportConfiguration.getSettingsMap();

	parameterMap = (Map<String, String[]>)settingsMap.get("parameterMap");

	int type = exportImportConfiguration.getType();

	if (type == ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE) {
		Map<Long, Boolean> layoutIdMap = (Map<Long, Boolean>)settingsMap.get("layoutIdMap");

		selectedLayoutIdsArray = ExportImportHelperUtil.getLayoutIds(layoutIdMap);
	}
	else {
		selectedLayoutIdsArray = GetterUtil.getLongValues(settingsMap.get("layoutIds"));
	}
}
else {
	String openNodes = SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode");

	if (openNodes == null) {
		selectedLayoutIdsArray = ExportImportHelperUtil.getAllLayoutIds(groupIdAttribute, privateLayoutAttribute);

		for (long selectedLayoutId : selectedLayoutIdsArray) {
			SessionTreeJSClicks.openLayoutNodes(request, treeId + "SelectedNode", privateLayoutAttribute, selectedLayoutId, true);
		}
	}
	else {
		selectedLayoutIdsArray = GetterUtil.getLongValues(StringUtil.split(openNodes, ','));
	}
}

String selectedLayoutIds = StringUtil.merge(selectedLayoutIdsArray);
%>