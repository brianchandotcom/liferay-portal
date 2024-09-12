<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePaymentEntryDisplayContext commercePaymentEntryDisplayContext = (CommercePaymentEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<frontend-data-set:headless-display
	apiURL="/o/headless-commerce-admin-payment/v1.0/payments"
	fdsActionDropdownItems="<%= commercePaymentEntryDisplayContext.getFDSActionDropdownItems() %>"
	formName="fm"
	id="<%= CommercePaymentsFDSNames.PAYMENTS %>"
	propsTransformer="{commercePaymentPropsTransformer} from commerce-payment-web"
	style="fluid"
/>