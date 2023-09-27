<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/mini_cart/init.jsp" %>

<c:choose>
	<c:when test="<%= commerceChannelId == 0 %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="cart-root" id="<%= miniCartId %>"></div>

		<aui:script require="commerce-frontend-js/components/mini_cart/entry as Cart">
			var initialProps = {
				accountId: <%= accountEntryId %>,
				cartActionURLs: {
					checkoutURL: '<%= HtmlUtil.escapeJS(checkoutURL) %>',
					orderDetailURL: '<%= HtmlUtil.escapeJS(orderDetailURL) %>',
					productURLSeparator: '<%= HtmlUtil.escapeJS(productURLSeparator) %>',
					siteDefaultURL: '<%= HtmlUtil.escapeJS(siteDefaultURL) %>',
				},
				channel: {
					currencyCode: '<%= commerceCurrencyCode %>',
					groupId: <%= commerceChannelGroupId %>,
					id: <%= commerceChannelId %>,
				},
				displayDiscountLevels: <%= displayDiscountLevels %>,
				displayTotalItemsQuantity: <%= displayTotalItemsQuantity %>,
				itemsQuantity: <%= itemsQuantity %>,
				orderId: <%= orderId %>,
				requestQuoteEnabled: <%= requestCodeEnabled %>,
				toggleable: <%= toggleable %>,
			};

			<%
			if (!cartViews.isEmpty()) {
			%>

				initialProps.cartViews = {};

				<%
				for (Map.Entry<String, String> cartView : cartViews.entrySet()) {
				%>

					initialProps.cartViews['<%= cartView.getKey() %>'] = {
						contentRendererModuleUrl: '<%= cartView.getValue() %>',
					};

				<%
					}
				}

				if (!labels.isEmpty()) {
				%>

				initialProps.labels = {};

				<%
				for (Map.Entry<String, String> label : labels.entrySet()) {
				%>

					initialProps.labels['<%= label.getKey() %>'] = '<%= label.getValue() %>';

			<%
				}
			}
			%>

			Cart.default('<%= miniCartId %>', '<%= miniCartId %>', initialProps);
		</aui:script>
	</c:otherwise>
</c:choose>