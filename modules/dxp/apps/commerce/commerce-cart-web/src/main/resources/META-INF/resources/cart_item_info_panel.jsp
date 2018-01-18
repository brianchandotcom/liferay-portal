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

List<CommerceCartItem> commerceCartItems = (List<CommerceCartItem>)request.getAttribute(CommerceWebKeys.COMMERCE_CART_ITEMS);

if (commerceCartItems == null) {
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

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(commerceCartItem.getCommerceCartItemId())) %>
			</p>

			<%
			Date createDate = commerceCartItem.getCreateDate();
			Date modifiedDate = commerceCartItem.getModifiedDate();

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
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= commerceCartItems.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>