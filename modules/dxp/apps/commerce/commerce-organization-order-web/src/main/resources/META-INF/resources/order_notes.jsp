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
CommerceOrganizationOrderDisplayContext commerceOrganizationOrderDisplayContext = (CommerceOrganizationOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrganizationOrderDisplayContext.getCommerceOrder();

if (commerceOrder == null) {
	ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

	commerceOrder = (CommerceOrder)row.getObject();
}

String taglibIconCssClass = StringPool.BLANK;
String taglibLinkCssClass = GetterUtil.getString(request.getAttribute("order_notes.jsp-taglibLinkCssClass"));
String taglibMessage = "notes";

if (taglibLinkCssClass == StringPool.BLANK) {
	taglibLinkCssClass = "table-action-link";
}

boolean showLabel = GetterUtil.getBoolean(request.getAttribute("order_notes.jsp-showLabel"));

if (!showLabel) {
	int commerceOrderNotesCount = commerceOrganizationOrderDisplayContext.getCommerceOrderNotesCount(commerceOrder);

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
	taglibIconCssClass = "inline-item inline-item-after";
}
%>

<portlet:renderURL var="editCommerceOrderNotesURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceOrderNotes" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
</portlet:renderURL>

<liferay-ui:icon
	cssClass="notes-icon"
	icon="forms"
	iconCssClass="<%= taglibIconCssClass %>"
	label="<%= showLabel %>"
	linkCssClass="<%= taglibLinkCssClass %>"
	markupView="lexicon"
	message="<%= taglibMessage %>"
	method="get"
	url="<%= editCommerceOrderNotesURL %>"
/>