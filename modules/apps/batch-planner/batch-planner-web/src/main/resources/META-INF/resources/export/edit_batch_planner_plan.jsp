<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long batchPlannerPlanId = ParamUtil.getLong(renderRequest, "batchPlannerPlanId");

boolean editable = ParamUtil.getBoolean(renderRequest, "editable");

EditBatchPlannerPlanDisplayContext editBatchPlannerPlanDisplayContext = (EditBatchPlannerPlanDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())));

renderResponse.setTitle(editable ? LanguageUtil.get(request, "edit-template") : LanguageUtil.get(request, "export"));
%>

<div class="container pt-4">
	<form id="<portlet:namespace />fm" name="<portlet:namespace />fm">
		<input id="<portlet:namespace />batchPlannerPlanId" name="<portlet:namespace />batchPlannerPlanId" type="hidden" value="<%= batchPlannerPlanId %>" />
		<input id="<portlet:namespace />export" name="<portlet:namespace />export" type="hidden" value="<%= true %>" />

		<div class="card">
			<h4 class="card-header"><liferay-ui:message key="export-settings" /></h4>

			<div class="card-body">
				<liferay-frontend:edit-form-body>
					<div id="<portlet:namespace />templateSelect"></div>

					<clay:row>
						<clay:col
							md="6"
						>
							<clay:select
								id='<%= liferayPortletResponse.getNamespace() + "internalClassNameKey" %>'
								label="entity-type"
								name="internalClassNameKey"
								options="<%= editBatchPlannerPlanDisplayContext.getInternalClassNameKeySelectOptions() %>"
							/>
						</clay:col>

						<clay:col
							md="6"
						>
							<clay:select
								id='<%= liferayPortletResponse.getNamespace() + "externalType" %>'
								label="export-file-format"
								name="externalType"
								options="<%= editBatchPlannerPlanDisplayContext.getExternalTypeSelectOptions() %>"
							/>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="6"
						>
							<react:component
								module="js/components/Scope"
							/>
						</clay:col>
					</clay:row>

					<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-173135") %>'>
						<div class="contains-headers-wrapper d-none">
							<clay:checkbox
								checked="<%= true %>"
								disabled="<%= true %>"
								id='<%= liferayPortletResponse.getNamespace() + "containsHeaders" %>'
								label="include-headers"
								name='<%= liferayPortletResponse.getNamespace() + "containsHeaders" %>'
							/>
						</div>
					</c:if>
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

		<div class="mt-4">
			<liferay-frontend:edit-form-footer>
				<span>
					<react:component
						module="js/SaveTemplate"
						props='<%=
							HashMapBuilder.<String, Object>put(
								"formSaveAsTemplateDataQuerySelector", "#" + liferayPortletResponse.getNamespace() + "fm"
							).put(
								"formSaveAsTemplateURL",
								ActionURLBuilder.createActionURL(
									renderResponse
								).setActionName(
									"/batch_planner/edit_export_batch_planner_plan_template"
								).setCMD(
									Constants.ADD
								).setParameter(
									"template", true
								).buildString()
							).put(
								"namespace", liferayPortletResponse.getNamespace()
							).put(
								"type", "export"
							).build()
						%>'
					/>
				</span>
				<span>
					<react:component
						module="js/export/Export"
						props='<%=
							HashMapBuilder.<String, Object>put(
								"formExportDataQuerySelector", "#" + liferayPortletResponse.getNamespace() + "fm"
							).put(
								"formExportURL",
								ResourceURLBuilder.createResourceURL(
									renderResponse
								).setCMD(
									Constants.EXPORT
								).setResourceID(
									"/batch_planner/submit_batch_planner_plan"
								).buildString()
							).build()
						%>'
					/>
				</span>
			</liferay-frontend:edit-form-footer>
		</div>
	</form>
</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"initialExternalType", editBatchPlannerPlanDisplayContext.getSelectedExternalType()
		).put(
			"initialTemplateClassName", editBatchPlannerPlanDisplayContext.getSelectedInternalClassNameKey()
		).put(
			"initialTemplateMapping", editBatchPlannerPlanDisplayContext.getSelectedBatchPlannerPlanMappings()
		).put(
			"isExport", true
		).put(
			"templatesOptions", editBatchPlannerPlanDisplayContext.getTemplateSelectOptions()
		).build()
	%>'
	module="js/edit_batch_planner_plan"
/>