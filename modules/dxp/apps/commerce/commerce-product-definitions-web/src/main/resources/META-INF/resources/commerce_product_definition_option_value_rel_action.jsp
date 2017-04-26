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

CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel = null;

if (row != null) {
	commerceProductDefinitionOptionValueRel = (CommerceProductDefinitionOptionValueRel)row.getObject();
}
else {
	commerceProductDefinitionOptionValueRel = (CommerceProductDefinitionOptionValueRel)request.getAttribute("commerce_product_definition_option_value_rel_info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editProductDefinitionOptionValueRelURL">
		<portlet:param name="mvcRenderCommandName" value="editProductDefinitionOptionValueRel" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceProductDefinitionOptionValueRelId" value="<%= String.valueOf(commerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionValueRelId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editProductDefinitionOptionValueRelURL %>"
	/>

	<portlet:actionURL name="editProductDefinitionOptionValueRel" var="deleteProductDefinitionOptionValueRelURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceProductDefinitionOptionValueRelId" value="<%= String.valueOf(commerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionValueRelId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		message="delete"
		url="<%= deleteProductDefinitionOptionValueRelURL %>"
	/>
</liferay-ui:icon-menu>