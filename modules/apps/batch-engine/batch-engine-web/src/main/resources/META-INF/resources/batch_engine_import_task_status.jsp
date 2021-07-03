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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BatchEngineImportTask batchEngineImportTask = (BatchEngineImportTask)row.getObject();

long batchEngineImportTaskId = batchEngineImportTask.getBatchEngineImportTaskId();

int percentage = 0;

if (batchEngineImportTask.getTotalItemsCount() != 0) {
	percentage = (batchEngineImportTask.getProcessedItemsCount() * 100) / batchEngineImportTask.getTotalItemsCount();
}
%>

<portlet:resourceURL id="/batch_engine/batch_engine_import_task_status" var="batchEngineImportTaskStatusURL">
	<portlet:param name="batchEngineImportTaskId" value="<%= String.valueOf(batchEngineImportTask.getBatchEngineImportTaskId()) %>" />
</portlet:resourceURL>

<c:choose>
	<c:when test='<%= Objects.equals(batchEngineImportTask.getExecuteStatus(), "STARTED") %>'>
		<div class="active progress">
			<div class="progress-bar text-center" id="<portlet:namespace />importTaskProgressBar<%= batchEngineImportTaskId %>" style="width: <%= percentage %>%;">
				<%= percentage %>%
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="<%= batchEngineImportTask.getExecuteStatus() %>" />
	</c:otherwise>
</c:choose>

<aui:script>
	(function () {
		var defaultError =
			'<liferay-ui:message key="an-unexpected-error-occurred" />';

		var importTaskProgressBar = document.getElementById(
			'<portlet:namespace />importTaskProgressBar<%= batchEngineImportTaskId %>'
		);

		if (importTaskProgressBar) {
			updateProgress();
		}

		function updateProgress() {
			Liferay.Util.fetch('<%= batchEngineImportTaskStatusURL %>', {
				method: 'POST',
			})
				.then((response) => {
					if (!response.ok) {
						throw defaultError;
					}

					return response.json();
				})
				.then((response) => {
					importTaskProgressBar.style.width = response.percentage + '%';

					importTaskProgressBar.innerHTML = response.percentage + '%';

					setTimeout(updateProgress, 1000);
				});
		}
	})();
</aui:script>