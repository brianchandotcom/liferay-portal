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
SelectLayoutCollectionDisplayContext selectLayoutCollectionDisplayContext = (SelectLayoutCollectionDisplayContext)request.getAttribute(LayoutAdminWebKeys.SELECT_LAYOUT_COLLECTION_DISPLAY_CONTEXT);
%>

<div class="lfr-search-container-wrapper" id="<portlet:namespace />collections">
	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= selectLayoutCollectionDisplayContext.getCollectionsSearchContainer() %>"
		var="collectionsSearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.list.model.AssetListEntry"
			modelVar="assetListEntry"
		>
			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new CollectionsVerticalCard(assetListEntry, selectLayoutCollectionDisplayContext.getSelGroupId(), renderRequest, renderResponse) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
	var collections = document.getElementById('<portlet:namespace />collections');

	var delegate = delegateModule.default;

	var addCollectionActionOptionQueryClickHandler = delegate(
		collections,
		'click',
		'.select-collection-action-option',
		function (event) {}
	);

	function handleDestroyPortlet() {
		addCollectionActionOptionQueryClickHandler.dispose();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>