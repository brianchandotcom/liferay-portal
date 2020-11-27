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
CommerceProductInstanceItemSelectorViewDisplayContext commerceProductInstanceItemSelectorViewDisplayContext = (CommerceProductInstanceItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = commerceProductInstanceItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpInstances"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceProductInstanceItemSelectorViewDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceProductInstanceItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceProductInstanceItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "display-date", "sku"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<liferay-commerce:search-input
				actionURL="<%= portletURL %>"
				formName="searchFm"
			/>
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />cpInstanceSelectorWrapper">
	<liferay-ui:search-container
		id="cpInstances"
		searchContainer="<%= commerceProductInstanceItemSelectorViewDisplayContext.getSearchContainer() %>"
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
				cssClass="table-cell-expand"
				name="title"
			>
				<div class="commerce-product-definition-title" data-id="<%= cpDefinition.getCPDefinitionId() %>">
					<%= HtmlUtil.escape(cpDefinition.getName(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="sku"
			/>

			<liferay-ui:search-container-column-status
				cssClass="table-cell-expand"
				name="status"
				status="<%= cpInstance.getStatus() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var cpInstanceSelectorWrapper = A.one(
		'#<portlet:namespace />cpInstanceSelectorWrapper'
	);

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />cpInstances'
	);

	searchContainer.on('rowToggled', function (event) {
		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(commerceProductInstanceItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
			{
				data: Liferay.Util.listCheckedExcept(
					cpInstanceSelectorWrapper,
					'<portlet:namespace />allRowIds'
				),
			}
		);
	});
</aui:script>