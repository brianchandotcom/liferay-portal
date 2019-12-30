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
String from = ParamUtil.getString(request, "from");

if (!CurrencyConverterUtil.isCurrency(from)) {
	from = CurrencyConverter.DEFAULT_FROM;
}

String to = ParamUtil.getString(request, "to");

if (!CurrencyConverterUtil.isCurrency(to)) {
	to = CurrencyConverter.DEFAULT_TO;
}

CurrencyConverter currencyConverter = CurrencyConverterUtil.getCurrencyConverter(from + to);

double number = ParamUtil.getDouble(request, "number", 1.0);

String chartId = ParamUtil.getString(request, "chartId", "90");

NumberFormat decimalFormat = NumberFormat.getNumberInstance(locale);
%>

<portlet:renderURL var="convertURL" windowState="<%= WindowState.MAXIMIZED.toString() %>" />

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

		<liferay-ui:message key="to" />

		<aui:select inlineField="<%= true %>" label="" name="to">

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
				<c:when test="<%= symbols.length > 0 %>">
					<table class="table table-bordered table-hover table-striped">
						<thead>
							<tr>
								<th>
									<liferay-ui:message key="currency" />
								</th>

								<%
								decimalFormat.setMaximumFractionDigits(4);

								for (int i = 0; i < symbols.length; i++) {
									String symbol = symbols[i];
								%>

									<th>
										<liferay-ui:message key='<%= "currency." + HtmlUtil.escape(symbol) %>' /><br />
										(<%= HtmlUtil.escape(symbol) %>)
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
										<%= HtmlUtil.escape(symbol) %>
									</td>

									<%
									for (int j = 0; j < symbols.length; j++) {
										String symbol2 = symbols[j];

										currencyConverter = CurrencyConverterUtil.getCurrencyConverter(symbol2 + symbol);

										if (currencyConverter != null) {
									%>

											<td>
												<c:if test="<%= i != j %>">
													<%= decimalFormat.format(currencyConverter.getRate()) %>
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
						<a href="<portlet:renderURL portletMode="<%= PortletMode.EDIT.toString() %>" />"><liferay-ui:message key="please-select-a-currency" /></a>
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<table class="conversion-data table table-bordered">
				<tbody>
					<tr>
						<td class="col-md-4 currency-data">
							<span class="currency-header"><%= HtmlUtil.escape(currencyConverter.getFromSymbol()) %></span>
							<%= number %>
						</td>
						<td class="col-md-4 currency-data">

							<%
							decimalFormat.setMaximumFractionDigits(2);
							decimalFormat.setMinimumFractionDigits(2);
							%>

							<span class="currency-header"><%= HtmlUtil.escape(currencyConverter.getToSymbol()) %></span>
							<%= decimalFormat.format(number * currencyConverter.getRate()) %>
						</td>
						<td class="col-md-4 currency-data">
							<span class="currency-header"><liferay-ui:message key="historical-charts" /></span>

							<%
							PortletURL portletURL = renderResponse.createRenderURL();

							portletURL.setParameter("number", String.valueOf(number));
							portletURL.setParameter("from", currencyConverter.getFromSymbol());
							portletURL.setParameter("to", currencyConverter.getToSymbol());
							%>

							<c:choose>
								<c:when test='<%= chartId.equals("90") %>'>
									3<liferay-ui:message key="month-abbreviation" />,
								</c:when>
								<c:otherwise>

									<%
									portletURL.setParameter("chartId", "90");
									%>

									<aui:a href="<%= portletURL.toString() %>">3<liferay-ui:message key="month-abbreviation" /></aui:a>,
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='<%= chartId.equals("365") %>'>
									1<liferay-ui:message key="year-abbreviation" />,
								</c:when>
								<c:otherwise>

									<%
									portletURL.setParameter("chartId", "365");
									%>

									<aui:a href="<%= portletURL.toString() %>">1<liferay-ui:message key="year-abbreviation" /></aui:a>,
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='<%= chartId.equals("730") %>'>
									2<liferay-ui:message key="year-abbreviation" />
								</c:when>
								<c:otherwise>

									<%
									portletURL.setParameter("chartId", "730");
									%>

									<aui:a href="<%= portletURL.toString() %>">2<liferay-ui:message key="year-abbreviation" /></aui:a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="conversion-graph">
				<img class="currency-graph" height="420" src="http://www.indexmundi.com/xrates/image.aspx?c1=<%= HtmlUtil.escapeAttribute(HtmlUtil.escapeURL(from)) %>&c2=<%= HtmlUtil.escapeAttribute(HtmlUtil.escapeURL(to)) %>&days=<%= HtmlUtil.escape(chartId) %>" width="512" />
			</div>
		</c:otherwise>
	</c:choose>
</aui:form>