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
String analyticsReportsPanelState = SessionClicks.get(request, "com.liferay.analytics.reports.web_panelState", "closed");
%>

<div class="lfr-analytics-reports-sidebar" id="analyticsReportsSidebar">
	<div class="d-flex justify-content-between p-3 sidebar-header">
		<h1 class="sr-only"><liferay-ui:message key="content-performance-panel" /></h1>

		<span class="font-weight-bold"><liferay-ui:message key="content-performance" /></span>

		<clay:button
			cssClass="sidenav-close text-secondary"
			displayType="unstyled"
			icon="times"
			monospaced="<%= true %>"
		/>
	</div>

	<div class="sidebar-body">
		<c:if test='<%= Objects.equals(analyticsReportsPanelState, "open") %>'>
			<liferay-util:include page="/analytics_reports_panel.jsp" servletContext="<%= application %>" />
		</c:if>
	</div>
</div>

<aui:script>
	var analyticsReportsPanelToggle = document.getElementById(
		'<portlet:namespace />analyticsReportsPanelToggleId'
	);

	var sidenavInstance = Liferay.SideNavigation.initialize(
		analyticsReportsPanelToggle
	);

	sidenavInstance.on('open.lexicon.sidenav', (event) => {
		Liferay.Util.Session.set(
			'com.liferay.analytics.reports.web_panelState',
			'open'
		);
	});

	sidenavInstance.on('closed.lexicon.sidenav', (event) => {
		Liferay.Util.Session.set(
			'com.liferay.analytics.reports.web_panelState',
			'closed'
		);
	});

	Liferay.once('screenLoad', () => {
		Liferay.SideNavigation.destroy(analyticsReportsPanelToggle);
	});
</aui:script>