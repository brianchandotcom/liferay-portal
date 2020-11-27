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
CommercePricingClassItemSelectorViewDisplayContext commercePricingClassItemSelectorViewDisplayContext = (CommercePricingClassItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String itemSelectedEventName = commercePricingClassItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = commercePricingClassItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commercePricingClasses"
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
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commercePricingClassItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= commercePricingClassItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<liferay-commerce:search-input
				actionURL="<%= commercePricingClassItemSelectorViewDisplayContext.getPortletURL() %>"
				formName="searchFm"
			/>
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />commercePricingClassSelectorWrapper">
	<liferay-ui:search-container
		id="commercePricingClasses"
		searchContainer="<%= commercePricingClassItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.pricing.model.CommercePricingClass"
			cssClass="commerce-pricing-class-row"
			keyProperty="commercePricingClassId"
			modelVar="commercePricingClass"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="title"
			>
				<div class="commerce-pricing-class-title" data-id="<%= commercePricingClass.getCommercePricingClassId() %>">
					<%= HtmlUtil.escape(commercePricingClass.getTitle(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="author"
				property="userName"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand"
				name="create-date"
				property="createDate"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchContainer="<%= commercePricingClassItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>

		<liferay-ui:search-paginator
			searchContainer="<%= commercePricingClassItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commercePricingClassSelectorWrapper = A.one(
		'#<portlet:namespace />commercePricingClassSelectorWrapper'
	);

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />commercePricingClasses'
	);

	searchContainer.on('rowToggled', function (event) {
		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
			{
				data: Liferay.Util.listCheckedExcept(
					commercePricingClassSelectorWrapper,
					'<portlet:namespace />allRowIds'
				),
			}
		);
	});
</aui:script>