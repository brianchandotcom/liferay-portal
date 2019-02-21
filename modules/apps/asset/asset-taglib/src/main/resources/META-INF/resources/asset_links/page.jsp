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
	<h2 class="asset-links-title">
		<aui:icon image="link" />

		<liferay-ui:message key="related-assets" />:
	</h2>

	<ul class="asset-links-list">

		<%
		for (Tuple tuple : assetLinkEntries) {
			AssetEntry assetLinkEntry = (AssetEntry)tuple.getObject(0);
		%>

			<li class="asset-links-list-item">
				<aui:a href="<%= (String)tuple.getObject(1) %>" target='<%= themeDisplay.isStatePopUp() ? "_blank" : "_self" %>'>
					<%= HtmlUtil.escape(assetLinkEntry.getTitle(locale)) %>
				</aui:a>
			</li>

		<%
		}
		%>

	</ul>
</div>