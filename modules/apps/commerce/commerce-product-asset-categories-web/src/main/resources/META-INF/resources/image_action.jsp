<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CPAttachmentFileEntry cpAttachmentFileEntry = (CPAttachmentFileEntry)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="/commerce_product_asset_categories/edit_asset_category_cp_attachment_file_entry" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="categoryId" value="<%= String.valueOf(cpAttachmentFileEntry.getClassPK()) %>" />
		<portlet:param name="cpAttachmentFileEntryId" value="<%= String.valueOf(cpAttachmentFileEntry.getCPAttachmentFileEntryId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/commerce_product_asset_categories/edit_asset_category_cp_attachment_file_entry" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="cpAttachmentFileEntryId" value="<%= String.valueOf(cpAttachmentFileEntry.getCPAttachmentFileEntryId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>