<%@ page import="com.liferay.portlet.announcements.NoSuchEntryException" %>
<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/asset_links/init.jsp" %>

<%
String className = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-links:className"));
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-links:classPK"));

AssetEntry assetEntry = null;

List<AssetLink> assetLinks = new ArrayList<AssetLink>();

if (classPK > 0) {
	try {
		assetEntry = AssetEntryLocalServiceUtil.getEntry(className, classPK);
	}
	catch (NoSuchEntryException nsee) {
	}

	if (assetEntry != null) {
		assetLinks = AssetLinkLocalServiceUtil.getDirectLinks(assetEntry.getEntryId());
	}
}
%>

<c:if test="<%= !assetLinks.isEmpty() %>">
	<div class="taglib-asset-links">
		<h2 class="asset-links-title"><liferay-ui:message key="related-assets" />:</h2>

		<ul class="asset-links-list">

			<%
			for (AssetLink assetLink : assetLinks) {
				AssetEntry assetLinkEntry = null;

				if (assetLink.getEntryId1() == assetEntry.getEntryId()) {
					assetLinkEntry = AssetEntryServiceUtil.getEntry(assetLink.getEntryId2());
				}
				else {
					assetLinkEntry = AssetEntryServiceUtil.getEntry(assetLink.getEntryId1());
				}

				if (!assetLinkEntry.isVisible()) {
					continue;
				}

				AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(PortalUtil.getClassName(assetLinkEntry.getClassNameId()));

				AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(assetLinkEntry.getClassPK());

				if (assetRenderer.hasViewPermission(permissionChecker)) {
					String asseLinktEntryTitle = assetRenderer.getTitle(locale);

					String urlViewInContext = assetRenderer.getURLViewInContext((LiferayPortletRequest)portletRequest, (LiferayPortletResponse)portletResponse, "viewFullContentURLString");
			%>

					<li class="asset-links-list-item">
						<liferay-ui:icon
							label="<%= true %>"
							message="<%= asseLinktEntryTitle %>"
							src="<%= assetRenderer.getIconPath(portletRequest) %>"
							url="<%= urlViewInContext %>"
						/>
					</li>

			<%
				}
			}
			%>

		</ul>
	</div>
</c:if>