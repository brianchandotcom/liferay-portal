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
String redirect = ParamUtil.getString(request, "redirect");

long categoryId = ParamUtil.getLong(request, "categoryId");

AssetCategory category = null;

if (categoryId > 0) {
	category = AssetCategoryLocalServiceUtil.fetchCategory(categoryId);
}

long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

AssetVocabulary vocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId);

if (Validator.isNull(redirect)) {
	PortletURL backURL = renderResponse.createRenderURL();

	if (category != null) {
		backURL.setParameter("mvcPath", "/view_categories.jsp");
		backURL.setParameter("categoryId", String.valueOf(category.getParentCategoryId()));

		if (vocabularyId > 0) {
			backURL.setParameter("vocabularyId", String.valueOf(vocabularyId));
		}
	}

	redirect = backURL.toString();
}

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String keywords = ParamUtil.getString(request, "keywords");

PortletURL iteratorURL = renderResponse.createRenderURL();

iteratorURL.setParameter("mvcPath", "/view_categories.jsp");
iteratorURL.setParameter("redirect", currentURL);
iteratorURL.setParameter("categoryId", String.valueOf(categoryId));
iteratorURL.setParameter("vocabularyId", String.valueOf(vocabularyId));
iteratorURL.setParameter("displayStyle", displayStyle);
iteratorURL.setParameter("keywords", keywords);

SearchContainer categoriesSearchContainer = new SearchContainer(renderRequest, iteratorURL, null, "there-are-no-categories.-you-can-add-a-category-by-clicking-the-plus-button-on-the-bottom-right-corner");

String orderByCol = ParamUtil.getString(request, "orderByCol", "create-date");

categoriesSearchContainer.setOrderByCol(orderByCol);

boolean orderByAsc = false;

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

if (orderByType.equals("asc")) {
	orderByAsc = true;
}

OrderByComparator<AssetCategory> orderByComparator = new AssetCategoryCreateDateComparator(orderByAsc);

categoriesSearchContainer.setOrderByComparator(orderByComparator);

categoriesSearchContainer.setOrderByType(orderByType);
categoriesSearchContainer.setRowChecker(new EmptyOnClickRowChecker(renderResponse));

List<AssetCategory> categories = null;
int categoriesCount = 0;

if (Validator.isNotNull(keywords)) {
	Sort sort = new Sort("createDate", Sort.LONG_TYPE, orderByAsc);

	AssetCategoryDisplay assetCategoryDisplay = AssetCategoryServiceUtil.searchCategoriesDisplay(scopeGroupId, keywords, vocabularyId, categoryId, categoriesSearchContainer.getStart(), categoriesSearchContainer.getEnd(), sort);

	categoriesCount = assetCategoryDisplay.getTotal();

	categoriesSearchContainer.setTotal(categoriesCount);

	categories = assetCategoryDisplay.getCategories();
}
else {
	categoriesCount = AssetCategoryServiceUtil.getVocabularyCategoriesCount(scopeGroupId, categoryId, vocabularyId);

	categoriesSearchContainer.setTotal(categoriesCount);

	categories = AssetCategoryServiceUtil.getVocabularyCategories(scopeGroupId, categoryId, vocabularyId, categoriesSearchContainer.getStart(), categoriesSearchContainer.getEnd(), categoriesSearchContainer.getOrderByComparator());
}

categoriesSearchContainer.setResults(categories);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((category != null) ? category.getTitle(locale) : vocabulary.getTitle(locale));

AssetCategoryUtil.addPortletBreadcrumbEntry(vocabulary, category, request, renderResponse);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="categories" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= Validator.isNotNull(keywords) || (categoriesCount > 0) %>">
		<portlet:renderURL var="portletURL">
			<portlet:param name="mvcPath" value="/view_categories.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" />
			<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabularyId) %>" />
			<portlet:param name="displayStyle" value="<%= displayStyle %>" />
		</portlet:renderURL>

		<aui:nav-bar-search>
			<aui:form action="<%= portletURL %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<c:if test="<%= Validator.isNotNull(keywords) || (categoriesCount > 0) %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="assetCategories"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"all"} %>'
					portletURL="<%= PortletURLUtil.clone(iteratorURL, liferayPortletResponse) %>"
				/>

				<liferay-frontend:management-bar-sort
					orderByCol="<%= orderByCol %>"
					orderByType="<%= orderByType %>"
					orderColumns='<%= new String[] {"create-date"} %>'
					portletURL="<%= PortletURLUtil.clone(iteratorURL, liferayPortletResponse) %>"
				/>
			</liferay-frontend:management-bar-filters>

			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= PortletURLUtil.clone(iteratorURL, liferayPortletResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedCategories" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

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
		searchContainer="<%= categoriesSearchContainer %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.asset.model.AssetCategory"
			keyProperty="categoryId"
			modelVar="curCategory"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/view_categories.jsp" />
				<portlet:param name="categoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
				<portlet:param name="vocabularyId" value="<%= String.valueOf(curCategory.getVocabularyId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= (AssetCategoryServiceUtil.getVocabularyCategoriesCount(scopeGroupId, curCategory.getCategoryId(), vocabularyId) > 0) ? rowURL : null %>"
				name="category"
				value="<%= HtmlUtil.escape(curCategory.getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= HtmlUtil.escape(curCategory.getDescription(locale)) %>"
			/>

			<liferay-ui:search-container-column-date
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				path="/category_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_CATEGORY) %>">
	<portlet:renderURL var="addCategoryURL">
		<portlet:param name="mvcPath" value="/edit_category.jsp" />

		<c:if test="<%= categoryId > 0 %>">
			<portlet:param name="parentCategoryId" value="<%= String.valueOf(categoryId) %>" />
		</c:if>

		<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabularyId) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-category") %>' url="<%= addCategoryURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedCategories').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>