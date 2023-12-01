<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePricingClassDisplayContext commercePricingClassDisplayContext = (CommercePricingClassDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL editPricingClassPortletURL = commercePricingClassDisplayContext.getEditCommercePricingClassRenderURL();

Locale defaultLocale = LocaleUtil.getSiteDefault();

String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
%>

<portlet:actionURL name="/commerce_pricing_classes/edit_commerce_pricing_class" var="editCommercePricingClassActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-product-group") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<aui:input label="name" name="title" required="<%= true %>" />

		<aui:input name="description" type="textarea" />
	</aui:form>

	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider, frontend-js-web/index as frontendJsWeb">
		const {createPortletURL} = frontendJsWeb;

		const CommerceProductGroupsResource = ServiceProvider.default.AdminCatalogAPI(
			'v1'
		);

		Liferay.provide(window, '<portlet:namespace />apiSubmit', (form) => {
			const description = document.getElementById('description').value;
			const title = document.getElementById('title').value;

			const productGroupData = {
				description: {<%= defaultLanguageId %>: description},
				title: {<%= defaultLanguageId %>: title},
			};

			return CommerceProductGroupsResource.addProductGroup(productGroupData)
				.then((payload) => {
					const redirectURL = createPortletURL(
						'<%= editPricingClassPortletURL %>',
						{
							commercePricingClassId: payload.id,
							p_auth: Liferay.authToken,
						}
					);

					window.parent.Liferay.fire(events.CLOSE_MODAL, {
						redirectURL: redirectURL,
						successNotification: {
							message:
								'<liferay-ui:message key="your-request-completed-successfully" />',
							showSuccessNotification: true,
						},
					});
				})
				.catch((error) => {
					return Promise.reject(error);
				});
		});
	</aui:script>
</commerce-ui:modal-content>