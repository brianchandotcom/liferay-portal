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

<!-- <c:choose>
	<c:when test="<%= SegmentsExperimentUtil.isAnalyticsSynced(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId()) %>"> -->
		<div class="inline-item my-5 p-5 w-100">
			<span aria-hidden="true" class="loading-animation"></span>
		</div>

		<react:component
			module="js/SegmentsExperimentApp.es"
			props="<%= segmentsExperimentDisplayContext.getData() %>"
		/>
	<!-- </c:when>
	<c:otherwise>
		<div class="p-3 pt-4 text-center">
			<liferay-ui:icon
				alt="connect-to-liferay-analytics-cloud"
				src='<%= PortalUtil.getPathContext(request) + "/images/ac_icon.svg" %>'
			/>

			<c:choose>
				<c:when test="<%= SegmentsExperimentUtil.isAnalyticsConnected(themeDisplay.getCompanyId()) %>">
					<h4 class="font-weight-semi-bold h5 mt-3"><liferay-ui:message key="sync-to-analytics-cloud" /></h4>

					<p class="text-secondary"><liferay-ui:message key="in-order-to-perform-an-ab-test,-your-site-has-to-be-synced-to-liferay-analytics-cloud" /></p>

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
					<h4 class="font-weight-semi-bold h5 mt-3"><liferay-ui:message key="connect-to-liferay-analytics-cloud" /></h4>

					<p class="text-secondary"><liferay-ui:message key="in-order-to-perform-an-ab-test,-your-liferay-dxp-instance-has-to-be-connected" /></p>

					<liferay-ui:icon
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						markupView="lexicon"
						message="start-free-trial"
						target="_blank"
						url="<%= SegmentsExperimentUtil.ANALYTICS_CLOUD_TRIAL_URL %>"
					/>

					<portlet:actionURL name="/segments_experiment/hide_segments_experiment_panel" var="hideSegmentsExperimentPanelURL">
						<portlet:param name="redirect" value="<%= themeDisplay.getLayoutFriendlyURL(layout) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						label="<%= true %>"
						linkCssClass="d-block font-weight-bold mb-2 mt-5"
						markupView="lexicon"
						message="do-not-show-me-this-again"
						url="<%= hideSegmentsExperimentPanelURL %>"
					/>

					<p class="text-secondary"><liferay-ui:message key="do-not-show-me-this-again-help" /></p>
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose> -->