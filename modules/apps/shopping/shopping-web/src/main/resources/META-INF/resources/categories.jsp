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

String displayStyle = ParamUtil.getString(request, "displayStyle", "descriptive");
String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/shopping/view");
portletURL.setParameter("tabs1", "categories");
portletURL.setParameter("categoryId", String.valueOf(categoryId));

SearchContainer categoriesSearch = new SearchContainer(renderRequest, portletURL, null, "no-results-were-found");

String orderByCol = ParamUtil.getString(request, "orderByCol", "name");

categoriesSearch.setOrderByCol(orderByCol);

boolean orderByAsc = false;

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

if (orderByType.equals("asc")) {
	orderByAsc = true;
}

OrderByComparator orderByComparator = null;

if (orderByCol.equals("name")) {
	if (Validator.isNull(keywords)) {
		orderByComparator = new CategoryItemNameComparator(orderByAsc);
	}
	else {
		orderByComparator = new ItemNameComparator(orderByAsc);
	}
}

categoriesSearch.setOrderByComparator(orderByComparator);

categoriesSearch.setOrderByType(orderByType);

int categoriesAndItemsCount = 0;

if (Validator.isNull(keywords)) {
	categoriesAndItemsCount = ShoppingCategoryServiceUtil.getCategoriesAndItemsCount(scopeGroupId, categoryId);
}
else {
	categoriesAndItemsCount = ShoppingItemLocalServiceUtil.searchCount(scopeGroupId, null, keywords);
}

categoriesSearch.setTotal(categoriesAndItemsCount);

List results = null;

if (Validator.isNull(keywords)) {
	results = ShoppingCategoryServiceUtil.getCategoriesAndItems(scopeGroupId, categoryId, categoriesSearch.getStart(), categoriesSearch.getEnd(), categoriesSearch.getOrderByComparator());
}
else {
	results = ShoppingItemLocalServiceUtil.search(scopeGroupId, null, keywords, categoriesSearch.getStart(), categoriesSearch.getEnd(), categoriesSearch.getOrderByComparator());
}

categoriesSearch.setResults(results);

boolean showSearch = (categoriesAndItemsCount > 0);
%>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= categoriesSearch.getOrderByCol() %>"
			orderByType="<%= categoriesSearch.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<c:if test="<%= showSearch %>">

			<%
			PortletURL searchURL = renderResponse.createRenderURL();

			searchURL.setParameter("tabs1", "categories");
			%>

			<li>
				<aui:form action="<%= searchURL.toString() %>" name="searchFm">
					<liferay-ui:input-search markupView="lexicon" />
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<%
		boolean showAddCategoryButton = ShoppingCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_CATEGORY);
		boolean showAddItemButton = ShoppingCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_ITEM);
		%>

		<c:if test="<%= showAddCategoryButton || showAddItemButton %>">
			<liferay-frontend:add-menu inline="<%= true %>">
				<c:if test="<%= showAddCategoryButton %>">
					<portlet:renderURL var="addCategoriesURL">
						<portlet:param name="mvcRenderCommandName" value="/shopping/edit_category" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="parentCategoryId" value="<%= String.valueOf(categoryId) %>" />
					</portlet:renderURL>

					<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-category") %>' url="<%= addCategoriesURL.toString() %>" />
				</c:if>

				<c:if test="<%= showAddItemButton %>">
					<portlet:renderURL var="addItemURL">
						<portlet:param name="mvcRenderCommandName" value="/shopping/edit_item" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" />
					</portlet:renderURL>

					<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-item") %>' url="<%= addItemURL.toString() %>" />
				</c:if>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<div class="breadcrumbs">
		<%= ShoppingUtil.getBreadcrumbs(category, renderRequest, renderResponse) %>
	</div>

	<liferay-ui:search-container
		searchContainer="<%= categoriesSearch %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="object"
		>

			<%
			ShoppingCategory curCategory = null;
			ShoppingItem curItem = null;

			Object result = row.getObject();

			if (result instanceof ShoppingCategory) {
				curCategory = (ShoppingCategory)result;
			}
			else {
				curItem = (ShoppingItem)result;
			}
			%>

			<c:choose>
				<c:when test="<%= curItem != null %>">

					<%
					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcRenderCommandName", "/shopping/view_item");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("itemId", String.valueOf(curItem.getItemId()));
					%>

					<c:choose>
						<c:when test="<%= curItem.isSmallImage() %>">
							<liferay-ui:search-container-column-image
								src="<%= curItem.getShoppingItemImageURL(themeDisplay) %>"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-icon
								icon="tags"
							/>
						</c:otherwise>
					</c:choose>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date createDate = curItem.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<h6 class="text-default">
							<liferay-ui:message arguments="<%= new String[] {curItem.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</h6>

						<h5>
							<aui:a href="<%= rowURL != null ? rowURL.toString() : null %>">
								<%= HtmlUtil.escape(curItem.getName()) %>
							</aui:a>
						</h5>

						<h6 class="text-default">
							<span><%= HtmlUtil.escape(curItem.getDescription()) %></span>
						</h6>

						<%
						Properties props = new OrderedProperties();

						PropertiesUtil.load(props, curItem.getProperties());

						Enumeration enu = props.propertyNames();

						StringBundler sb = new StringBundler();

						while (enu.hasMoreElements()) {
							String propsKey = (String)enu.nextElement();
							String propsValue = props.getProperty(propsKey, StringPool.BLANK);

							sb.append("<br />");
							sb.append(propsKey);
							sb.append(": ");
							sb.append(propsValue);
						}
						%>

						<h6 class="text-default">
							<span><%= sb.toString() %></span>
						</h6>

						<h6 class="text-default">
							<span><%= curItem.getMinQuantity() %></span>
						</h6>

						<h6 class="text-default">
							<c:choose>
								<c:when test="<%= curItem.getDiscount() <= 0 %>">
									<span><%= currencyFormat.format(curItem.getPrice()) %></span>
								</c:when>
								<c:otherwise>
									<span><%= currencyFormat.format(curItem.getPrice() - ShoppingUtil.calculateDiscountPrice(curItem)) %></span>
								</c:otherwise>
							</c:choose>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/item_action.jsp"
					/>
				</c:when>
				<c:when test="<%= curCategory != null %>">

					<%
					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcRenderCommandName", "/shopping/view");
					rowURL.setParameter("categoryId", String.valueOf(curCategory.getCategoryId()));
					%>

					<liferay-ui:search-container-column-icon
						icon="categories"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date createDate = curCategory.getCreateDate();

						String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<h6 class="text-default">
							<liferay-ui:message arguments="<%= new String[] {curCategory.getUserName(), createDateDescription} %>" key="x-modified-x-ago" />
						</h6>

						<h5>
							<aui:a href="<%= rowURL != null ? rowURL.toString() : null %>">
								<%= curCategory.getName() %>
							</aui:a>
						</h5>

						<h6 class="text-default">
							<span><%= curCategory.getDescription() %></span>
						</h6>

						<%
						List subcategoryIds = new ArrayList();

						subcategoryIds.add(Long.valueOf(curCategory.getCategoryId()));

						ShoppingCategoryServiceUtil.getSubcategoryIds(subcategoryIds, scopeGroupId, curCategory.getCategoryId());

						int subcategoriesCount = subcategoryIds.size() - 1;
						int subitemsCount = ShoppingItemServiceUtil.getCategoriesItemsCount(scopeGroupId, subcategoryIds);
						%>

						<h6 class="text-default">
							<liferay-ui:message key="categories" />: <span><%= subcategoriesCount %></span>
							<liferay-ui:message key="items" />: <span><%= subitemsCount %></span>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/category_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" resultRowSplitter="<%= new ShoppingResultRowSplitter() %>" />
	</liferay-ui:search-container>
</div>