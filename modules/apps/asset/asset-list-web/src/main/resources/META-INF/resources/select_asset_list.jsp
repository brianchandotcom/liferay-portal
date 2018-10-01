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
SelectAssetListDisplayContext selectAssetListDisplayContext = new SelectAssetListDisplayContext(request, renderResponse);
%>

<clay:management-toolbar
	clearResultsURL="<%= selectAssetListDisplayContext.getAssetListEntryClearResultsURL() %>"
	componentId="assetListEntriesEntriesManagementToolbar"
	filterDropdownItems="<%= selectAssetListDisplayContext.getAssetListEntryFilterItemsDropdownItems() %>"
	itemsTotal="<%= selectAssetListDisplayContext.getAssetListEntryTotalItems() %>"
	searchActionURL="<%= selectAssetListDisplayContext.getAssetListEntrySearchActionURL() %>"
	searchContainerId="assetListEntries"
	sortingOrder="<%= selectAssetListDisplayContext.getOrderByType() %>"
	sortingURL="<%= selectAssetListDisplayContext.getSortingURL() %>"
/>

<div class="container-fluid-1280" id="<portlet:namespace />assetLists">
	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-asset-lists"
		id="assetListEntries"
		searchContainer="<%= selectAssetListDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.list.model.AssetListEntry"
			cssClass="asset-list-entry entry-display-style"
			keyProperty="assetListEntryId"
			modelVar="assetListEntry"
		>

			<%
			Map<String, Object> data = new HashMap<>();

			data.put("assetListEntryId", assetListEntry.getAssetListEntryId());
			data.put("assetListEntryTitle", assetListEntry.getTitle());
			%>

			<liferay-ui:search-container-column-icon
				icon="list"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5>
					<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
				</h5>

				<h6 class="text-default">
					<strong><liferay-ui:message key="<%= HtmlUtil.escape(assetListEntry.getTypeLabel()) %>" /></strong>
				</h6>
			</liferay-ui:search-container-column-text>

			<c:if test="<%= assetListEntry.getAssetListEntryId() != selectAssetListDisplayContext.getSelectedAssetListEntryId() %>">
				<liferay-ui:search-container-column-text>
					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
			searchContainer="<%= selectAssetListDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />assetLists', '<%= selectAssetListDisplayContext.getEventName() %>');
</aui:script>