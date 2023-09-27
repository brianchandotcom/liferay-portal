<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="commerce-checkout-confirmation">
	<div class="success-message">
		<liferay-ui:message key="your-order-has-already-been-placed" />
	</div>

	<aui:button-row>
		<aui:button href="<%= (String)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_ORDER_DETAIL_URL) %>" primary="<%= true %>" type="submit" value="go-to-order-details" />
	</aui:button-row>
</div>