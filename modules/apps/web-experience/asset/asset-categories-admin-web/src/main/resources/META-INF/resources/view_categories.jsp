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

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL">
		<portlet:param name="mvcPath" value="/view_categories.jsp" />
		<portlet:param name="vocabularyId" value="<%= String.valueOf(assetCategoriesDisplayContext.getVocabularyId()) %>" />
	</portlet:renderURL>

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="categories" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= assetCategoriesDisplayContext.isShowCategoriesSearch() %>">
		<portlet:renderURL var="portletURL">
			<portlet:param name="mvcPath" value="/view_categories.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="categoryId" value="<%= String.valueOf(assetCategoriesDisplayContext.getCategoryId()) %>" />
			<portlet:param name="vocabularyId" value="<%= String.valueOf(assetCategoriesDisplayContext.getVocabularyId()) %>" />
			<portlet:param name="displayStyle" value="<%= assetCategoriesDisplayContext.getDisplayStyle() %>" />
		</portlet:renderURL>

		<aui:nav-bar-search>
			<aui:form action="<%= portletURL %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= assetCategoriesDisplayContext.isDisabledCategoriesManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="assetCategories"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				label="<%= assetCategoriesDisplayContext.isNavigationCategory() ? assetCategoriesDisplayContext.getCategoryTitle() : null %>"
			>

				<%
				PortletURL viewCategoryHomeURL = PortletURLUtil.clone(assetCategoriesDisplayContext.getIteratorURL(), liferayPortletResponse);

				viewCategoryHomeURL.setParameter("navigation", "all");
				%>

				<liferay-frontend:management-bar-filter-item active="<%= assetCategoriesDisplayContext.isNavigationAll() %>" label="all" url="<%= viewCategoryHomeURL.toString() %>" />

				<c:if test="<%= assetCategoriesDisplayContext.isFlattenedNavigationAllowed() %>">
					<liferay-frontend:management-bar-filter-item active="<%= assetCategoriesDisplayContext.isNavigationCategory() %>" id="selectCategory" label="category" url="javascript:;" />
				</c:if>
			</liferay-frontend:management-bar-navigation>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= assetCategoriesDisplayContext.getOrderByCol() %>"
				orderByType="<%= assetCategoriesDisplayContext.getOrderByType() %>"
				orderColumns="<%= assetCategoriesDisplayContext.getOrderColumns() %>"
				portletURL="<%= PortletURLUtil.clone(assetCategoriesDisplayContext.getIteratorURL(), liferayPortletResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews="<%= assetCategoriesDisplayContext.getDisplayViews() %>"
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= assetCategoriesDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedCategories" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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

		<liferay-ui:search-iterator displayStyle="<%= assetCategoriesDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= assetCategoriesDisplayContext.isShowCategoriesAddButton() %>">
	<portlet:renderURL var="addCategoryURL">
		<portlet:param name="mvcPath" value="/edit_category.jsp" />

		<c:if test="<%= assetCategoriesDisplayContext.getCategoryId() > 0 %>">
			<portlet:param name="parentCategoryId" value="<%= String.valueOf(assetCategoriesDisplayContext.getCategoryId()) %>" />
		</c:if>

		<portlet:param name="vocabularyId" value="<%= String.valueOf(assetCategoriesDisplayContext.getVocabularyId()) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, (assetCategoriesDisplayContext.getCategoryId() > 0) ? "add-subcategory" : "add-category") %>' url="<%= addCategoryURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<portlet:actionURL name="moveCategory" var="moveCategoryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="mvcPath" value="/view_categories.jsp" />
</portlet:actionURL>

<aui:form action="<%= moveCategoryURL %>" name="moveCategoryFm">
	<aui:input name="categoryId" type="hidden" />
	<aui:input name="parentCategoryId" type="hidden" />
	<aui:input name="vocabularyId" type="hidden" />
</aui:form>

<aui:script sandbox="<%= true %>" use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectCategory').on(
		'click',
		function(event) {
			event.preventDefault();

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
	);

	$('#<portlet:namespace />deleteSelectedCategories').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>