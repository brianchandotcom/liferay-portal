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
CPOptionItemSelectorViewDisplayContext cpOptionItemSelectorViewDisplayContext = (CPOptionItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer cpOptionSearchContainer = cpOptionItemSelectorViewDisplayContext.getSearchContainer();

String displayStyle = cpOptionItemSelectorViewDisplayContext.getDisplayStyle();

String itemSelectedEventName = cpOptionItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = cpOptionItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpOptions"
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
			orderByCol="<%= cpOptionItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpOptionItemSelectorViewDisplayContext.getOrderByType() %>"
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

<div class="container-fluid-1280" id="<portlet:namespace />cpOptionSelectorWrapper">
	<liferay-ui:search-container
		id="cpOptions"
		searchContainer="<%= cpOptionSearchContainer %>"
	>
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
				<div class="commerce-product-option-title" data-id="<%= cpOption.getCPOptionId() %>">
					<%= HtmlUtil.escape(cpOption.getName(themeDisplay.getLanguageId())) %>
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