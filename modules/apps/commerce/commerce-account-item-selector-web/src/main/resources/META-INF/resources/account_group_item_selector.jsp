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
CommerceAccountGroupItemSelectorViewDisplayContext commerceAccountGroupItemSelectorViewDisplayContext = (CommerceAccountGroupItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceAccountGroup> commerceAccountGroupSearchContainer = commerceAccountGroupItemSelectorViewDisplayContext.getSearchContainer();
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new CommerceAccountGroupItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, commerceAccountGroupSearchContainer) %>"
/>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />commerceAccountGroupSelectorWrapper">
	<liferay-ui:search-container
		id="commerceAccountGroups"
		searchContainer="<%= commerceAccountGroupSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.account.model.CommerceAccountGroup"
			cssClass="commerce-account-row"
			keyProperty="commerceAccountGroupId"
			modelVar="commerceAccountGroup"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"commerce-account-group-id", commerceAccountGroup.getCommerceAccountGroupId()
				).put(
					"name", commerceAccountGroup.getName()
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="name"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand"
				name="create-date"
				property="createDate"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			searchContainer="<%= commerceAccountGroupSearchContainer %>"
		/>
	</liferay-ui:search-container>
</div>