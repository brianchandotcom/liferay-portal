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
String cartToolbarItem = ParamUtil.getString(request, "cartToolbarItem", "view-all-carts");

String languageId = LanguageUtil.getLanguageId(locale);

CommerceCartItemDisplayContext commerceCartItemDisplayContext = (CommerceCartItemDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCart commerceCart = commerceCartItemDisplayContext.getCommerceCart();

CommerceCartItem commerceCartItem = commerceCartItemDisplayContext.getCommerceCartItem();

CPDefinition cpDefinition = commerceCartItem.getCPDefinition();

String lifecycle = (String)request.getAttribute(liferayPortletRequest.LIFECYCLE_PHASE);

PortletURL cartAdminURLObj = PortalUtil.getControlPanelPortletURL(request, CommercePortletKeys.COMMERCE_CART, lifecycle);

cartAdminURLObj.setParameter("toolbarItem", cartToolbarItem);

PortletURL cartItemsAdminURLObj = PortalUtil.getControlPanelPortletURL(request, CommercePortletKeys.COMMERCE_CART, lifecycle);

cartItemsAdminURLObj.setParameter("mvcRenderCommandName", "viewCommerceCartItems");
cartItemsAdminURLObj.setParameter("commerceCartId", String.valueOf(commerceCart.getCommerceCartId()));

String cartItemsAdminURL = cartItemsAdminURLObj.toString();

PortletURL cartItemsURL = renderResponse.createRenderURL();

cartItemsURL.setParameter("mvcRenderCommandName", "viewCommerceCartItems");
cartItemsURL.setParameter("commerceCartId", String.valueOf(commerceCart.getCommerceCartId()));

String title = commerceCart.getName() + " - " + cpDefinition.getTitle(languageId);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String cartToolbarItemLabel = "carts";

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, cartToolbarItemLabel), cartAdminURLObj.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, commerceCart.getName(), cartItemsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "cart"));
%>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceCartItem" var="editCommerceCartItemActionURL" />

<aui:form action="<%= editCommerceCartItemActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= cartItemsAdminURL %>" />
	<aui:input name="commerceCartId" type="hidden" value="<%= commerceCartItem.getCommerceCartId() %>" />
	<aui:input name="commerceCartItemId" type="hidden" value="<%= commerceCartItem.getCommerceCartItemId() %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceCartItem %>" model="<%= CommerceCartItem.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="quantity" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= cartItemsAdminURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>