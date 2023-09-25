<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/uom_tier_price/init.jsp" %>

<div class="mb-2 tier-price-table" id="<%= uomTierPriceId %>"></div>

<aui:script require="commerce-frontend-js/components/tier_price/entry as TierPrice">
	const props = {
		accountId: <%= commerceAccountId %>,
		channelId: <%= commerceChannelId %>,
		cpInstanceId: <%= cpInstanceId %>,
		namespace: '<%= namespace %>',
		productId: <%= productId %>,
	};

	TierPrice.default('<%= uomTierPriceId %>', '<%= uomTierPriceId %>', props);
</aui:script>