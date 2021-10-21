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

<portlet:actionURL name="/batch_planner/edit_import_batch_planner_plan" var="editBatchPlannerPlanURL" />

<div class="container pt-4">
	<liferay-frontend:edit-form
		action="<%= editBatchPlannerPlanURL %>"
	>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (batchPlannerPlanId == 0) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
		<aui:input name="batchPlannerPlanId" type="hidden" value="<%= batchPlannerPlanId %>" />
		<aui:input name="taskItemDelegateName" type="hidden" value="DEFAULT" />

		<div class="card">
			<h4 class="card-header"><%= LanguageUtil.get(request, "import-settings") %></h4>

			<div class="card-body">
				<liferay-frontend:edit-form-body>
					<aui:input bean="<%= batchPlannerPlan %>" model="<%= BatchPlannerPlan.class %>" name="name" />

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

		<div class="card hide">
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

		<div class="mt-4">
			<liferay-frontend:edit-form-footer>
				<aui:button name="save" type="submit" value="import" />

				<aui:button href="<%= backURL %>" type="cancel" />
			</liferay-frontend:edit-form-footer>
		</div>
	</liferay-frontend:edit-form>
</div>

<aui:script use="aui-io-request,aui-parse-content">
	A.one('#<portlet:namespace />headlessEndpoint').on('change', function (event) {
		this.attr('disabled', true);

		var openapiDiscoveryURL = A.one(
			'#<portlet:namespace />headlessEndpoint'
		).val();

		Liferay.Util.fetch(openapiDiscoveryURL, {
			method: 'GET',
			credentials: 'include',
			headers: [
				['content-type', 'application/json'],
				['x-csrf-token', window.Liferay.authToken],
			],
		})
			.then((response) => {
				if (!response.ok) {
					throw new Error(`Failed to fetch: '${openapiDiscoveryURL}'`);
				}

				return response.json();
			})
			.then((jsonResponse) => {
				var internalClassName = A.one(
					'#<portlet:namespace />internalClassName'
				);

				internalClassName.empty();

				let schemas = jsonResponse.components.schemas;

				for (key in schemas) {
					let properties = schemas[key].properties;

					if (!properties || !properties['x-class-name']) {
						continue;
					}

					const className = properties['x-class-name'].default;

					const schemaName =
						(properties['x-schema-name'] &&
							properties['x-schema-name'].default) ||
						'';

					internalClassName.appendChild(
						'<option schemaName="' +
							schemaName +
							'" value="' +
							className +
							'">' +
							key +
							'</option>'
					);
				}

				<portlet:namespace />renderMappings();

				internalClassName.attr('disabled', false);
			})
			.catch((response) => {
				alert('FETCH failed ' + response);
			})
			.then(() => {
				A.one('#<portlet:namespace />headlessEndpoint').attr(
					'disabled',
					false
				);
			});
	});

	A.one('#<portlet:namespace />internalClassName').on('change', function (event) {
		this.attr('disabled', true);

		<portlet:namespace />renderMappings();

		this.attr('disabled', false);
	});

	function <portlet:namespace />renderMappings() {
		var openAPIURL = A.one('#<portlet:namespace />headlessEndpoint').val();

		const selectedOption = A.one(
			'#<portlet:namespace />internalClassName option:checked'
		);

		const schemaName = selectedOption.getAttribute('schemaName');

		A.one('#<portlet:namespace />taskItemDelegateName').val(
			schemaName || 'DEFAULT'
		);

		let internalClassNameValue = schemaName || selectedOption.val();

		internalClassNameValue = internalClassNameValue.substr(
			internalClassNameValue.lastIndexOf('.') + 1
		);

		Liferay.Util.fetch(openAPIURL, {
			method: 'GET',
			credentials: 'include',
			headers: [
				['content-type', 'application/json'],
				['x-csrf-token', window.Liferay.authToken],
			],
		})
			.then((response) => {
				if (!response.ok) {
					throw new Error(`Failed to fetch: '${openAPIURL}'`);
				}

				return response.json();
			})
			.then((jsonResponse) => {
				let schemas = jsonResponse.components.schemas;

				let schemaEntry = schemas[internalClassNameValue];

				var mappingArea = A.one('.plan-mappings');
				var mappingRowTemplate = A.one(
					'.plan-mappings-template'
				).getContent();

				mappingArea.empty();

				let curId = 1;

				for (key in schemaEntry.properties) {
					const object = schemaEntry.properties[key];

					if (object.readOnly) {
						continue;
					}

					let mappingRow = mappingRowTemplate
						.replaceAll('ID_TEMPLATE', curId)
						.replace('VALUE_TEMPLATE', key);

					mappingArea.append(mappingRow);

					curId++;
				}

				mappingArea.ancestor('.card').removeClass('hide');
			})
			.catch((response) => {
				alert('FETCH failed ' + response);
			});
	}
</aui:script>