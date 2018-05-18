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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
CommerceOrderItem commerceOrderItem = commerceOrderEditDisplayContext.getCommerceOrderItem();

CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

renderResponse.setTitle(commerceOrderItem.getName(locale));
%>

<portlet:actionURL name="editCommerceOrderItem" var="editCommerceOrderItemActionURL" />

<aui:form action="<%= editCommerceOrderItemActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />

	<div class="lfr-form-content sheet">
		<aui:model-context bean="<%= commerceOrderItem %>" model="<%= CommerceOrderItem.class %>" />

		<aui:input name="quantity" />

		<c:if test="<%= !commerceOrder.isOpen() %>">
			<aui:input name="price" suffix="<%= commerceCurrency.getCode() %>" type="text" value="<%= commerceOrderEditDisplayContext.format(commerceOrderItem.getPrice()) %>" />
		</c:if>
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

			var url = '<%= editCommerceOrderItemActionURL.toString() %>';

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
							Liferay.Util.getOpener().closePopup('<portlet:namespace/>editOrderItemDialog');
						}
					}
				}
			);
		}
	);

	A.one('#<portlet:namespace/>cancelButton').on(
		'click',
		function(event) {
			Liferay.Util.getOpener().closePopup('<portlet:namespace/>editOrderItemDialog');
		}
	);
</aui:script>