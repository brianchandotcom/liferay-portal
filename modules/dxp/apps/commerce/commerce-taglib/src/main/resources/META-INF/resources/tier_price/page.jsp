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

<%@ include file="/tier_price/init.jsp" %>

<%
CommercePriceFormatter commercePriceFormatter = (CommercePriceFormatter)request.getAttribute("liferay-commerce:tier-price:commercePriceFormatter");
List<CommerceTierPriceEntry> commerceTierPriceEntries = (List<CommerceTierPriceEntry>)request.getAttribute("liferay-commerce:tier-price:commerceTierPriceEntries");
String taglibQuantityInputId = (String)request.getAttribute("liferay-commerce:tier-price:taglibQuantityInputId");

CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

String tierPriceId = randomNamespace + "tierPrice";
%>

<c:if test="<%= (commerceTierPriceEntries != null) && !commerceTierPriceEntries.isEmpty() %>">
	<div class="commerce-tier-price" id="<%= tierPriceId %>">
		<div class="table-responsive">
			<table class="table table-autofit table-hover table-nowrap">
				<thead>
					<th class="price-point-column"><%= LanguageUtil.get(request, "quantity") %></th>
					<th class="price-column table-cell-expand"><%= LanguageUtil.get(request, "price") %></th>
					<th class="discount-column table-cell-expand"><%= LanguageUtil.get(request, "discount") %></th>
					<th class="savings-column table-cell-expand"><%= LanguageUtil.get(request, "savings") %></th>
					<th class="table-cell-expand total-column"><%= LanguageUtil.get(request, "total") %></th>
				</thead>

				<tbody>

					<%
					for (CommerceTierPriceEntry commerceTierPriceEntry : commerceTierPriceEntries) {
						BigDecimal price = commerceTierPriceEntry.getCommercePriceEntry().getPrice();

						BigDecimal priceTotal = price.multiply(BigDecimal.valueOf(commerceTierPriceEntry.getMinQuantity()));

						BigDecimal discount = price.subtract(commerceTierPriceEntry.getPrice());

						BigDecimal discountPercent = (discount.divide(price)).multiply(BigDecimal.valueOf(100));

						BigDecimal total = commerceTierPriceEntry.getPrice().multiply(BigDecimal.valueOf(commerceTierPriceEntry.getMinQuantity()));

						BigDecimal savings = priceTotal.subtract(total);
					%>

						<tr class="multiples-row" onclick="<%= randomNamespace %>setQuantity('<%= commerceTierPriceEntry.getMinQuantity() %>')">
							<td class="price-point-column"><%= commerceTierPriceEntry.getMinQuantity() %></td>
							<td class="msrp-column table-cell-expand"><%= commercePriceFormatter.format(commerceContext.getCommerceCurrency(), priceTotal) %></td>
							<td class="discount-column table-cell-expand"><%= commercePriceFormatter.format(discountPercent) %> %</td>
							<td class="savings-column table-cell-expand"><%= commercePriceFormatter.format(commerceContext.getCommerceCurrency(), savings) %></td>
							<td class="table-cell-expand total-column"><%= commercePriceFormatter.format(commerceContext.getCommerceCurrency(), total) %></td>
						</tr>

					<%
					}
					%>

				</tbody>
			</table>
		</div>
	</div>

	<script use="aui-base">
		Liferay.provide(
			window,
			'<%= randomNamespace %>setQuantity',
			function(qt) {
				var quantityNode = document.querySelector('#<%= taglibQuantityInputId %>');

				if (quantityNode) {
					quantityNode.value = qt;
				}
			}
		);
	</script>
</c:if>