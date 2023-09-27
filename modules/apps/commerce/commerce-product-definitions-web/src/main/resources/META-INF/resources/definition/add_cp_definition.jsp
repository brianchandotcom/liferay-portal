<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "create-new-product") %>'
>
	<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit();" %>'>
		<aui:input name="name" required="<%= true %>" type="text" />

		<label class="control-label" for="catalogId"><liferay-ui:message key="catalog" /></label>

		<div id="autocomplete-root"></div>
	</aui:form>

	<portlet:renderURL var="editProductDefinitionURL">
		<portlet:param name="mvcRenderCommandName" value="/cp_definitions/edit_cp_definition" />
	</portlet:renderURL>

	<aui:script require="commerce-frontend-js/components/autocomplete/entry as autocomplete, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/modals/index as ModalUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
		var <portlet:namespace />defaultLanguageId = null;
		var <portlet:namespace />productData = {
			active: true,
			productStatus: <%= WorkflowConstants.STATUS_DRAFT %>,
			productType: '<%= ParamUtil.getString(request, "productTypeName") %>',
		};

		var AdminCatalogResource = ServiceProvider.default.AdminCatalogAPI('v1');

		Liferay.provide(
			window,
			'<portlet:namespace />apiSubmit',
			() => {
				ModalUtils.isSubmitting();

				var formattedData = Object.assign(
					{},
					<portlet:namespace />productData,
					{
						defaultSku: '<%= CPInstanceConstants.DEFAULT_SKU %>',
						name: {},
					}
				);

				formattedData.name[
					<portlet:namespace />defaultLanguageId
				] = document.getElementById('<portlet:namespace />name').value;

				AdminCatalogResource.createProduct(formattedData)
					.then((cpDefinition) => {
						var redirectURL = new Liferay.PortletURL.createURL(
							'<%= editProductDefinitionURL %>'
						);

						redirectURL.setParameter(
							'p_p_state',
							'<%= LiferayWindowState.MAXIMIZED.toString() %>'
						);

						redirectURL.setParameter('cpDefinitionId', cpDefinition.id);

						ModalUtils.closeAndRedirect(redirectURL);
					})
					.catch(ModalUtils.onSubmitFail);
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
					<portlet:namespace />productData.catalogId = catalogData.id;
					<portlet:namespace />defaultLanguageId =
						catalogData.defaultLanguageId;
				}
			},
			required: true,
		});
	</aui:script>
</commerce-ui:modal-content>