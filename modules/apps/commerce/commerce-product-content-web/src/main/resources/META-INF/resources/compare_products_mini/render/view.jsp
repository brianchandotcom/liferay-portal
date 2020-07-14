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

CPDataSourceResult cpDataSourceResult = (CPDataSourceResult)request.getAttribute(CPWebKeys.CP_DATA_SOURCE_RESULT);

List<CPCatalogEntry> cpCatalogEntries = cpDataSourceResult.getCPCatalogEntries();

CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

long commerceAccountId = 0;

CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

if (commerceAccount != null) {
	commerceAccountId = GetterUtil.getLong(commerceAccount.getCommerceAccountId());
}

HttpServletRequest originalHttpServletRequest = PortalUtil.getOriginalServletRequest(request);

List<Long> cpDefinitionIds = CPCompareHelperUtil.getCPDefinitionIds(commerceContext.getCommerceChannelGroupId(), commerceAccountId, originalHttpServletRequest.getSession());
%>

<div id="mini-compare-root"></div>

<aui:script require="commerce-frontend-js/components/mini_compare/entry as miniCompare">
	miniCompare.default('mini-compare', 'mini-compare-root', {
		compareProductsURL: '/test',
		editCompareProductActionURL: '/test',
		items: [],
		itemsLimit: 5,
		portletNamespace: 'portletNamespace',
		spritemap: '/o/admin-theme/images/clay/icons.svg'
	});
</aui:script>
