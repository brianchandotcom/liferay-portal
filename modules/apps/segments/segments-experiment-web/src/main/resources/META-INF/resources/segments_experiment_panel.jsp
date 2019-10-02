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

		<div id="<%= segmentsExperimentRootId %>"></div>

		<aui:script require='<%= npmResolvedPackageName + "/js/index.es as segmentsExperimentsApp" %>'>
			segmentsExperimentsApp.default(
				'<%= segmentsExperimentRootId %>',
				{
					context: {
						assetsPath: '<%= segmentsExperimentDisplayContext.getAssetsPath() %>',
						contentPageEditorNamespace: '<%= segmentsExperimentDisplayContext.getContentPageEditorPortletNamespace() %>',
						endpoints: {
							calculateSegmentsExperimentEstimatedDurationURL: '<%= segmentsExperimentDisplayContext.getCalculateSegmentsExperimentEstimatedDurationURL() %>',
							createSegmentsExperimentURL: '<%= segmentsExperimentDisplayContext.getCreateSegmentsExperimentURL() %>',
							createSegmentsVariantURL: '<%= segmentsExperimentDisplayContext.getCreateSegmentsVariantURL() %>',
							deleteSegmentsExperimentURL: '<%= segmentsExperimentDisplayContext.getDeleteSegmentsExperimentURL() %>',
							deleteSegmentsVariantURL: '<%= segmentsExperimentDisplayContext.getDeleteSegmentsVariantURL() %>',
							editSegmentsExperimentStatusURL: '<%= segmentsExperimentDisplayContext.getEditSegmentsExperimentStatusURL() %>',
							editSegmentsExperimentURL: '<%= segmentsExperimentDisplayContext.getEditSegmentsExperimentURL() %>',
							editSegmentsVariantLayoutURL: '<%= segmentsExperimentDisplayContext.getEditSegmentsVariantLayoutURL() %>',
							editSegmentsVariantURL: '<%= segmentsExperimentDisplayContext.getEditSegmentsVariantURL() %>',
							runSegmentsExperimentURL: '<%= segmentsExperimentDisplayContext.getRunSegmentsExperimenttURL() %>'
						},
						namespace: '<portlet:namespace />',
						page: {
							classPK: '<%= themeDisplay.getPlid() %>',
							classNameId: '<%= PortalUtil.getClassNameId(Layout.class.getName()) %>',
							type: '<%= layout.getType() %>'
						}
					},
					props: {
						historySegmentsExperiments: <%= segmentsExperimentDisplayContext.getHistorySegmentsExperimentsJSONArray(locale) %>,
						initialSegmentsVariants: <%= segmentsExperimentDisplayContext.getSegmentsExperimentRelsJSONArray(locale) %>,
						segmentsExperiences: <%= segmentsExperimentDisplayContext.getSegmentsExperiencesJSONArray(locale) %>,
						segmentsExperiment: <%= segmentsExperimentDisplayContext.getSegmentsExperimentJSONObject(locale) %>,
						segmentsExperimentGoals: <%= segmentsExperimentDisplayContext.getSegmentsExperimentGoalsJSONArray(locale) %>,
						selectedSegmentsExperienceId: '<%= segmentsExperimentDisplayContext.getSelectedSegmentsExperienceId() %>',
						viewSegmentsExperimentDetailsURL: '<%= segmentsExperimentDisplayContext.getViewSegmentsExperimentDetailsURL() %>',
						winnerSegmentsVariantId: '<%= segmentsExperimentDisplayContext.getWinnerSegmentsExperienceId() %>'
					}
				}
			);
		</aui:script>
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