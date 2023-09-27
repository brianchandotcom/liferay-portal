<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<liferay-ui:message key="channel-defaults" />
		</h2>

		<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/channel_defaults.jsp#commerce_addresses" />

		<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/channel_defaults.jsp#terms" />

		<%@ include file="/account_entry/commerce_shipping_options/default_commerce_shipping_options.jspf" %>

		<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/channel_defaults.jsp#price_lists" />

		<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/channel_defaults.jsp#discounts" />

		<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/channel_defaults.jsp#currencies" />

		<liferay-util:dynamic-include key="com.liferay.commerce.account.web#/account_entry/channel_defaults.jsp#payment_methods" />

		<%@ include file="/account_entry/users/default_users.jspf" %>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>