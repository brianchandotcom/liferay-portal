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
String closeRedirect = ParamUtil.getString(request, "closeRedirect");
long groupId = ParamUtil.getLong(request, "groupId");

PortletURL renderURL = liferayPortletResponse.createRenderURL();

renderURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
renderURL.setParameter("tabs2", "all-publication-processes");
renderURL.setParameter("closeRedirect", closeRedirect);
renderURL.setParameter("groupId", String.valueOf(groupId));

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

OrderByComparator orderByComparator = BackgroundTaskUtil.getBackgroundTaskOrderByComparator(orderByCol, orderByType);

String expandedBrackgroundTasks = GetterUtil.getString(SessionClicks.get(request, "publish-layout-task", null));

long[] expandedBackgroundTasks = StringUtil.split(expandedBrackgroundTasks, 0L);
%>

<liferay-ui:search-container
	emptyResultsMessage="no-publication-processes-were-found"
	iteratorURL="<%= renderURL %>"
	orderByCol="<%= orderByCol %>"
	orderByComparator="<%= orderByComparator %>"
	orderByType="<%= orderByType %>"
	total="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(groupId, LayoutStagingBackgroundTaskExecutor.class.getName()) %>"
>
	<liferay-ui:search-container-results
		results="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasks(groupId, LayoutStagingBackgroundTaskExecutor.class.getName(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.BackgroundTask"
		modelVar="backgroundTask"
	>
		<liferay-ui:search-container-column-text
			name="user-name"
			value="<%= backgroundTask.getUserName() %>"
		/>

		<liferay-ui:search-container-column-text
			name="status" cssClass="background-task-status-column"
		>
			<strong class="label background-task-status-<%= BackgroundTaskConstants.getStatusLabel(backgroundTask.getStatus()) %> <%= BackgroundTaskConstants.getStatusCssClass(backgroundTask.getStatus()) %>">
				<liferay-ui:message key="<%= backgroundTask.getStatusLabel() %>" />
			</strong>

			<c:if test="<%= Validator.isNotNull(backgroundTask.getStatusMessage()) %>">
				<a class="details-link toggler-header-<%= ArrayUtil.contains(expandedBackgroundTasks, backgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>" data-persist-id="<%= backgroundTask.getBackgroundTaskId() %>" href="#"><liferay-ui:message key="details" /></a>

				<div class="background-task-status-message toggler-content-<%= ArrayUtil.contains(expandedBackgroundTasks, backgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>">

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
								<%= backgroundTask.getStatusMessage() %>
							</div>
						</c:when>
						<c:otherwise>
							<div class="alert alert-error publish-error">
								<h4 class="upload-error-message"><liferay-ui:message key="the-publication-process-did-not-start-due-to-validation-errors" /></h4>

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
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="create-date"
			orderable="<%= true %>"
			orderableProperty="createDate"
			value="<%= dateFormatDateTime.format(backgroundTask.getCreateDate()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="completion-date"
			orderable="<%= true %>"
			orderableProperty="completionDate"
			value="<%= backgroundTask.getCompletionDate() != null ? dateFormatDateTime.format(backgroundTask.getCompletionDate()) : StringPool.BLANK %>"
		/>

		<liferay-ui:search-container-column-text>
			<portlet:actionURL var="deleteBackgroundTaskURL">
				<portlet:param name="struts_action" value="/group_pages/delete_background_task" />
				<portlet:param name="redirect" value="<%= renderURL.toString() %>" />
				<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				label="true"
				url="<%= deleteBackgroundTaskURL %>"
			/>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>