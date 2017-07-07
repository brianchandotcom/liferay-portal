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

<%@ include file="/html/taglib/ui/input_asset_links/init.jsp" %>

<liferay-util:buffer var="removeLinkIcon">
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="none"
	headerNames="type,title,scope,null"
	total="<%= inputAssetLinksDisplayContext.getAssetLinksCount() %>"
>
	<liferay-ui:search-container-results
		results="<%= inputAssetLinksDisplayContext.getAssetLinks() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.asset.kernel.model.AssetLink"
		keyProperty="entryId2"
		modelVar="assetLink"
	>

		<%
		AssetEntry assetLinkEntry = inputAssetLinksDisplayContext.getAssetLinkEntry(assetLink);
		%>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= inputAssetLinksDisplayContext.getAssetType(assetLinkEntry) %>"
		/>

		<liferay-ui:search-container-column-text
			name="title"
			truncate="<%= true %>"
			value="<%= HtmlUtil.escape(assetLinkEntry.getTitle(locale)) %>"
		/>

		<liferay-ui:search-container-column-text
			name="scope"
			value="<%= HtmlUtil.escape(inputAssetLinksDisplayContext.getGroupDescriptiveName(assetLinkEntry)) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= assetLinkEntry.getEntryId() %>" href="javascript:;"><%= removeLinkIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
</liferay-ui:search-container>

<liferay-ui:icon-menu
	cssClass="select-existing-selector"
	direction="right"
	id='<%= inputAssetLinksDisplayContext.getRandomNamespace() + "inputAssetLinks" %>'
	message="select"
	showArrow="<%= false %>"
	showWhenSingleIcon="<%= true %>"
>

	<%
	for (Map<String, Object> selectorEntry : inputAssetLinksDisplayContext.getSelectorEntries()) {
	%>

		<liferay-ui:icon
			cssClass="asset-selector"
			data='<%= (Map<String, Object>)selectorEntry.get("data") %>'
			id='<%= (String)selectorEntry.get("id") %>'
			message='<%= HtmlUtil.escape((String)selectorEntry.get("message")) %>'
			url="javascript:;"
		/>

	<%
	}
	%>

</liferay-ui:icon-menu>

<aui:input name="assetLinkEntryIds" type="hidden" />

<aui:script use="aui-base,escape,liferay-search-container">
	var assetSelectorHandle = A.getBody().delegate(
		'click',
		function(event) {
			event.preventDefault();

			var searchContainerName = '<portlet:namespace />assetLinksSearchContainer';

			var searchContainer = Liferay.SearchContainer.get(searchContainerName);

			var searchContainerData = searchContainer.getData();

			if (searchContainerData) {
				searchContainerData = searchContainerData.split(',');
			}
			else {
				searchContainerData = [];
			}

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					eventName: '<%= inputAssetLinksDisplayContext.getEventName() %>',
					id: '<%= inputAssetLinksDisplayContext.getEventName() %>' + event.currentTarget.attr('id'),
					selectedData: searchContainerData,
					title: event.currentTarget.attr('data-title'),
					uri: event.currentTarget.attr('data-href')
				},
				function(event) {
					var entityId = event.entityid;

					var entryLink = '<a class="modify-link" data-rowId="' + entityId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeLinkIcon) %></a>';

					searchContainer.addRow([event.assettype, A.Escape.html(event.assettitle), A.Escape.html(event.groupdescriptivename), entryLink], entityId);

					searchContainer.updateDataStore();
				}
			);
		},
		'.asset-selector a'
	);

	var clearAssetSelectorHandle = function(event) {
		if (event.portletId === '<%= portletDisplay.getId() %>') {
			assetSelectorHandle.detach();

			Liferay.detach('destroyPortlet', clearAssetSelectorHandle);
		}
	};

	Liferay.on('destroyPortlet', clearAssetSelectorHandle);
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />assetLinksSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>