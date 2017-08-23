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

<%@ include file="/html/taglib/ui/asset_display/init.jsp" %>

<%
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);

boolean showHeader = ParamUtil.getBoolean(request, "showHeader");
%>

<c:if test="<%= showHeader %>">
	<liferay-ui:header
		title="<%= assetRenderer.getTitle(locale) %>"
	/>
</c:if>

<%
String summary = StringUtil.shorten(assetRenderer.getSummary(renderRequest, renderResponse), Integer.MAX_VALUE);
%>

<%= HtmlUtil.escape(summary) %>