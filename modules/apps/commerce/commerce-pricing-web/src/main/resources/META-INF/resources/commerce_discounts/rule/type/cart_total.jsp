<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CartTotalCommerceDiscountRuleDisplayContext cartTotalCommerceDiscountRuleDisplayContext = (CartTotalCommerceDiscountRuleDisplayContext)request.getAttribute("view.jsp-cartTotalCommerceDiscountRuleDisplayContext");
%>

<div class="col-12">
	<liferay-ui:error exception="<%= CommerceDiscountRuleTypeSettingsException.class %>" message="cart-total-minimum-amount-cannot-be-empty-and-must-be-a-decimal-number" />

	<commerce-ui:panel
		bodyClasses="flex-fill"
		title='<%= LanguageUtil.get(request, "configuration") %>'
	>
		<aui:input label="cart-total-minimum-amount" name="typeSettings" required="<%= true %>" suffix="<%= HtmlUtil.escape(cartTotalCommerceDiscountRuleDisplayContext.getDefaultCommerceCurrencyCode()) %>" type="text" value="<%= cartTotalCommerceDiscountRuleDisplayContext.getTypeSettings() %>">
			<aui:validator name="number" />
		</aui:input>
	</commerce-ui:panel>
</div>