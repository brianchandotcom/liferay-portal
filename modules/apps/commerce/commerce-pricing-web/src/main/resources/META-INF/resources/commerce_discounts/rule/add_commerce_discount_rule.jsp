<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceDiscountRuleType> commerceDiscountRuleTypes = commerceDiscountDisplayContext.getCommerceDiscountRuleTypes();
%>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-discount-rule") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<aui:input bean="<%= commerceDiscountDisplayContext.getCommerceDiscountRule() %>" model="<%= CommerceDiscountRule.class %>" name="name" required="<%= true %>" />

		<aui:select label="rule-type" name="type" required="<%= true %>">

			<%
			for (CommerceDiscountRuleType commerceDiscountRuleType : commerceDiscountRuleTypes) {
			%>

				<aui:option label="<%= commerceDiscountRuleType.getLabel(locale) %>" value="<%= commerceDiscountRuleType.getKey() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>

	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/forms/index as FormUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
		var CommerceDiscountRuleResource = ServiceProvider.default.AdminPricingAPI(
			'v2'
		);

		Liferay.provide(
			window,
			'<portlet:namespace />apiSubmit',
			(form) => {
				var name = form.querySelector('#name').value;

				var commerceDiscountRuleType = form.querySelector('#type').value;

				var discountRuleData = {
					name: name,
					type: commerceDiscountRuleType,
				};

				return CommerceDiscountRuleResource.addDiscountRule(
					'<%= commerceDiscountDisplayContext.getCommerceDiscountId() %>',
					discountRuleData
				)
					.then((payload) => {
						window.parent.Liferay.fire(events.CLOSE_MODAL, {
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
			},
			['liferay-portlet-url']
		);
	</aui:script>
</commerce-ui:modal-content>