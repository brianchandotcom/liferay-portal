<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %>

<%@ page import="com.liferay.portal.kernel.backgroundtask.BackgroundTask" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil" %><%@
page import="com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants" %><%@
page import="com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay" %><%@
page import="com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplayFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.model.CompanyConstants" %><%@
page import="com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.search.admin.web.internal.constants.SearchAdminWebKeys" %><%@
page import="com.liferay.portal.search.admin.web.internal.display.context.IndexActionsDisplayContext" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %>

<portlet:defineObjects />

<%
IndexActionsDisplayContext indexActionsDisplayContext = (IndexActionsDisplayContext)request.getAttribute(SearchAdminWebKeys.INDEX_ACTIONS_DISPLAY_CONTEXT);

List<BackgroundTask> indexReindexerBackgroundTasks = BackgroundTaskManagerUtil.getBackgroundTasks(CompanyConstants.SYSTEM, "com.liferay.portal.search.internal.background.task.ReindexIndexReindexerBackgroundTaskExecutor", BackgroundTaskConstants.STATUS_IN_PROGRESS);

List<BackgroundTask> reindexPortalBackgroundTasks = BackgroundTaskManagerUtil.getBackgroundTasks(CompanyConstants.SYSTEM, "com.liferay.portal.search.internal.background.task.ReindexPortalBackgroundTaskExecutor", BackgroundTaskConstants.STATUS_IN_PROGRESS);

List<BackgroundTask> reindexSingleBackgroundTasks = BackgroundTaskManagerUtil.getBackgroundTasks(CompanyConstants.SYSTEM, "com.liferay.portal.search.internal.background.task.ReindexSingleIndexerBackgroundTaskExecutor", BackgroundTaskConstants.STATUS_IN_PROGRESS);

JSONObject classNameToBackgroundTaskJSONObject = JSONFactoryUtil.createJSONObject();

if (!reindexPortalBackgroundTasks.isEmpty()) {
	BackgroundTask backgroundTask = reindexPortalBackgroundTasks.get(0);

	BackgroundTaskDisplay backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask);

	classNameToBackgroundTaskJSONObject.put("portal", backgroundTaskDisplay.getPercentage());
}

for (BackgroundTask backgroundTask : ListUtil.concat(reindexSingleBackgroundTasks, indexReindexerBackgroundTasks)) {
	Map<String, Serializable> taskContextMap = backgroundTask.getTaskContextMap();

	String className = (String)taskContextMap.get("className");

	BackgroundTaskDisplay backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask);

	classNameToBackgroundTaskJSONObject.put(className, backgroundTaskDisplay.getPercentage());
}
%>

<span class="hide" id="<portlet:namespace />classNameToBackgroundTaskMap">
	<%= classNameToBackgroundTaskJSONObject.toString() %>
</span>

<div>
	<react:component
		module="{IndexActions} from portal-search-admin-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"data", indexActionsDisplayContext.getData()
			).put(
				"redirectURL",
				PortletURLBuilder.createRenderURL(
					renderResponse
				).setMVCRenderCommandName(
					"/portal_search_admin/view"
				).setTabs1(
					"index-actions"
				).buildString()
			).put(
				"reindexURL",
				PortletURLBuilder.createActionURL(
					renderResponse
				).setActionName(
					"/portal_search_admin/edit"
				).buildString()
			).build()
		%>'
	/>
</div>