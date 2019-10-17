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

<liferay-util:dynamic-include key="com.liferay.asset.publisher.web#/view_content.jsp#pre" />

<%
AssetPublisherViewContentDisplayContext assetPublisherViewContentDisplayContext = new AssetPublisherViewContentDisplayContext(renderRequest, assetPublisherDisplayContext.isEnablePermissions());

if (Validator.isNotNull(assetPublisherViewContentDisplayContext.getReturnToFullPageURL())) {
	portletDisplay.setURLBack(assetPublisherViewContentDisplayContext.getReturnToFullPageURL());
}
%>

<c:choose>
	<c:when test="<%= assetPublisherViewContentDisplayContext.isAssetEntryVisible() %>">

		<%
		AssetEntry assetEntry = assetPublisherViewContentDisplayContext.getAssetEntry();
		AssetRenderer<?> assetRenderer = assetPublisherViewContentDisplayContext.getAssetRenderer();

		request.setAttribute("view.jsp-assetEntry", assetEntry);
		request.setAttribute("view.jsp-assetRenderer", assetRenderer);
		request.setAttribute("view.jsp-assetRendererFactory", assetPublisherViewContentDisplayContext.getAssetRendererFactory());
		request.setAttribute("view.jsp-print", assetPublisherViewContentDisplayContext.getPrint());
		request.setAttribute("view.jsp-showBackURL", assetPublisherViewContentDisplayContext.isShowBackURL());

		PortalUtil.addPortletBreadcrumbEntry(request, assetRenderer.getTitle(locale), currentURL);

		String summary = StringUtil.shorten(assetRenderer.getSummary(liferayPortletRequest, liferayPortletResponse), assetPublisherDisplayContext.getAbstractLength());

		PortalUtil.setPageDescription(summary, request);

		PortalUtil.setPageKeywords(assetHelper.getAssetKeywords(assetEntry.getClassName(), assetEntry.getClassPK()), request);
		PortalUtil.setPageTitle(assetRenderer.getTitle(locale), request);
		%>

		<liferay-util:include page="/view_asset_entry_full_content.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/error.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>

<aui:script>
	Liferay.once('allPortletsReady', function() {
		if (!Liferay.Browser.isIe()) {
			document
				.getElementById('p_p_id_<%= portletDisplay.getId() %>_')
				.scrollIntoView();
		}
	});
</aui:script>

<liferay-util:dynamic-include key="com.liferay.asset.publisher.web#/view_content.jsp#post" />