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
CommerceMLForecastAlertEntryListDisplayContext commerceMLForecastAlertEntryListDisplayContext = (CommerceMLForecastAlertEntryListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />commerceMLForecastAlertEntryWrapper">
	<liferay-ui:error key="principalException" message="you-do-not-have-permission-to-update-forecast-alert-statuses" />

	<c:choose>
		<c:when test="<%= commerceMLForecastAlertEntryListDisplayContext.hasViewPermission() %>">
			<liferay-ui:search-container
				cssClass="table-nowrap"
				id="commerceMLForecastAlertEntries"
				searchContainer="<%= commerceMLForecastAlertEntryListDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntry"
					keyProperty="commerceMLForecastAlertEntryId"
					modelVar="commerceMLForecastAlertEntry"
				>

					<%
					CommerceAccount commerceAccount = commerceMLForecastAlertEntryListDisplayContext.getCommerceAccount(commerceMLForecastAlertEntry.getCommerceAccountId());

					long logoId = commerceAccount.getLogoId();
					%>

					<liferay-ui:search-container-column-image
						colspan="<%= 1 %>"
						name="logo"
						src='<%= themeDisplay.getPathImage() + "/organization_logo?img_id=" + logoId + "&t=" + WebServerServletTokenUtil.getToken(logoId) %>'
					/>

					<liferay-ui:search-container-column-text
						cssClass="important table-cell-expand"
						name="name"
						value="<%= HtmlUtil.escape(commerceAccount.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						property="forecast"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						property="actual"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						property="relativeChange"
					/>

					<liferay-ui:search-container-column-jsp
						align="center"
						cssClass="entry-action-column"
						name="status"
						path="/forecast_alert_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="you-do-not-have-permission-to-view-forecast-alerts" />
		</c:otherwise>
	</c:choose>
</div>