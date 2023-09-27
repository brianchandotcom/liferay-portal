<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/product_list_entry_renderer/init.jsp" %>

<%
CPCatalogEntry cpCatalogEntry = (CPCatalogEntry)request.getAttribute("liferay-commerce-product:product-list-entry-renderer:cpCatalogEntry");
CPContentListEntryRenderer cpContentListEntryRenderer = (CPContentListEntryRenderer)request.getAttribute("liferay-commerce-product:product-list-entry-renderer:cpContentListEntryRenderer");

request.setAttribute(CPWebKeys.CP_CATALOG_ENTRY, cpCatalogEntry);

if (cpContentListEntryRenderer != null) {
	cpContentListEntryRenderer.render(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
}
%>