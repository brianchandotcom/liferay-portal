<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BackgroundTask backgroundTask = (BackgroundTask)row.getObject();
%>

<strong class="label background-task-status-<%= BackgroundTaskConstants.getStatusLabel(backgroundTask.getStatus()) %> <%= BackgroundTaskConstants.getStatusCssClass(backgroundTask.getStatus()) %>">
	<liferay-ui:message key="<%= backgroundTask.getStatusLabel() %>" />
</strong>

<c:if test="<%= backgroundTask.isInProgress() %>">

	<%
	BackgroundTaskStatus backgroundTaskStatus = BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(backgroundTask.getBackgroundTaskId());
	%>

	<c:if test="<%= backgroundTaskStatus != null %>">

		<%
		double percentage = 100;

		long allModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));
		long currentModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("currentModelAdditionCountersTotal"));

		if (allModelAdditionCountersTotal > 0) {
			percentage = Math.round((double)currentModelAdditionCountersTotal / allModelAdditionCountersTotal * 100);
		}
		%>

		<div class="progress progress-striped active">
			<div class="bar" style="width: <%= percentage %>%;">
				<c:if test="<%= allModelAdditionCountersTotal > 0 %>">
					<%= currentModelAdditionCountersTotal %> / <%= allModelAdditionCountersTotal %>
				</c:if>
			</div>
		</div>

		<%
		String stagedModelName = (String)backgroundTaskStatus.getAttribute("stagedModelName");
		String stagedModelType = (String)backgroundTaskStatus.getAttribute("stagedModelType");
		%>

		<c:if test="<%= Validator.isNotNull(stagedModelName) && Validator.isNotNull(stagedModelType) %>">

			<%
			String messageKey = "exporting";

			Map<String, Serializable> taskContextMap = backgroundTask.getTaskContextMap();

			String cmd = (String)taskContextMap.get(Constants.CMD);

			if (Validator.equals(cmd, Constants.IMPORT)) {
				messageKey = "importing";
			}
			else if (Validator.equals(cmd, Constants.PUBLISH)) {
				messageKey = "publishing";
			}
			%>

			<div class="progress-current-item">
				<strong><liferay-ui:message key="<%= messageKey %>" /><%= StringPool.TRIPLE_PERIOD %></strong> <%= ResourceActionsUtil.getModelResource(locale, stagedModelType) %> <em><%= stagedModelName %></em>
			</div>
		</c:if>
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(backgroundTask.getStatusMessage()) %>">

	<%
	long[] expandedBackgroundTaskIds = StringUtil.split(GetterUtil.getString(SessionClicks.get(request, "background-task-ids", null)), 0L);
	%>

	<a class="details-link toggler-header-<%= ArrayUtil.contains(expandedBackgroundTaskIds, backgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>" data-persist-id="<%= backgroundTask.getBackgroundTaskId() %>" href="#"><liferay-ui:message key="details" /></a>

	<div class="background-task-status-message toggler-content-<%= ArrayUtil.contains(expandedBackgroundTaskIds, backgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>">

		<%
		JSONObject jsonObject = null;

		try {
			jsonObject = JSONFactoryUtil.createJSONObject(backgroundTask.getStatusMessage());
		}
		catch (JSONException jsone) {
		}
		%>

		<c:choose>
			<c:when test="<%= jsonObject == null %>">
				<div class="alert <%= backgroundTask.getStatus() == BackgroundTaskConstants.STATUS_FAILED ? "alert-error" : StringPool.BLANK %> publish-error">
					<liferay-ui:message arguments="<%= backgroundTask.getStatusMessage() %>" key="unable-to-execute-process-x" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-error publish-error">
					<h4 class="upload-error-message">

						<%
						boolean exported = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "exported");
						boolean validated = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "validated");
						%>

						<c:choose>
							<c:when test="<%= exported && !validated %>">
								<liferay-ui:message key="the-publication-process-did-not-start-due-to-validation-errors" /></h4>
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="an-unexpected-error-occurred-with-the-publication-process.-please-check-your-portal-and-publishing-configuration" /></h4>
							</c:otherwise>
						</c:choose>

					<span class="error-message"><%= jsonObject.getString("message") %></span>

					<%
					JSONArray messageListItemsJSONArray = jsonObject.getJSONArray("messageListItems");
					%>

					<c:if test="<%= (messageListItemsJSONArray != null) && (messageListItemsJSONArray.length() > 0) %>">
						<ul class="error-list-items">

							<%
							for (int i = 0; i < messageListItemsJSONArray.length(); i++) {
								JSONObject messageListItemJSONArray = messageListItemsJSONArray.getJSONObject(i);

								String info = messageListItemJSONArray.getString("info");
							%>

								<li>
									<%= messageListItemJSONArray.getString("type") %>:

									<strong><%= messageListItemJSONArray.getString("name") %></strong>

									<c:if test="<%= Validator.isNotNull(info) %>">
										<span class="error-info">(<%= messageListItemJSONArray.getString("info") %>)</span>
									</c:if>
								</li>

							<%
							}
							%>

						</ul>
					</c:if>
				</div>

				<%
				JSONArray warningMessagesJSONArray = jsonObject.getJSONArray("warningMessages");
				%>

				<c:if test="<%= (warningMessagesJSONArray != null) && (warningMessagesJSONArray.length() > 0) %>">
					<div class="alert upload-error">
						<span class="error-message"><liferay-ui:message key='<%= ((messageListItemsJSONArray != null) && (messageListItemsJSONArray.length() > 0)) ? "consider-that-the-following-data-would-not-have-been-published-either" : "the-following-data-has-not-been-published" %>' /></span>

						<ul class="error-list-items">

							<%
							for (int i = 0; i < warningMessagesJSONArray.length(); i++) {
								JSONObject warningMessageJSONArray = warningMessagesJSONArray.getJSONObject(i);

								String info = warningMessageJSONArray.getString("info");
							%>

								<li>
									<%= warningMessageJSONArray.getString("type") %>:

									<strong><%= warningMessageJSONArray.getString("size") %></strong>

									<c:if test="<%= Validator.isNotNull(info) %>">
										<span class="error-info">(<%= warningMessageJSONArray.getString("info") %>)</span>
									</c:if>
								</li>

							<%
							}
							%>

						</ul>
					</div>
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>