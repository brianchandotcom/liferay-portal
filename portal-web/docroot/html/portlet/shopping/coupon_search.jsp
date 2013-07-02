<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
CouponSearch searchContainer = (CouponSearch)request.getAttribute("liferay-ui:search:searchContainer");

CouponDisplayTerms displayTerms = (CouponDisplayTerms)searchContainer.getDisplayTerms();
%>

<aui:fieldset column="<%= true %>">
	<aui:col width="<%= 33 %>">
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="<%= displayTerms.CODE %>" size="20" type="text" value="<%= displayTerms.getCode() %>" />

		<aui:select label="" name="<%= displayTerms.AND_OPERATOR %>">
			<aui:option label="and" selected="<%= displayTerms.isAndOperator() %>" value="1" />
			<aui:option label="or" selected="<%= !displayTerms.isAndOperator() %>" value="0" />
		</aui:select>
	</aui:col>

	<aui:col width="<%= 33 %>">
		<aui:select name="<%= displayTerms.DISCOUNT_TYPE %>" showEmptyOption="<%= true %>">

			<%
			for (int i = 0; i < ShoppingCouponConstants.DISCOUNT_TYPES.length; i++) {
			%>

				<aui:option label="<%= ShoppingCouponConstants.DISCOUNT_TYPES[i] %>" selected="<%= displayTerms.getDiscountType().equals(ShoppingCouponConstants.DISCOUNT_TYPES[i]) %>" />

			<%
			}
			%>

		</aui:select>
	</aui:col>

	<aui:col width="<%= 33 %>">
		<aui:select name="<%= displayTerms.ACTIVE %>">
			<aui:option label="yes" selected="<%= displayTerms.isActive() %>" value="1" />
			<aui:option label="no" selected="<%= !displayTerms.isActive() %>" value="0" />
		</aui:select>
	</aui:col>
</aui:fieldset>

<aui:button-row>
	<aui:button type="submit" value="search" />

	<aui:button onClick='<%= renderResponse.getNamespace() + "addCoupon();" %>' value="add-coupon" />
</aui:button-row>

<aui:script>
	function <portlet:namespace />addCoupon() {
		document.<portlet:namespace />fm.method = 'post';

		submitForm(document.<portlet:namespace />fm, '<portlet:renderURL><portlet:param name="struts_action" value="/shopping/edit_coupon" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>');
	}
</aui:script>