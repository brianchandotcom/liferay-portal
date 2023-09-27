<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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