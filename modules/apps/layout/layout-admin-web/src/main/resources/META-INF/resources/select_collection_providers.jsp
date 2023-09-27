<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			className="com.liferay.info.collection.provider.InfoCollectionProvider"
			modelVar="infoCollectionProvider"
		>
			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new CollectionProvidersVerticalCard(selectLayoutCollectionDisplayContext.getSelGroupId(), infoCollectionProvider, renderRequest, renderResponse) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>