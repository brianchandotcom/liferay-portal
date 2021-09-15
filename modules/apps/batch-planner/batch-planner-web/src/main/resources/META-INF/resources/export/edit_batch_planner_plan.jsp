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

<portlet:actionURL name="/batch_planner/edit_export_batch_planner_plan" var="exportBatchPlannerPlanURL" />

<liferay-frontend:edit-form
	action="<%= exportBatchPlannerPlanURL %>"
>
	<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
	<aui:input name="batchPlannerPlanId" type="hidden" value="<%= batchPlannerPlanId %>" />
	<aui:input name="export" type="hidden" value="<%= true %>" />

	<liferay-frontend:edit-form-body>
		<aui:input name="name" />

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
					options="<%= editBatchPlannerPlanDisplayContext.getSelectOptions() %>"
				/>
			</clay:col>
		</clay:row>

		<clay:content-section>
			<clay:row>
				<clay:col>
					<clay:select
						label="external-type"
						name="externalType"
						options='<%=
							Arrays.asList(new SelectOption("CSV", "CSV"), new SelectOption("TXT", "TXT"), new SelectOption("XLS", "XLS"), new SelectOption("XML", "XML"))
						%>'
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

		<clay:sheet-section>
			<clay:content-section>
				<clay:row
					cssClass="plan-mappings"
				>

				</clay:row>

				<clay:row
					cssClass="hide plan-mappings-template"
				>
					<div class="input-group">
						<div class="input-group-item input-group-item-shrink input-group-prepend">
							<span class="input-group-text input-group-text-secondary">
								<div class="custom-checkbox custom-control">
									<label>
										<input aria-label="Checkbox for following text input" class="custom-control-input" type="checkbox" checked
											id='<%= liferayPortletResponse.getNamespace() + "externalFieldName_ID_TEMPLATE" %>'
											name='<%= liferayPortletResponse.getNamespace() + "externalFieldName_ID_TEMPLATE" %>'
										/>

										<span class="custom-control-label"></span>
									</label>
								</div>
							</span>
						</div>

						<div class="input-group-append input-group-item">
							<input aria-label="Text input with checkbox" class="form-control" id="<%= liferayPortletResponse.getNamespace() + "internalFieldName_ID_TEMPLATE" %>" name="<%= liferayPortletResponse.getNamespace() + "internalFieldName_ID_TEMPLATE" %>" placeholder="Liferay object field name" type="text" value="VALUE_TEMPLATE" />
						</div>
					</div>
				</clay:row>
			</clay:content-section>
		</clay:sheet-section>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button name="export" type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

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

					let xClassName = properties['x-class-name'];

					internalClassName.appendChild(
						'<option value="' +
							xClassName.default +
							'">' +
							key +
							'</option>'
					);
				}

				<portlet:namespace />renderMappings();

				internalClassName.attr('disabled', false);
			})
			.catch((response) => {
				alert('Failed to fetch ' + response);
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
		var openapiURL = A.one('#<portlet:namespace />headlessEndpoint').val();

		var internalClassName = A.one(
			'#<portlet:namespace />internalClassName'
		).val();

		internalClassName = internalClassName.substr(
			internalClassName.lastIndexOf('.') + 1
		);

		Liferay.Util.fetch(openapiURL, {
			method: 'GET',
			credentials: 'include',
			headers: [
				['content-type', 'application/json'],
				['x-csrf-token', window.Liferay.authToken],
			],
		})
			.then((response) => {
				if (!response.ok) {
					throw new Error(`Failed to fetch: '${openapiURL}'`);
				}

				return response.json();
			})
			.then((jsonResponse) => {
				let schemas = jsonResponse.components.schemas;

				let schemaEntry = schemas[internalClassName];

				var mappingArea = A.one('.plan-mappings');
				var mappingRowTemplate = A.one(
					'.plan-mappings-template'
				).getContent();

				mappingArea.empty();

				let curId = 1;

				for (key in schemaEntry.properties) {
					let mappingRow = mappingRowTemplate
						.replaceAll('ID_TEMPLATE', curId)
						.replace('VALUE_TEMPLATE', key);

					mappingArea.append(mappingRow);

					curId++;
				}
			})
			.catch((response) => {
				alert('Failed to fetch ' + response);
			});
	}
</aui:script>