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
ResultRow resultRow = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BatchPlannerPlanDisplay batchPlannerPlanDisplay = (BatchPlannerPlanDisplay)resultRow.getObject();
%>

<liferay-ui:icon-menu
	direction="right-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= batchPlannerPlanDisplay.getFailedItemsCount() > 0 %>">
		<liferay-ui:icon
			id='<%= "downloadErrorReport" + batchPlannerPlanDisplay.getBatchPlannerPlanId() %>'
			message="download-error-report"
			url="#"
		/>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"externalReferenceCode", batchPlannerPlanDisplay.getBatchPlannerPlanId()
				).put(
					"HTMLElementId", liferayPortletResponse.getNamespace() + "downloadErrorReport" + batchPlannerPlanDisplay.getBatchPlannerPlanId()
				).put(
					"type", "errorReport"
				).build()
			%>'
			module="js/DownloadHelper"
		/>
	</c:if>

	<c:if test="<%= batchPlannerPlanDisplay.isStatusCompleted() && !batchPlannerPlanDisplay.isExport() %>">
		<liferay-ui:icon
			id='<%= "downloadImportFile" + batchPlannerPlanDisplay.getBatchPlannerPlanId() %>'
			message="download-import-file"
			url="#"
		/>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"externalReferenceCode", batchPlannerPlanDisplay.getBatchPlannerPlanId()
				).put(
					"HTMLElementId", liferayPortletResponse.getNamespace() + "downloadImportFile" + batchPlannerPlanDisplay.getBatchPlannerPlanId()
				).put(
					"type", "importFile"
				).build()
			%>'
			module="js/DownloadHelper"
		/>
	</c:if>
</liferay-ui:icon-menu>