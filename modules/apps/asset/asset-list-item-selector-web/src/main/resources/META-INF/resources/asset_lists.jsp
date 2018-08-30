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
AssetListEntry selectedAssetListEntry = assetListItemSelectorViewDisplayContext.getSelectedAssetListEntry();
%>

<div class="container-fluid-1280" id="<portlet:namespace />assetLists">
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
			<div>
				<liferay-ui:search-container-column-icon
					icon="list"
				/>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<div class="asset-list-entry-data" data-asset-list-entry-id="<%= assetListEntry.getAssetListEntryId() %>" data-asset-list-entry-title="<%= assetListEntry.getTitle() %>">
						<h5>
							<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
						</h5>

						<h6 class="text-default">
							<strong><liferay-ui:message key="<%= HtmlUtil.escape(assetListEntry.getTypeLabel()) %>" /></strong>
						</h6>
					</div>
				</liferay-ui:search-container-column-text>
			</div>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
			searchContainer="<%= assetListItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="metal-dom/src/all/dom as dom">
	var selectAssetListEntryHandler = dom.delegate(
		document.querySelector('#<portlet:namespace/>assetLists'),
		'click',
		'.asset-list-entry',
		function(event) {
			document.querySelectorAll('.asset-list-entry-checkbox').forEach(
				function(checkbox) {
					checkbox.checked = false;
				}
			);

			dom.removeClasses(document.querySelectorAll('.asset-list-entry.active'), 'active');
			dom.addClasses(event.delegateTarget, 'active');

			event.delegateTarget.querySelector('input').checked = true;

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

	<c:if test="<%= selectedAssetListEntry != null %>">
		var assetListEntry = dom.closest(document.querySelector('[data-asset-list-entry-id="<%= selectedAssetListEntry.getAssetListEntryId() %>"]'), '.asset-list-entry');

		assetListEntry.click();
	</c:if>
</aui:script>