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

<div class="taglib-asset-links">
	<ul class="asset-links-list list-group">
		<li class="list-group-header">
			<h3 class="list-group-header-title">
				<liferay-ui:message key="related-assets" />
			</h3>
		</li>

		<%
		for (Tuple tuple : assetLinkEntries) {
			AssetEntry assetLinkEntry = (AssetEntry)tuple.getObject(0);

			AssetRenderer assetRenderer = assetLinkEntry.getAssetRenderer();
		%>

			<li class="list-group-item list-group-item-flex">
				<div class="autofit-col">
					<div class="sticker sticker-secondary">
						<span class="inline-item">
							<aui:icon image="<%= assetRenderer.getIconCssClass() %>" markupView="lexicon" />
						</span>
					</div>
				</div>

				<div class="autofit-col autofit-col-expand">
					<h4 class="list-group-title text-truncate">
						<aui:a href="<%= (String)tuple.getObject(1) %>" target='<%= themeDisplay.isStatePopUp() ? "_blank" : "_self" %>'>
							<%= HtmlUtil.escape(assetLinkEntry.getTitle(locale)) %>
						</aui:a>
					</h4>
				</div>
			</li>

		<%
		}
		%>

	</ul>
</div>