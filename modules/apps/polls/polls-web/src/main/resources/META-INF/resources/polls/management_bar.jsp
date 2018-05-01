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

<%@ include file="/polls/init.jsp" %>

<%
boolean showAddPollButton = PollsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_QUESTION);

PortletURL portletURL = pollsDisplayContext.getBasePortletURL();

portletURL.setParameter("mvcRenderCommandName", "/polls/view");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews="<%= pollsDisplayContext.getPollsQuestionDisplayViews() %>"
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= pollsDisplayContext.getPollsQuestionDisplayStyle() %>"
		/>

		<c:if test="<%= showAddPollButton %>">
			<portlet:renderURL var="editQuestionURL">
				<portlet:param name="mvcRenderCommandName" value="/polls/edit_question" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-poll") %>'
					url="<%= editQuestionURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= pollsDisplayContext.getOrderByCol() %>"
			orderByType="<%= pollsDisplayContext.getOrderByType() %>"
			orderColumns="<%= pollsDisplayContext.getOrderColumns() %>"
			portletURL="<%= portletURL %>"
		/>

		<li>
			<portlet:renderURL var="searchURL">
				<portlet:param name="mvcRenderCommandName" value="/polls/view" />
			</portlet:renderURL>

			<aui:form action="<%= searchURL.toString() %>" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="portletURL" />

				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>