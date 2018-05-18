<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

<aui:form action="<%= editCommerceShippingFixedOptionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingFixedOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="commerceShippingFixedOptionId" type="hidden" value="<%= commerceShippingFixedOptionId %>" />
	<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />

	<div class="lfr-form-content sheet">
		<aui:model-context bean="<%= commerceShippingFixedOption %>" model="<%= CommerceShippingFixedOption.class %>" />

		<aui:input autoFocus="<%= true %>" name="name" />

		<aui:input name="description" />

		<c:if test="<%= commerceShippingFixedOptionsDisplayContext.isFixed() %>">
			<aui:input name="amount" suffix="<%= commerceShippingFixedOptionsDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= (commerceShippingFixedOption == null) ? commerceShippingFixedOptionsDisplayContext.format(BigDecimal.ZERO) : commerceShippingFixedOptionsDisplayContext.format(commerceShippingFixedOption.getAmount()) %>" />
		</c:if>

		<aui:input name="priority" />
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="saveButton" primary="<%= true %>" value="save" />

		<aui:button cssClass="btn-lg" name="cancelButton" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,aui-io-request">
	A.one('#<portlet:namespace/>saveButton').on(
		'click',
		function(event) {
			var A = AUI();

			var url = '<%= editCommerceShippingFixedOptionActionURL.toString() %>';

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
		}
	);

	A.one('#<portlet:namespace/>cancelButton').on(
		'click',
		function(event) {
			Liferay.Util.getOpener().closePopup('editShippingFixedOptionDialog');
		}
	);
</aui:script>