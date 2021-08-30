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