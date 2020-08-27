<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String historyChartRootElementId = liferayPortletResponse.getNamespace() + "-history-chart";

CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

CommerceAccount commerceAccount = commerceContext.getCommerceAccount();
%>

<div id="<%= historyChartRootElementId %>">
	<span aria-hidden="true" class="loading-animation"></span>
</div>

<aui:script require="commerce-dashboard-web/js/history/index.es as chart">
	chart.default('<%= historyChartRootElementId %>', {
		APIBaseUrl: '/o/----',
		accountIdParamName: '----',
		commerceAccountId: '<%= commerceAccount.getCommerceAccountId() %>',
		noAccountErrorMessage: Liferay.Language.get('no-account-selected'),
		noDataErrorMessage: Liferay.Language.get('no-data-available'),
		portletId: '<%= portletDisplay.getId() %>',
	});
</aui:script>