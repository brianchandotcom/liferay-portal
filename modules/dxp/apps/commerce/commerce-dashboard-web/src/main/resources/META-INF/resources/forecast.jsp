<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

CommerceDashboardForecastDisplayContext commerceDashboardForecastDisplayContext = (CommerceDashboardForecastDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String assetCategoryIdsString = commerceDashboardForecastDisplayContext.getAssetCategoryIds();

String categoryIds = "[]";

if (Validator.isNotNull(assetCategoryIdsString)) {
	categoryIds = jsonSerializer.serializeDeep(assetCategoryIdsString.split(StringPool.COMMA));
}

String accountIds = "[]";

AccountEntry accountEntry = null;

if (commerceContext != null) {
	accountEntry = commerceContext.getAccountEntry();

	if (accountEntry != null) {
		accountIds = jsonSerializer.serializeDeep(new Long[] {accountEntry.getAccountEntryId()});
	}
}
%>

<c:if test="<%= commerceDashboardForecastDisplayContext.hasViewPermission() %>">
	<react:component
		module="{ForecastChart} from commerce-dashboard-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"accountIds", accountIds
			).put(
				"APIBaseUrl", "/o/headless-commerce-machine-learning/v1.0/accountCategoryForecasts/by-monthlyRevenue"
			).put(
				"categoryIds", categoryIds
			).put(
				"portletId", portletDisplay.getId()
			).build()
		%>'
	/>
</c:if>