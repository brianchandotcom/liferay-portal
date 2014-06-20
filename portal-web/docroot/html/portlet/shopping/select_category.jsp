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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
ShoppingCategory category = (ShoppingCategory)request.getAttribute(WebKeys.SHOPPING_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "categoryId", ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
%>

<aui:form method="post" name="fm">
	<liferay-ui:header
		title="categories"
	/>

	<c:if test="<%= category != null %>">
		<div class="breadcrumbs">
			<%= ShoppingUtil.getBreadcrumbs(category, renderRequest, renderResponse) %>
		</div>
	</c:if>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/shopping/select_category");
	portletURL.setParameter("categoryId", String.valueOf(categoryId));

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("category");
	headerNames.add("num-of-categories");
	headerNames.add("num-of-items");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	int total = ShoppingCategoryServiceUtil.getCategoriesCount(scopeGroupId, categoryId);

	searchContainer.setTotal(total);

	List results = ShoppingCategoryServiceUtil.getCategories(scopeGroupId, categoryId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		ShoppingCategory curCategory = (ShoppingCategory)results.get(i);

		curCategory = curCategory.toEscapedModel();

		ResultRow row = new ResultRow(curCategory, curCategory.getCategoryId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/shopping/select_category");
		rowURL.setParameter("categoryId", String.valueOf(curCategory.getCategoryId()));

		// Name and description

		if (Validator.isNotNull(curCategory.getDescription())) {
			row.addText(curCategory.getName().concat("<br />").concat(curCategory.getDescription()), rowURL);
		}
		else {
			row.addText(curCategory.getName(), rowURL);
		}

		// Statistics

		List subcategoryIds = new ArrayList();

		subcategoryIds.add(new Long(curCategory.getCategoryId()));

		ShoppingCategoryServiceUtil.getSubcategoryIds(subcategoryIds, scopeGroupId, curCategory.getCategoryId());

		int categoriesCount = subcategoryIds.size() - 1;
		int itemsCount = ShoppingItemServiceUtil.getCategoriesItemsCount(scopeGroupId, subcategoryIds);

		row.addText(String.valueOf(categoriesCount), rowURL);
		row.addText(String.valueOf(itemsCount), rowURL);

		// Action

		StringBundler sb = new StringBundler(7);

		sb.append("opener.");
		sb.append(renderResponse.getNamespace());
		sb.append("selectCategory('");
		sb.append(curCategory.getCategoryId());
		sb.append("', '");
		sb.append(UnicodeFormatter.toString(curCategory.getName()));
		sb.append("'); window.close();");

		row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(request, "choose"), sb.toString());

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>