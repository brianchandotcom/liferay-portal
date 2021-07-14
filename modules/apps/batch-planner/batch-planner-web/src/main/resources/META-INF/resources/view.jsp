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

BatchPlannerPlan batchPlannerPlan = BatchPlannerPlanServiceUtil.fetchBatchPlannerPlan(batchPlannerPlanId);

String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

renderResponse.setTitle((batchPlannerPlan == null) ? LanguageUtil.get(request, "add") : LanguageUtil.get(request, "edit"));
%>

<portlet:actionURL name="/batch_planner/add_batch_planner_plan" var="editBatchPlannerPlanURL" />

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

		<aui:input bean="<%= batchPlannerPlan %>" model="<%= BatchPlannerPlan.class %>" name="internalClassName" />

		<%
		SelectHeadlessEndpointDisplayContext selectHeadlessEndpointDisplayContext = (SelectHeadlessEndpointDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
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
					options="<%= selectHeadlessEndpointDisplayContext.getHeadlessEndpointSelectOptions() %>"
				/>

				<clay:select
					containerCssClass="custom-container-css-class"
					cssClass="custom-css-class"
					disabled="<%= true %>"
					id='<%= liferayPortletResponse.getNamespace() + "headlessEndpointSchema" %>'
					label="headless-endpoint-schema"
					name='<%= liferayPortletResponse.getNamespace() + "headlessEndpointSchema" %>'
					options="<%= selectHeadlessEndpointDisplayContext.getHeadlessEndpointSelectOptions() %>"
				/>
			</clay:col>
		</clay:row>

		<div class="form-group-autofit">
			<c:if test="<%= batchPlannerPlanId > 0 %>">
				<c:forEach items="<%= BatchPlannerPolicyServiceUtil.getBatchPlannerPolicies(batchPlannerPlanId) %>" var="batchPlannerPolicy">
					<div class="form-group-item">
						<aui:input name="name${batchPlannerPolicy.batchPlannerPolicyId}" value="${batchPlannerPolicy.name}" />
					</div>

					<div class="form-group-item">
						<aui:input name="value${batchPlannerPolicy.batchPlannerPolicyId}" value="${batchPlannerPolicy.value}" />
					</div>
				</c:forEach>
			</c:if>

			<div class="form-group-item">
				<aui:input name="nameDefaultId" placeholder="name policy" value="" />
			</div>

			<div class="form-group-item">
				<aui:input name="valueDefaultId" placeholder="policy value" value="" />
			</div>
		</div>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="aui-io-request,aui-parse-content">
	A.one('#<portlet:namespace />headlessEndpoint').on('change', function (event) {
		this.attr('disabled', true);

		var openapiURL = A.one('#<portlet:namespace />headlessEndpoint').val();

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
				alert('I see dead objects: ' + jsonResponse.components);
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
</aui:script>