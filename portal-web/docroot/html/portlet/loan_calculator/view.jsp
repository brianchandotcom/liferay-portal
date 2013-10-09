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

<%@ include file="/html/portlet/loan_calculator/init.jsp" %>

<%
int loanAmount = ParamUtil.get(request, "loanAmount", 200000);
double interest = ParamUtil.get(request, "interest", 7.00);
int years = ParamUtil.get(request, "years", 30);
int paymentsPerYear = ParamUtil.get(request, "paymentsPerYear", 12);

double tempValue = Math.pow((1 + (interest / 100 / paymentsPerYear)), (years * paymentsPerYear));
double amountPerPayment = (loanAmount * tempValue * (interest / 100 / paymentsPerYear)) / (tempValue - 1);
double totalPaid = amountPerPayment * years * paymentsPerYear;
double interestPaid = totalPaid - loanAmount;

NumberFormat doubleFormat = NumberFormat.getNumberInstance(locale);

doubleFormat.setMaximumFractionDigits(2);
doubleFormat.setMinimumFractionDigits(2);

NumberFormat integerFormat = NumberFormat.getNumberInstance(locale);

integerFormat.setMaximumFractionDigits(0);
integerFormat.setMinimumFractionDigits(0);
%>

<portlet:actionURL var="portletURL" />

<aui:form action="<%= portletURL %>" method="post" name="fm">
	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="loan-amount" />
		</td>
		<td>
			<input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="<portlet:namespace />loanAmount" size="5" type="text" value="<%= integerFormat.format(loanAmount) %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="interest-rate" />
		</td>
		<td>
			<input name="<portlet:namespace />interest" size="5" type="text" value="<%= doubleFormat.format(interest) %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="years" />
		</td>
		<td>
			<input name="<portlet:namespace />years" size="5" type="text" value="<%= years %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="monthly-payment" />
		</td>
		<td>
			<strong><%= integerFormat.format(amountPerPayment) %></strong>
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="interest-paid" />
		</td>
		<td>
			<strong><%= integerFormat.format(interestPaid) %></strong>
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="total-paid" />
		</td>
		<td>
			<strong><%= integerFormat.format(totalPaid) %></strong>
		</td>
	</tr>
	</table>

	<br />

	<input type="submit" value="<liferay-ui:message key="calculate" />" />
</aui:form>