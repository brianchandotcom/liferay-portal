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

long batchPlannerLogId = ParamUtil.getLong(request, "batchPlannerLogId");
BatchPlannerLog batchPlannerLog;
batchPlannerLog = BatchPlannerLogServiceUtil.getBatchPlannerLog(batchPlannerLogId);

String creaDate;

if (batchPlannerLog.getCreateDate() != null)
	creaDate = fastDateFormat.format(batchPlannerLog.getCreateDate());
	else
	creaDate = "";
String modDate;

if (batchPlannerLog.getModifiedDate() != null)
	modDate = fastDateFormat.format(batchPlannerLog.getModifiedDate());
	else
	modDate = "";

String fieldNull = LanguageUtil.get(request, "field.null");
String importTaskERC = String.valueOf(batchPlannerLog.getBatchEngineImportTaskERC());

if (importTaskERC.compareTo("0") < 0)
	importTaskERC = fieldNull;
String exportTaskERC = String.valueOf(batchPlannerLog.getBatchEngineExportTaskERC());

if (exportTaskERC.compareTo("0") < 0)
	exportTaskERC = fieldNull;

String myTitle = "<i>Error</i>";

if (batchPlannerLog.getStatus() == 1)
	myTitle = "<em>Success<em>";
%>

<div class="container pt-4">
	<div class="card">
		<h4 class="card-header"><%= LanguageUtil.get(request, "batch-jobs") %></h4>

		<div class="card-body">
			<clay:row padded="true>">
				<clay:col>
					<p><%= LanguageUtil.get(request, "field.date-created") %> </p>
				</clay:col>

				<clay:col>
					<p> <%= creaDate %> </p>
				</clay:col>
			</clay:row>

			<clay:row>
				<clay:col>
					<p><%= LanguageUtil.get(request, "field.date-modified") %></p>
				</clay:col>

				<clay:col>
					<p><%= modDate %></p>
				</clay:col>
			</clay:row>

			<clay:row>
				<clay:col>
					<p><%= LanguageUtil.get(request, "batch-planner-log-plan-id") %></p>
				</clay:col>

				<clay:col>
					<p><%= String.valueOf(batchPlannerLog.getBatchPlannerPlanId()) %></p>
				</clay:col>
			</clay:row>

			<clay:row>
				<clay:col>
					<p><%= LanguageUtil.get(request, "batch-planner-log-export-task-id") %></p>
				</clay:col>

				<clay:col>
					<p><%= exportTaskERC %></p>
				</clay:col>
			</clay:row>

			<clay:row>
				<clay:col>
					<p><%= LanguageUtil.get(request, "batch-planner-log-import-task-id") %></p>
				</clay:col>

				<clay:col>
					<p><%= importTaskERC %></p>
				</clay:col>
			</clay:row>

			<clay:row>
				<clay:col>
					<p><%= LanguageUtil.get(request, "batch-planner-log-size") %></p>
				</clay:col>

				<clay:col>
					<p><%= String.valueOf(batchPlannerLog.getSize()) %></p>
				</clay:col>
			</clay:row>

			<clay:row>
				<clay:col>
					<p><%= LanguageUtil.get(request, "batch-planner-log-status") %></p>
				</clay:col>

				<clay:col>
					<p><%= myTitle %></p>
				</clay:col>
			</clay:row>

			<clay:row>
				<aui:button href="<%= backURL %>" type="cancel" />
			</clay:row>
		</div>
	</div>
</div>