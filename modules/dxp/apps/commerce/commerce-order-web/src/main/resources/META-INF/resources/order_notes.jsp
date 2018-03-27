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
CommerceOrderListDisplayContext commerceOrderListDisplayContext = (CommerceOrderListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceOrder commerceOrder = (CommerceOrder)row.getObject();

String taglibIconCssClass = "icon-file-text";
String taglibMessage = "notes";

int commerceOrderNotesCount = commerceOrderListDisplayContext.getCommerceOrderNotesCount(commerceOrder);

if (commerceOrderNotesCount <= 0) {
	taglibIconCssClass += " no-notes";
}

if (commerceOrderNotesCount == 1) {
	taglibMessage = LanguageUtil.get(request, "1-note");
}
else {
	taglibMessage = LanguageUtil.format(request, "x-notes", commerceOrderNotesCount, false);
}
%>

<portlet:renderURL var="editCommerceOrderNotesURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceOrder" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
	<portlet:param name="screenNavigationCategoryKey" value="<%= CommerceOrderScreenNavigationConstants.CATEGORY_KEY_COMMERCE_ORDER_NOTES %>" />
</portlet:renderURL>

<liferay-ui:icon
	cssClass="notes-icon"
	iconCssClass="<%= taglibIconCssClass %>"
	message="<%= taglibMessage %>"
	method="get"
	url="<%= editCommerceOrderNotesURL %>"
/>