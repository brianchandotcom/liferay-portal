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

<div class="lfr-segments-experiment-sidebar" id="segmentsExperimentSidebar">
	<div class="sidebar-header">
		<h1 class="sr-only"><liferay-ui:message key="ab-test-panel" /></h1>

		<span><liferay-ui:message key="ab-test" /></span>

		<aui:icon cssClass="icon-monospaced sidenav-close" image="times" markupView="lexicon" url="javascript:;" />
	</div>

	<div class="sidebar-body">
		<c:if test="<%= GetterUtil.getBoolean(request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_PANEL_STATE_OPEN)) %>">
			<liferay-util:include page="/segments_experiment_panel.jsp" servletContext="<%= application %>" />
		</c:if>
	</div>
</div>

<aui:script>
	var segmentsExperimentPanelToggle = document.getElementById('<portlet:namespace />segmentsExperimentPanelToggleId');

	var sidenavInstance = Liferay.SideNavigation.initialize(segmentsExperimentPanelToggle);

	sidenavInstance.on(
		'open.lexicon.sidenav',
		function(event) {
			Liferay.Util.Session.set('com.liferay.segments.experiment.web_panelState', 'open');
		}
	);

	sidenavInstance.on(
		'closed.lexicon.sidenav',
		function(event) {
			Liferay.Util.Session.set('com.liferay.segments.experiment.web_panelState', 'closed');
		}
	);

	Liferay.once(
		'screenLoad',
		function() {
			Liferay.SideNavigation.destroy(segmentsExperimentPanelToggle);
		}
	);
</aui:script>