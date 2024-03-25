<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/unit_of_measure_tier_price/init.jsp" %>

<div class="mb-2 tier-price-table" id="<%= unitOfMeasureTierPriceId %>"></div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"accountId", commerceAccountId
		).put(
			"channelId", commerceChannelId
		).put(
			"cpInstanceId", cpInstanceId
		).put(
			"namespace", namespace
		).put(
			"productId", productId
		).put(
			"unitOfMeasureTierPriceId", unitOfMeasureTierPriceId
		).build()
	%>'
	module="{TierPrice} from commerce-taglib"
/>