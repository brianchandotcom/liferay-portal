<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		<label class="control-label" for="catalogId"><liferay-ui:message key="catalog" /></label>

		<div id="autocomplete-root"></div>
	</aui:form>

	<portlet:renderURL var="editProductDefinitionURL">
		<portlet:param name="mvcRenderCommandName" value="/cp_definitions/edit_cp_definition" />
	</portlet:renderURL>

	<aui:script require="commerce-frontend-js/components/autocomplete/entry as autocomplete, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider, frontend-js-web/index as frontendJsWeb">
		const {createPortletURL} = frontendJsWeb;

		let defaultLanguageId = null;
		const product = {
			active: true,
			productStatus: <%= WorkflowConstants.STATUS_DRAFT %>,
			productType: '<%= cpDefinition.getProductTypeName() %>',
		};

		Liferay.provide(window, '<portlet:namespace />apiSubmit', (form) => {
			const API_URL =
				'/o/headless-commerce-admin-catalog/v1.0/products/<%= cpDefinition.getCProductId() %>/clone?catalogId=' +
				product.catalogId;

			FormUtils.apiSubmit(form, API_URL)
				.then((payload) => {
					const redirectURL = createPortletURL(
						'<%= editProductDefinitionURL %>',
						{
							cpDefinitionId: payload.id,
							p_p_state: '<%= LiferayWindowState.MAXIMIZED.toString() %>',
						}
					);

					window.parent.Liferay.fire(events.CLOSE_MODAL, {
						redirectURL: redirectURL.toString(),
						successNotification: {
							showSuccessNotification: true,
							message:
								'<liferay-ui:message key="your-request-completed-successfully" />',
						},
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
		});

		autocomplete.default('autocomplete', 'autocomplete-root', {
			apiUrl: '/o/headless-commerce-admin-catalog/v1.0/catalogs',
			inputId: '<portlet:namespace />catalogId',
			inputName: '<%= liferayPortletResponse.getNamespace() %>catalogId',
			itemsKey: 'id',
			itemsLabel: 'name',
			onValueUpdated: function (value, catalogData) {
				if (value) {
					product.catalogId = catalogData.id;
					defaultLanguageId = catalogData.defaultLanguageId;
				}
			},
			required: true,
		});
	</aui:script>
</commerce-ui:modal-content>