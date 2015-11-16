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
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.PUBLISH_TO_LIVE);

long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");
String layoutSetBranchName = ParamUtil.getString(request, "layoutSetBranchName");
boolean localPublishing = true;

if ((liveGroup.isStaged() && liveGroup.isStagedRemotely()) || cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
	localPublishing = false;
}

PortletURL renderURL = liferayPortletResponse.createRenderURL();

renderURL.setParameter("mvcRenderCommandName", "publishLayouts");
renderURL.setParameter("groupId", String.valueOf(groupId));
renderURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
renderURL.setParameter("layoutSetBranchName", layoutSetBranchName);
renderURL.setParameter("localPublishing", String.valueOf(localPublishing));

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", "create-date");
	orderByType = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", "desc");
}

String taskExecutorClassName = localPublishing ? BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR : BackgroundTaskExecutorNames.LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR;
%>

<div id="<portlet:namespace />publishProcessesSearchContainer">
	<liferay-ui:search-container
		emptyResultsMessage="no-publication-processes-were-found"
		id="publishProcesses"
		iteratorURL="<%= renderURL %>"
		orderByCol="<%= orderByCol %>"
		orderByType="<%= orderByType %>"
		rowChecker="<%= new EmptyOnClickRowChecker(liferayPortletResponse) %>"
	>

		<liferay-ui:search-container-results>

			<%
			List<BackgroundTask> backgroundTasks = BackgroundTaskManagerUtil.getBackgroundTasks(groupId, taskExecutorClassName);

			results.addAll(backgroundTasks);

			if (localPublishing) {
				results.addAll(BackgroundTaskManagerUtil.getBackgroundTasks(liveGroupId, taskExecutorClassName));
			}

			searchContainer.setTotal(results.size());

			results = ListUtil.subList(results, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.backgroundtask.BackgroundTask"
			keyProperty="backgroundTaskId"
			modelVar="backgroundTask"
		>

			<%
			User backgroundTaskUser = UserLocalServiceUtil.getUser(backgroundTask.getUserId());
			%>

			<liferay-ui:search-container-column-user
				showDetails="<%= false %>"
				userId="<%= backgroundTaskUser.getUserId() %>"
			/>

			<liferay-ui:search-container-column-text>
				<small>
					<liferay-ui:message arguments="<%= new Object[] {backgroundTaskUser.getFullName(), LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - backgroundTask.getCreateDate().getTime(), true)} %>" key="x,-created-x-ago" />
				</small>
				<h5>
					<liferay-ui:message key="<%= backgroundTask.getName() %>" />
				</h5>

				<c:if test="<%= backgroundTask.isInProgress() %>">

					<%
					BackgroundTaskStatus backgroundTaskStatus = BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(backgroundTask.getBackgroundTaskId());
					%>

					<c:if test="<%= backgroundTaskStatus != null %>">

						<%
						Map<String, Serializable> taskContextMap = backgroundTask.getTaskContextMap();

						String curCMD = (String)taskContextMap.get(Constants.CMD);

						int percentage = 100;

						long allModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));
						long allPortletAdditionCounter = GetterUtil.getLong(backgroundTaskStatus.getAttribute("allPortletAdditionCounter"));
						long currentModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("currentModelAdditionCountersTotal"));
						long currentPortletAdditionCounter = GetterUtil.getLong(backgroundTaskStatus.getAttribute("currentPortletAdditionCounter"));

						long allProgressBarCountersTotal = allModelAdditionCountersTotal + allPortletAdditionCounter;
						long currentProgressBarCountersTotal = currentModelAdditionCountersTotal + currentPortletAdditionCounter;

						if (allProgressBarCountersTotal > 0) {
							int base = 100;

							String phase = GetterUtil.getString(backgroundTaskStatus.getAttribute("phase"));

							if (phase.equals(Constants.EXPORT) && !Validator.equals(curCMD, Constants.PUBLISH_TO_REMOTE)) {
								base = 50;
							}

							percentage = Math.round((float)currentProgressBarCountersTotal / allProgressBarCountersTotal * base);
						}
						%>

						<div class="row">
							<div class="col-sm-12">
								<div class="progress">
									<div aria-valuemax="100" aria-valuemin="0" aria-valuenow="<%= percentage %>>" class="progress-bar" role="progressbar" style="width: <%= percentage %>%;">
										<c:if test="<%= (allProgressBarCountersTotal > 0) && (!Validator.equals(curCMD, Constants.PUBLISH_TO_REMOTE) || (percentage < 100)) %>">
											<%= percentage + StringPool.PERCENT %>
										</c:if>
									</div>
								</div>
							</div>
						</div>
						<small>
							<strong class="background-task-status-<%= BackgroundTaskConstants.getStatusLabel(backgroundTask.getStatus()) %> <%= BackgroundTaskConstants.getStatusCssClass(backgroundTask.getStatus()) %> label">
								<liferay-ui:message key="<%= backgroundTask.getStatusLabel() %>" />
							</strong>
						</small>
					</c:if>
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>
				<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
					<c:if test="<%= backgroundTask.getGroupId() != liveGroupId %>">
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

					<portlet:actionURL name="deleteBackgroundTask" var="deleteBackgroundTaskURL">
						<portlet:param name="redirect" value="<%= currentURL.toString() %>" />
						<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
					</portlet:actionURL>

					<%
					Date completionDate = backgroundTask.getCompletionDate();
					%>

					<liferay-ui:icon
						message='<%= LanguageUtil.get(request, ((completionDate != null) && completionDate.before(new Date())) ? "clear" : "cancel") %>'
						url="<%= deleteBackgroundTaskURL %>"
					/>
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<%
int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(groupId, taskExecutorClassName, false);

if (localPublishing) {
	incompleteBackgroundTaskCount += BackgroundTaskManagerUtil.getBackgroundTasksCount(liveGroupId, taskExecutorClassName, false);
}
%>

<div class="hide incomplete-process-message">
	<liferay-util:include page="/processes_list/incomplete_processes_message.jsp" servletContext="<%= application %>">
		<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
	</liferay-util:include>
</div>