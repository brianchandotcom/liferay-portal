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
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AssetListEntryAssetEntryRel assetListEntryAssetEntryRel = (AssetListEntryAssetEntryRel)row.getObject();

int position = assetListEntryAssetEntryRel.getPosition();

boolean last = (position == (searchContainer.getTotal() - 1));
%>

<c:choose>
	<c:when test="<%= (position > 0) && !last %>">
		<portlet:actionURL name="/asset_list/move_asset_entry_selection" var="moveAssetEntrySelectionDownURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="assetListEntryId" value="<%= String.valueOf(assetListEntryAssetEntryRel.getAssetListEntryId()) %>" />
			<portlet:param name="segmentsEntryId" value="<%= String.valueOf(assetListEntryAssetEntryRel.getSegmentsEntryId()) %>" />
			<portlet:param name="position" value="<%= String.valueOf(position) %>" />
			<portlet:param name="newPosition" value="<%= String.valueOf(position + 1) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			icon="angle-down"
			markupView="lexicon"
			message="down"
			url="<%= moveAssetEntrySelectionDownURL %>"
		/>
	</c:when>
	<c:when test="<%= (position == 0) && (searchContainer.getTotal() > 1) %>">
		<portlet:actionURL name="/asset_list/move_asset_entry_selection" var="moveAssetEntrySelectionDownURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="assetListEntryId" value="<%= String.valueOf(assetListEntryAssetEntryRel.getAssetListEntryId()) %>" />
			<portlet:param name="segmentsEntryId" value="<%= String.valueOf(assetListEntryAssetEntryRel.getSegmentsEntryId()) %>" />
			<portlet:param name="position" value="<%= String.valueOf(position) %>" />
			<portlet:param name="newPosition" value="<%= String.valueOf(position + 1) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			icon="angle-down"
			markupView="lexicon"
			message="down"
			url="<%= moveAssetEntrySelectionDownURL %>"
		/>
	</c:when>
	<c:when test="<%= last && (searchContainer.getTotal() > 1) %>">
		<portlet:actionURL name="/asset_list/move_asset_entry_selection" var="moveAssetEntrySelectionUpURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="assetListEntryId" value="<%= String.valueOf(assetListEntryAssetEntryRel.getAssetListEntryId()) %>" />
			<portlet:param name="segmentsEntryId" value="<%= String.valueOf(assetListEntryAssetEntryRel.getSegmentsEntryId()) %>" />
			<portlet:param name="position" value="<%= String.valueOf(position) %>" />
			<portlet:param name="newPosition" value="<%= String.valueOf(position - 1) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			icon="angle-up"
			markupView="lexicon"
			message="up"
			url="<%= moveAssetEntrySelectionUpURL %>"
		/>
	</c:when>
</c:choose>