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

<liferay-frontend:edit-form
	action="<%= editBatchPlannerPlanURL %>"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (batchPlannerPlanId == 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
	<aui:input name="batchPlannerPlanId" type="hidden" value="<%= batchPlannerPlanId %>" />

	<liferay-frontend:edit-form-body>
		<aui:input bean="<%= batchPlannerPlan %>" model="<%= BatchPlannerPlan.class %>" name="name" />

		<aui:input bean="<%= batchPlannerPlan %>" model="<%= BatchPlannerPlan.class %>" name="export" />

		<aui:select bean="<%= batchPlannerPlan %>" model="<%= BatchPlannerPlan.class %>" name="externalType">
			<aui:option label="CSV" value="CSV" />
			<aui:option label="TXT" value="TXT" />
			<aui:option label="XLS" value="XLS" />
			<aui:option label="XML" value="XML" />
		</aui:select>

		<aui:input bean="<%= batchPlannerPlan %>" model="<%= BatchPlannerPlan.class %>" name="externalURL" />

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
					label="internal-class-name"
					name='<%= liferayPortletResponse.getNamespace() + "internalClassName" %>'
					options="<%= editBatchPlannerPlanDisplayContext.getSelectOptions() %>"
				/>
			</clay:col>
		</clay:row>

		<div class="form-group-autofit">
			<c:if test="<%= batchPlannerPlanId > 0 %>">
				<c:forEach items="<%= BatchPlannerPolicyServiceUtil.getBatchPlannerPolicies(batchPlannerPlanId) %>" var="batchPlannerPolicy">
					<div class="form-group-item">
						<aui:input name="policyName_${batchPlannerPolicy.batchPlannerPolicyId}" value="${batchPlannerPolicy.name}" />
					</div>

					<div class="form-group-item">
						<aui:input name="policyValue_${batchPlannerPolicy.batchPlannerPolicyId}" value="${batchPlannerPolicy.value}" />
					</div>
				</c:forEach>
			</c:if>

			<div class="form-group-item">
				<aui:input name="policyName" placeholder="name policy" value="" />
			</div>

			<div class="form-group-item">
				<aui:input name="policyValue" placeholder="policy value" value="" />
			</div>
		</div>

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
					<aui:input name="externalFieldName_ID_TEMPLATE" placeholder="external field name" value="" />
				</clay:col>

				<clay:col
					md="6"
				>
					<aui:input name="internalFieldName_ID_TEMPLATE" placeholder="open API field name" value="VALUE_TEMPLATE" />
				</clay:col>
			</clay:row>
		</clay:content-section>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

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

		var internalClassName = A.one(
			'#<portlet:namespace />internalClassName'
		).val();

		internalClassName = internalClassName.substr(
			internalClassName.lastIndexOf('.') + 1
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
				alert('FETCH failed ' + response);
			});
	}
</aui:script>