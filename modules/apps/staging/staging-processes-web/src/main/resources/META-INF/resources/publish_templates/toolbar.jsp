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
String layoutSetBranchId = ParamUtil.getString(request, "layoutSetBranchId");
String layoutSetBranchName = ParamUtil.getString(request, "layoutSetBranchName");
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<li>
			<liferay-portlet:renderURL varImpl="searchURL">
				<portlet:param name="mvcRenderCommandName" value="viewPublishConfigurations" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= searchURL.toString() %>" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="searchURL" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<portlet:renderURL var="addPublishConfigurationURL">
			<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			<portlet:param name="layoutSetBranchId" value="<%= layoutSetBranchId %>" />
			<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
		</portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "new") %>'
				url="<%= addPublishConfigurationURL %>"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>