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

renderResponse.setTitle((batchPlannerPlan == null) ? LanguageUtil.get(request, "import") : LanguageUtil.get(request, "edit"));

EditBatchPlannerPlanDisplayContext editBatchPlannerPlanDisplayContext = (EditBatchPlannerPlanDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:container
	cssClass="container pt-4"
>
	<form id="<portlet:namespace />fm" name="<portlet:namespace />fm">
		<input id="<portlet:namespace />batchPlannerPlanId" name="<portlet:namespace />batchPlannerPlanId" type="hidden" value="<%= batchPlannerPlanId %>" />
		<input id="<portlet:namespace />taskItemDelegateName" name="<portlet:namespace />taskItemDelegateName" type="hidden" value="DEFAULT" />

		<div class="row">
			<div class="col-lg-6">
				<div class="card">
					<h4 class="card-header"><%= LanguageUtil.get(request, "import-settings") %></h4>

					<div class="card-body">
						<liferay-frontend:edit-form-body>
							<div id="<portlet:namespace />templateSelect"></div>

							<div class="row">
								<div class="col">
									<clay:select
										id='<%= liferayPortletResponse.getNamespace() + "headlessEndpoint" %>'
										label='<%= LanguageUtil.get(request, "headless-endpoint") %>'
										name="headlessEndpoint"
										options="<%= editBatchPlannerPlanDisplayContext.getSelectOptions() %>"
									/>

									<div class="mt-2">
										<clay:select
											disabled="<%= true %>"
											id='<%= liferayPortletResponse.getNamespace() + "internalClassName" %>'
											label='<%= LanguageUtil.get(request, "entity-name") %>'
											name="internalClassName"
											options="<%= editBatchPlannerPlanDisplayContext.getSelectOptions() %>"
										/>
									</div>

									<div class="mt-2">
										<clay:checkbox
											checked="<%= true %>"
											label='<%= LanguageUtil.get(request, "detect-category-names-from-CSV-file") %>'
											name="headerCheckbox"
										/>
									</div>

									<div class="mt-2">
										<clay:checkbox
											checked="<%= true %>"
											label='<%= LanguageUtil.get(request, "override-existing-records") %>'
											name="headerCheckbox"
										/>
										</div><div class="mt-2">

										<clay:checkbox
											checked="<%= true %>"
											label='<%= LanguageUtil.get(request, "ignore-blank-field-values-during-import") %>'
											name="headerCheckbox"
										/>
									</div>
								</div>
							</div>
						</liferay-frontend:edit-form-body>
					</div>
				</div>
			</div>

			<div class="col-lg-6">
				<div class="card">
					<h4 class="card-header"><%= LanguageUtil.get(request, "file-settings") %></h4>

					<div class="card-body">
						<liferay-frontend:edit-form-body>
							<div id="<portlet:namespace />fileSettings"></div>

							<div class="row">
								<div class="col">
									<clay:radio
										label='<%= LanguageUtil.get(request, "upload-a-csv-file-from-my-computer") %>'
										name="uploadFile"
									/>

									<span>
										<react:component
											module="js/components/FileUpload"
										/>
									</span>

									<clay:radio
										checked="<%= true %>"
										label='<%= LanguageUtil.get(request, "use-a-file-already-on-the-server") %>'
										name="selectFile"
									/>

									<clay:checkbox
										checked="<%= true %>"
										id='<%= liferayPortletResponse.getNamespace() + "containsHeaders" %>'
										label='<%= LanguageUtil.get(request, "this-file-contains-headers") %>'
										name='<%= liferayPortletResponse.getNamespace() + "containsHeaders" %>'
									/>

									<div class="row">
										<div class="col-lg-6">
											<aui:input name="field-separator" placeholder="," type="input" />
										</div>

										<div class="col-lg-6">
											<aui:input name="field-enclosure" placeholder="'" type="input" />
										</div>
									</div>
								</div>
							</div>
						</liferay-frontend:edit-form-body>
					</div>
				</div>
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
</clay:container>
