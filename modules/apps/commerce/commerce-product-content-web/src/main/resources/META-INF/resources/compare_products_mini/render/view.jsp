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
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

long commerceAccountId = 0;

CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

if (commerceAccount != null) {
	commerceAccountId = GetterUtil.getLong(commerceAccount.getCommerceAccountId());
}

HttpServletRequest originalHttpServletRequest = PortalUtil.getOriginalServletRequest(request);

List<CPCatalogEntry> cpCatalogEntries = CPCompareHelperUtil.getCPCatalogEntries(commerceContext.getCommerceChannelGroupId(), commerceAccountId, originalHttpServletRequest.getSession());
%>

<div id="mini-compare-root"></div>

<aui:script require="commerce-frontend-js/components/mini_compare/entry as MiniCompare">
	MiniCompare.default('mini-compare', 'mini-compare-root', {
		compareProductsURL: '<%= cpCompareContentHelper.getCompareProductsURL(themeDisplay) %>',
		editCompareProductActionURL: '<%= cpCompareContentHelper.getEditCompareProductActionURL(request) %>',
		items: <%= jsonSerializer.serializeDeep(cpCatalogEntries) %>.map(function(item){
			return {
			id: item.CPDefinitionId,
			thumbnail: item.defaultImageFileUrl
			}
			}),
		itemsLimit: <%= cpCompareContentHelper.getProductsLimit(portletDisplay) %>,
		portletNamespace: '<%= cpCompareContentHelper.getCompareContentPortletNamespace() %>',
		spritemap: '<%= themeDisplay.getPathThemeImages() + "/icons.svg" %>'
	});
</aui:script>
