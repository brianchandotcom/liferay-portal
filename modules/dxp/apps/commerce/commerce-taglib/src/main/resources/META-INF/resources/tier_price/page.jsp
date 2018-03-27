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
%>

<c:if test="<%= (commerceTierPriceEntries != null) && !commerceTierPriceEntries.isEmpty() %>">

	<%
	for (CommerceTierPriceEntry commerceTierPriceEntry : commerceTierPriceEntries) {
	%>

		<div class="form-group-item">
			<button class="btn commerce-custom-control-button" onclick="<%= randomNamespace %>setQuantity('<%= commerceTierPriceEntry.getMinQuantity() %>')" type="button"></button>
			<div class="input-group">
				<div class="input-group-item input-group-prepend input-group-item-shrink">
					<span class="input-group-text input-group-text-secondary">x<%= commerceTierPriceEntry.getMinQuantity() %></span>
				</div>

				<div class="input-group-item">
					<input class="form-control" readonly tabindex="-1" type="text" value="<%= formattedPrice %>">
				</div>
			</div>
		</div>

	<%
	}
	%>

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