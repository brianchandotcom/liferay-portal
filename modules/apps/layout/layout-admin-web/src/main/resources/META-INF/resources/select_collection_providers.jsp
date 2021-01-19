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

<div class="lfr-search-container-wrapper" id="<portlet:namespace />collectionProviders">
	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= selectLayoutCollectionDisplayContext.getCollectionProvidersSearchContainer() %>"
		var="collectionsSearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.info.list.provider.InfoListProvider"
			modelVar="infoListProvider"
		>
			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new CollectionProvidersVerticalCard(selectLayoutCollectionDisplayContext.getSelGroupId(), infoListProvider, renderRequest, renderResponse) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>