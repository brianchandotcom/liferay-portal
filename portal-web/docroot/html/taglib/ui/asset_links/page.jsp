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

<%@ include file="/html/taglib/ui/asset_links/init.jsp" %>

<%
long assetEntryId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-links:assetEntryId"));

List<AssetLink> assetLinks = null;

if (assetEntryId > 0) {
	assetLinks = AssetLinkLocalServiceUtil.getDirectLinks(assetEntryId);
}
%>

<c:if test="<%= (assetLinks != null) && !assetLinks.isEmpty() %>">
	<div class="taglib-asset-links">
		<h2 class="asset-links-title">
			<aui:icon image="link" />

			<liferay-ui:message key="related-assets" />:
		</h2>

		<ul class="asset-links-list">

			<%
			for (AssetLink assetLink : assetLinks) {
				AssetEntry assetLinkEntry = null;

				if (assetLink.getEntryId1() == assetEntryId) {
					assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId2());
				}
				else {
					assetLinkEntry = AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId1());
				}

				if (!assetLinkEntry.isVisible()) {
					continue;
				}

				assetLinkEntry = assetLinkEntry.toEscapedModel();

				AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassNameId(assetLinkEntry.getClassNameId());

				if (Validator.isNull(assetRendererFactory)) {
					if (_log.isWarnEnabled()) {
						_log.warn("No asset renderer factory found for class " + PortalUtil.getClassName(assetLinkEntry.getClassNameId()));
					}

					continue;
				}

				if (!assetRendererFactory.isActive(company.getCompanyId())) {
					continue;
				}

				AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(assetLinkEntry.getClassPK());

				if (assetRenderer.hasViewPermission(permissionChecker)) {
					String asseLinktEntryTitle = assetLinkEntry.getTitle(locale);

					PortletURL assetPublisherURL = PortletProviderUtil.getPortletURL(request, assetRenderer.getClassName(), PortletProvider.Action.VIEW);

					assetPublisherURL.setParameter("redirect", currentURL);
					assetPublisherURL.setParameter("assetEntryId", String.valueOf(assetLinkEntry.getEntryId()));
					assetPublisherURL.setParameter("type", assetRendererFactory.getType());

					if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
						if (assetRenderer.getGroupId() != themeDisplay.getSiteGroupId()) {
							assetPublisherURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
						}

						assetPublisherURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
					}

					assetPublisherURL.setWindowState(WindowState.MAXIMIZED);

					String viewFullContentURLString = assetPublisherURL.toString();

					viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

					String urlViewInContext = assetRenderer.getURLViewInContext((LiferayPortletRequest)portletRequest, (LiferayPortletResponse)portletResponse, viewFullContentURLString);

					urlViewInContext = HttpUtil.setParameter(urlViewInContext, "inheritRedirect", true);
			%>

					<li class="asset-links-list-item">
						<aui:a href="<%= urlViewInContext %>" target='<%= themeDisplay.isStatePopUp() ? "_blank" : "_self" %>'>
							<%= asseLinktEntryTitle %>
						</aui:a>
					</li>

			<%
				}
			}
			%>

		</ul>
	</div>
</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("portal_web.docroot.html.taglib.ui.asset_links.page_jsp");
%>