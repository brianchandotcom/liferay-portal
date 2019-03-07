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
AssetEntryResult assetEntryResult = (AssetEntryResult)request.getAttribute("view.jsp-assetEntryResult");

for (AssetEntry assetEntry : assetEntryResult.getAssetEntries()) {
	AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassNameId(assetEntry.getClassNameId());

	if (assetRendererFactory == null) {
		continue;
	}

	AssetRenderer<?> assetRenderer = null;

	try {
		assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());
	}
	catch (Exception e) {
		if (_log.isWarnEnabled()) {
			_log.warn(e, e);
		}
	}

	if ((assetRenderer == null) || !assetRenderer.isDisplayable()) {
		continue;
	}

	request.setAttribute("view.jsp-assetEntry", assetEntry);
	request.setAttribute("view.jsp-assetRenderer", assetRenderer);

	try {
		String title = assetRenderer.getTitle(locale);

		String viewURL = assetPublisherHelper.getAssetViewURL(liferayPortletRequest, liferayPortletResponse, assetRenderer, assetEntry, assetPublisherDisplayContext.isAssetLinkBehaviorViewInPortlet());
%>

		<div class="asset-abstract <%= assetPublisherWebUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), assetPublisherDisplayContext.getPortletResource()) ? "default-asset-publisher" : StringPool.BLANK %>">
			<liferay-util:include page="/asset_actions.jsp" servletContext="<%= application %>" />

			<span class="asset-anchor lfr-asset-anchor" id="<%= assetEntry.getEntryId() %>"></span>

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
					<liferay-asset:asset-display
						abstractLength="<%= assetPublisherDisplayContext.getAbstractLength() %>"
						assetEntry="<%= assetEntry %>"
						assetRenderer="<%= assetRenderer %>"
						assetRendererFactory="<%= assetRendererFactory %>"
						template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
						viewURL="<%= viewURL %>"
					/>
				</div>
			</div>

			<liferay-asset:asset-metadata
				className="<%= assetEntry.getClassName() %>"
				classPK="<%= assetEntry.getClassPK() %>"
				filterByMetadata="<%= true %>"
				metadataFields="<%= assetPublisherDisplayContext.getMetadataFields() %>"
			/>
		</div>

<%
	}
	catch (Exception e) {
		_log.error(e.getMessage());
	}
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_asset_publisher_web.view_asset_entries_abstract_jsp");
%>