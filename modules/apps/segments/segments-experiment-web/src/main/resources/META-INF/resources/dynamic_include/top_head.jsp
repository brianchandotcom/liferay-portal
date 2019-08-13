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
SegmentsExperiment segmentsExperiment = (SegmentsExperiment)request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT);
String segmentsExperimentSegmentsExperienceKey = GetterUtil.getString(request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_SEGMENTS_EXPERIENCE_KEY));
%>

<aui:script sandbox="<%= true %>">
	if (window.Analytics) {
		Analytics.registerMiddleware(
			function(request) {
				request.context.experienceId = '<%= (segmentsExperiment == null) ? segmentsExperimentSegmentsExperienceKey : segmentsExperiment.getSegmentsExperienceKey() %>';

				<c:if test="<%= segmentsExperiment != null %>">
					request.context.experimentId = '<%= segmentsExperiment.getSegmentsExperimentKey() %>';
					request.context.variantId = '<%= segmentsExperimentSegmentsExperienceKey %>';
				</c:if>

				return request;
			}
		);
	}
</aui:script>