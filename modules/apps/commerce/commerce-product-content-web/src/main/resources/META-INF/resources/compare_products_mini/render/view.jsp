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
CPCompareContentHelper cpCompareContentHelper = (CPCompareContentHelper)request.getAttribute(CPContentWebKeys.CP_COMPARE_CONTENT_HELPER);
CPCompareContentMiniDisplayContext cpCompareContentMiniDisplayContext = (CPCompareContentMiniDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDataSourceResult cpDataSourceResult = cpCompareContentMiniDisplayContext.getCPDataSourceResult();

List<CPCatalogEntry> cpCatalogEntries = cpDataSourceResult.getCPCatalogEntries();
%>

<div id="mini-compare-root"></div>

<aui:script require="commerce-frontend-js/components/mini_compare/entry as MiniCompare">
	MiniCompare.default('mini-compare', 'mini-compare-root', {
		compareProductsURL: '<%= cpCompareContentHelper.getCompareProductsURL(themeDisplay) %>',
		editCompareProductActionURL: '<%= cpCompareContentHelper.getEditCompareProductActionURL(request) %>',
		items: <%= jsonSerializer.serializeDeep(cpCatalogEntries) %>,
		itemsLimit: <%= cpCompareContentHelper.getProductsLimit(portletDisplay) %>,
		portletNamespace: '<%= cpCompareContentHelper.getCompareContentPortletNamespace() %>',
		spritemap: '<%= themeDisplay.getPathThemeImages() + "/icons.svg" %>'
	});
</aui:script>
