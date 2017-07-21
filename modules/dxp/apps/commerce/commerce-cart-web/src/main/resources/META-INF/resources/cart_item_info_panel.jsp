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
String languageId = LanguageUtil.getLanguageId(locale);

List<CommerceCartItem> commerceCartItems = (List<CommerceCartItem>)request.getAttribute(CommerceCartWebKeys.COMMERCE_CART_ITEMS);

if (ListUtil.isEmpty(commerceCartItems)) {
	commerceCartItems = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= commerceCartItems.size() == 1 %>">

		<%
		CommerceCartItem commerceCartItem = commerceCartItems.get(0);

		request.setAttribute("info_panel.jsp-entry", commerceCartItem);

		CPDefinition cpDefinition = commerceCartItem.getCPDefinition();
		%>

		<div class="sidebar-header">
			<h4><%= HtmlUtil.escape(cpDefinition.getTitle(languageId)) %></h4>
		</div>

		<aui:nav-bar markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(commerceCartItem.getCommerceCartItemId())) %>
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
			<h4><liferay-ui:message arguments="<%= commerceCartItems.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>