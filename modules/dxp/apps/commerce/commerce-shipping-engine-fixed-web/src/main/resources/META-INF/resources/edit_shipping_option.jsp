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
long commerceShippingMethodId = ParamUtil.getLong(request, "commerceShippingMethodId");

CommerceShippingFixedOptionsDisplayContext commerceShippingFixedOptionsDisplayContext = (CommerceShippingFixedOptionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingFixedOption commerceShippingFixedOption = commerceShippingFixedOptionsDisplayContext.getCommerceShippingFixedOption();

long commerceShippingFixedOptionId = 0;

if (commerceShippingFixedOption != null) {
	commerceShippingFixedOptionId = commerceShippingFixedOption.getCommerceShippingFixedOptionId();
}
%>

<portlet:actionURL name="editCommerceShippingFixedOption" var="editCommerceShippingFixedOptionActionURL" />

<div class="container-fluid-1280 sheet">
	<aui:form action="<%= editCommerceShippingFixedOptionActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceShippingFixedOption();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingFixedOption == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="commerceShippingFixedOptionId" type="hidden" value="<%= commerceShippingFixedOptionId %>" />
		<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />

		<div class="lfr-form-content">
			<aui:input autoFocus="<%= true %>" bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" name="name" />

			<aui:input bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" name="description" />

			<c:if test="<%= commerceShippingFixedOptionsDisplayContext.isFixed() %>">
				<aui:input name="amount" suffix="<%= commerceShippingFixedOptionsDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= (commerceShippingFixedOption == null) ? BigDecimal.ZERO : commerceShippingFixedOptionsDisplayContext.round(commerceShippingFixedOption.getAmount()) %>">
					<aui:validator name="number" />
				</aui:input>
			</c:if>

			<aui:input bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" name="priority" />
		</div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="saveButton" primary="<%= true %>" type="submit" value="save" />

			<aui:button cssClass="btn-lg" name="cancelButton" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveCommerceShippingFixedOption',
		function() {
			var A = AUI();

			var url = '<%= editCommerceShippingFixedOptionActionURL %>';

			A.io.request(
				url,
				{
					form: {
						id: '<portlet:namespace/>fm'
					},
					method: 'POST',
					on: {
						success: function() {
							Liferay.Util.getOpener().refreshPortlet();
							Liferay.Util.getOpener().closePopup('editShippingFixedOptionDialog');
						}
					}
				}
			);
		},
		['aui-base', 'aui-io-request']
	);
</aui:script>

<aui:script use="aui-base">
	A.one('#<portlet:namespace/>cancelButton').on(
		'click',
		function(event) {
			Liferay.Util.getOpener().closePopup('editShippingFixedOptionDialog');
		}
	);
</aui:script>