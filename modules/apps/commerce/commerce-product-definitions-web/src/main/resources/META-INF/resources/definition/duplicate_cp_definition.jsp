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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();
%>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "duplicate-product") %>'
>
	<aui:form cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="duplicatefm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>'>
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<aui:input name="name" required="<%= true %>" type="text" value='<%= LanguageUtil.format(locale, "copy-of-x", cpDefinition.getName(languageId)) %>' />

		<label class="control-label" for="catalogId"><%= LanguageUtil.get(request, "catalog") %></label>

		<div id="autocomplete-root"></div>
	</aui:form>

	<portlet:renderURL var="editProductDefinitionURL">
		<portlet:param name="mvcRenderCommandName" value="/cp_definitions/edit_cp_definition" />
	</portlet:renderURL>

	<aui:script require="commerce-frontend-js/components/autocomplete/entry as autocomplete, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
		var <portlet:namespace />defaultLanguageId = null;
		var <portlet:namespace />product = {
			active: true,
			productType: '<%= cpDefinition.getProductTypeName() %>',
		};

		Liferay.provide(
			window,
			'<portlet:namespace />apiSubmit',
			(form) => {
				var API_URL =
					'/o/headless-commerce-admin-catalog/v1.0/products/<%= cpDefinition.getCProductId() %>/clone?catalogId=' +
					<portlet:namespace />product.catalogId;

				FormUtils.apiSubmit(form, API_URL)
					.then((payload) => {
						var headers = new Headers({
							'Accept': 'application/json',
							'Content-Type': 'application/json',
						});

						var formattedData = {
							active: false,
							catalogId: <portlet:namespace />product.catalogId,
							name: {},
							productType: <portlet:namespace />product.productType,
						};

						formattedData.name[
							<portlet:namespace />defaultLanguageId
						] = document.getElementById('<portlet:namespace />name').value;

						Liferay.Util.fetch(
							'/o/headless-commerce-admin-catalog/v1.0/products/' +
								payload.productId,
							{
								body: JSON.stringify(formattedData),
								headers: headers,
								method: 'patch',
							}
						).then(() => {
							var redirectURL = new Liferay.PortletURL.createURL(
								'<%= editProductDefinitionURL %>'
							);

							redirectURL.setParameter('cpDefinitionId', payload.id);
							redirectURL.setParameter(
								'p_p_state',
								'<%= LiferayWindowState.MAXIMIZED.toString() %>'
							);

							window.parent.Liferay.fire(events.CLOSE_MODAL, {
								redirectURL: redirectURL.toString(),
								successNotification: {
									showSuccessNotification: true,
									message:
										'<liferay-ui:message key="your-request-completed-successfully" />',
								},
							});
						});
					})
					.catch(() => {
						window.parent.Liferay.fire(events.IS_LOADING_MODAL, {
							isLoading: false,
						});

						new Liferay.Notification({
							closeable: true,
							delay: {
								hide: 5000,
								show: 0,
							},
							duration: 500,
							message:
								'<liferay-ui:message key="an-unexpected-error-occurred" />',
							render: true,
							title: '<liferay-ui:message key="danger" />',
							type: 'danger',
						});
					});
			},
			['liferay-portlet-url']
		);

		autocomplete.default('autocomplete', 'autocomplete-root', {
			apiUrl: '/o/headless-commerce-admin-catalog/v1.0/catalogs',
			inputId: '<portlet:namespace />catalogId',
			inputName: '<%= liferayPortletResponse.getNamespace() %>catalogId',
			itemsKey: 'id',
			itemsLabel: 'name',
			onValueUpdated: function (value, catalogData) {
				if (value) {
					<portlet:namespace />product.catalogId = catalogData.id;
					<portlet:namespace />defaultLanguageId =
						catalogData.defaultLanguageId;
				}
			},
			required: true,
		});
	</aui:script>
</commerce-ui:modal-content>