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

CommerceProductInstance commerceProductInstance = null;

if (row != null) {
	commerceProductInstance = (CommerceProductInstance)row.getObject();
}
else {
	commerceProductInstance = (CommerceProductInstance)request.getAttribute("commerce_product_instance_info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:actionURL name="editProductInstance" var="copyURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.COPY %>" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="commerceProductInstanceId" value="<%= String.valueOf(commerceProductInstance.getCommerceProductDefinitionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="copy"
		url="<%= copyURL %>"
	/>

	<portlet:actionURL name="editProductInstance" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="commerceProductInstanceId" value="<%= String.valueOf(commerceProductInstance.getCommerceProductDefinitionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="delete"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>