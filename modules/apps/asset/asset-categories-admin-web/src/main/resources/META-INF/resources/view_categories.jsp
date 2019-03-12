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
AssetCategoriesManagementToolbarDisplayContext assetCategoriesManagementToolbarDisplayContext = new AssetCategoriesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, assetCategoriesDisplayContext);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= assetCategoriesDisplayContext.getAssetCategoriesNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= assetCategoriesManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="deleteCategory" var="deleteCategoryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteCategoryURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= AssetCategoryUtil.getAssetCategoriesBreadcrumbEntries(assetCategoriesDisplayContext.getVocabulary(), assetCategoriesDisplayContext.getCategory(), request, renderResponse) %>"
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

			Map<String, Object> rowData = new HashMap<>();

			rowData.put("actions", assetCategoriesManagementToolbarDisplayContext.getAvailableActions(curCategory));

			row.setData(rowData);
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
						<span class="text-default">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - curCategory.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</span>

						<h2 class="h5">
							<aui:a href="<%= rowURL.toString() %>"><%= HtmlUtil.escape(curCategory.getTitle(locale)) %></aui:a>
						</h2>

						<span class="text-default">
							<%= HtmlUtil.escape(curCategory.getDescription(locale)) %>
						</span>
						<span class="text-default">
							<liferay-ui:message arguments="<%= subcategoriesCount %>" key="x-subcategories" />
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/category_action.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals(assetCategoriesDisplayContext.getDisplayStyle(), "list") %>'>
					<c:choose>
						<c:when test="<%= assetCategoriesDisplayContext.isFlattenedNavigationAllowed() %>">
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 table-title"
								name="category"
								value="<%= HtmlUtil.escape(curCategory.getTitle(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200"
								name="path"
							>
								<%= HtmlUtil.escape(curCategory.getPath(locale, true)) %> > <strong><%= HtmlUtil.escape(curCategory.getTitle(locale)) %></strong>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 table-title"
								href="<%= rowURL %>"
								name="category"
								value="<%= HtmlUtil.escape(curCategory.getTitle(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200"
								name="description"
								value="<%= HtmlUtil.escape(curCategory.getDescription(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smaller table-column-text-center"
								name="subcategories"
								value="<%= String.valueOf(subcategoriesCount) %>"
							/>
						</c:otherwise>
					</c:choose>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-ws-nowrap"
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

<liferay-frontend:component
	componentId="<%= assetCategoriesManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/AssetCategoriesManagementToolbarDefaultEventHandler.es"
/>