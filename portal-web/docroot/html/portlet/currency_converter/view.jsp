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

<portlet:renderURL var="convertURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/currency_converter/view" />
</portlet:renderURL>

<aui:form action="<%= convertURL %>" cssClass="form-inline" method="post" name="fm">
	<aui:field-wrapper>
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" inlineField="<%= true %>" label="" name="number" size="3" type="text" value="<%= number %>" />

		<aui:select inlineField="<%= true %>" label="" name="from">

			<%
			for (Map.Entry<String, String> entry : allSymbols.entrySet()) {
				String symbol = entry.getValue();
				String currencyValue = entry.getKey();
			%>

				<aui:option label="<%= currencyValue %>" selected="<%= symbol.equals(from) %>" value="<%= symbol %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select inlineField="<%= true %>" label="to" name="to">

			<%
			for (Map.Entry<String, String> entry : allSymbols.entrySet()) {
				String symbol = entry.getValue();
				String currencyValue = entry.getKey();
			%>

				<aui:option label="<%= currencyValue %>" selected="<%= symbol.equals(to) %>" value="<%= symbol %>" />

			<%
			}
			%>

		</aui:select>

		<aui:button type="submit" value="convert" />
	</aui:field-wrapper>

	<c:choose>
		<c:when test="<%= windowState.equals(WindowState.NORMAL) %>">
			<c:choose>
				<c:when test="<%= (symbols.length > 0) %>">
					<table class="table table-bordered table-hover table-striped">
					<thead>
					<tr>
						<th>
							<liferay-ui:message key="currency" />
						</th>

						<%
						for (int i = 0; i < symbols.length; i++) {
							String symbol = symbols[i];
						%>

							<th>
								<liferay-ui:message key='<%= "currency." + symbol %>' /><br />
								(<%= symbol %>)
							</th>

						<%
						}
						%>

					</tr>
					</thead>

					<tbody>

						<%
						for (int i = 0; i < symbols.length; i++) {
							String symbol = symbols[i];
						%>

							<tr>
								<td>
									<%= symbol %>
								</td>

								<%
								for (int j = 0; j < symbols.length; j++) {
									String symbol2 = symbols[j];

									currency = CurrencyUtil.getCurrency(symbol2 + symbol);

									if (currency != null) {
								%>

								<td>
									<c:if test="<%= i != j %>">
										<%= currency.getRate() %>
									</c:if>

									<c:if test="<%= i == j %>">
										1
									</c:if>
								</td>

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
					<div class="alert alert-info">
						<liferay-ui:message key="please-select-a-currency" />
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<table class="conversion-data table table-bordered">
			<tbody>
				<tr>
					<td class="currency-data span4">
						<span class="currency-header"><%= currency.getFromSymbol() %></span>
						<%= number %>
					</td>
					<td class="currency-data span4">
						<span class="currency-header"><%= currency.getToSymbol() %></span>
						<%= decimalFormat.format(number * currency.getRate()) %>
					</td>
					<td class="currency-data span4">
						<span class="currency-header"><liferay-ui:message key="historical-charts" /></span>

						<%
						PortletURL portletURL = renderResponse.createRenderURL();

						portletURL.setParameter("struts_action", "/currency_converter/view");
						portletURL.setParameter("number", String.valueOf(number));
						portletURL.setParameter("from", currency.getFromSymbol());
						portletURL.setParameter("to", currency.getToSymbol());
						%>

						<c:choose>
							<c:when test='<%= chartId.equals("3m") %>'>
								3<liferay-ui:message key="month-abbreviation" />,
							</c:when>
							<c:otherwise>

								<%
								portletURL.setParameter("chartId", "3m");
								%>

								<aui:a href="<%= portletURL.toString() %>">3<liferay-ui:message key="month-abbreviation" /></aui:a>,
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test='<%= chartId.equals("1y") %>'>
								1<liferay-ui:message key="year-abbreviation" />,
							</c:when>
							<c:otherwise>

								<%
								portletURL.setParameter("chartId", "1y");
								%>

								<aui:a href="<%= portletURL.toString() %>">1<liferay-ui:message key="year-abbreviation" /></aui:a>,
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test='<%= chartId.equals("2y") %>'>
								2<liferay-ui:message key="year-abbreviation" />
							</c:when>
							<c:otherwise>

								<%
								portletURL.setParameter("chartId", "2y");
								%>

								<aui:a href="<%= portletURL.toString() %>">2<liferay-ui:message key="year-abbreviation" /></aui:a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</tbody>
			</table>

			<div class="conversion-graph">
				<img alt="" class="currency-graph" height="288" src="http://ichart.yahoo.com/z?s=<%= currency.getSymbol() %>=X&t=<%= HtmlUtil.escape(chartId) %>?" width="512" />
			</div>
		</c:otherwise>
	</c:choose>
</aui:form>
