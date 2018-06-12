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

<%@ include file="/init.jsp" %>

<%
CommerceDashboardKPIDisplayContext commerceDashboardKPIDisplayContext = (CommerceDashboardKPIDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:row>
	<aui:col width="<%= 30 %>">
		<div class="kpi-value">
			<%= commerceDashboardKPIDisplayContext.getOrdersCount() %>
		</div>

		<div class="kpi-title">
			<liferay-ui:message key="number-of-orders" />
		</div>
	</aui:col>

	<aui:col width="<%= 30 %>">
		<div class="kpi-value">

			<%
			CommerceMoney averageOrderPrice = commerceDashboardKPIDisplayContext.getAverageOrderPrice();
			%>

			<%= HtmlUtil.escape(averageOrderPrice.format(locale)) %>
		</div>

		<div class="kpi-title">
			<liferay-ui:message key="average-order-size" />
		</div>
	</aui:col>

	<aui:col width="<%= 30 %>">
		<div class="kpi-value">

			<%
			CommerceMoney ordersTotal = commerceDashboardKPIDisplayContext.getOrdersTotal();
			%>

			<%= HtmlUtil.escape(ordersTotal.format(locale)) %>
		</div>

		<div class="kpi-title">
			<liferay-ui:message key="total-spent" />
		</div>
	</aui:col>
</aui:row>