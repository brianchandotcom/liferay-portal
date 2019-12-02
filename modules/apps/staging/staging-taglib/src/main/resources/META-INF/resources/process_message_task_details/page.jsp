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

<%@ include file="/process_message_task_details/init.jsp" %>

<c:if test="<%= Validator.isNotNull(backgroundTaskStatusMessage) %>">
	<h6 class="<%= linkClass %>">
		<a class="details-link" href="javascript:Liferay.fire('<portlet:namespace />viewBackgroundTaskDetails', {nodeId: 'backgroundTaskStatusMessage<%= backgroundTaskId %>', title: document.getElementById('<portlet:namespace />backgroundTaskName<%= backgroundTaskId %>').textContent}); void(0);"><liferay-ui:message key="see-more-details" /></a>
	</h6>

	<div class="background-task-status-message hide" id="<portlet:namespace />backgroundTaskStatusMessage<%= backgroundTaskId %>">

		<%
		BackgroundTask backgroundTask = BackgroundTaskManagerUtil.fetchBackgroundTask(backgroundTaskId);

		JSONObject jsonObject = null;

		try {
			jsonObject = JSONFactoryUtil.createJSONObject(backgroundTaskStatusMessage);
		}
		catch (Exception e) {
		}
		%>

		<c:choose>
			<c:when test="<%= jsonObject == null %>">
				<div class="alert <%= (backgroundTask.getStatus() == BackgroundTaskConstants.STATUS_FAILED) ? "alert-danger" : StringPool.BLANK %> publish-error">
					<liferay-ui:message arguments="<%= backgroundTaskStatusMessage %>" key="unable-to-execute-process-x" translateArguments="<%= false %>" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-danger publish-error">

					<%
					boolean exported = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "exported");
					boolean validated = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "validated");
					%>

					<c:choose>
						<c:when test="<%= exported && !validated %>">
							<h4 class="upload-error-message"><liferay-ui:message key="the-publication-process-did-not-start-due-to-validation-errors" /></h4>
						</c:when>
						<c:otherwise>
							<h4 class="upload-error-message"><liferay-ui:message key="an-unexpected-error-occurred-with-the-publication-process.-please-check-your-portal-and-publishing-configuration" /></h4>
						</c:otherwise>
					</c:choose>

					<span class="error-message"><%= HtmlUtil.escape(jsonObject.getString("message")) %></span>

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
									<%= messageListItemJSONArray.getString("type") %>

									<%= messageListItemJSONArray.getString("site") %>:

									<strong><%= HtmlUtil.escape(messageListItemJSONArray.getString("name")) %></strong>

									<c:if test="<%= Validator.isNotNull(info) %>">
										<span class="error-info">(<%= HtmlUtil.escape(messageListItemJSONArray.getString("info")) %>)</span>
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
										<span class="error-info">(<%= HtmlUtil.escape(warningMessageJSONArray.getString("info")) %>)</span>
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