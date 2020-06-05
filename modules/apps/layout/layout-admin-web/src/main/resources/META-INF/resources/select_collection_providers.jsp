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

SearchContainer <InfoListProvider<?>> searchContainer = selectCollectionDisplayContext.getCollectionProvidersSearchContainer();
%>

<div class="lfr-search-container-wrapper" id="<portlet:namespace/>collectionProviders">
	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= searchContainer %>"
		var="collectionsSearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.info.list.provider.InfoListProvider"
			cssClass="entry"
			modelVar="infoListProvider"
		>

			<%
			row.setCssClass("entry-card entry-display-style lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new CollectionProvidersVerticalCard(selectCollectionDisplayContext.getSelGroupId(), infoListProvider, renderRequest, renderResponse) %>"
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