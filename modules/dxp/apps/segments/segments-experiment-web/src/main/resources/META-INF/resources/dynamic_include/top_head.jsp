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
SegmentsExperiment segmentsExperiment = (SegmentsExperiment)request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT);
%>

<aui:script sandbox="<%= true %>">
	<c:if test='<%= (segmentsExperiment != null) && Objects.equals(segmentsExperiment.getGoal(), "click") && Validator.isNotNull(segmentsExperiment.getGoalTarget()) %>'>
		var element = document.querySelector(
			'<%= segmentsExperiment.getGoalTarget() %>'
		);

		if (element) {
			element.addEventListener('click', (event) => {
				if (window.Analytics) {
					Analytics.send('ctaClicked', 'Page', {elementId: event.target.id});
				}
			});
		}
	</c:if>
</aui:script>