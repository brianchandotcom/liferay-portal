<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/request_quote/init.jsp" %>

<c:if test="<%= priceOnApplication || requestQuoteEnabled %>">
	<div class="request-quote-wrapper" id="<%= requestQuoteElementId %>">
		<button class="btn btn-lg request-quote skeleton">
			<liferay-ui:message key="request-a-quote" />
		</button>
	</div>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"accountId", commerceAccountId
			).put(
				"channel",
				HashMapBuilder.<String, Object>put(
					"currencyCode", commerceCurrencyCode
				).put(
					"id", commerceChannelId
				).put(
					"requestQuoteEnabled", requestQuoteEnabled
				).build()
			).put(
				"cpDefinitionId", cpDefinitionId
			).put(
				"cpInstance",
				HashMapBuilder.<String, Object>put(
					"priceOnApplication", priceOnApplication
				).put(
					"skuId", cpInstanceId
				).put(
					"skuOptions", skuOptions
				).build()
			).put(
				"disabled", disabled
			).put(
				"namespace", namespace
			).put(
				"orderDetailURL", orderDetailURL
			).put(
				"requestQuoteElementId", requestQuoteElementId
			).build()
		%>'
		module="{requestQuote} from commerce-frontend-taglib"
	/>
</c:if>