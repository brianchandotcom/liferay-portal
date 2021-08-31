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
long batchPlannerPlanId = ParamUtil.getLong(renderRequest, "batchPlannerPlanId");

String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

renderResponse.setTitle(LanguageUtil.get(request, "export"));
%>

<portlet:actionURL name="/batch_planner/edit_export_batch_planner_plan" var="exportBatchPlannerPlanURL" />

<liferay-frontend:edit-form
	action="<%= exportBatchPlannerPlanURL %>"
>
	<aui:input name="export" type="hidden" value="<%= true %>" />
	<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
	<aui:input name="batchPlannerPlanId" type="hidden" value="<%= batchPlannerPlanId %>" />

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
					containerCssClass="custom-container-css-class"
					cssClass="custom-css-class"
					id='<%= liferayPortletResponse.getNamespace() + "headlessEndpoint" %>'
					label="headless-endpoint"
					name='<%= liferayPortletResponse.getNamespace() + "headlessEndpoint" %>'
					options="<%= editBatchPlannerPlanDisplayContext.getSelectOptions() %>"
				/>
			</clay:col>

			<clay:col
				md="6"
			>
				<clay:select
					containerCssClass="custom-container-css-class"
					cssClass="custom-css-class"
					disabled="<%= true %>"
					id='<%= liferayPortletResponse.getNamespace() + "internalClassName" %>'
					label="entity-type"
					name='<%= liferayPortletResponse.getNamespace() + "internalClassName" %>'
					options="<%= editBatchPlannerPlanDisplayContext.getSelectOptions() %>"
				/>
			</clay:col>
		</clay:row>

		<clay:content-section>
			<clay:row>
				<clay:col>

					<%
					List<SelectOption> selectOptions = new ArrayList<>();

					selectOptions.add(new SelectOption("CSV", "CSV"));
					selectOptions.add(new SelectOption("TXT", "TXT"));
					selectOptions.add(new SelectOption("XLS", "XLS"));
					selectOptions.add(new SelectOption("XML", "XML"));
					%>

					<clay:select
						id='<%= liferayPortletResponse.getNamespace() + "externalType" %>'
						label="external-type"
						name='<%= liferayPortletResponse.getNamespace() + "externalType" %>'
						options="<%= selectOptions %>"
					/>
				</clay:col>
			</clay:row>

			<clay:row>
				<clay:col
					md="6"
				>
					<clay:checkbox
						id='<%= liferayPortletResponse.getNamespace() + "policySaveForLaterDownload" %>'
						label="save-export-to-the-server-for-later-download"
						name='<%= liferayPortletResponse.getNamespace() + "policySaveForLaterDownload" %>'
					/>
				</clay:col>
			</clay:row>

			<clay:row>
				<clay:col
					md="6"
				>
					<clay:checkbox
						checked="<%= true %>"
						id='<%= liferayPortletResponse.getNamespace() + "policyHasColumnHeaders" %>'
						label="this-file-contains-headers"
						name='<%= liferayPortletResponse.getNamespace() + "policyHasColumnHeaders" %>'
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

				renderMappings();

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

		renderMappings();

		this.attr('disabled', false);
	});

	function renderMappings() {
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