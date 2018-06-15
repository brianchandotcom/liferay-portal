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

<%@ include file="/message_boards/init.jsp" %>

<%
SearchContainer categoryEntriesSearchContainer = (SearchContainer)request.getAttribute("view.jsp-categoryEntriesSearchContainer");

long categoryId = GetterUtil.getLong(request.getAttribute("view.jsp-categoryId"));

MBCategoryDisplay categoryDisplay = new MBCategoryDisplay(scopeGroupId, categoryId);
%>

<liferay-ui:search-container
	searchContainer="<%= categoryEntriesSearchContainer %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.message.boards.model.MBCategory"
		escapedModel="<%= true %>"
		keyProperty="categoryId"
		modelVar="category"
	>

		<%
		row.setPrimaryKey(String.valueOf(category.getCategoryId()));
		%>

		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/view_category" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-icon
			icon="folder"
			toggleRowChecker="<%= true %>"
		/>

		<liferay-ui:search-container-column-text
			colspan="<%= 2 %>"
		>
			<h4>
				<aui:a href="<%= rowURL.toString() %>">
					<%= category.getName() %>
				</aui:a>
			</h4>

			<h5 class="text-default">
				<%= category.getDescription() %>
			</h5>

			<%
			int subcategoriesCount = categoryDisplay.getSubcategoriesCount(category);
			int threadsCount = categoryDisplay.getSubcategoriesThreadsCount(category);
			%>

			<span class="h6 text-default">
				<liferay-ui:message arguments="<%= subcategoriesCount %>" key='<%= (subcategoriesCount == 1) ? "x-subcategory" : "x-subcategories" %>' />
			</span>
			<span class="h6 text-default">
				<liferay-ui:message arguments="<%= threadsCount %>" key='<%= (threadsCount == 1) ? "x-thread" : "x-threads" %>' />
			</span>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			path="/message_boards/category_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="descriptive"
		markupView="lexicon"
		resultRowSplitter="<%= new MBResultRowSplitter() %>"
	/>
</liferay-ui:search-container>