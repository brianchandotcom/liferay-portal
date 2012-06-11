<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<aui:form name="fm">

<div class="categories-admin-container lfr-app-column-view">
	<div class="lfr-header-row">
		<div class="lfr-header-row-content">
			<div class="categories-admin-actions toolbar">
				<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.ADD_VOCABULARY) %>">
					<aui:button name="addVocabularyButton" value="add-vocabulary" />
				</c:if>

				<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.ADD_CATEGORY) %>">
					<aui:button cssClass="add-category-button" disabled="<%= true %>" name="addCategoryButton" value="add-category" />
				</c:if>

				<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.portlet.asset"
						modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
						resourcePrimKey="<%= String.valueOf(themeDisplay.getParentGroupId()) %>"
						var="permissionsURL"
						windowState="<%= LiferayWindowState.POP_UP.toString() %>"
					/>

					<aui:button cssClass="permissions-button" data-url="<%= permissionsURL %>" name="categoryPermissionsButton" value="permissions" />
				</c:if>

				<liferay-ui:icon-menu
					cssClass="category-actions"
					direction="down"
					icon=""
					message="actions"
					showExpanded="<%= false %>"
					showWhenSingleIcon="true"
				>
					<liferay-ui:icon
						id="deleteSelectedItems"
						image="delete"
						url="javascript:;"
					/>
				</liferay-ui:icon-menu>
			</div>

			<div class="lfr-search-combobox search-button-container categories-search-combobox">
				<aui:input cssClass="first keywords lfr-search-combobox-item categories-admin-search" label="" name="categoriesAdminSearchInput" type="text" />

				<aui:select cssClass="categories-admin-select-search" label="" name="categoriesAdminSelectSearch">
					<aui:option label="categories" />
					<aui:option label="vocabularies" selected="<%= true %>" />
				</aui:select>
			</div>
		</div>
	</div>

	<div class="categories-admin-content-wrapper">
		<aui:layout cssClass="categories-admin-content">
			<aui:column columnWidth="25" cssClass="vocabulary-list-container">
				<div class="results-header">
					<aui:input cssClass="select-vocabularies" inline="<%= true %>" label="" name="checkAllVocabularies" title='<%= LanguageUtil.get(pageContext, "check-all-vocabularies") %>' type="checkbox" />

					<liferay-ui:message key="vocabularies" />
				</div>

				<div class="vocabulary-message lfr-component"></div>

				<div class="vocabulary-list lfr-component"></div>

				<div class="vocabularies-paginator"></div>
			</aui:column>

			<aui:column columnWidth="75" cssClass="vocabulary-categories-container">
				<div class="results-header">
					<aui:input cssClass="select-categories" inline="<%= true %>" label="" name="checkAllCategories" title='<%= LanguageUtil.get(pageContext, "check-all-categories") %>' type="checkbox" />

					<liferay-ui:message key="categories" />
				</div>

				<div class="vocabulary-categories"></div>
			</aui:column>

			<aui:column columnWidth="35" cssClass="aui-helper-hidden vocabulary-edit-category">
				<div class="results-header">
					<liferay-ui:message key="category-details" />

					<div class="category-view-close">
						<span>
							<liferay-ui:icon
								id="category-view-close"
								image="close"
							/>
						</span>
					</div>
				</div>

				<div class="category-view"></div>
			</aui:column>
		</aui:layout>
	</div>
</div>

</aui:form>

<aui:script use="liferay-category-admin">
	new Liferay.Portlet.AssetCategoryAdmin(
		{
			itemsPerPage: <%= SearchContainer.DEFAULT_DELTA %>,
			itemsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>],
			portletId: '<%= portletDisplay.getId() %>'
		}
	);
</aui:script>