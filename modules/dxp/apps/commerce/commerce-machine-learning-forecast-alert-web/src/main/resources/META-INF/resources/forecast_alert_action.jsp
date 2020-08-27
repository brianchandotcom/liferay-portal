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

		<portlet:actionURL name="updateStatus" var="updateStatusURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= CommerceMLForecastAlertActionKeys.MANAGE_ALERT_STATUS %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceMLForecastAlertEntryId" value="<%= String.valueOf(commerceMLForecastAlertEntry.getCommerceMLForecastAlertEntryId()) %>" />
			<portlet:param name="status" value="<%= String.valueOf(newStatus) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="updateStatus"
			url="<%= updateStatusURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>