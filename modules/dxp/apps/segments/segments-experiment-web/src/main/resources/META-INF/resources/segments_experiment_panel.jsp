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
SegmentsExperimentDisplayContext segmentsExperimentDisplayContext = (SegmentsExperimentDisplayContext)request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= SegmentsExperimentUtil.isAnalyticsEnabled(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId()) %>">
		<div id="<portlet:namespace />-segments-experiment-root" %>
			<div class="inline-item my-5 p-5 w-100">
				<span aria-hidden="true" class="loading-animation"></span>
			</div>

			<react:component
				data="<%= segmentsExperimentDisplayContext.getData() %>"
				module="js/SegmentsExperimentApp.es"
			/>
		</div>
	</c:when>
	<c:otherwise>
		<div class="p-3 pt-5 text-center">
			<liferay-ui:icon
				alt="connect-to-liferay-analytics-cloud"
				src='<%= PortalUtil.getPathContext(request) + "/assets/ac-icon.svg" %>'
			/>

			<c:choose>
				<c:when test="<%= SegmentsExperimentUtil.isAnalyticsEnabled(themeDisplay.getCompanyId()) %>">
					<h4 class="mt-3"><liferay-ui:message key="sync-to-analytics-cloud" /></h4>

					<p class="text-secondary"><liferay-ui:message key="sync-to-analytics-cloud-help" /></p>

					<liferay-ui:icon
						label="<%= true %>"
						linkCssClass="btn btn-primary btn-sm mb-3"
						markupView="lexicon"
						message="open-analytics-cloud"
						target="_blank"
						url="<%= segmentsExperimentDisplayContext.getLiferayAnalyticsURL(themeDisplay.getCompanyId()) %>"
					/>
				</c:when>
				<c:otherwise>
					<h4 class="mt-3"><liferay-ui:message key="connect-to-liferay-analytics-cloud" /></h4>

					<p class="text-secondary"><liferay-ui:message key="connect-to-liferay-analytics-cloud-help" /></p>

					<liferay-ui:icon
						label="<%= true %>"
						linkCssClass="btn btn-primary btn-sm mb-3"
						markupView="lexicon"
						message="start-free-trial"
						target="_blank"
						url="<%= SegmentsExperimentUtil.ANALYTICS_CLOUD_TRIAL_URL %>"
					/>
				</c:otherwise>
			</c:choose>

			<portlet:actionURL name="/segments_experiment/hide_panel" var="hideSegmentsExperimentPanelURL">
				<portlet:param name="redirect" value="<%= themeDisplay.getLayoutFriendlyURL(layout) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm mb-4"
				markupView="lexicon"
				message="hide-ab-test-panel"
				url="<%= hideSegmentsExperimentPanelURL %>"
			/>
		</div>
	</c:otherwise>
</c:choose>