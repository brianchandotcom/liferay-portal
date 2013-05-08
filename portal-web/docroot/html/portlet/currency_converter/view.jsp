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

<%@ include file="/html/portlet/currency_converter/init.jsp" %>

<%
String from = ParamUtil.getString(request, "from", com.liferay.portlet.currencyconverter.model.Currency.DEFAULT_FROM);
String to = ParamUtil.getString(request, "to", com.liferay.portlet.currencyconverter.model.Currency.DEFAULT_TO);

com.liferay.portlet.currencyconverter.model.Currency currency = CurrencyUtil.getCurrency(from + to);

double number = ParamUtil.getDouble(request, "number", 1.0);

String chartId = ParamUtil.getString(request, "chartId", "3m");

NumberFormat decimalFormat = NumberFormat.getNumberInstance(locale);

decimalFormat.setMaximumFractionDigits(2);
decimalFormat.setMinimumFractionDigits(2);
%>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/currency_converter/view" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">

<input type="submit" value="<liferay-ui:message key="convert" />" />

<input name="<portlet:namespace />number" size="3" type="text" value="<%= number %>" />

<select name="<portlet:namespace />from">

	<%
	for (Map.Entry<String, String> entry : allSymbols.entrySet()) {
		String symbol = entry.getValue();
		String currencyValue = entry.getKey();
	%>

		<option <%= symbol.equals(from) ? "selected" : "" %> value="<%= symbol %>"><%= currencyValue %></option value>

	<%
	}
	%>

</select>

<strong><liferay-ui:message key="to" /></strong>

<select name="<portlet:namespace />to">

	<%
	for (Map.Entry<String, String> entry : allSymbols.entrySet()) {
		String symbol = entry.getValue();
		String currencyValue = entry.getKey();
	%>

		<option <%= symbol.equals(to) ? "selected" : "" %> value="<%= symbol %>"><%= currencyValue %></option value>

	<%
	}
	%>

</select>

<br /><br />

<c:choose>
	<c:when test="<%= windowState.equals(WindowState.NORMAL) %>">
		<table class="table table-bordered table-hover table-striped">
		<thead class="table-columns">
		<tr>
			<th class="table-header">
				<strong><liferay-ui:message key="currency" /></strong>
			</th>

			<%
			for (int i = 0; i < symbols.length; i++) {
				String symbol = symbols[i];
			%>

				<th class="table-header">
					<liferay-ui:message key='<%= "currency." + symbol %>' /><br />
					(<%= symbol %>)
				</th>

			<%
			}
			%>

		</tr>
		</thead>

		<tbody class="table-data">

		<%
		for (int i = 0; i < symbols.length; i++) {
			String symbol = symbols[i];
		%>

			<tr>
				<td class="table-cell">
					<%= symbol %>
				</td>

		<%
				for (int j = 0; j < symbols.length; j++) {
					String symbol2 = symbols[j];

					currency = CurrencyUtil.getCurrency(symbol2 + symbol);

					if (currency != null) {
		%>

						<c:if test="<%= i != j %>">
							<td class="table-cell"><%= currency.getRate() %></td>
						</c:if>

						<c:if test="<%= i == j %>">
							<td class="table-cell">1</td>
						</c:if>

		<%
					}
				}
		%>

			</tr>

		<%
		}
		%>

		</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<table border="1" cellpadding="0" cellspacing="0" width="520">
		<tr>
			<td align="center" width="33%">
				<%= currency.getFromSymbol() %><br />
				<strong><%= number %></strong>
			</td>
			<td align="center" width="33%">
				<%= currency.getToSymbol() %><br />
				<strong><%= decimalFormat.format(number * currency.getRate()) %></strong>
			</td>
			<td align="center" width="34%">
				<liferay-ui:message key="historical-charts" /><br />

				<%
				PortletURL portletURL = renderResponse.createRenderURL();

				portletURL.setParameter("struts_action", "/currency_converter/view");
				portletURL.setParameter("number", String.valueOf(number));
				portletURL.setParameter("from", currency.getFromSymbol());
				portletURL.setParameter("to", currency.getToSymbol());
				%>

				<c:if test='<%= chartId.equals("3m") %>'>
					3<liferay-ui:message key="month-abbreviation" />, <a href="<% portletURL.setParameter("chartId", "1y"); %><%= portletURL.toString() %>">1<liferay-ui:message key="year-abbreviation" /></a>, <a href="<% portletURL.setParameter("chartId", "2y"); %><%= portletURL.toString() %>">2<liferay-ui:message key="year-abbreviation" /></a>
				</c:if>

				<c:if test='<%= chartId.equals("1y") %>'>
					<a href="<% portletURL.setParameter("chartId", "3m"); %><%= portletURL.toString() %>">3<liferay-ui:message key="month-abbreviation" /></a>, 1<liferay-ui:message key="year-abbreviation" />, <a href="<% portletURL.setParameter("chartId", "2y"); %><%= portletURL.toString() %>">2<liferay-ui:message key="year-abbreviation" /></a>
				</c:if>

				<c:if test='<%= chartId.equals("2y") %>'>
					<a href="<% portletURL.setParameter("chartId", "3m"); %><%= portletURL.toString() %>">3<liferay-ui:message key="month-abbreviation" /></a>, <a href="<% portletURL.setParameter("chartId", "1y"); %><%= portletURL.toString() %>">1<liferay-ui:message key="year-abbreviation" /></a>, 2<liferay-ui:message key="year-abbreviation" />
				</c:if>
			</td>
		</tr>
		</table>

		<br />

		<table border="1" cellpadding="2" cellspacing="0">
		<tr>
			<td>
				<img height="288" src="http://ichart.yahoo.com/z?s=<%= currency.getSymbol() %>=X&t=<%= HtmlUtil.escape(chartId) %>?" width="512" />
			</td>
		</tr>
		</table>
	</c:otherwise>
</c:choose>

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />number);
	</aui:script>
</c:if>