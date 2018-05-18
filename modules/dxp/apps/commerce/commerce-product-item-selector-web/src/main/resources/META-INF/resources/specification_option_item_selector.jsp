<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPSpecificationOptionItemSelectorViewDisplayContext cpSpecificationOptionItemSelectorViewDisplayContext = (CPSpecificationOptionItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer cpSpecificationOptionSearchContainer = cpSpecificationOptionItemSelectorViewDisplayContext.getSearchContainer();

String displayStyle = cpSpecificationOptionItemSelectorViewDisplayContext.getDisplayStyle();

String itemSelectedEventName = cpSpecificationOptionItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = cpSpecificationOptionItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpSpecificationOptions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpSpecificationOptionItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpSpecificationOptionItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"modified-date", "title"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(portletURL) %>" name="searchFm">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />cpSpecificationOptionSelectorWrapper">
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
				cssClass="table-cell-content"
				name="label"
			>
				<div class="commerce-product-option-title" data-id="<%= cpSpecificationOption.getCPSpecificationOptionId() %>">
					<%= HtmlUtil.escape(cpSpecificationOption.getTitle(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
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
	var cpSpecificationOptionSelectorWrapper = A.one("#<portlet:namespace />cpSpecificationOptionSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />cpSpecificationOptions');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(cpSpecificationOptionSelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>