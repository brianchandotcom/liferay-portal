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

<%@ include file="/product_list_entry_renderer/init.jsp" %>

<%
CPCatalogEntry cpCatalogEntry = (CPCatalogEntry)request.getAttribute("liferay-commerce-product:product-list-entry-renderer:cpCatalogEntry");
CPContentListEntryRenderer cpContentListEntryRenderer = (CPContentListEntryRenderer)request.getAttribute("liferay-commerce-product:product-list-entry-renderer:cpContentListEntryRenderer");

request.setAttribute(CPWebKeys.CP_CATALOG_ENTRY, cpCatalogEntry);

if (cpContentListEntryRenderer != null) {
	cpContentListEntryRenderer.render(request, PipingServletResponse.createPipingServletResponse(pageContext));
}
%>