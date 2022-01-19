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
			cssClass="lfr-title-column"
		>
			<div id="<portlet:namespace />exportStatus">
				<h5>
					<liferay-ui:message key="<%= UADLanguageUtil.getApplicationName(UADExportProcessUtil.getApplicationKey(backgroundTask), locale) %>" />
				</h5>

				<clay:label
					displayType="<%= UADExportProcessUtil.getStatusStyle(backgroundTask.getStatus()) %>"
					label="<%= backgroundTask.getStatusLabel() %>"
				/>
			</div>
		</liferay-ui:search-container-column-text>

		<%
		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy.MM.dd - hh:mm a", locale, themeDisplay.getTimeZone());
		%>

		<liferay-ui:search-container-column-text
			cssClass="lfr-create-date-column table-cell-expand text-nowrap"
		>
			<%= LanguageUtil.get(request, "create-date") + ": " + dateFormat.format(backgroundTask.getCreateDate()) %>

			<c:if test="<%= backgroundTask.isInProgress() %>">

				<%
				request.setAttribute("backgroundTask", backgroundTask);
				%>

				<liferay-util:include page="/export_process_progress_bar.jsp" servletContext="<%= application %>">
					<liferay-util:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
				</liferay-util:include>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			cssClass="lfr-completion-date-column table-cell-expand text-nowrap"
		>
			<c:choose>
				<c:when test="<%= backgroundTask.isCompleted() %>">
					<%= LanguageUtil.get(request, "completion-date") + ": " + dateFormat.format(backgroundTask.getCompletionDate()) %>
				</c:when>
				<c:otherwise>
					<%= LanguageUtil.get(request, "in-progress") %>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/export_process_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		resultRowSplitter="<%= new UADExportProcessResultRowSplitter() %>"
	/>
</liferay-ui:search-container>