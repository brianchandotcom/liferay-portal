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
CommerceWarehouseItemSelectorViewDisplayContext commerceWarehouseItemSelectorViewDisplayContext = (CommerceWarehouseItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceCountryId = commerceWarehouseItemSelectorViewDisplayContext.getCommerceCountryId();
String itemSelectedEventName = commerceWarehouseItemSelectorViewDisplayContext.getItemSelectedEventName();
List<ManagementBarFilterItem> managementBarFilterItems = commerceWarehouseItemSelectorViewDisplayContext.getManagementBarFilterItems();
PortletURL portletURL = commerceWarehouseItemSelectorViewDisplayContext.getPortletURL();

String managementBarFilterValue = null;

for (ManagementBarFilterItem managementBarFilterItem : managementBarFilterItems) {
	if (commerceCountryId == Long.valueOf(managementBarFilterItem.getId())) {
		managementBarFilterValue = managementBarFilterItem.getLabel();

		break;
	}
}
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceWarehouses"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-filter
			label="country"
			managementBarFilterItems="<%= managementBarFilterItems %>"
			value="<%= managementBarFilterValue %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceWarehouseItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceWarehouseItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"city", "name"} %>'
			portletURL="<%= commerceWarehouseItemSelectorViewDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />commerceWarehouseSelectorWrapper">
	<liferay-ui:search-container
		id="commerceWarehouses"
		searchContainer="<%= commerceWarehouseItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceWarehouse"
			keyProperty="commerceWarehouseId"
			modelVar="commerceWarehouse"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="city"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commerceWarehouseSelectorWrapper = A.one("#<portlet:namespace />commerceWarehouseSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceWarehouses');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(commerceWarehouseSelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>