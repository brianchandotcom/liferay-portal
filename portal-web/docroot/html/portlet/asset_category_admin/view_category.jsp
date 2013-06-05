<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_category_admin/init.jsp" %>

<%
long categoryId = ParamUtil.getLong(request, "categoryId");

AssetCategory category = AssetCategoryServiceUtil.getCategory(categoryId);

List<AssetCategoryProperty> categoryProperties = AssetCategoryPropertyServiceUtil.getCategoryProperties(category.getCategoryId());
%>

<div class="view-category">
	<liferay-ui:header
		localizeTitle="<%= false %>"
		title="<%= category.getTitle(locale) %>"
	/>

	<c:if test="<%= category != null %>">
		<c:if test="<%= AssetCategoryPermission.contains(permissionChecker, category, ActionKeys.UPDATE) %>">
			<aui:button id="editCategoryButton" value="edit" />
		</c:if>

		<c:if test="<%= AssetCategoryPermission.contains(permissionChecker, category, ActionKeys.DELETE) %>">
			<aui:button id="deleteCategoryButton" value="delete" />
		</c:if>

		<c:if test="<%= AssetCategoryPermission.contains(permissionChecker, category, ActionKeys.PERMISSIONS) %>">
			<liferay-security:permissionsURL
				modelResource="<%= AssetCategory.class.getName() %>"
				modelResourceDescription="<%= category.getTitle(locale) %>"
				resourcePrimKey="<%= String.valueOf(category.getCategoryId()) %>"
				var="permissionsURL"
				windowState="<%= LiferayWindowState.POP_UP.toString() %>"
			/>

			<aui:button data-url="<%= permissionsURL %>" id="updateCategoryPermissions" value="permissions" />
		</c:if>

		<c:if test="<%= AssetCategoryPermission.contains(permissionChecker, category, ActionKeys.ADD_CATEGORY) %>">
			<aui:button id="addSubCategoryButton" value="add-subcategory" />
		</c:if>
	</c:if>

	<c:if test="<%= Validator.isNotNull(category.getDescription(locale)) %>">
		<div class="category-field">
			<label><liferay-ui:message key="description" />:</label><aui:spacer /><%= HtmlUtil.escape(category.getDescription(locale)) %>
		</div>
	</c:if>

	<c:if test="<%= !categoryProperties.isEmpty() %>">
		<div class="category-field">
			<label><liferay-ui:message key="properties" />:</label>

			<%
			for (AssetCategoryProperty categoryProperty : categoryProperties) {
			%>

				<span class="property-key"><%= categoryProperty.getKey() %></span>: <span class="property-value"><%= categoryProperty.getValue() %></span><br />

			<%
			}
			%>

		</div>
	</c:if>
</div>