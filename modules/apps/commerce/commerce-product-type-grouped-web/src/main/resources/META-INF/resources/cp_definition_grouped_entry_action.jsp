<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CPDefinitionGroupedEntry cpDefinitionGroupedEntry = null;

if (row != null) {
	cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)row.getObject();
}
else {
	cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="/cp_definitions/edit_cp_definition_grouped_entry" />
		<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionId()) %>" />
		<portlet:param name="cpDefinitionGroupedEntryId" value="<%= String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId()) %>" />
		<portlet:param name="toolbarItem" value="<%= toolbarItem %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/cp_definitions/edit_cp_definition_grouped_entry" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="cpDefinitionGroupedEntryId" value="<%= String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>