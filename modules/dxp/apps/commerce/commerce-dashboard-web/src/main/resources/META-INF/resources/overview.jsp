<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String overviewChartRootElementId = liferayPortletResponse.getNamespace() + "-overview-chart";

CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

AccountEntry accountEntry = commerceContext.getAccountEntry();
%>

<div id="<%= overviewChartRootElementId %>">
	<span aria-hidden="true" class="loading-animation"></span>
</div>

<aui:script require="commerce-dashboard-web/js/overview/index.es as chart">
	chart.default('<%= overviewChartRootElementId %>', {
		APIBaseUrl: '/o/----',
		accountIdParamName: '----',
		commerceAccountId: '<%= accountEntry.getAccountEntryId() %>',
		noAccountErrorMessage: Liferay.Language.get('no-account-selected'),
		noDataErrorMessage: Liferay.Language.get('no-data-available'),
		portletId: '<%= portletDisplay.getId() %>',
	});
</aui:script>