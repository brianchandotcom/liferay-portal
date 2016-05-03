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
OrderSearch searchContainer = (OrderSearch)request.getAttribute("liferay-ui:search:searchContainer");

OrderDisplayTerms displayTerms = (OrderDisplayTerms)searchContainer.getDisplayTerms();
%>

<aui:fieldset column="<%= true %>">
	<aui:col width="<%= 33 %>">
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="<%= OrderDisplayTerms.NUMBER %>" size="20" type="text" value="<%= displayTerms.getNumber() %>" />

		<aui:select label="" name="<%= OrderDisplayTerms.AND_OPERATOR %>" title="and-or-operator">
			<aui:option label="and" selected="<%= displayTerms.isAndOperator() %>" value="1" />
			<aui:option label="or" selected="<%= !displayTerms.isAndOperator() %>" value="0" />
		</aui:select>
	</aui:col>

	<aui:col width="<%= 33 %>">
		<%@ include file="/order_search_user_name.jspf" %>
	</aui:col>

	<aui:col width="<%= 33 %>">
		<aui:select name="<%= OrderDisplayTerms.STATUS %>" showEmptyOption="<%= true %>">

			<%
			for (int i = 0; i < ShoppingOrderConstants.STATUSES.length; i++) {
			%>

				<aui:option label="<%= ShoppingOrderConstants.STATUSES[i] %>" selected="<%= displayTerms.getStatus().equals(ShoppingOrderConstants.STATUSES[i]) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input name="<%= OrderDisplayTerms.EMAIL_ADDRESS %>" size="20" type="text" value="<%= displayTerms.getEmailAddress() %>" />
	</aui:col>
</aui:fieldset>

<aui:button-row>
	<aui:button cssClass="btn-lg" type="submit" value="search" />
</aui:button-row>