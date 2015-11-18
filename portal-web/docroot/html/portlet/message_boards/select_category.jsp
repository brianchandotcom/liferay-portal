<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectCategory");

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

String categoryName = null;

if (category != null) {
	MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);

	categoryName = category.getName();
}
else {
	categoryName = LanguageUtil.get(pageContext, "message-boards-home");
}
%>

<aui:form method="post" name="selectCategoryFm">
	<liferay-ui:header
		title="message-boards-home"
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/message_boards/select_category");
	portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));
	%>

	<liferay-ui:search-container
		headerNames="category[message-board],num-of-categories,num-of-threads,num-of-posts,"
		iteratorURL="<%= portletURL %>"
		total="<%= MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED) %>"
	>
		<liferay-ui:search-container-results
			results="<%= MBCategoryServiceUtil.getCategories(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.messageboards.model.MBCategory"
			escapedModel="<%= true %>"
			keyProperty="categoryId"
			modelVar="curCategory"
		>

			<portlet:renderURL var="rowURL">
				<portlet:param name="struts_action" value="/message_boards/select_category" />
				<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				buffer="buffer"
				href="<%= rowURL %>"
				name="category[message-board]"
			>

				<%
				buffer.append(curCategory.getName());

				if (Validator.isNotNull(curCategory.getDescription())) {
					buffer.append("<br />");
					buffer.append(curCategory.getDescription());
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-categories"
				value="<%= String.valueOf(categoryDisplay.getSubcategoriesCount(curCategory)) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-threads"
				value="<%= String.valueOf(categoryDisplay.getSubcategoriesThreadsCount(curCategory)) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-posts"
				value="<%= String.valueOf(categoryDisplay.getSubcategoriesMessagesCount(curCategory)) %>"
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

		<aui:button-row>

			<%
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("categoryId", categoryId);
			data.put("name", categoryName);
			%>

			<aui:button cssClass="selector-button"  data="<%= data %>" value="choose-this-category" />
		</aui:button-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectCategoryFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>