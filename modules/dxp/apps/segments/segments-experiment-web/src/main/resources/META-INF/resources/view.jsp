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

<div class="lfr-segments-experiment-sidebar" id="segmentsExperimentSidebar">
	<div class="d-flex justify-content-between p-3 sidebar-header">
		<h1 class="sr-only"><liferay-ui:message key="ab-test-panel" /></h1>

		<span class="font-weight-bold"><liferay-ui:message key="ab-test" /></span>

		<clay:button
			aria-label='<%= LanguageUtil.get(request, "close") %>'
			cssClass="sidenav-close text-secondary"
			displayType="unstyled"
			icon="times"
			monospaced="<%= true %>"
		/>
	</div>

	<div class="sidebar-body">
		<c:if test="<%= GetterUtil.getBoolean(request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_PANEL_STATE_OPEN)) %>">
			<liferay-util:include page="/segments_experiment_panel.jsp" servletContext="<%= application %>" />
		</c:if>
	</div>
</div>