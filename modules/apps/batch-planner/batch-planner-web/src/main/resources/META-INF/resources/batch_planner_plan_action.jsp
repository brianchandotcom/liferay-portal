<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	<c:if test="<%= (batchPlannerPlanDisplay.isStatusCompleted() || batchPlannerPlanDisplay.isStatusFailed()) && !batchPlannerPlanDisplay.isExport() %>">
		<liferay-ui:icon
			id='<%= "downloadOriginalFile" + batchPlannerPlanDisplay.getBatchPlannerPlanId() %>'
			message="download-original-file"
			url="#"
		/>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"externalReferenceCode", batchPlannerPlanDisplay.getBatchPlannerPlanId()
				).put(
					"HTMLElementId", liferayPortletResponse.getNamespace() + "downloadOriginalFile" + batchPlannerPlanDisplay.getBatchPlannerPlanId()
				).put(
					"type", "importFile"
				).build()
			%>'
			module="js/DownloadHelper"
		/>
	</c:if>

	<c:if test="<%= batchPlannerPlanDisplay.isExport() && batchPlannerPlanDisplay.isStatusCompleted() %>">
		<liferay-ui:icon
			id='<%= "downloadExportFile" + batchPlannerPlanDisplay.getBatchPlannerPlanId() %>'
			message="download-file"
			url="#"
		/>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"externalReferenceCode", batchPlannerPlanDisplay.getBatchPlannerPlanId()
				).put(
					"HTMLElementId", liferayPortletResponse.getNamespace() + "downloadExportFile" + batchPlannerPlanDisplay.getBatchPlannerPlanId()
				).put(
					"type", "exportFile"
				).build()
			%>'
			module="js/DownloadHelper"
		/>
	</c:if>
</liferay-ui:icon-menu>