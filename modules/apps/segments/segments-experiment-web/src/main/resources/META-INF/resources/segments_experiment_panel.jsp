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

<%
SegmentsExperimentDisplayContext segmentsExperimentDisplayContext = (SegmentsExperimentDisplayContext)request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_DISPLAY_CONTEXT);

String segmentsExperimentRootId = renderResponse.getNamespace() + "-segments-experiment-root";
%>

<div id="<%= segmentsExperimentRootId %>"></div>

<aui:script require='<%= npmResolvedPackageName + "/js/index.es as segmentsExperimentsApp" %>'>
	segmentsExperimentsApp.default(
		'<%= segmentsExperimentRootId %>',
		{
			initialSegmentsVariants: <%= segmentsExperimentDisplayContext.getSegmentsExperimentRelsJSONArray(locale) %>,
			segmentsExperiences: <%= segmentsExperimentDisplayContext.getSegmentsExperiencesJSONArray(locale) %>,
			segmentsExperiment: <%= segmentsExperimentDisplayContext.getSegmentsExperimentJSONObject() %>,
			selectedSegmentsExperienceId: '<%= segmentsExperimentDisplayContext.getSelectedSegmentsExperienceId() %>'
		},
		{
			endpoints: {
				createSegmentsExperimentURL: '/segments.segmentsexperiment/add-segments-experiment',
				editSegmentsExperimentURL: '/segments.segmentsexperiment/update-segments-experiment',
				createSegmentsVariantURL: '<%= segmentsExperimentDisplayContext.getCreateSegmentsVariantURL() %>'
			},
			namespace: '<portlet:namespace />',
			contentPageEditorNamespace: '<%= segmentsExperimentDisplayContext.getContentPageEditorPortletNamespace() %>',
			page: {
				classPK: '<%= themeDisplay.getPlid() %>',
				classNameId: '<%= PortalUtil.getClassNameId(Layout.class.getName()) %>',
				type: '<%= layout.getType() %>'
			},
			spritemap: '<%= themeDisplay.getPathThemeImages() + "/lexicon/icons.svg" %>'
		}
	);
</aui:script>