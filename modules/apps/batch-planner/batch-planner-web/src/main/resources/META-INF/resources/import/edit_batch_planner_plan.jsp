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

boolean editable = ParamUtil.getBoolean(renderRequest, "editable");

renderResponse.setTitle(editable ? LanguageUtil.get(request, "edit-template") : LanguageUtil.get(request, "import"));
%>

<div class="container pt-4">
	<form id="<portlet:namespace />fm" name="<portlet:namespace />fm">
		<input id="<portlet:namespace />batchPlannerPlanId" name="<portlet:namespace />batchPlannerPlanId" type="hidden" value="<%= batchPlannerPlanId %>" />
		<input id="<portlet:namespace />taskItemDelegateName" name="<portlet:namespace />taskItemDelegateName" type="hidden" value="DEFAULT" />

		<div class="card">
			<h4 class="card-header"><%= LanguageUtil.get(request, "import-settings") %></h4>

			<div class="card-body">

				<%
				EditBatchPlannerPlanDisplayContext editBatchPlannerPlanDisplayContext = (EditBatchPlannerPlanDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
				%>

				<liferay-frontend:edit-form-body>
					<div id="<portlet:namespace />templateSelect"></div>

					<span>
						<react:component
							module="js/FileUpload"
						/>
					</span>

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
								label="internal-class-name"
								name="internalClassName"
								options="<%= editBatchPlannerPlanDisplayContext.getSelectOptions() %>"
							/>
						</clay:col>
					</clay:row>

					<clay:content-section>
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

		<span>
			<react:component
				module="js/import/ImportForm"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"backUrl", backURL
					).put(
						"formDataQuerySelector", "#" + liferayPortletResponse.getNamespace() + "fm"
					).put(
						"formImportURL",
						ResourceURLBuilder.createResourceURL(
							renderResponse
						).setCMD(
							Constants.IMPORT
						).setResourceID(
							"/batch_planner/submit_batch_planner_plan"
						).buildString()
					).put(
						"formSaveAsTemplateURL",
						ActionURLBuilder.createActionURL(
							renderResponse
						).setActionName(
							"/batch_planner/edit_import_batch_planner_plan"
						).setCMD(
							Constants.ADD
						).setParameter(
							"template", true
						).buildString()
					).build()
				%>'
			/>
		</span>
	</form>
</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"initialTemplateClassName", editBatchPlannerPlanDisplayContext.getSelectedInternalClassName()
		).put(
			"initialTemplateHeadlessEndpoint", editBatchPlannerPlanDisplayContext.getSelectedHeadlessEndpoint()
		).put(
			"initialTemplateMapping", editBatchPlannerPlanDisplayContext.getSelectedBatchPlannerPlanMappings()
		).put(
			"templatesOptions", editBatchPlannerPlanDisplayContext.getTemplateSelectOptions()
		).build()
	%>'
	module="js/edit_batch_planner_plan"
/>