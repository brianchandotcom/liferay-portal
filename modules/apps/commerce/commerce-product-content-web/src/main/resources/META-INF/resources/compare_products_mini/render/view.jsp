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

JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

List<JSONObject> itemsList = new ArrayList<>();

for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
	itemsList.add(
		JSONFactoryUtil.createJSONObject(
		).put(
			"id", cpCatalogEntry.getCPDefinitionId()
		).put(
			"thumbnail", cpCompareContentHelper.getDefaultImageFileURL(commerceAccountId, cpCatalogEntry.getCPDefinitionId())
		));
}
%>

<div id="mini-compare-root"></div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"commerceChannelGroupId", commerceContext.getCommerceChannelGroupId()
		).put(
			"compareProductsURL", String.valueOf(cpCompareContentHelper.getCompareProductsURL(themeDisplay))
		).put(
			"items", jsonSerializer.serializeDeep(itemsList)
		).put(
			"itemsLimit", cpCompareContentHelper.getProductsLimit(portletDisplay)
		).put(
			"portletNamespace", cpCompareContentHelper.getCompareContentPortletNamespace()
		).put(
			"spritemap", themeDisplay.getPathThemeSpritemap()
		).build()
	%>'
	module="{miniCompare} from commerce-product-content-web"
/>