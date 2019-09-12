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
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

long excludedCategoryId = ParamUtil.getLong(request, "excludedMBCategoryId");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectCategory");

MBCategoryDisplay categoryDisplay = new MBCategoryDisplay(scopeGroupId, categoryId);

String categoryName = null;

MBBreadcrumbUtil.addPortletBreadcrumbEntries(category, request, renderResponse);

if (category != null) {
	categoryName = category.getName();
}
else {
	categoryName = LanguageUtil.get(request, "home");
}
%>

<div class="container-fluid-1280">
	<aui:form method="post" name="selectCategoryFm">
		<liferay-ui:breadcrumb
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>

		<%
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/message_boards/select_category");
		portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));
		%>

		<liferay-ui:search-container
			headerNames="category[message-board],categories,threads,posts,"
			iteratorURL="<%= portletURL %>"
			total="<%= MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, excludedCategoryId, categoryId, WorkflowConstants.STATUS_APPROVED) %>"
		>
			<liferay-ui:search-container-results
				results="<%= MBCategoryServiceUtil.getCategories(scopeGroupId, excludedCategoryId, categoryId, WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.message.boards.model.MBCategory"
				escapedModel="<%= true %>"
				keyProperty="categoryId"
				modelVar="curCategory"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/message_boards/select_category" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand table-cell-minw-200 table-title"
					href="<%= rowURL %>"
					name="category[message-board]"
				>
					<%= curCategory.getName() %>

					<c:if test="<%= Validator.isNotNull(curCategory.getDescription()) %>">
						<br />

						<%= curCategory.getDescription() %>
					</c:if>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-column-text-end"
					href="<%= rowURL %>"
					name="categories"
					value="<%= String.valueOf(categoryDisplay.getSubcategoriesCount(curCategory)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-column-text-end"
					href="<%= rowURL %>"
					name="threads"
					value="<%= String.valueOf(categoryDisplay.getSubcategoriesThreadsCount(curCategory)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-column-text-end"
					href="<%= rowURL %>"
					name="posts"
					value="<%= String.valueOf(categoryDisplay.getSubcategoriesMessagesCount(curCategory)) %>"
				/>

				<liferay-ui:search-container-column-text>

					<%
					Map<String, Object> data = new HashMap<>();

					data.put("categoryId", curCategory.getCategoryId());
					data.put("name", curCategory.getName());
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="select" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<aui:button-row>

				<%
				Map<String, Object> data = new HashMap<>();

				data.put("categoryId", categoryId);
				data.put("name", categoryName);
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="select-this-category" />
			</aui:button-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectCategoryFm',
		'<%= HtmlUtil.escapeJS(eventName) %>'
	);
</aui:script>