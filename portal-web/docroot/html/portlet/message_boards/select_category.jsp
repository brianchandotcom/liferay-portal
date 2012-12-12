<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

if (category != null) {
	MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);
}
%>

<aui:form method="post" name="fm">
	<liferay-ui:header
		title="message-boards-home"
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/message_boards/select_category");
	portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("category");
	headerNames.add("num-of-categories");
	headerNames.add("num-of-threads");
	headerNames.add("num-of-posts");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	int total = MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED);

	searchContainer.setTotal(total);

	List results = MBCategoryServiceUtil.getCategories(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		MBCategory curCategory = (MBCategory)results.get(i);

		curCategory = curCategory.toEscapedModel();

		ResultRow row = new ResultRow(curCategory, curCategory.getCategoryId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/message_boards/select_category");
		rowURL.setParameter("mbCategoryId", String.valueOf(curCategory.getCategoryId()));

		// Name and description

		if (Validator.isNotNull(curCategory.getDescription())) {
			row.addText(curCategory.getName().concat("<br />").concat(curCategory.getDescription()), rowURL);
		}
		else {
			row.addText(curCategory.getName(), rowURL);
		}

		// Statistics

		int categoriesCount = categoryDisplay.getSubcategoriesCount(curCategory);
		int threadsCount = categoryDisplay.getSubcategoriesThreadsCount(curCategory);
		int messagesCount = categoryDisplay.getSubcategoriesMessagesCount(curCategory);

		row.addText(String.valueOf(categoriesCount), rowURL);
		row.addText(String.valueOf(threadsCount), rowURL);
		row.addText(String.valueOf(messagesCount), rowURL);

		// Action

		StringBundler sb = new StringBundler(7);

		sb.append("opener.");
		sb.append(renderResponse.getNamespace());
		sb.append("selectCategory('");
		sb.append(curCategory.getCategoryId());
		sb.append("', '");
		sb.append(UnicodeFormatter.toString(curCategory.getName()));
		sb.append("'); window.close();");

		row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, "choose"), sb.toString());

		// Add result row

		resultRows.add(row);
	}
	%>

	<aui:button-row>

		<%
		String taglibSelectOnClick = "opener." + renderResponse.getNamespace() + "selectCategory('" + categoryId + "','" + ((category != null) ? category.getName() : LanguageUtil.get(pageContext, "message-boards-home")) + "'); window.close();";
		%>

		<aui:button onClick="<%= taglibSelectOnClick %>" value="choose-this-category" />
	</aui:button-row>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>