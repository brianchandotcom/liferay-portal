<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ContentDashboardFileExtensionItemSelectorViewDisplayContext contentDashboardFileExtensionItemSelectorViewDisplayContext = (ContentDashboardFileExtensionItemSelectorViewDisplayContext)request.getAttribute(ContentDashboardFileExtensionItemSelectorViewDisplayContext.class.getName());
%>

<section class="h-100">
	<span aria-hidden="true" class="loading-animation mt-0 tree-filter-loader" style="top: 50%; transform: translateY(-50%);"></span>

	<react:component
		module="js/components/SelectFileExtensionWrapper"
		props="<%= contentDashboardFileExtensionItemSelectorViewDisplayContext.getData() %>"
	/>
</section>