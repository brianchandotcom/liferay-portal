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
AnalyticsReportsDisplayContext analyticsReportsDisplayContext = (AnalyticsReportsDisplayContext)request.getAttribute(AnalyticsReportsWebKeys.ANALYTICS_REPORTS_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= AnalyticsReportsUtil.isAnalyticsEnabled(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId()) %>">
		<div id="<portlet:namespace />-analytics-reports-root">
			<div class="inline-item my-5 p-5 w-100">
				<span aria-hidden="true" class="loading-animation"></span>
			</div>

			<react:component
				data="<%= analyticsReportsDisplayContext.getData() %>"
				module="js/AnalyticsReportsApp"
			/>
		</div>
	</c:when>
	<c:otherwise>
		<div id="<portlet:namespace />-analytics-reports-root">
			<div class="p-3 pt-5 text-center">
				<liferay-ui:icon
					alt="connect-to-liferay-analytics-cloud"
					src='<%= PortalUtil.getPathContext(request) + "/assets/ac-icon.svg" %>'
				/>

				<c:choose>
					<c:when test="<%= AnalyticsReportsUtil.isAnalyticsEnabled(themeDisplay.getCompanyId()) %>">
						<h4 class="mt-3"><liferay-ui:message key="sync-to-analytics-cloud" /></h4>

						<p class="text-secondary"><liferay-ui:message key="sync-to-analytics-cloud-help" /></p>

						<liferay-ui:icon
							label="<%= true %>"
							linkCssClass="btn btn-primary btn-sm mb-3"
							markupView="lexicon"
							message="open-analytics-cloud"
							target="_blank"
							url="<%= analyticsReportsDisplayContext.getLiferayAnalyticsURL(themeDisplay.getCompanyId()) %>"
						/>
					</c:when>
					<c:otherwise>
						<h4 class="mt-3"><liferay-ui:message key="connect-to-liferay-analytics-cloud" /></h4>

						<p class="text-secondary"><liferay-ui:message key="connect-to-liferay-analytics-cloud-help" /></p>

						<liferay-ui:icon
							label="<%= true %>"
							linkCssClass="btn btn-secondary btn-sm"
							markupView="lexicon"
							message="start-free-trial"
							target="_blank"
							url="<%= AnalyticsReportsUtil.ANALYTICS_CLOUD_TRIAL_URL %>"
						/>

						<portlet:actionURL name="/analytics_reports/hide_panel" var="hideAnalyticsReportsPanelURL">
							<portlet:param name="redirect" value="<%= themeDisplay.getLayoutFriendlyURL(layout) %>" />
						</portlet:actionURL>

						<liferay-ui:icon
							label="<%= true %>"
							linkCssClass="d-block font-weight-bold mb-3 mt-5"
							markupView="lexicon"
							message="dont-show-me-this-again"
							url="<%= hideAnalyticsReportsPanelURL %>"
						/>

						<p class="text-secondary"><liferay-ui:message key="dont-show-me-this-again-help" /></p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:otherwise>
</c:choose>