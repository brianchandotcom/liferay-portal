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
List<CommerceOrder> commerceOrders = (List<CommerceOrder>)request.getAttribute(CommerceWebKeys.COMMERCE_ORDERS);

if (commerceOrders == null) {
	commerceOrders = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= commerceOrders.size() == 1 %>">

		<%
		CommerceOrder commerceOrder = commerceOrders.get(0);

		request.setAttribute("info_panel.jsp-entry", commerceOrder);
		%>

		<div class="sidebar-header">
			<h4><%= HtmlUtil.escape(String.valueOf(commerceOrder.getCommerceOrderId())) %></h4>
		</div>

		<aui:nav-bar markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="order-number" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(commerceOrder.getCommerceOrderId())) %>
			</p>

			<%
			Date createDate = commerceOrder.getCreateDate();
			Date modifiedDate = commerceOrder.getModifiedDate();

			String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
			String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
			%>

			<h5><liferay-ui:message key="create-date" /></h5>

			<p>
				<liferay-ui:message arguments="<%= createDateDescription %>" key="x-ago" />
			</p>

			<h5><liferay-ui:message key="modified-date" /></h5>

			<p>
				<liferay-ui:message arguments="<%= modifiedDateDescription %>" key="x-ago" />
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= commerceOrders.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>