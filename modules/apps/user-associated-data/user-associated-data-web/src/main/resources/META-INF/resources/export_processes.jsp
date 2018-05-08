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
UADExportProcessDisplayContext uadExportProcessDisplayContext = new UADExportProcessDisplayContext(request, renderResponse);
%>

<liferay-ui:search-container
	searchContainer="<%= uadExportProcessDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.backgroundtask.BackgroundTask"
		keyProperty="backgroundTaskId"
		modelVar="backgroundTask"
	>
		<liferay-ui:search-container-column-text
			cssClass="autofit-col-expand"
		>
			<div>
				<h5>
					<liferay-ui:message key="<%= backgroundTask.getName() %>" />
				</h5>

				<clay:label
					label="<%= LanguageUtil.get(request, backgroundTask.getStatusLabel()) %>"
					style="<%= UADExportProcessUtil.getStatusStyle(backgroundTask.getStatus()) %>"
				/>
			</div>
		</liferay-ui:search-container-column-text>

		<%
		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy.MM.dd - hh:mm a", locale, themeDisplay.getTimeZone());
		%>

		<liferay-ui:search-container-column-text
			cssClass="autofit-col-expand"
		>
			<%= LanguageUtil.get(request, "create-date") + StringPool.COLON + dateFormat.format(backgroundTask.getCreateDate()) %>

			<c:if test="<%= backgroundTask.isInProgress() %>">

				<%
				request.setAttribute("backgroundTask", backgroundTask);
				%>

				<liferay-util:include page="/export_process_progress_bar.jsp" servletContext="<%= application %>">
					<liferay-util:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
				</liferay-util:include>
			</c:if>
		</liferay-ui:search-container-column-text>

		<c:if test="<%= backgroundTask.isCompleted() %>">
			<liferay-ui:search-container-column-text
				cssClass="autofit-col-expand"
			>
				<%= LanguageUtil.get(request, "completion-date") + StringPool.COLON + dateFormat.format(backgroundTask.getCompletionDate()) %>
			</liferay-ui:search-container-column-text>
		</c:if>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/export_process_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="descriptive"
		markupView="lexicon"
		resultRowSplitter="<%= new UADExportProcessResultRowSplitter() %>"
	/>
</liferay-ui:search-container>