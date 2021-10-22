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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

BatchPlannerLogDisplay batchPlannerLogDisplay = (BatchPlannerLogDisplay)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="container pt-4">
	<div class="card">
		<h4 class="card-header"><%= LanguageUtil.get(request, "batch-engine-task-details") %></h4>

		<div class="card-body">
			<clay:content-row>
				<clay:content-col
					expand="<%= true %>"
				>
					<clay:row>
						<clay:col
							md="2"
						>
							<%= LanguageUtil.get(request, "name") %>
						</clay:col>

						<clay:col
							md="8"
						>
							<%= batchPlannerLogDisplay.getTitle() %>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="2"
						>
							<%= LanguageUtil.get(request, "type") %>
						</clay:col>

						<clay:col
							md="8"
						>

							<%
							String exportImportLabel = "import";

							if (batchPlannerLogDisplay.isExport()) {
								exportImportLabel = "export";
							}
							%>

							<%= LanguageUtil.get(request, exportImportLabel) %>
						</clay:col>
					</clay:row>
				</clay:content-col>

				<clay:content-col
					expand="<%= true %>"
				>
					<clay:row>
						<clay:col
							md="4"
						>
							<%= LanguageUtil.get(request, "id") %>
						</clay:col>

						<clay:col
							md="6"
						>
							<%= String.valueOf(batchPlannerLogDisplay.getBatchPlannerLogId()) %>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="4"
						>
							<%= LanguageUtil.get(request, "create-date") %>
						</clay:col>

						<clay:col
							md="6"
						>
							<%= dateFormatDateTime.format(batchPlannerLogDisplay.getCreateDate()) %>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="4"
						>
							<%= LanguageUtil.get(request, "modified-date") %>
						</clay:col>

						<clay:col
							md="6"
						>
							<%= dateFormatDateTime.format(batchPlannerLogDisplay.getModifiedDate()) %>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="4"
						>
							<%= LanguageUtil.get(request, "external-reference-code") %>
						</clay:col>

						<clay:col
							md="6"
						>

							<%
							String externalReferenceCode = batchPlannerLogDisplay.getBatchEngineImportTaskERC();

							if (batchPlannerLogDisplay.isExport()) {
								externalReferenceCode = batchPlannerLogDisplay.getBatchEngineExportTaskERC();
							}
							%>

							<%= externalReferenceCode %>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="4"
						>
							<%= LanguageUtil.get(request, "count") %>
						</clay:col>

						<clay:col
							md="6"
						>
							<%= String.valueOf(batchPlannerLogDisplay.getTotalItemsCount()) %>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="4"
						>
							<%= LanguageUtil.get(request, "status") %>
						</clay:col>

						<clay:col
							md="6"
						>
							<%= batchPlannerLogDisplay.getStatus() %>
						</clay:col>
					</clay:row>
				</clay:content-col>
			</clay:content-row>

			<div class="mt-4">
				<clay:link
					displayType="primary"
					href="<%= backURL %>"
					label="back"
					type="button"
				/>
			</div>
		</div>
	</div>
</div>