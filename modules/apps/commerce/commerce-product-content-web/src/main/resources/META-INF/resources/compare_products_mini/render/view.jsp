<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCompareContentHelper cpCompareContentHelper = (CPCompareContentHelper)request.getAttribute(CPContentWebKeys.CP_COMPARE_CONTENT_HELPER);

CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

long commerceAccountId = CommerceUtil.getCommerceAccountId(commerceContext);

List<CPCatalogEntry> cpCatalogEntries = cpCompareContentHelper.getCPCatalogEntries(commerceContext.getCommerceChannelGroupId(), commerceAccountId, request);
%>

<div id="mini-compare-root"></div>

<aui:script require="commerce-frontend-js/components/mini_compare/entry as MiniCompare">
	MiniCompare.default('mini-compare', 'mini-compare-root', {
		commerceChannelGroupId:
			'<%= commerceContext.getCommerceChannelGroupId() %>',
		compareProductsURL:
			'<%= cpCompareContentHelper.getCompareProductsURL(themeDisplay) %>',
		items: [

			<%
			for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
			%>

				{
					id: '<%= cpCatalogEntry.getCPDefinitionId() %>',
					thumbnail:
						'<%= cpCompareContentHelper.getDefaultImageFileURL(commerceAccountId, cpCatalogEntry.getCPDefinitionId()) %>',
				},

			<%
			}
			%>

		],
		itemsLimit: <%= cpCompareContentHelper.getProductsLimit(portletDisplay) %>,
		portletNamespace:
			'<%= cpCompareContentHelper.getCompareContentPortletNamespace() %>',
		spritemap: '<%= themeDisplay.getPathThemeSpritemap() %>',
	});
</aui:script>