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
String redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceProductOption commerceProductOption = null;

if (row != null) {
	commerceProductOption = (CommerceProductOption)row.getObject();
}
else {
	commerceProductOption = (CommerceProductOption)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editURL">
		<portlet:param
			name="commerceProductOptionId"
			value="<%= String.valueOf(commerceProductOption.getCommerceProductOptionId()) %>"
		/>

		<portlet:param
			name="mvcRenderCommandName"
			value="/commerce_product_options/edit_product_option"
		/>

		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= PortalUtil.getCurrentCompleteURL(request) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/editProductOption" var="deleteURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(commerceProductOption.getGroupId()) %>" />
		<portlet:param name="commerceProductOptionId" value="<%= String.valueOf(commerceProductOption.getCommerceProductOptionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="delete"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>