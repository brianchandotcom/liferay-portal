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
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(assetCategoriesDisplayContext.getCategoriesRedirect());

renderResponse.setTitle(assetCategoriesDisplayContext.getCategoryTitle());

AssetCategoryUtil.addPortletBreadcrumbEntry(assetCategoriesDisplayContext.getVocabulary(), assetCategoriesDisplayContext.getCategory(), request, renderResponse);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= assetCategoriesDisplayContext.getAssetCategoriesNavigationItems() %>"
/>

<clay:management-toolbar
	actionItems="<%= assetCategoriesDisplayContext.getCategoriesActionItemsDropdownItems() %>"
	clearResultsURL="<%= assetCategoriesDisplayContext.getCategoriesClearResultsURL() %>"
	componentId="assetCategoriesManagementToolbar"
	creationMenu="<%= assetCategoriesDisplayContext.isShowCategoriesAddButton() ? assetCategoriesDisplayContext.getCategoriesCreationMenu() : null %>"
	disabled="<%= assetCategoriesDisplayContext.isDisabledCategoriesManagementBar() %>"
	filterItems="<%= assetCategoriesDisplayContext.getCategoriesFilterItemsDropdownItems() %>"
	searchActionURL="<%= assetCategoriesDisplayContext.getCategoriesSearchActionURL() %>"
	searchContainerId="assetCategories"
	searchFormName="searchFm"
	showSearch="<%= assetCategoriesDisplayContext.isShowCategoriesSearch() %>"
	sortingOrder="<%= assetCategoriesDisplayContext.getOrderByType() %>"
	sortingURL="<%= assetCategoriesDisplayContext.getCategoriesSortingURL() %>"
	totalItems="<%= assetCategoriesDisplayContext.getCategoriesTotalItems() %>"
	viewTypes="<%= assetCategoriesDisplayContext.getCategoriesViewTypeItems() %>"
/>

<portlet:actionURL name="deleteCategory" var="deleteCategoryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteCategoryURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		id="assetCategories"
		searchContainer="<%= assetCategoriesDisplayContext.getCategoriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetCategory"
			keyProperty="categoryId"
			modelVar="curCategory"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/view_categories.jsp" />
				<portlet:param name="categoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
				<portlet:param name="vocabularyId" value="<%= String.valueOf(curCategory.getVocabularyId()) %>" />
			</portlet:renderURL>

			<%
			int subcategoriesCount = AssetCategoryLocalServiceUtil.getChildCategoriesCount(curCategory.getCategoryId());
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(assetCategoriesDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="categories"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h6 class="text-default">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - curCategory.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</h6>

						<h5>
							<aui:a href="<%= rowURL.toString() %>"><%= HtmlUtil.escape(curCategory.getTitle(locale)) %></aui:a>
						</h5>

						<h6 class="text-default">
							<%= HtmlUtil.escape(curCategory.getDescription(locale)) %>
						</h6>

						<h6 class="text-default">
							<liferay-ui:message arguments="<%= subcategoriesCount %>" key="x-subcategories" />
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/category_action.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals(assetCategoriesDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/category_action.jsp"
							actionJspServletContext="<%= application %>"
							icon="categories"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							subtitle="<%= curCategory.getDescription(locale) %>"
							title="<%= curCategory.getName() %>"
							url="<%= rowURL != null ? rowURL.toString() : null %>"
						>
							<liferay-frontend:vertical-card-header>
								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - curCategory.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<liferay-ui:message arguments="<%= subcategoriesCount %>" key="x-subcategories" />
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(assetCategoriesDisplayContext.getDisplayStyle(), "list") %>'>
					<c:choose>
						<c:when test="<%= assetCategoriesDisplayContext.isFlattenedNavigationAllowed() %>">
							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="category"
								value="<%= HtmlUtil.escape(curCategory.getTitle(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="path"
							>
								<%= HtmlUtil.escape(curCategory.getPath(locale, true)) %> > <strong><%= HtmlUtil.escape(curCategory.getTitle(locale)) %></strong>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="category"
								value="<%= HtmlUtil.escape(curCategory.getTitle(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="description"
								value="<%= HtmlUtil.escape(curCategory.getDescription(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="subcategories"
								value="<%= String.valueOf(subcategoriesCount) %>"
							/>
						</c:otherwise>
					</c:choose>

					<liferay-ui:search-container-column-date
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-jsp
						path="/category_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= assetCategoriesDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="moveCategory" var="moveCategoryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="mvcPath" value="/view_categories.jsp" />
</portlet:actionURL>

<aui:form action="<%= moveCategoryURL %>" name="moveCategoryFm">
	<aui:input name="categoryId" type="hidden" />
	<aui:input name="parentCategoryId" type="hidden" />
	<aui:input name="vocabularyId" type="hidden" />
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	window.<portlet:namespace />selectCategory = function() {
		var itemSelectorDialog = new A.LiferayItemSelectorDialog(
			{
				eventName: '<portlet:namespace />selectCategory',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;
						var category = selectedItem ? selectedItem[Object.keys(selectedItem)[0]] : null;

						if (category) {
							var uri = '<portlet:renderURL><portlet:param name="mvcPath" value="/view_categories.jsp" /><portlet:param name="navigation" value="all" /><portlet:param name="vocabularyId" value="<%= String.valueOf(assetCategoriesDisplayContext.getVocabularyId()) %>" /></portlet:renderURL>';

							uri = Liferay.Util.addParams('<portlet:namespace />categoryId=' + category.categoryId, uri);

							location.href = uri;
						}
					}
				},
				strings: {
					add: '<liferay-ui:message key="select" />',
					cancel: '<liferay-ui:message key="cancel" />'
				},
				title: '<liferay-ui:message key="select-category" />',
				url: '<%= assetCategoriesDisplayContext.getAssetCategoriesSelectorURL() %>'
			}
		);

		itemSelectorDialog.open();
	}

	window.<portlet:namespace />deleteSelectedCategories = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(document.querySelector('#<portlet:namespace />fm'));
		}
	}
</aui:script>