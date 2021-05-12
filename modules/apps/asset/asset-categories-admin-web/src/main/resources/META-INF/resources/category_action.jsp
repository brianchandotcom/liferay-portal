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

AssetCategory category = (AssetCategory)row.getObject();

String displayPageURL = assetCategoriesDisplayContext.getDisplayPageURL(category);
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= assetCategoriesDisplayContext.hasPermission(category, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editCategoryURL">
			<portlet:param name="mvcPath" value="/edit_category.jsp" />
			<portlet:param name="categoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
			<portlet:param name="vocabularyId" value="<%= String.valueOf(category.getVocabularyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editCategoryURL %>"
		/>
	</c:if>

	<c:if test="<%= assetCategoriesDisplayContext.hasPermission(category, ActionKeys.ADD_CATEGORY) %>">
		<portlet:renderURL var="addSubcategoryCategoryURL">
			<portlet:param name="mvcPath" value="/edit_category.jsp" />
			<portlet:param name="vocabularyId" value="<%= String.valueOf(category.getVocabularyId()) %>" />
			<portlet:param name="parentCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="add-subcategory"
			url="<%= addSubcategoryCategoryURL %>"
		/>
	</c:if>

	<c:if test="<%= assetCategoriesDisplayContext.hasPermission(category, ActionKeys.UPDATE) %>">
		<liferay-ui:icon
			id='<%= row.getRowId() + "moveCategory" %>'
			message="move"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= Validator.isNotNull(displayPageURL) %>">
		<liferay-ui:icon
			message="view-display-page"
			url="<%= displayPageURL %>"
		/>
	</c:if>

	<c:if test="<%= assetCategoriesDisplayContext.hasPermission(category, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= AssetCategory.class.getName() %>"
			modelResourceDescription="<%= category.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(category.getCategoryId()) %>"
			var="permissionsCategoryURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsCategoryURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= assetCategoriesDisplayContext.hasPermission(category, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteCategory" var="deleteCategoryURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="categoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			confirmation="this-category-might-be-being-used-in-some-contents"
			url="<%= deleteCategoryURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>

<c:if test="<%= assetCategoriesDisplayContext.hasPermission(category, ActionKeys.UPDATE) %>">
	<aui:script sandbox="<%= true %>">
		const moveCategoryIcon = document.getElementById(
			'<portlet:namespace /><%= row.getRowId() %>moveCategory'
		);

		if (moveCategoryIcon) {
			moveCategoryIcon.addEventListener('click', (event) => {
				Liferay.Util.openSelectionModal({
					multiple: true,
					onSelect: (selectedItems) => {
						if (!selectedItems) {
							return;
						}

						const item = Object.values(selectedItems).find(
							(item) => !item.unchecked
						);

						const categoryId = '<%= category.getCategoryId() %>';
						const parentCategoryId = item.categoryId || 0;
						const vocabularyId = item.vocabularyId || 0;

						if (categoryId === parentCategoryId) {
							Liferay.Util.openToast({
								message:
									'<liferay-ui:message arguments="<%= category.getTitle(locale) %>" key="unable-to-move-the-category-x-into-itself" />',
								type: 'danger',
							});

							return;
						}

						if (vocabularyId > 0 || parentCategoryId > 0) {
							const form = document.getElementById(
								'<portlet:namespace />moveCategoryFm'
							);

							if (form) {
								form.<portlet:namespace />categoryId.value = categoryId;
								form.<portlet:namespace />parentCategoryId.value = parentCategoryId;
								form.<portlet:namespace />vocabularyId.value = vocabularyId;

								submitForm(form);
							}
						}
					},
					selectEventName: '<portlet:namespace />selectCategory',
					title:
						'<liferay-ui:message arguments="<%= category.getTitle(locale) %>" key="move-x" />',
					url:
						'<%= assetCategoriesDisplayContext.getSelectCategoryURL(category.getVocabularyId()) %>',
				});
			});
		}
	</aui:script>
</c:if>