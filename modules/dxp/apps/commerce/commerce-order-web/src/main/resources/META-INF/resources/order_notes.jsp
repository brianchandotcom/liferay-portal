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