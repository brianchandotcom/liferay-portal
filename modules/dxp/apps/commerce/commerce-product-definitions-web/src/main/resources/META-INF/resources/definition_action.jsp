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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CPDefinition cpDefinition = null;

if (row != null) {
	cpDefinition = (CPDefinition)row.getObject();
}
else {
	cpDefinition = (CPDefinition)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="editProductDefinition" />
		<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinition.getCPDefinitionId()) %>" />
		<portlet:param name="screenNavigationCategoryKey" value="<%= CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<%
	String productURL = cpDefinitionsDisplayContext.getProductURL(cpDefinition);
	%>

	<liferay-ui:icon
		iconCssClass="icon-new-window"
		message="view-in-public-store"
		target="_blank"
		url="<%= productURL %>"
	/>

	<portlet:actionURL name="editProductDefinition" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinition.getCPDefinitionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		label="<%= true %>"
		trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>