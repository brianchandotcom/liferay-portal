<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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