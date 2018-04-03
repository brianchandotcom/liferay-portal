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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceOrderItem commerceOrderItem = (CommerceOrderItem)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<liferay-portlet:renderURL var="editURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="editCommerceOrderItem" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceOrderItemId" value="<%= String.valueOf(commerceOrderItem.getCommerceOrderItemId()) %>" />
	</liferay-portlet:renderURL>

	<%
	StringBundler sb = new StringBundler(13);

	sb.append("javascript:");
	sb.append(renderResponse.getNamespace());
	sb.append("editCommerceOrderItem");
	sb.append(StringPool.OPEN_PARENTHESIS);
	sb.append(StringPool.APOSTROPHE);
	sb.append(HtmlUtil.escapeJS(commerceOrderItem.getTitle(locale)));
	sb.append(StringPool.APOSTROPHE);
	sb.append(StringPool.COMMA_AND_SPACE);
	sb.append(StringPool.APOSTROPHE);
	sb.append(HtmlUtil.escapeJS(editURL));
	sb.append(StringPool.APOSTROPHE);
	sb.append(StringPool.CLOSE_PARENTHESIS);
	sb.append(StringPool.SEMICOLON);
	%>

	<liferay-ui:icon
		message="edit"
		url="<%= sb.toString() %>"
	/>

	<portlet:actionURL name="editCommerceOrderItem" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceOrderItemId" value="<%= String.valueOf(commerceOrderItem.getCommerceOrderItemId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>