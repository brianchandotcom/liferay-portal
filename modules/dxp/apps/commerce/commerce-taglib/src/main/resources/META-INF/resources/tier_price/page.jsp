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
List<CommerceTierPriceEntry> commerceTierPriceEntries = (List<CommerceTierPriceEntry>)request.getAttribute("liferay-commerce:tier-price:commerceTierPriceEntries");
long commerceCurrencyId = (long)request.getAttribute("liferay-commerce:tier-price:commerceCurrencyId");
long cpInstanceId = (long)request.getAttribute("liferay-commerce:tier-price:cpInstanceId");
String taglibQuantityInputId = (String)request.getAttribute("liferay-commerce:tier-price:taglibQuantityInputId");

String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

String tableRowCssClass = StringPool.BLANK;
String tierPriceId = randomNamespace + "tierPrice";
String tierPriceTogglerId = randomNamespace + "commerceTierPriceToggler";
boolean truncateTable = false;
%>

<c:if test="<%= (commerceTierPriceEntries != null) && !commerceTierPriceEntries.isEmpty() %>">
	<div class="commerce-tier-price" id="<%= tierPriceId %>">
		<div class="table-responsive">
			<table class="table table-autofit table-hover table-nowrap">
				<thead>
					<th class="price-point-column"><%= LanguageUtil.get(request, "pp") %></th>
					<th class="msrp-column table-cell-expand"><%= LanguageUtil.get(request, "msrp") %> (USD)</th>
					<th class="discount-column table-cell-expand"><%= LanguageUtil.get(request, "discount") %> (%)</th>
					<th class="savings-column table-cell-expand"><%= LanguageUtil.get(request, "savings") %> (USD)</th>
					<th class="total-column table-cell-expand"><%= LanguageUtil.get(request, "total") %> (USD)</th>
				</thead>
				<tbody>
					<%
					int index = 1;

					for (CommerceTierPriceEntry commerceTierPriceEntry : commerceTierPriceEntries) {
						CommerceMoney commerceMoney = commerceTierPriceEntry.getPriceMoney(commerceCurrencyId);

						float msrp = commerceTierPriceEntry.getCommercePriceEntry().getPrice().floatValue();
						float msrpTotal = msrp * commerceTierPriceEntry.getMinQuantity();

						float discount = msrp - commerceTierPriceEntry.getPrice().floatValue();
						float discountPercent = 100 * (discount / msrp);

						float total = commerceTierPriceEntry.getPrice().floatValue() * commerceTierPriceEntry.getMinQuantity();

						float savings = msrpTotal - total;

						if (index == 5) {
							tableRowCssClass = " table-truncate-threshold";
							truncateTable = true;
						}
						else {
							tableRowCssClass = StringPool.BLANK;
						}
					%>

						<tr class="multiples-row<%= tableRowCssClass %>" onclick="<%= randomNamespace %>setQuantity('<%= commerceTierPriceEntry.getMinQuantity() %>')">
							<td class="price-point-column"><%= commerceTierPriceEntry.getMinQuantity() %></td>
							<td class="msrp-column table-cell-expand"><%= String.format("%.2f", msrpTotal) %></td>
							<td class="discount-column table-cell-expand"><%= String.format("%.2f", discountPercent) %>%</td>
							<td class="savings-column table-cell-expand"><%= String.format("%.2f", savings) %></td>
							<td class="total-column table-cell-expand"><%= String.format("%.2f", total) %></td>
						</tr>

					<%
						index++;
					}
					%>
				</tbody>
			</table>
		</div>

		<%
		if (truncateTable) {
		%>
			<div class="commerce-tier-price-footer">
				<button class="btn btn-link btn-sm commerce-tier-price-toggler" id="<%= tierPriceTogglerId %>">
					<span class="view-more">
						+ <%= LanguageUtil.get(request, "view-more") %>
					</span>
					<span class="view-less">
						- <%= LanguageUtil.get(request, "view-less") %>
					</span>
				</button>
			</div>
		<%
		}
		%>
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

		document.getElementById('<%= tierPriceTogglerId %>').addEventListener(
			'click',
			function (event) {
				var currentTarget = event.currentTarget;

				document.getElementById('<%= tierPriceId %>').classList.toggle('expand');
			}
		);
	</script>
</c:if>