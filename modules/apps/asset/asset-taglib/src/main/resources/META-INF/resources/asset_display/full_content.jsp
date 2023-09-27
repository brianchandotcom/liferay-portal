<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset_display/init.jsp" %>

<%
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);
%>

<c:if test='<%= ParamUtil.getBoolean(request, "showHeader") %>'>
	<liferay-ui:header
		title="<%= assetRenderer.getTitle(locale) %>"
	/>
</c:if>

<%
String summary = StringUtil.shorten(assetRenderer.getSummary(renderRequest, renderResponse), Integer.MAX_VALUE);
%>

<%= HtmlUtil.escape(summary) %>