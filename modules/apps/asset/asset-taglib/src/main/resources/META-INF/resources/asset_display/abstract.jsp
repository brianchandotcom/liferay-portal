<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset_display/init.jsp" %>

<%
int abstractLength = GetterUtil.getInteger(request.getAttribute(WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH));
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);
%>

<div class="asset-summary">

	<%
	String imagePreviewURL = assetRenderer.getURLImagePreview(renderRequest);
	%>

	<c:if test="<%= Validator.isNotNull(imagePreviewURL) %>">
		<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image mb-4" style="background-image: url(<%= imagePreviewURL %>);"></div>
	</c:if>

	<%
	String summary = StringUtil.shorten(assetRenderer.getSummary(renderRequest, renderResponse), abstractLength);
	%>

	<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(summary)) %>
</div>