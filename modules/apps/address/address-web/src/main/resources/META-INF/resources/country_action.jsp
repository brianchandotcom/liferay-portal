<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CountriesManagementAdminActionsDisplayContext countriesManagementAdminActionsDisplayContext = new CountriesManagementAdminActionsDisplayContext((Country)row.getObject(), request, permissionChecker, renderResponse);
%>

<clay:dropdown-actions
	aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
	dropdownItems="<%= countriesManagementAdminActionsDisplayContext.getActionDropdownItems() %>"
	message="<%= StringPool.BLANK %>"
	propsTransformer="{CountryActionPropsTransformer} from address-web"
/>