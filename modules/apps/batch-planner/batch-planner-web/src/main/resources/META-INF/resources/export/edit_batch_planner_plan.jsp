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

long batchPlannerPlanId = ParamUtil.getLong(renderRequest, "batchPlannerPlanId");

renderResponse.setTitle(LanguageUtil.get(request, "export"));
%>

<portlet:actionURL name="/batch_planner/edit_export_batch_planner_plan" var="exportBatchPlannerPlanURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
</portlet:actionURL>

<div class="container pt-4">
	<form action="<%= exportBatchPlannerPlanURL %>" id="<portlet:namespace />fm" method="POST" name="<portlet:namespace />fm">
		<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
		<aui:input name="batchPlannerPlanId" type="hidden" value="<%= batchPlannerPlanId %>" />
		<aui:input name="export" type="hidden" value="<%= true %>" />
		<aui:input name="taskItemDelegateName" type="hidden" value="DEFAULT" />
		<aui:input name="name" type="hidden" />

		<div class="card">
			<h4 class="card-header"><%= LanguageUtil.get(request, "export-settings") %></h4>

			<div class="card-body">
				<liferay-frontend:edit-form-body>

					<%
					EditBatchPlannerPlanDisplayContext editBatchPlannerPlanDisplayContext = (EditBatchPlannerPlanDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
					%>

					<clay:row>
						<clay:col
							md="6"
						>
							<clay:select
								id='<%= liferayPortletResponse.getNamespace() + "headlessEndpoint" %>'
								label="headless-endpoint"
								name="headlessEndpoint"
								options="<%= editBatchPlannerPlanDisplayContext.getSelectOptions() %>"
							/>
						</clay:col>

						<clay:col
							md="6"
						>
							<clay:select
								disabled="<%= true %>"
								id='<%= liferayPortletResponse.getNamespace() + "internalClassName" %>'
								label="entity-type"
								name="internalClassName"
								options="<%= Arrays.asList(new SelectOption(StringPool.BLANK, StringPool.BLANK)) %>"
							/>
						</clay:col>
					</clay:row>

					<clay:content-section>
						<clay:row>
							<clay:col>
								<clay:select
									label="export-file-format"
									name="externalType"
									options="<%=
										editBatchPlannerPlanDisplayContext.getExternalTypeSelectOptions()
									%>"
								/>
							</clay:col>
						</clay:row>

						<clay:row>
							<clay:col
								md="6"
							>
								<clay:checkbox
									id='<%= liferayPortletResponse.getNamespace() + "saveExport" %>'
									label="save-export"
									name='<%= liferayPortletResponse.getNamespace() + "saveExport" %>'
								/>
							</clay:col>
						</clay:row>

						<clay:row>
							<clay:col
								md="6"
							>
								<clay:checkbox
									checked="<%= true %>"
									id='<%= liferayPortletResponse.getNamespace() + "containsHeaders" %>'
									label="contains-headers"
									name='<%= liferayPortletResponse.getNamespace() + "containsHeaders" %>'
								/>
							</clay:col>
						</clay:row>
					</clay:content-section>
				</liferay-frontend:edit-form-body>
			</div>
		</div>

		<liferay-frontend:edit-form-body>
			<div>
				<react:component
					module="js/FieldsTable"
				/>
			</div>

			<div class="hide plan-mappings-template">
				<div class="input-group">
					<div class="input-group-item input-group-item-shrink input-group-prepend">
						<span class="input-group-text input-group-text-secondary">
							<div class="custom-checkbox custom-control">
								<label>
									<input class="custom-control-input" type="checkbox" checked
										id='<%= liferayPortletResponse.getNamespace() + "externalFieldName_ID_TEMPLATE" %>'
										name='<%= liferayPortletResponse.getNamespace() + "externalFieldName_ID_TEMPLATE" %>'
									/>

									<span class="custom-control-label"></span>
								</label>
							</div>
						</span>
					</div>

					<div class="input-group-append input-group-item">
						<input class="form-control" id="<portlet:namespace />internalFieldName_ID_TEMPLATE" name="<portlet:namespace />internalFieldName_ID_TEMPLATE" placeholder="Liferay object field name" type="text" value="VALUE_TEMPLATE" />
					</div>
				</div>
			</div>
		</liferay-frontend:edit-form-body>

		<div class="mt-4" id="<portlet:namespace />formButtons">
			<liferay-frontend:edit-form-footer>
				<clay:link
					displayType="secondary"
					href="<%= backURL %>"
					label="cancel"
					type="button"
				/>

				<clay:button
					disabled="true"
					displayType="secondary"
					id='<%= liferayPortletResponse.getNamespace() + "saveTemplate" %>'
					label="save-as-template"
					type="button"
				/>

				<clay:button
					disabled="true"
					displayType="primary"
					label="export"
					type="submit"
				/>
			</liferay-frontend:edit-form-footer>
		</div>
	</form>
</div>

<liferay-frontend:component
	module="js/edit_batch_planner_plan"
/>

<portlet:actionURL name="/batch_planner/edit_export_batch_planner_plan" var="saveBatchPlannerPlanURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
	<portlet:param name="template" value="<%= String.valueOf(Boolean.TRUE) %>" />
</portlet:actionURL>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"buttonContainerId", liferayPortletResponse.getNamespace() + "formButtons"
		).put(
			"formSaveAsTemplateDataQuerySelector", "#" + liferayPortletResponse.getNamespace() + "fm"
		).put(
			"formSaveAsTemplateURL", saveBatchPlannerPlanURL
		).put(
			"portletNamespace", liferayPortletResponse.getNamespace()
		).build()
	%>'
	module="js/save_template_modal"
/>