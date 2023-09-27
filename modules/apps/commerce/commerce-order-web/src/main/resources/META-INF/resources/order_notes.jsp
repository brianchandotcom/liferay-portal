<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String taglibIconCssClass = "icon-file-text";
String taglibMessage = "notes";

CommerceOrderListDisplayContext commerceOrderListDisplayContext = (CommerceOrderListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceOrder commerceOrder = (CommerceOrder)row.getObject();

int commerceOrderNotesCount = commerceOrderListDisplayContext.getCommerceOrderNotesCount(commerceOrder);

if (commerceOrderNotesCount == 1) {
	taglibMessage = LanguageUtil.get(request, "1-note");
}
else {
	if (commerceOrderNotesCount <= 0) {
		taglibIconCssClass += " no-notes";
	}

	taglibMessage = LanguageUtil.format(request, "x-notes", commerceOrderNotesCount, false);
}
%>

<portlet:renderURL var="editCommerceOrderNotesURL">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order" />
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