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
String categoryName = null;

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

String eventName = ParamUtil.getString(request, "eventName", "selectCategory");

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

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

	int categoriesCount = MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED);
	%>

	<c:if test="<%= categoriesCount > 0 %>">
		<br />

		<liferay-ui:search-container iteratorURL="<%= portletURL %>">
			<liferay-ui:search-container-results
				results="<%= MBCategoryServiceUtil.getCategories(scopeGroupId, categoryId, WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= categoriesCount %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.messageboards.model.MBCategory"
				modelVar="curCategory"
				>

				<portlet:renderURL var="rowURL">
					<portlet:param name="struts_action" value="/message_boards/select_category" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="category">

					<c:choose>
						<c:when test="<%= Validator.isNotNull(curCategory.getDescription()) %>">
							<%= curCategory.getName().concat("<br />").concat(curCategory.getDescription()) %>
						</c:when>
						<c:otherwise>
							<%= curCategory.getName() %>
						</c:otherwise>
					</c:choose>
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

						data.put("categoryid", curCategory.getCategoryId());
						data.put("name", HtmlUtil.escape(curCategory.getName()));
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<aui:button-row>
				<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("categoryid", categoryId);
					data.put("name", HtmlUtil.escape(categoryName));
				%>

				<aui:button cssClass="selector-button"  data="<%= data %>" value="choose-this-category" />
			</aui:button-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:if>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	A.one('#<portlet:namespace />selectCategoryFm').delegate(
		'click',
		function(event) {
			var result = Util.getAttributes(event.currentTarget, 'data-');

			Util.getOpener().Liferay.fire('<portlet:namespace /><%= eventName %>', result);

			Util.getWindow().close();
		},
		'.selector-button input'
	);
</aui:script>