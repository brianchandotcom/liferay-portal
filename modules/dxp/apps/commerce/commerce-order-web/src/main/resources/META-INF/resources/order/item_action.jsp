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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceOrderItem commerceOrderItem = (CommerceOrderItem)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-portlet:renderURL var="editURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="editCommerceOrderItem" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceOrderItemId" value="<%= String.valueOf(commerceOrderItem.getCommerceOrderItemId()) %>" />
	</liferay-portlet:renderURL>

	<%
	Map<String, Object> data = new HashMap<>();

	data.put("destroyOnHide", true);
	data.put("id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editOrderItemDialog");
	data.put("title", HtmlUtil.escape(commerceOrderItem.getName(locale)));
	%>

	<liferay-ui:icon
		data="<%= data %>"
		message="edit"
		url="<%= editURL %>"
		useDialog="<%= true %>"
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