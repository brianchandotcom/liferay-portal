<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long previewClassNameId = ParamUtil.getLong(request, "previewClassNameId");
long previewClassPK = ParamUtil.getLong(request, "previewClassPK");
int previewType = ParamUtil.getInteger(request, "previewType");

AssetEntryResult assetEntryResult = (AssetEntryResult)request.getAttribute("view.jsp-assetEntryResult");
%>

<c:if test="<%= Validator.isNotNull(assetEntryResult.getTitle()) %>">
	<p class="asset-entries-group-label h3"><%= HtmlUtil.escape(assetEntryResult.getTitle()) %></p>
</c:if>

<%
for (AssetEntry assetEntry : assetEntryResult.getAssetEntries()) {
	AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetEntry.getClassName());

	if (assetRendererFactory == null) {
		continue;
	}

	AssetRenderer<?> assetRenderer = null;

	try {
		if ((previewClassNameId == assetEntry.getClassNameId()) && (previewClassPK == assetEntry.getClassPK())) {
			assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK(), previewType);
		}
		else {
			assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());
		}
	}
	catch (Exception e) {
		if (_log.isWarnEnabled()) {
			_log.warn(e);
		}
	}

	if ((assetRenderer == null) || (!assetRenderer.isDisplayable() && (previewClassPK <= 0))) {
		continue;
	}

	request.setAttribute("view.jsp-assetEntry", assetEntry);
	request.setAttribute("view.jsp-assetRenderer", assetRenderer);
	request.setAttribute("view.jsp-assetRendererFactory", assetRendererFactory);
%>

	<liferay-util:include page="/view_asset_entry_full_content.jsp" servletContext="<%= application %>">
		<liferay-util:param name="viewSingleAsset" value="false" />
	</liferay-util:include>

<%
}
%>

<%!
private static final Log _log = LogFactoryUtil.getLog("com_liferay_asset_publisher_web.view_asset_entries_full_content_jsp");
%>