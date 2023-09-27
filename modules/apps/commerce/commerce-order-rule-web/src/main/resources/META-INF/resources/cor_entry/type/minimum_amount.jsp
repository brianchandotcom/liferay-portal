<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

				<div class="col-auto">
					<aui:select label="apply-to" name='<%= "type--settings--" + COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_APPLY_TO + "--" %>' required="<%= true %>" title="apply-to">

						<%
						String applyTo = corEntryDisplayContext.getApplyTo();
						%>

						<aui:option label="total" selected="<%= Validator.isNull(applyTo) || applyTo.equals(COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_APPLY_TO_ORDER_TOTAL) %>" value="<%= COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_APPLY_TO_ORDER_TOTAL %>" />
						<aui:option label="subtotal" selected="<%= applyTo.equals(COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_APPLY_TO_ORDER_SUBTOTAL) %>" value="<%= COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_APPLY_TO_ORDER_SUBTOTAL %>" />
					</aui:select>
				</div>
			</div>
		</commerce-ui:panel>
	</div>
</div>