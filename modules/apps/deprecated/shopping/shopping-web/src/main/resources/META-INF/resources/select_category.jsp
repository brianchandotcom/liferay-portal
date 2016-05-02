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
ShoppingCategory category = (ShoppingCategory)request.getAttribute(WebKeys.SHOPPING_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "categoryId", ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectCategory");
%>

<aui:form method="post" name="fm">
	<c:if test="<%= category != null %>">
		<div class="breadcrumbs">
			<%= ShoppingUtil.getBreadcrumbs(category, renderRequest, renderResponse) %>
		</div>
	</c:if>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcRenderCommandName", "/shopping/select_category");
	portletURL.setParameter("categoryId", String.valueOf(categoryId));
	%>

	<liferay-ui:search-container
		headerNames="category,num-of-categories,num-of-items, ,"
		iteratorURL="<%= portletURL %>"
		total="<%= ShoppingCategoryServiceUtil.getCategoriesCount(scopeGroupId, categoryId) %>"
	>
		<liferay-ui:search-container-results
			results="<%= ShoppingCategoryServiceUtil.getCategories(scopeGroupId, categoryId, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.shopping.model.ShoppingCategory"
			escapedModel="<%= true %>"
			keyProperty="categoryId"
			modelVar="curCategory"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/shopping/select_category" />
				<portlet:param name="categoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="category"
			>
				<%= curCategory.getName() %>

				<c:if test="<%= Validator.isNotNull(curCategory.getDescription()) %>">
					<br />

					<%= curCategory.getDescription() %>
				</c:if>
			</liferay-ui:search-container-column-text>

			<%
			List subcategoryIds = new ArrayList();

			subcategoryIds.add(Long.valueOf(curCategory.getCategoryId()));

			ShoppingCategoryServiceUtil.getSubcategoryIds(subcategoryIds, scopeGroupId, curCategory.getCategoryId());

			int categoriesCount = subcategoryIds.size() - 1;
			int itemsCount = ShoppingItemServiceUtil.getCategoriesItemsCount(scopeGroupId, subcategoryIds);
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-categories"
				value="<%= String.valueOf(categoriesCount) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-items"
				value="<%= String.valueOf(itemsCount) %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("categoryId", curCategory.getCategoryId());
				data.put("name", curCategory.getName());
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />fm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>