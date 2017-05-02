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
List<CPOption> cpOptions = (List<CPOption>)request.getAttribute("cpOptions");
int cpOptionsCount = GetterUtil.getInteger(request.getAttribute("cpOptionsCount"));
String displayStyle = GetterUtil.getString(request.getAttribute("displayStyle"));
String emptyResultsMessage = GetterUtil.getString(request.getAttribute("emptyResultsMessage"));
String itemSelectedEventName = GetterUtil.getString(request.getAttribute("itemSelectedEventName"));
PortletURL portletURL = (PortletURL)request.getAttribute("portletURL");

SearchContainer searchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, emptyResultsMessage);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpOptions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>

		<%
		PortletURL sortURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

		String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
		String orderByType = ParamUtil.getString(request, "orderByType", "asc");
		%>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= sortURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />cpOptionSelectorWrapper">
	<liferay-ui:search-container
		id="cpOptions"
		rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
		searchContainer="<%= searchContainer %>"
		total="<%= cpOptionsCount %>"
		var="listSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= cpOptions %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPOption"
			cssClass="commerce-product-option-row"
            keyProperty="CPOptionId"
			modelVar="cpOption"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
            >
				<div class="commerce-product-option-name"
					data-id="<%= cpOption.getCPOptionId() %>">

					<%= HtmlUtil.escape(cpOption.getName(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="facetable"
				property="facetable"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="sku-contributor"
				property="skuContributor"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</div>


<aui:script use="liferay-search-container">
    var cpOptionSelectorWrapper = A.one("#<portlet:namespace />cpOptionSelectorWrapper");

    var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />cpOptions');

    searchContainer.on(
        'rowToggled',
        function(event) {
            Liferay.Util.getOpener().Liferay.fire(
                '<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
                {
                    data: Liferay.Util.listCheckedExcept(cpOptionSelectorWrapper, '<portlet:namespace />allRowIds')
                }
            );
        }
    );
</aui:script>