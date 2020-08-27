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

<%@ include file="/product_list_renderer/init.jsp" %>

<%
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute("liferay-commerce-product:product-list-renderer:cpContentHelper");
CPContentListRenderer cpContentListRenderer = (CPContentListRenderer)request.getAttribute("liferay-commerce-product:product-list-renderer:cpContentListRenderer");
CPDataSourceResult cpDataSourceResult = (CPDataSourceResult)request.getAttribute("liferay-commerce-product:product-list-renderer:cpDataSourceResult");
Map<String, String> entryKeys = (Map<String, String>)request.getAttribute("liferay-commerce-product:product-list-renderer:entryKeys");

request.setAttribute(CPContentWebKeys.CP_CONTENT_HELPER, cpContentHelper);
request.setAttribute(CPContentWebKeys.CP_CONTENT_LIST_ENTRY_RENDERER_KEYS, entryKeys);
request.setAttribute(CPWebKeys.CP_DATA_SOURCE_RESULT, cpDataSourceResult);

if (cpContentListRenderer != null) {
	cpContentListRenderer.render(request, PipingServletResponse.createPipingServletResponse(pageContext));
}
%>