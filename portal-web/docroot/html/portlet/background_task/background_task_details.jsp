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

<%@ include file="/html/portlet/background_task/init.jsp" %>

<%
BackgroundTaskDisplay backgroundTaskDisplay = (BackgroundTaskDisplay)request.getAttribute(WebKeys.BACKGROUND_TASK_DISPLAY);

long backgroundTaskId = ParamUtil.getLong(request, "backgroundTaskId");

BackgroundTask backgroundTask = BackgroundTaskLocalServiceUtil.fetchBackgroundTask(backgroundTaskId);

int backgroundTaskStatus = backgroundTask.getStatus();

String backgroundTaskStatusMessage = backgroundTask.getStatusMessage();
%>

<c:choose>
	<c:when test="<%= !backgroundTaskDisplay.hasDetails() %>">
		<div class="alert <%= (backgroundTaskStatus == BackgroundTaskConstants.STATUS_FAILED) ? "alert-danger" : StringPool.BLANK %> publish-error">
			<liferay-ui:message arguments="<%= backgroundTaskStatusMessage %>" key="unable-to-execute-process-x" translateArguments="<%= false %>" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		JSONObject detailsJSONObject = backgroundTaskDisplay.getDetailsJSONObject(locale);

		String detailsHeader = detailsJSONObject.getString("detailsHeader");
		%>

		<div class="alert alert-danger publish-error">
			<h4 class="upload-error-message">
				<liferay-ui:message key="<%= detailsHeader %>" localizeKey="<%= false %>" />
			</h4>

			<%
			JSONArray detailsItems = detailsJSONObject.getJSONArray("detailsItems");

			for (int i = 0; i < detailsItems.length(); i++) {
				JSONObject detailsItemJSONObject = detailsItems.getJSONObject(i);
			%>

			<span class="error-message">
				<%= HtmlUtil.escape(detailsItemJSONObject.getString("message")) %>
			</span>

			<ul class="error-list-items">

				<%
				JSONArray itemsListJSONArray = detailsItemJSONObject.getJSONArray("itemsList");

				for (int j = 0; j < itemsListJSONArray.length(); j++) {
					JSONObject itemsListJSONObject = itemsListJSONArray.getJSONObject(j);

					String info = itemsListJSONObject.getString("info");
				%>

					<li>
						<%= itemsListJSONObject.getString("errorMessage") %>:

						<strong><%= HtmlUtil.escape(itemsListJSONObject.getString("errorStrongMessage")) %></strong>

						<c:if test="<%= Validator.isNotNull(info) %>">
							<span class="error-info">(<%= HtmlUtil.escape(info) %>)</span>
						</c:if>
					</li>

			<%
				}
			}
			%>

			</ul>
	</c:otherwise>
</c:choose>