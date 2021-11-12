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

BatchPlannerPlan batchPlannerPlan = BatchPlannerPlanServiceUtil.fetchBatchPlannerPlan(batchPlannerPlanId);

renderResponse.setTitle((batchPlannerPlan == null) ? LanguageUtil.get(request, "add") : LanguageUtil.get(request, "edit"));
%>

<div class="container pt-4">
	<form
		action="<%=
			PortletURLBuilder.createActionURL(
				renderResponse
			).setActionName(
				"/batch_planner/edit_import_batch_planner_plan"
			).setCMD(
				(batchPlannerPlanId == 0) ? Constants.IMPORT : Constants.UPDATE
			).setRedirect(
				backURL
			).buildString()
		%>"
		id="<portlet:namespace />fm"
		method="POST"
		name="<portlet:namespace />fm"
	>
		<aui:input name="batchPlannerPlanId" type="hidden" value="<%= batchPlannerPlanId %>" />
		<aui:input name="taskItemDelegateName" type="hidden" value="DEFAULT" />

		<div class="card">
			<h4 class="card-header"><%= LanguageUtil.get(request, "import-settings") %></h4>

			<div class="card-body">
				<liferay-frontend:edit-form-body>
					<div class="form-group">
						<label for="<portlet:namespace />name"><%= LanguageUtil.get(request, "template-name") %></label>

						<input class="form-control" id="<portlet:namespace />name" type="text" />
					</div>

					<aui:select bean="<%= batchPlannerPlan %>" model="<%= BatchPlannerPlan.class %>" name="externalType">
						<aui:option label="CSV" value="CSV" />
						<aui:option label="TXT" value="TXT" />
						<aui:option label="XLS" value="XLS" />
						<aui:option label="XML" value="XML" />
					</aui:select>

					<aui:input name="importFile" required="<%= true %>" type="file" />

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

		<div class="card hide import-mapping-table">
			<h4 class="card-header"><%= LanguageUtil.get(request, "import-mappings") %></h4>

			<div class="card-body">
				<liferay-frontend:edit-form-body>
					<clay:content-section>
						<clay:row
							cssClass="plan-mappings"
						>

						</clay:row>

						<clay:row
							cssClass="hide plan-mappings-template"
						>
							<clay:col
								md="6"
							>
								<aui:input name="externalFieldName_ID_TEMPLATE" value="" />
							</clay:col>

							<clay:col
								md="6"
							>
								<aui:input name="internalFieldName_ID_TEMPLATE" value="VALUE_TEMPLATE" />
							</clay:col>
						</clay:row>
					</clay:content-section>
				</liferay-frontend:edit-form-body>
			</div>
		</div>

		<div class="mt-4" id="<portlet:namespace />formButtons">
			<liferay-frontend:edit-form-footer>
				<clay:link
					displayType="secondary"
					href="<%= backURL %>"
					label="cancel"
					type="button"
				/>

				<span>
					<react:component
						module="js/SaveTemplate"
						props='<%=
							HashMapBuilder.<String, Object>put(
								"formSaveAsTemplateDataQuerySelector", "#" + liferayPortletResponse.getNamespace() + "fm"
							).put(
								"formSaveAsTemplateURL",
								ResourceURLBuilder.createResourceURL(
									renderResponse
								).setCMD(
									Constants.SAVE
								).setParameter(
									"template", true
								).setResourceID(
									"/batch_planner/edit_import_batch_planner_plan"
								).buildString()
							).build()
						%>'
					/>
				</span>

				<clay:button
					disabled="true"
					displayType="primary"
					label="import"
					type="submit"
				/>
			</liferay-frontend:edit-form-footer>
		</div>
	</form>
</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"importMapping", "true"
		).build()
	%>'
	module="js/edit_batch_planner_plan"
/>