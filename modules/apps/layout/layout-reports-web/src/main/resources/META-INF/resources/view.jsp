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

<div class="lfr-layouts-reports-sidebar" id="layoutsReportsSidebar">
	<div class="sidebar-header">
		<clay:content-row
			cssClass="sidebar-section"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<h1 class="sr-only"><liferay-ui:message key="page-audit" /></h1>

				<span><liferay-ui:message key="page-audit" /></span>
			</clay:content-col>

			<clay:content-col>
				<clay:button
					cssClass="sidenav-close"
					displayType="unstyled"
					icon="times-small"
					monospaced="<%= true %>"
				/>
			</clay:content-col>
		</clay:content-row>
	</div>

	<div class="sidebar-body">
		<liferay-util:include page="/layout_reports_panel.jsp" servletContext="<%= application %>" />
	</div>
</div>

<aui:script>
	var layoutReportsPanelToggle = document.getElementById(
		'<portlet:namespace />layoutReportsPanelToggleId'
	);

	var sidenavInstance = Liferay.SideNavigation.initialize(
		layoutReportsPanelToggle
	);

	sidenavInstance.on('open.lexicon.sidenav', (event) => {
		Liferay.Util.Session.set(
			'com.liferay.layout.reports.web_layoutReportsPanelState',
			'open'
		);
	});

	sidenavInstance.on('closed.lexicon.sidenav', (event) => {
		Liferay.Util.Session.set(
			'com.liferay.layout.reports.web_layoutReportsPanelState',
			'closed'
		);
	});

	Liferay.once('screenLoad', () => {
		Liferay.SideNavigation.destroy(layoutReportsPanelToggle);
	});
</aui:script>