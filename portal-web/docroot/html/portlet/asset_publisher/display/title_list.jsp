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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
List results = (List)request.getAttribute("view.jsp-results");

int assetEntryIndex = ((Integer)request.getAttribute("view.jsp-assetEntryIndex")).intValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory assetRendererFactory = (AssetRendererFactory)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute("view.jsp-assetRenderer");

String title = (String)request.getAttribute("view.jsp-title");

if (Validator.isNull(title)) {
	title = assetRenderer.getTitle(locale);
}

request.setAttribute("view.jsp-showIconLabel", false);

PortletURL viewFullContentURL = renderResponse.createRenderURL();

viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");
viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
viewFullContentURL.setParameter("type", assetRendererFactory.getType());

if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
	if (assetRenderer.getGroupId() != scopeGroupId) {
		viewFullContentURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
	}

	viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
}

String viewURL = null;

boolean viewInContext = ((Boolean)request.getAttribute("view.jsp-viewInContext")).booleanValue();

if (viewInContext) {
	String viewFullContentURLString = viewFullContentURL.toString();

	viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

	viewURL = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, viewFullContentURLString);
}
else {
	viewURL = viewFullContentURL.toString();
}

viewURL = AssetUtil.checkViewURL(assetEntry, viewInContext, viewURL, currentURL, themeDisplay);
%>

	<c:if test="<%= assetEntryIndex == 0 %>">
		<ul class="title-list">
	</c:if>

	<li class="title-list <%= assetRendererFactory.getType() %>">
		<liferay-ui:icon
			iconCssClass="<%= assetRenderer.getIconCssClass() %>"
			label="<%= true %>"
			message="<%= HtmlUtil.escape(title) %>"
			url="<%= viewURL %>"
		/>

		<liferay-util:include page="/html/portlet/asset_publisher/asset_actions.jsp" />

		<div class="asset-metadata">

			<%
			boolean filterByMetadata = true;

			String[] metadataFields = assetPublisherDisplayContext.getMetadataFields();
			%>

			<%@ include file="/html/portlet/asset_publisher/asset_metadata.jspf" %>
		</div>
	</li>

	<c:if test="<%= (assetEntryIndex + 1) == results.size() %>">
		</ul>
	</c:if>