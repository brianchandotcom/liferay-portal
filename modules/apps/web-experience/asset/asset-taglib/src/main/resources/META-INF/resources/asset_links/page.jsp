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
long assetEntryId = GetterUtil.getLong((String)request.getAttribute("liferay-asset:asset-links:assetEntryId"));
List<AssetLink> assetLinks = (List<AssetLink>)request.getAttribute("liferay-asset:asset-links:assetLinks");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-asset:asset-links:portletURL");
boolean viewInContext = GetterUtil.getBoolean(request.getAttribute("liferay-asset:asset-links:viewInContext"), true);
%>

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
				Group group = GroupLocalServiceUtil.getGroup(assetLinkEntry.getGroupId());

				Group scopeGroup = themeDisplay.getScopeGroup();

				if (group.isStaged() && (group.isStagingGroup() ^ scopeGroup.isStagingGroup())) {
					continue;
				}

				PortletURL viewAssetURL = null;

				if (portletURL != null) {
					viewAssetURL = PortletURLUtil.clone(portletURL, PortalUtil.getLiferayPortletResponse(portletResponse));
				}
				else {
					viewAssetURL = PortletProviderUtil.getPortletURL(request, assetRenderer.getClassName(), PortletProvider.Action.VIEW);

					viewAssetURL.setParameter("redirect", currentURL);
					viewAssetURL.setWindowState(WindowState.MAXIMIZED);
				}

				viewAssetURL.setParameter("assetEntryId", String.valueOf(assetLinkEntry.getEntryId()));
				viewAssetURL.setParameter("type", assetRendererFactory.getType());

				if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
					if (assetRenderer.getGroupId() != themeDisplay.getSiteGroupId()) {
						viewAssetURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
					}

					viewAssetURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
				}

				String viewURL = null;

				if (viewInContext) {
					String noSuchEntryRedirect = viewAssetURL.toString();

					String urlViewInContext = assetRenderer.getURLViewInContext((LiferayPortletRequest)portletRequest, (LiferayPortletResponse)portletResponse, noSuchEntryRedirect);

					if (Validator.isNotNull(urlViewInContext) && !Objects.equals(urlViewInContext, noSuchEntryRedirect)) {
						urlViewInContext = HttpUtil.setParameter(urlViewInContext, "inheritRedirect", Boolean.TRUE);
						urlViewInContext = HttpUtil.setParameter(urlViewInContext, "redirect", currentURL);
					}

					viewURL = urlViewInContext;
				}

				if (Validator.isNull(viewURL)) {
					viewURL = viewAssetURL.toString();
				}
		%>

				<li class="asset-links-list-item">
					<aui:a href="<%= viewURL %>" target='<%= themeDisplay.isStatePopUp() ? "_blank" : "_self" %>'>
						<%= HtmlUtil.escape(assetLinkEntry.getTitle(locale)) %>
					</aui:a>
				</li>

		<%
			}
		}
		%>

	</ul>
</div>

<%!
private static Log _log = LogFactoryUtil.getLog("com.liferay.asset.taglib.asset_links.page_jsp");
%>