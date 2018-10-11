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

AssetListEntry assetListEntry = (AssetListEntry)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= AssetListEntryPermission.contains(permissionChecker, assetListEntry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editAssetListEntryURL">
			<portlet:param name="mvcPath" value="/edit_asset_list_entry.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="assetListEntryId" value="<%= String.valueOf(assetListEntry.getAssetListEntryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editAssetListEntryURL %>"
		/>

		<portlet:actionURL name="/asset_list/update_asset_list_entry" var="updateAssetListEntryURL">
			<portlet:param name="assetListEntryId" value="<%= String.valueOf(assetListEntry.getAssetListEntryId()) %>" />
		</portlet:actionURL>

		<%
		Map<String, Object> updateAssetListEntryData = new HashMap<>();

		updateAssetListEntryData.put("form-submit-url", updateAssetListEntryURL.toString());
		updateAssetListEntryData.put("id-field-value", assetListEntry.getAssetListEntryId());
		updateAssetListEntryData.put("main-field-value", assetListEntry.getTitle());
		%>

		<liferay-ui:icon
			cssClass='<%= renderResponse.getNamespace() + "update-asset-list-entry-action-option" %>'
			data="<%= updateAssetListEntryData %>"
			message="rename"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= AssetListEntryPermission.contains(permissionChecker, assetListEntry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= AssetListEntry.class.getName() %>"
			modelResourceDescription="<%= assetListEntry.getTitle() %>"
			resourcePrimKey="<%= String.valueOf(assetListEntry.getAssetListEntryId()) %>"
			var="assetListEntryPermissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= assetListEntryPermissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<portlet:renderURL var="viewAssetListContentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/view_content.jsp" />
		<portlet:param name="assetListEntryId" value="<%= String.valueOf(assetListEntry.getAssetListEntryId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view-content"
		url="<%= viewAssetListContentURL %>"
		useDialog="<%= true %>"
	/>

	<portlet:renderURL var="viewAssetListEntryUsagesURL">
		<portlet:param name="mvcPath" value="/view_asset_list_entry_usages.jsp" />
		<portlet:param name="assetListEntryId" value="<%= String.valueOf(assetListEntry.getAssetListEntryId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view-usages"
		url="<%= viewAssetListEntryUsagesURL %>"
	/>

	<c:if test="<%= AssetListEntryPermission.contains(permissionChecker, assetListEntry, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/asset_list/delete_asset_list_entry" var="deleteAssetListEntryURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="assetListEntryId" value="<%= String.valueOf(assetListEntry.getAssetListEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteAssetListEntryURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>