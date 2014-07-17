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

<%@ include file="/html/portlet/asset_category_admin/init.jsp" %>

<%
long categoryId = ParamUtil.getLong(request, "categoryId");

AssetCategory category = null;

if (categoryId > 0) {
	category = AssetCategoryLocalServiceUtil.fetchCategory(categoryId);
}

long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

AssetVocabulary vocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId);

String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/asset_category_admin/view_categories");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("categoryId", String.valueOf(categoryId));
portletURL.setParameter("vocabularyId", String.valueOf(vocabularyId));

String title = StringPool.BLANK;

if (category != null) {
	title = category.getTitle(locale);
}
else {
	title = vocabulary.getTitle(locale);
}

AssetCategoryUtil.addPortletBreadcrumbEntry(vocabulary, category, request, renderResponse);
%>

<liferay-ui:header
	title="<%= title %>"
/>

<aui:form name="fm">
	<aui:input name="deleteCategoryIds" type="hidden" />

	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav">
			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_CATEGORY) %>">
				<portlet:renderURL var="addCategoryURL">
					<portlet:param name="struts_action" value="/asset_category_admin/edit_category" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="parentCategoryId" value="<%= String.valueOf(categoryId) %>" />
					<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabularyId) %>" />
				</portlet:renderURL>

				<aui:nav-item href="<%= addCategoryURL %>" iconCssClass="icon-plus" label="add-category" />
			</c:if>

			<aui:nav-item cssClass="hide" dropdown="<%= true %>" id="categoriesActionsButton" label="actions">
				<aui:nav-item cssClass="item-remove" iconCssClass="icon-remove" id="deleteSelectedCategories" label="delete" />
			</aui:nav-item>
		</aui:nav>

		<aui:nav-bar-search cssClass="pull-right">
			<div class="form-search">
				<liferay-ui:input-search />
			</div>
		</aui:nav-bar-search>
	</aui:nav-bar>

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-categories"
		iteratorURL="<%= portletURL %>"
		rowChecker="<%= new RowChecker(renderResponse) %>"
	>

		<%
		AssetCategoryDisplay assetCategoryDisplay = AssetCategoryServiceUtil.searchCategoriesDisplay(scopeGroupId, keywords, categoryId, vocabularyId, searchContainer.getStart(), searchContainer.getEnd());
		%>

		<liferay-ui:search-container-results
			results="<%= assetCategoryDisplay.getCategories() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.asset.model.AssetCategory"
			keyProperty="categoryId"
			modelVar="curCategory"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="struts_action" value="/asset_category_admin/view_categories" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="categoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
				<portlet:param name="vocabularyId" value="<%= String.valueOf(curCategory.getVocabularyId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= (AssetCategoryServiceUtil.getVocabularyCategoriesCount(scopeGroupId, curCategory.getCategoryId(), vocabularyId) > 0) ? rowURL : null %>"
				name="category"
				value="<%= curCategory.getTitle(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= curCategory.getDescription(locale) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/html/portlet/asset_category_admin/category_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base,liferay-util-list-fields">
	A.one('#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer').delegate(
		'click',
		function() {
			var hide = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0);

			A.one('#<portlet:namespace />categoriesActionsButton').toggle(!hide);
		},
		'input[type=checkbox]'
	);

	A.one('#<portlet:namespace />deleteSelectedCategories').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				<portlet:actionURL var="deleteURL">
					<portlet:param name="struts_action" value="/asset_category_admin/edit_category" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				document.<portlet:namespace />fm.<portlet:namespace />deleteCategoryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

				submitForm(document.<portlet:namespace />fm, '<%= deleteURL %>');
			}
		}
	);
</aui:script>