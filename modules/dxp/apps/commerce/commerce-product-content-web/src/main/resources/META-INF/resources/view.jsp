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
CPDefinition cpDefinition = (CPDefinition)request.getAttribute(CPWebKeys.CP_DEFINITION);
%>

<portlet:actionURL name="addCartItem" var="addCartItemURL" />

<c:if test="<%= cpDefinition != null %>">
	<aui:form action="<%= addCartItemURL %>" cssClass="container-fluid-1280" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cCartId" type="hidden" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= String.valueOf(cpDefinition.getCPDefinitionId()) %>" />
		<aui:input name="type" type="hidden" />

		<h1><%= cpDefinition.getTitle(languageId) %></h1>

		<img src="<%= cpDefinition.getDefaultImageThumbnailSrc(themeDisplay) %>">

		<p><%= cpDefinition.getDescription(languageId) %></p>

		<aui:input name="quantity" type="number" />

		<aui:button name="addToCart" type="submit" value="add-to-cart" />

		<aui:button name="addToWishList" type="submit" value="add-to-wish-list" />
	</aui:form>

	<aui:script use="aui-base,event-input">
		var form = A.one('#<portlet:namespace />fm');

		var addToCartButton = form.one('#<portlet:namespace />addToCart');
		var addToWishListButton = form.one('#<portlet:namespace />addToWishList');

		addToCartButton.on(
			'click',
			function() {
				var typeInput = form.one('#<portlet:namespace />type');

				if (typeInput) {
					typeInput.val(<%= CCartConstants.C_CART_TYPE_CART %>);
				}
			}
		);

		addToWishListButton.on(
			'click',
			function() {
				var typeInput = form.one('#<portlet:namespace />type');

				if (typeInput) {
					typeInput.val(<%= CCartConstants.C_CART_TYPE_WISH_LIST %>);
				}
			}
		);
	</aui:script>
</c:if>