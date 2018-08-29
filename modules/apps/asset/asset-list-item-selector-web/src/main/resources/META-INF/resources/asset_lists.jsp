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

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-asset-lists"
	id="assetListEntries"
	searchContainer="<%= assetListItemSelectorViewDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.asset.list.model.AssetListEntry"
		cssClass="asset-list-entry entry-display-style"
		keyProperty="assetListEntryId"
		modelVar="assetListEntry"
	>
		<liferau-ui:search-container-column-icon
			icon="list"
		/>

		<liferay-ui:search-container-column-text
			colspan="<%= 2 %>"
		>
			<div class="asset-list-entry-data" data-asset-list-entry-id="<%= assetListEntry.getAssetListEntryId() %>">
				<h5>
					<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
				</h5>

				<h6 class="text-default">
					<strong><liferay-ui:message key="<%= HtmlUtil.escape(assetListEntry.getTypeLabel()) %>" /></strong>
				</h6>
			</div>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="descriptive"
		markupView="lexicon"
		searchContainer="<%= assetListItemSelectorViewDisplayContext.getSearchContainer() %>"
	/>
</liferay-ui:search-container>

<aui:script require="metal-dom/src/all/dom as dom">
	var selectAssetListEntryHandler = dom.delegate(
		document.querySelector('#<portlet:namespace/>fm'),
		'click',
		'.asset-list-entry',
		function(event) {
			dom.removeClasses(document.querySelectorAll('.asset-list-entry.active'), 'active');
			dom.addClasses(event.delegateTarget, 'active');

			Liferay.Util.getOpener().Liferay.fire(
				'<%= assetListItemSelectorViewDisplayContext.getEventName() %>',
				{
					data: event.delegateTarget.querySelector('.asset-list-entry-data').dataset
				}
			);
		}
	);

	function removeListener() {
		selectAssetListEntryHandler.removeListener();

		Liferay.detach('destroyPortlet', removeListener);
	}

	Liferay.on('destroyPortlet', removeListener);
</aui:script>