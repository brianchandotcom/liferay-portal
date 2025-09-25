<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/accounts_data_set/init.jsp" %>

<%
AccountsDataSetDisplayContext accountsDataSetDisplayContext = (AccountsDataSetDisplayContext)request.getAttribute(AccountsDataSetDisplayContext.class.getName());
%>

<frontend-data-set:headless-display
	additionalProps="<%= accountsDataSetDisplayContext.getAdditionalProps() %>"
	apiURL="<%= accountsDataSetDisplayContext.getAPIURL() %>"
	fdsActionDropdownItems="<%= accountsDataSetDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= CommerceFragmentFDSNames.ACCOUNT_ENTRIES %>"
	propsTransformer="{AccountsFDSPropsTransformer} from commerce-fragment-impl"
	style="<%= accountsDataSetDisplayContext.getDisplayStyle() %>"
/>