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

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_categories.jsp");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("categoryId", String.valueOf(categoryId));
portletURL.setParameter("vocabularyId", String.valueOf(vocabularyId));
portletURL.setParameter("displayStyle", displayStyle);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((category != null) ? category.getTitle(locale) : vocabulary.getTitle(locale));

AssetCategoryUtil.addPortletBreadcrumbEntry(vocabulary, category, request, renderResponse);
%>

<aui:nav-bar cssClass="collapse-basic-search" view="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="categories" />
	</aui:nav>

	<aui:nav-bar-search>
		<liferay-ui:input-search markupView="lexicon" />
	</aui:nav-bar-search>
</aui:nav-bar>

<div class="management-bar-container">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-portlet:renderURL varImpl="displayStyleURL">
				<liferay-portlet:param name="mvcPath" value="/view_categories.jsp" />
				<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
				<liferay-portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" />
				<liferay-portlet:param name="vocabularyId" value="<%= String.valueOf(vocabularyId) %>" />
			</liferay-portlet:renderURL>

			<liferay-frontend:management-bar-display-buttons
				displayStyleURL="<%= displayStyleURL %>"
				displayViews='<%= new String[] {"list"} %>'
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>

	<liferay-frontend:management-bar
		cssClass="management-bar-no-collapse"
		id="categoriesActionsButton"
	>

		<liferay-frontend:management-bar-buttons>
			<aui:a cssClass="btn" href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedCategories" />
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>
</div>

<aui:form cssClass="container-fluid-1280" name="fm">
	<aui:input name="deleteCategoryIds" type="hidden" />

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-categories"
		id="assetCategories"
		iteratorURL="<%= portletURL %>"
		rowChecker="<%= new RowChecker(renderResponse) %>"
	>

		<liferay-ui:search-container-results>

			<%
			List<AssetCategory> categories = null;

			if (Validator.isNotNull(keywords)) {
				AssetCategoryDisplay assetCategoryDisplay = AssetCategoryServiceUtil.searchCategoriesDisplay(scopeGroupId, keywords, categoryId, vocabularyId, searchContainer.getStart(), searchContainer.getEnd());

				total = assetCategoryDisplay.getTotal();

				searchContainer.setTotal(total);

				categories = assetCategoryDisplay.getCategories();
			}
			else {
				total = AssetCategoryServiceUtil.getVocabularyCategoriesCount(scopeGroupId, categoryId, vocabularyId);

				searchContainer.setTotal(total);

				categories = AssetCategoryServiceUtil.getVocabularyCategories(scopeGroupId, categoryId, vocabularyId, searchContainer.getStart(), searchContainer.getEnd(), null);
			}

			searchContainer.setResults(categories);
			%>

		</liferay-ui:search-container-results>

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
				value="<%= curCategory.getDescription(locale) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="checkbox-cell entry-action"
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
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />assetCategoriesSearchContainer').on(
		'click',
		'input[type=checkbox]',
		function() {
			var hide = (Util.listCheckedExcept(form, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0);

			$('#<portlet:namespace />categoriesActionsButton').toggleClass('on', !hide);
		}
	);

	$('#<portlet:namespace />deleteSelectedCategories').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				<portlet:actionURL name="deleteCategory" var="deleteCategoryURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				form.fm('deleteCategoryIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

				submitForm(form, '<%= deleteCategoryURL %>');
			}
		}
	);
</aui:script>