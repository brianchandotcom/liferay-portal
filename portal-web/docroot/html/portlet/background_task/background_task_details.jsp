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
BackgroundTaskDisplay backgroundTaskDisplay = (BackgroundTaskDisplay)request.getAttribute("backgroundTaskDisplay");

long backgroundTaskId = ParamUtil.getLong(request, "backgroundTaskId");

BackgroundTask backgroundTask = BackgroundTaskLocalServiceUtil.fetchBackgroundTask(backgroundTaskId);

int backgroundTaskStatus = backgroundTask.getStatus();

String backgroundTaskStatusMessage = backgroundTask.getStatusMessage();
%>

<c:choose>
	<c:when test="<%= !backgroundTaskDisplay.hasDetails() %>">
		<div class="alert <%= backgroundTaskStatus == BackgroundTaskConstants.STATUS_FAILED ? "alert-danger" : StringPool.BLANK %> publish-error">
			<liferay-ui:message arguments="<%= backgroundTaskStatusMessage %>" key="unable-to-execute-process-x" translateArguments="<%= false %>" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		JSONObject details = backgroundTaskDisplay.getDetails();

		String detailsHeader = details.getString("detailHeader");
		%>

		<div class="alert alert-danger publish-error">
			<h4 class="upload-error-message">
				<liferay-ui:message key="<%= detailsHeader %>" localizeKey="<%= false %>" />
			</h4>

			<%
			JSONArray detailItems = details.getJSONArray("detailItems");

			for (int i=0; i < detailItems.length(); i++ ) {
				JSONObject jsonObject = detailItems.getJSONObject(i);
			%>

			<span class="error-message">
				<%= HtmlUtil.escape(jsonObject.getString("message")) %>
			</span>

			<ul class="error-list-items">

				<%
				JSONArray messageListItemsJSONArray = jsonObject.getJSONArray("itemsList");

				for (int j = 0; j < messageListItemsJSONArray.length(); j++) {
					JSONObject messageListItemJSONArray = messageListItemsJSONArray.getJSONObject(j);

					String info = messageListItemJSONArray.getString("info");
				%>

					<li>
						<%= messageListItemJSONArray.getString("itemMessage") %>:

						<strong><%= HtmlUtil.escape(messageListItemJSONArray.getString("strongItemMessage")) %></strong>

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