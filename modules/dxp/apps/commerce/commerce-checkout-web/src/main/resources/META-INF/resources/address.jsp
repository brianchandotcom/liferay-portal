<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAddress commerceAddress = (CommerceAddress)request.getAttribute("address.jsp-commerceAddress");

commerceAddress = commerceAddress.toEscapedModel();
%>

<h4><%= commerceAddress.getName() %></h4>
<p><%= commerceAddress.getStreet1() %></p>

<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet2()) %>">
	<p><%= commerceAddress.getStreet2() %></p>
</c:if>

<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet3()) %>">
	<p><%= commerceAddress.getStreet3() %></p>
</c:if>

<p><%= commerceAddress.getCity() %></p>

<%
CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();
%>

<c:if test="<%= commerceCountry != null %>">
	<p><%= HtmlUtil.escape(commerceCountry.getName(locale)) %></p>
</c:if>