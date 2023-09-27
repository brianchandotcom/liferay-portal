<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	cpContentListRenderer.render(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
}
%>