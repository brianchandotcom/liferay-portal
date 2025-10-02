<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/account_orders_data_set/init.jsp" %>

<%
PendingAccountOrdersDataSetDisplayContext pendingAccountOrdersDataSetDisplayContext = (PendingAccountOrdersDataSetDisplayContext)request.getAttribute(PendingAccountOrdersDataSetDisplayContext.class.getName());
%>

<frontend-data-set:headless-display
	additionalProps="<%= pendingAccountOrdersDataSetDisplayContext.getAdditionalProps() %>"
	apiURL="<%= pendingAccountOrdersDataSetDisplayContext.getAPIURL() %>"
	fdsActionDropdownItems="<%= pendingAccountOrdersDataSetDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= CommerceFragmentFDSNames.PENDING_ACCOUNT_ORDERS %>"
	propsTransformer="{PendingAccountOrdersFDSPropsTransformer} from commerce-fragment-impl"
	style="<%= pendingAccountOrdersDataSetDisplayContext.getDisplayStyle() %>"
/>