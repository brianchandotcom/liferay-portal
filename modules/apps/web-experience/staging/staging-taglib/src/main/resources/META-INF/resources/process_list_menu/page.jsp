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

<%@ include file="/process_list_menu/init.jsp" %>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= !localPublishing || (backgroundTask.getGroupId() != liveGroupId) %>">
		<portlet:actionURL name="editPublishConfiguration" var="relaunchURL">
			<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RELAUNCH %>" />
			<portlet:param name="redirect" value="<%= currentURL.toString() %>" />
			<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="relaunch"
			url="<%= relaunchURL %>"
		/>
	</c:if>

	<portlet:actionURL name="deleteBackgroundTasks" var="deleteBackgroundTaskURL">
		<portlet:param name="redirect" value="<%= currentURL.toString() %>" />
		<portlet:param name="deleteBackgroundTaskIds" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="<%= deleteLabel %>"
		url="<%= deleteBackgroundTaskURL %>"
	/>

	<%
	long exportImportConfigurationId = MapUtil.getLong(backgroundTask.getTaskContextMap(), "exportImportConfigurationId");

	ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

	Map<String, Serializable> settingsMap = exportImportConfiguration.getSettingsMap();

	Map<String, String[]> parameterMap = (Map<String, String[]>)settingsMap.get("parameterMap");

	String processCmd = MapUtil.getString(parameterMap, "cmd");
	%>

	<c:if test="<%= backgroundTask.isCompleted() && Validator.isNotNull(processCmd) %>">
		<liferay-staging:process-summary-link
			backgroundTaskId="<%= backgroundTask.getBackgroundTaskId() %>"
		/>
	</c:if>
</liferay-ui:icon-menu>