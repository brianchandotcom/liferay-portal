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
CPInstanceItemSelectorViewDisplayContext cpInstanceItemSelectorViewDisplayContext = (CPInstanceItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer cpInstanceSearchContainer = cpInstanceItemSelectorViewDisplayContext.getSearchContainer();

String displayStyle = cpInstanceItemSelectorViewDisplayContext.getDisplayStyle();

String itemSelectedEventName = cpInstanceItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = cpInstanceItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpInstances"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= cpInstanceItemSelectorViewDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpInstanceItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpInstanceItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "display-date", "sku"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(portletURL) %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />cpInstanceSelectorWrapper">
	<liferay-ui:search-container
		id="cpInstances"
		searchContainer="<%= cpInstanceSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPInstance"
			cssClass="commerce-product-instance-row"
			keyProperty="CPInstanceId"
			modelVar="cpInstance"
		>

			<%
			CPDefinition cpDefinition = cpInstance.getCPDefinition();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="title"
			>
				<div class="commerce-product-definition-title"
					data-id="<%= cpDefinition.getCPDefinitionId() %>">

					<%= HtmlUtil.escape(cpDefinition.getTitle(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="sku"
			/>

			<liferay-ui:search-container-column-status
				cssClass="table-cell-content"
				name="status"
				status="<%= cpInstance.getStatus() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= cpInstanceSearchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= cpInstanceSearchContainer %>" />
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var cpInstanceSelectorWrapper = A.one("#<portlet:namespace />cpInstanceSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />cpInstances');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(cpInstanceSelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>