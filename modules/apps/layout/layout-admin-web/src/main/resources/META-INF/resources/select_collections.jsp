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
SelectCollectionDisplayContext selectCollectionDisplayContext = new SelectCollectionDisplayContext(liferayPortletRequest, liferayPortletResponse);

SearchContainer <AssetListEntry> searchContainer = selectCollectionDisplayContext.getCollectionsSearchContainer();
%>

<div class="lfr-search-container-wrapper" id="<portlet:namespace/>collections">
	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= searchContainer %>"
		var="collectionsSearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.list.model.AssetListEntry"
			cssClass="entry"
			modelVar="assetListEntry"
		>

			<%
			row.setCssClass("entry-card entry-display-style lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new CollectionsVerticalCard(assetListEntry, selectCollectionDisplayContext.getSelGroupId(), renderRequest, renderResponse) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="metal-dom/src/all/dom as dom">
	var collections = document.getElementById('<portlet:namespace />collections');

	var addCollectionActionOptionQueryClickHandler = dom.delegate(
		collections,
		'click',
		'.select-collection-action-option',
		function (event) {}
	);

	function handleDestroyPortlet() {
		addCollectionActionOptionQueryClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>