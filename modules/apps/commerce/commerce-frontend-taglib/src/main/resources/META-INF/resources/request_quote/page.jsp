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

	<aui:script require="commerce-frontend-js/components/request_quote/entry as RequestQuote">
		const props = {
			accountId: <%= commerceAccountId %>,
			channel: {
				currencyCode: '<%= HtmlUtil.escapeJS(commerceCurrencyCode) %>',
				id: <%= commerceChannelId %>,
				requestQuoteEnabled: <%= requestQuoteEnabled %>,
			},
			cpDefinitionId: <%= cpDefinitionId %>,
			cpInstance: {
				skuId: <%= cpInstanceId %>,
				skuOptions: <%= skuOptions %> || [],
				priceOnApplication: <%= priceOnApplication %>,
			},
			disabled: <%= disabled %>,
			namespace: '<%= namespace %>',
			orderDetailURL: '<%= orderDetailURL %>',
		};

		RequestQuote.default(
			'<%= requestQuoteElementId %>',
			'<%= requestQuoteElementId %>',
			props
		);
	</aui:script>
</c:if>