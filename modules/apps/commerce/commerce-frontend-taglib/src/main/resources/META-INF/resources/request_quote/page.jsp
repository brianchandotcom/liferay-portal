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

<%@ include file="/request_quote/init.jsp" %>

<c:if test="<%= requestQuoteEnabled || priceOnApplication %>">
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