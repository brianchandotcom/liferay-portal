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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

CommerceAccountDisplayContext commerceAccountDisplayContext = (CommerceAccountDisplayContext)request.getAttribute(CommerceAccountWebKeys.COMMERCE_ACCOUNT_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
renderResponse.setTitle((commerceAccountDisplayContext.getAccountEntryId() == 0) ? LanguageUtil.get(request, "add-account") : LanguageUtil.format(request, "edit-x", commerceAccountDisplayContext.getName(), false));
%>

<liferay-frontend:edit-form>
	<liferay-frontend:edit-form-body>
		<h2 class="sheet-title">
			<%= LanguageUtil.get(request, "order-defaults") %>
		</h2>

		<liferay-frontend:fieldset-group>
			<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/order_defaults.jsp#terms" />

			<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/order_defaults.jsp#payment_methods" />

			<%@ include file="/account_entry/commerce_shipping_options/default_commerce_shipping_options.jspf" %>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>