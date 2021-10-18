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
COREntryDisplayContext corEntryDisplayContext = (COREntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="row">
	<div class="col">
		<commerce-ui:panel
			bodyClasses="flex-fill"
			title='<%= LanguageUtil.get(request, "configuration") %>'
		>
			<div class="row">
				<div class="col">
					<aui:input label="minimum-order-amount" name='<%= "type--settings--" + COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_AMOUNT + "--" %>' required="<%= true %>" type="text" value="<%= corEntryDisplayContext.getMinimumAmount() %>">
						<aui:validator name="number" />
					</aui:input>
				</div>

				<div class="col-auto">
					<aui:select label="currency" name='<%= "type--settings--" + COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_CURRENCY_CODE + "--" %>' required="<%= true %>" title="currency">

						<%
						for (CommerceCurrency commerceCurrency : corEntryDisplayContext.getCommerceCurrencies()) {
							String commerceCurrencyCode = commerceCurrency.getCode();
						%>

							<aui:option label="<%= commerceCurrency.getName(locale) %>" selected="<%= commerceCurrencyCode.equals(corEntryDisplayContext.getCommerceCurrencyCode()) %>" value="<%= commerceCurrencyCode %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</div>
		</commerce-ui:panel>
	</div>
</div>