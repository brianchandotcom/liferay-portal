<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_discount/edit_commerce_discount" var="editCommerceDiscountActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-discount") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<aui:input bean="<%= commerceDiscountDisplayContext.getCommerceDiscount() %>" label="name" model="<%= CommerceDiscount.class %>" name="title" required="<%= true %>" />

		<aui:select label="type" name="commerceDiscountType" required="<%= true %>">

			<%
			for (String commerceDiscountType : CommerceDiscountConstants.TYPES) {
			%>

				<aui:option label="<%= commerceDiscountType %>" value="<%= commerceDiscountDisplayContext.getUsePercentage(commerceDiscountType) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="apply-to" name="commerceDiscountTarget" required="<%= true %>">

			<%
			for (CommerceDiscountTarget commerceDiscountTarget : commerceDiscountDisplayContext.getCommerceDiscountTargets()) {
			%>

				<aui:option label="<%= commerceDiscountTarget.getLabel(locale) %>" value="<%= commerceDiscountTarget.getKey() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>

	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider, frontend-js-web/index as frontendJsWeb">
		const {createPortletURL} = frontendJsWeb;
		const CommerceDiscountResource = ServiceProvider.default.AdminPricingAPI('v2');

		Liferay.provide(window, '<portlet:namespace />apiSubmit', (form) => {
			const discountData = {
				level: '<%= CommerceDiscountConstants.LEVEL_L1 %>',
				limitationType:
					'<%= CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED %>',
				target: document.getElementById('commerceDiscountTarget').value,
				title: document.getElementById('title').value,
				usePercentage: document.getElementById('commerceDiscountType').value,
			};

			return CommerceDiscountResource.addDiscount(discountData)
				.then((payload) => {
					const redirectURL = createPortletURL(
						'<%= commerceDiscountDisplayContext.getEditCommerceDiscountRenderURL() %>',
						{
							commerceDiscountId: payload.id,
							p_auth: Liferay.authToken,
							usePercentage: payload.usePercentage,
						}
					);

					window.parent.Liferay.fire(events.CLOSE_MODAL, {
						redirectURL: redirectURL,
						successNotification: {
							showSuccessNotification: true,
							message:
								'<liferay-ui:message key="your-request-completed-successfully" />',
						},
					});
				})
				.catch((error) => {
					return Promise.reject(error);
				});
		});
	</aui:script>
</commerce-ui:modal-content>