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
AccountEntryDisplay accountEntryDisplay = AccountEntryDisplay.of(ParamUtil.getLong(request, "accountEntryId"));

String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
renderResponse.setTitle((accountEntryDisplay.getAccountEntryId() == 0) ? LanguageUtil.get(request, "add-account") : LanguageUtil.format(request, "edit-x", accountEntryDisplay.getName(), false));
%>

<liferay-frontend:edit-form>
	<liferay-frontend:edit-form-body>
		<h2 class="sheet-title">
			<%= LanguageUtil.get(request, "order-defaults") %>
		</h2>

		<liferay-frontend:fieldset-group>
			<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/order_defaults.jsp#terms" />

			<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/order_defaults.jsp#payment_methods" />

			<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/order_defaults.jsp#shipping_options" />
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>