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
long assetEntryId = ParamUtil.getLong(request, "assetEntryId");
String type = ParamUtil.getString(request, "type");

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(type);

AssetEntry assetEntry = assetRendererFactory.getAssetEntry(assetEntryId);

AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();
%>

<c:if test="<%= (assetEntry != null) && assetEntry.isVisible() %>">
	<c:if test="<%= assetRenderer.hasEditPermission(permissionChecker) %>">
		<div class="asset-actions lfr-meta-actions">

			<%
			PortletURL redirectURL = renderResponse.createRenderURL();

			redirectURL.setParameter("mvcPath", "/edit_content_redirect.jsp");

			PortletURL editPortletURL = assetRenderer.getURLEdit((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, LiferayWindowState.POP_UP, redirectURL);

			String taglibEditURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "editAsset', title: '" + HtmlUtil.escapeJS(LanguageUtil.format(request, "edit-x", HtmlUtil.escape(assetRenderer.getTitle(locale)), false)) + "', uri:'" + HtmlUtil.escapeJS(editPortletURL.toString()) + "'});";
			%>

			<liferay-ui:icon
				iconCssClass="icon-edit-sign"
				message='<%= HtmlUtil.render(LanguageUtil.format(request, "edit-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(assetRenderer.getTitle(locale))}, false)) %>'
				url="<%= taglibEditURL %>"
			/>
		</div>
	</c:if>

	<liferay-ui:header
		localizeTitle="<%= false %>"
		title="<%= assetRenderer.getTitle(locale) %>"
	/>

	<liferay-ui:asset-display
		assetEntry="<%= assetEntry %>"
		assetRenderer="<%= assetRenderer %>"
		assetRendererFactory="<%= assetRendererFactory %>"
	/>
</c:if>