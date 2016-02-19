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
AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory<?> assetRendererFactory = (AssetRendererFactory<?>)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");

request.setAttribute("view.jsp-showIconLabel", true);

String title = (String)request.getAttribute("view.jsp-title");

if (Validator.isNull(title)) {
	title = assetRenderer.getTitle(locale);
}

boolean viewInContext = ((Boolean)request.getAttribute("view.jsp-viewInContext")).booleanValue();

String viewURL = AssetPublisherHelper.getAssetViewURL(liferayPortletRequest, liferayPortletResponse, assetEntry, viewInContext);
%>

<div class="asset-abstract <%= AssetUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), assetPublisherDisplayContext.getPortletResource()) ? "default-asset-publisher" : StringPool.BLANK %>">
	<liferay-util:include page="/asset_actions.jsp" servletContext="<%= application %>" />

	<h4 class="asset-title">
		<c:if test="<%= Validator.isNotNull(viewURL) %>">
			<a href="<%= viewURL %>">
		</c:if>

		<%= HtmlUtil.escape(title) %>

		<c:if test="<%= Validator.isNotNull(viewURL) %>">
			</a>
		</c:if>
	</h4>

	<div class="asset-content">
		<div class="asset-summary">
			<liferay-ui:asset-display
				abstractLength="<%= assetPublisherDisplayContext.getAbstractLength() %>"
				assetEntry="<%= assetEntry %>"
				assetRenderer="<%= assetRenderer %>"
				assetRendererFactory="<%= assetRendererFactory %>"
				template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
				viewURL="<%= viewURL %>"
			/>
		</div>
	</div>

	<liferay-ui:asset-metadata
		className="<%= assetEntry.getClassName() %>"
		classPK="<%= assetEntry.getClassPK() %>"
		filterByMetadata="<%= true %>"
		metadataFields="<%= assetPublisherDisplayContext.getMetadataFields() %>"
	/>
</div>