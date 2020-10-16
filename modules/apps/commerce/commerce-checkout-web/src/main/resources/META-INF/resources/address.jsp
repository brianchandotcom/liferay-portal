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
CommerceAddress commerceAddress = (CommerceAddress)request.getAttribute("address.jsp-commerceAddress");
%>

<h4><%= HtmlUtil.escape(commerceAddress.getName()) %></h4>
<p><%= HtmlUtil.escape(commerceAddress.getStreet1()) %></p>

<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet2()) %>">
	<p><%= HtmlUtil.escape(commerceAddress.getStreet2()) %></p>
</c:if>

<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet3()) %>">
	<p><%= HtmlUtil.escape(commerceAddress.getStreet3()) %></p>
</c:if>

<p><%= HtmlUtil.escape(commerceAddress.getCity()) %></p>

<%
Country country = commerceAddress.getCountry();
%>

<c:if test="<%= country != null %>">
	<p><%= HtmlUtil.escape(country.getTitle(locale)) %></p>
</c:if>