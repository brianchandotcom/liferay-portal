<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (Validator.isNull(request.getAttribute(WebKeys.PAGE_DESCRIPTION))) {
			String summary = StringUtil.shorten(assetRenderer.getSummary(liferayPortletRequest, liferayPortletResponse), assetPublisherDisplayContext.getAbstractLength());

			PortalUtil.setPageDescription(summary, request);
		}

		PortalUtil.setPageKeywords(assetHelper.getAssetKeywords(assetEntry.getClassName(), assetEntry.getClassPK(), locale), request);
		PortalUtil.setPageTitle(assetRenderer.getTitle(locale), request);
		%>

		<liferay-util:include page="/view_asset_entry_full_content.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/error.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>

<c:if test="<%= assetPublisherDisplayContext.isEnabledAutoscroll() %>">
	<aui:script>
		Liferay.once('allPortletsReady', () => {
			document
				.getElementById('p_p_id_<%= portletDisplay.getId() %>_')
				.scrollIntoView();
		});
	</aui:script>
</c:if>

<liferay-util:dynamic-include key="com.liferay.asset.publisher.web#/view_content.jsp#post" />