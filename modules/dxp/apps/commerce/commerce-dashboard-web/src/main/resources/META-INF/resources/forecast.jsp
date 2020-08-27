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
String forecastChartRootElementId = liferayPortletResponse.getNamespace() + "-forecast-chart";
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

CommerceDashboardForecastDisplayContext commerceDashboardForecastDisplayContext = (CommerceDashboardForecastDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String assetCategoryIdsString = commerceDashboardForecastDisplayContext.getAssetCategoryIds();

String categoryIds = "[]";

if (Validator.isNotNull(assetCategoryIdsString)) {
	categoryIds = jsonSerializer.serializeDeep(assetCategoryIdsString.split(StringPool.COMMA));
}

String accountIds = "[]";

CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

if (commerceAccount != null) {
	accountIds = jsonSerializer.serializeDeep(new Long[] {commerceAccount.getCommerceAccountId()});
}
%>

<c:if test="<%= commerceDashboardForecastDisplayContext.hasViewPermission() %>">
	<div id="<%= forecastChartRootElementId %>">
		<span aria-hidden="true" class="loading-animation"></span>
	</div>

	<aui:script require="commerce-dashboard-web/js/forecast/index.es as chart">
		chart.default('<%= forecastChartRootElementId %>', {
			APIBaseUrl:
				'/o/headless-commerce-machine-learning/v1.0/accountCategoryForecasts/by-monthlyRevenue',
			accountIds: <%= accountIds %>,
			categoryIds: <%= categoryIds %>,
			noAccountErrorMessage: Liferay.Language.get('no-account-selected'),
			noDataErrorMessage: Liferay.Language.get('no-data-available'),
			portletId: '<%= portletDisplay.getId() %>',
		});
	</aui:script>
</c:if>