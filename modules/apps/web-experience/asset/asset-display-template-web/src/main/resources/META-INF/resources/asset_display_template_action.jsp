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

AssetDisplayTemplate assetDisplayTemplate = (AssetDisplayTemplate)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= AssetDisplayTemplatePermission.contains(permissionChecker, assetDisplayTemplate, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editAssetDisplayTemplateURL">
			<portlet:param name="mvcPath" value="/edit_asset_display_template.jsp" />
			<portlet:param name="assetDisplayTemplateId" value="<%= String.valueOf(assetDisplayTemplate.getAssetDisplayTemplateId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editAssetDisplayTemplateURL %>"
		/>
	</c:if>

	<c:if test="<%= AssetDisplayTemplatePermission.contains(permissionChecker, assetDisplayTemplate, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= AssetDisplayTemplate.class.getName() %>"
			modelResourceDescription="<%= assetDisplayTemplate.getName() %>"
			resourcePrimKey="<%= String.valueOf(assetDisplayTemplate.getAssetDisplayTemplateId()) %>"
			var="assetDisplayTemplatePermissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= assetDisplayTemplatePermissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= AssetDisplayTemplatePermission.contains(permissionChecker, assetDisplayTemplate, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/asset_display_template/delete_asset_display_template" var="deleteAssetDisplayTemplateURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="assetDisplayTemplateId" value="<%= String.valueOf(assetDisplayTemplate.getAssetDisplayTemplateId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteAssetDisplayTemplateURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>