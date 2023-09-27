<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
boolean hasPublishStagingPermission = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PUBLISH_STAGING);
%>

<liferay-staging:process-list
	deleteMenu="<%= hasPublishStagingPermission %>"
	emptyResultsMessage="no-publish-processes-were-found"
	localTaskExecutorClassName="<%= BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR %>"
	mvcRenderCommandName="publishLayoutsView"
	relaunchMenu="<%= hasPublishStagingPermission %>"
	remoteTaskExecutorClassName="<%= BackgroundTaskExecutorNames.LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR %>"
	resultRowSplitter="<%= new PublishResultRowSplitter() %>"
/>