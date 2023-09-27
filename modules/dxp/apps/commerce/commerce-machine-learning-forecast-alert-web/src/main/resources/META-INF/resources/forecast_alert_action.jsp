<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceMLForecastAlertEntry commerceMLForecastAlertEntry = (CommerceMLForecastAlertEntry)row.getObject();

CommerceMLForecastAlertEntryListDisplayContext commerceMLForecastAlertEntryListDisplayContext = (CommerceMLForecastAlertEntryListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= false %>"
>
	<c:if test="<%= commerceMLForecastAlertEntryListDisplayContext.hasUpdatePermission() %>">

		<%
		int currentStatus = commerceMLForecastAlertEntry.getStatus();

		int newStatus = CommerceMLForecastAlertConstants.STATUS_ARCHIVE;

		if (currentStatus == CommerceMLForecastAlertConstants.STATUS_ARCHIVE) {
			newStatus = CommerceMLForecastAlertConstants.STATUS_NEW;
		}
		%>

		<portlet:actionURL name="/commerce_ml_forecast_alert/update_commerce_ml_forecast_alert_entry" var="updateStatusURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= CommerceMLForecastAlertActionKeys.MANAGE_ALERT_STATUS %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceMLForecastAlertEntryId" value="<%= String.valueOf(commerceMLForecastAlertEntry.getCommerceMLForecastAlertEntryId()) %>" />
			<portlet:param name="status" value="<%= String.valueOf(newStatus) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="/commerce_ml_forecast_alert/update_commerce_ml_forecast_alert_entry"
			url="<%= updateStatusURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>