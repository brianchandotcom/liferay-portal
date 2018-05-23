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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderItem commerceOrderItem = commerceOrderEditDisplayContext.getCommerceOrderItem();

String title = LanguageUtil.format(request, "edit-x", commerceOrderItem.getName(locale), false);

renderResponse.setTitle(title);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderItemsPortletURL()));
%>

<div id="<portlet:namespace />editOrderItemContainer">
	<liferay-frontend:screen-navigation
		containerCssClass="col-md-10"
		key="<%= CommerceOrderScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_ITEM_GENERAL %>"
		modelBean="<%= commerceOrderItem %>"
		navCssClass="col-md-2"
		portletURL="<%= currentURLObj %>"
	/>
</div>