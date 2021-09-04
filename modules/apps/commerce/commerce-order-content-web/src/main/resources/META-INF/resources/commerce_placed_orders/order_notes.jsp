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
CommerceOrder commerceOrder = commerceOrderContentDisplayContext.getCommerceOrder();

String taglibIconCssClass = StringPool.BLANK;

String taglibMessage = "notes";

int commerceOrderNotesCount = commerceOrderContentDisplayContext.getCommerceOrderNotesCount(commerceOrder);

boolean showLabel = GetterUtil.getBoolean(request.getAttribute("order_notes.jsp-showLabel"));

if (!showLabel) {
	if (commerceOrderNotesCount <= 0) {
		taglibIconCssClass = "no-notes";
	}

	if (commerceOrderNotesCount == 1) {
		taglibMessage = LanguageUtil.get(request, "1-note");
	}
	else {
		taglibMessage = LanguageUtil.format(request, "x-notes", commerceOrderNotesCount, false);
	}
}
else {
	taglibIconCssClass = "inline-item inline-item-before";
}
%>

<c:if test="<%= commerceOrderNotesCount > 0 %>">
	<portlet:renderURL var="viewCommerceOrderNotesURL">
		<portlet:param name="mvcRenderCommandName" value="/commerce_order_content/view_commerce_order_notes" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		cssClass="notes-icon"
		icon="forms"
		iconCssClass="<%= taglibIconCssClass %>"
		label=""
		linkCssClass=""
		markupView="lexicon"
		message="<%= taglibMessage %>"
		method="get"
		url="<%= viewCommerceOrderNotesURL %>"
	/>
</c:if>