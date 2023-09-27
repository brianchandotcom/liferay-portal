<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/tier_price/init.jsp" %>

<%
CommercePriceFormatter commercePriceFormatter = (CommercePriceFormatter)request.getAttribute("liferay-commerce:tier-price:commercePriceFormatter");
List<CommerceTierPriceEntry> commerceTierPriceEntries = (List<CommerceTierPriceEntry>)request.getAttribute("liferay-commerce:tier-price:commerceTierPriceEntries");
String taglibQuantityInputId = (String)request.getAttribute("liferay-commerce:tier-price:taglibQuantityInputId");

CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;
%>

<c:if test="<%= (commerceTierPriceEntries != null) && !commerceTierPriceEntries.isEmpty() %>">
	<div class="commerce-tier-price" id="<%= randomNamespace %>tierPrice">
		<div class="table-responsive">
			<table class="table table-hover table-nowrap">
				<thead>
					<th class="price-point-column"><liferay-ui:message key="quantity" /></th>
					<th class="price-column table-cell-expand"><liferay-ui:message key="price" /></th>
					<th class="discount-column table-cell-expand"><liferay-ui:message key="discount" /></th>
					<th class="savings-column table-cell-expand"><liferay-ui:message key="savings" /></th>
					<th class="table-cell-expand total-column"><liferay-ui:message key="total" /></th>
				</thead>

				<tbody>

					<%
					for (CommerceTierPriceEntry commerceTierPriceEntry : commerceTierPriceEntries) {
						CommercePriceEntry commercePriceEntry = commerceTierPriceEntry.getCommercePriceEntry();

						BigDecimal price = commercePriceEntry.getPrice();

						BigDecimal minQuantity = BigDecimal.ZERO;

						if ((commerceTierPriceEntry != null) && (commerceTierPriceEntry.getMinQuantity() != null)) {
							minQuantity = commerceTierPriceEntry.getMinQuantity(
							).setScale(
								0, BigDecimal.ROUND_DOWN
							);
						}

						BigDecimal priceTotal = price.multiply(minQuantity);

						BigDecimal commerceTierPriceEntryPrice = commerceTierPriceEntry.getPrice();

						BigDecimal discount = price.subtract(commerceTierPriceEntryPrice);

						BigDecimal discountPercent = discount.divide(price, RoundingMode.HALF_EVEN);

						discountPercent = discountPercent.multiply(BigDecimal.valueOf(100));

						BigDecimal total = commerceTierPriceEntryPrice.multiply(minQuantity);

						BigDecimal savings = priceTotal.subtract(total);
					%>

						<tr class="multiples-row" onclick="<%= randomNamespace %>setQuantity('<%= minQuantity %>');">
							<td class="price-point-column"><%= minQuantity %></td>
							<td class="msrp-column table-cell-expand"><%= commercePriceFormatter.format(commerceContext.getCommerceCurrency(), priceTotal, themeDisplay.getLocale()) %></td>
							<td class="discount-column table-cell-expand"><%= commercePriceFormatter.format(discountPercent, themeDisplay.getLocale()) %> %</td>
							<td class="savings-column table-cell-expand"><%= commercePriceFormatter.format(commerceContext.getCommerceCurrency(), savings, themeDisplay.getLocale()) %></td>
							<td class="table-cell-expand total-column"><%= commercePriceFormatter.format(commerceContext.getCommerceCurrency(), total, themeDisplay.getLocale()) %></td>
						</tr>

					<%
					}
					%>

				</tbody>
			</table>
		</div>
	</div>

	<aui:script use="aui-base">
		Liferay.provide(window, '<%= randomNamespace %>setQuantity', (qt) => {
			var quantityNode = document.querySelector(
				'#<%= HtmlUtil.escapeJS(taglibQuantityInputId) %>'
			);

			if (quantityNode) {
				quantityNode.value = qt;
			}
		});
	</aui:script>
</c:if>