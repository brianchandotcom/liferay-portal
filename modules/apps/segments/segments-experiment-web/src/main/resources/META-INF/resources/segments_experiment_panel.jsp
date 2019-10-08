<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= SegmentsExperimentUtil.isAnalyticsEnabled(themeDisplay.getCompanyId()) %>">

		<%
		SegmentsExperimentDisplayContext segmentsExperimentDisplayContext = (SegmentsExperimentDisplayContext)request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_DISPLAY_CONTEXT);

		String segmentsExperimentRootId = renderResponse.getNamespace() + "-segments-experiment-root";
		%>

		<div id="<%= segmentsExperimentRootId %>">
			<div class="inline-item my-5 p-5 w-100">
				<span aria-hidden="true" class="loading-animation"></span>
			</div>

			<react:component
				data="<%= segmentsExperimentDisplayContext.getData() %>"
				module="js/index.es"
			/>
		</div>
	</c:when>
	<c:otherwise>
		<div class="p-3 pt-5 text-center">
			<liferay-ui:icon
				alt="connect-to-analytics-cloud"
				src='<%= PortalUtil.getPathContext(request) + "/assets/ac-icon.svg" %>'
			/>

			<h4 class="mt-3"><liferay-ui:message key="connect-to-analytics-cloud" /></h4>

			<p><liferay-ui:message key="connect-to-analytics-cloud-help" /></p>

			<liferay-ui:icon
				label="<%= true %>"
				linkCssClass="btn btn-primary btn-sm mb-4"
				markupView="lexicon"
				message="start-free-trial"
				target="_blank"
				url="<%= SegmentsExperimentUtil.ANALYTICS_CLOUD_TRIAL_URL %>"
			/>

			<portlet:actionURL name="/hide_segments_experiment_panel" var="hideSegmentsExperimentPanelURL">
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