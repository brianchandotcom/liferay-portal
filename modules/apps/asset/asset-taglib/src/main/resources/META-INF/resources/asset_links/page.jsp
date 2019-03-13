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

<%@ include file="/asset_links/init.jsp" %>

<%
List<Tuple> assetLinkEntries = (List<Tuple>)request.getAttribute("liferay-asset:asset-links:assetLinkEntries");
%>

<liferay-ui:search-container
	total="<%= assetLinkEntries.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= assetLinkEntries %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.util.Tuple"
		modelVar="tuple"
	>

		<%
		AssetEntry assetLinkEntry = (AssetEntry)tuple.getObject(0);

		AssetRenderer assetRenderer = assetLinkEntry.getAssetRenderer();
		%>

		<liferay-ui:search-container-column-text
			name="related-assets"
		>
			<aui:icon cssClass="mr-2" image="<%= assetRenderer.getIconCssClass() %>" markupView="lexicon" />

			<aui:a href="<%= (String)tuple.getObject(1) %>" target='<%= themeDisplay.isStatePopUp() ? "_blank" : "_self" %>'>
				<%= HtmlUtil.escape(assetLinkEntry.getTitle(locale)) %>
			</aui:a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= false %>"
		searchResultCssClass="table table-autofit table-heading-nowrap"
	/>
</liferay-ui:search-container>