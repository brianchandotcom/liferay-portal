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
CommerceCart commerceCart = (CommerceCart)request.getAttribute(CommerceWebKeys.COMMERCE_CART);

long commerceCartId = 0;

if (commerceCart != null) {
	commerceCartId = commerceCart.getCommerceCartId();
}

boolean defaultCart = BeanParamUtil.getBoolean(commerceCart, request, "defaultCart", false);
%>

<portlet:actionURL name="editCommerceWishList" var="editCommerceWishListActionURL" />

<aui:form action="<%= editCommerceWishListActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceCart == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceCartId" type="hidden" value="<%= commerceCartId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceCart %>" model="<%= CommerceCart.class %>" />

		<aui:input name="name" />

		<aui:input checked="<%= defaultCart %>" label="default" name="defaultCart" />
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="saveButton" value="save" />

		<aui:button cssClass="btn-lg" name="cancelButton" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,aui-io-request">
	A.one('#<portlet:namespace/>saveButton').on(
		'click',
		function(event) {
			var A = AUI();

			var url = '<%= editCommerceWishListActionURL.toString() %>';

			A.io.request(
				url,
				{
					method: 'POST',
					form: {
					id: '<portlet:namespace/>fm'},
					on: {
						success: function() {
							Liferay.Util.getOpener().refreshPortlet();
							Liferay.Util.getOpener().closePopup('editWishListDialog');
						}
					}
				}
			);
		}
	);

	A.one('#<portlet:namespace/>cancelButton').on(
		'click',
		function(event) {
			Liferay.Util.getOpener().closePopup('editWishListDialog');
		}
	);
</aui:script>