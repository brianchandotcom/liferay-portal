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
CPSpecificationOptionItemSelectorViewDisplayContext cpSpecificationOptionItemSelectorViewDisplayContext = (CPSpecificationOptionItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CPSpecificationOption> cpSpecificationOptionSearchContainer = cpSpecificationOptionItemSelectorViewDisplayContext.getSearchContainer();

String displayStyle = cpSpecificationOptionItemSelectorViewDisplayContext.getDisplayStyle();

String itemSelectedEventName = cpSpecificationOptionItemSelectorViewDisplayContext.getItemSelectedEventName();
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new CPOptionItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, cpSpecificationOptionSearchContainer) %>"
/>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />cpSpecificationOptionSelectorWrapper">
	<liferay-ui:search-container
		id="cpSpecificationOptions"
		searchContainer="<%= cpSpecificationOptionSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPSpecificationOption"
			cssClass="commerce-product-option-row"
			keyProperty="CPSpecificationOptionId"
			modelVar="cpSpecificationOption"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="label"
			>
				<div class="commerce-product-option-title" data-id="<%= cpSpecificationOption.getCPSpecificationOptionId() %>">
					<%= HtmlUtil.escape(cpSpecificationOption.getTitle(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand"
				name="modified-date"
				property="modifiedDate"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var cpSpecificationOptionSelectorWrapper = A.one(
		'#<portlet:namespace />cpSpecificationOptionSelectorWrapper'
	);

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />cpSpecificationOptions'
	);

	searchContainer.on('rowToggled', (event) => {
		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
			{
				data: Liferay.Util.getCheckedCheckboxes(
					cpSpecificationOptionSelectorWrapper,
					'<portlet:namespace />allRowIds'
				),
			}
		);
	});
</aui:script>