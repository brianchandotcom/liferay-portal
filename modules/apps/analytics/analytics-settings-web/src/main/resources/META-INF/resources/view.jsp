<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AnalyticsSettingsDisplayContext analyticsSettingsDisplayContext = new AnalyticsSettingsDisplayContext(request, response);
%>

<div>
	<react:component
		module="js/App"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"connected", analyticsSettingsDisplayContext.isConnected()
			).put(
				"liferayAnalyticsURL", analyticsSettingsDisplayContext.getLiferayAnalyticsURL()
			).put(
				"token", analyticsSettingsDisplayContext.getToken()
			).put(
				"wizardMode", analyticsSettingsDisplayContext.isWizardMode()
			).build()
		%>'
	/>
</div>

<aui:script>
	function <portlet:namespace />resetPage() {
		const portlet = document.querySelector(
			'#portlet<portlet:namespace />'.slice(0, -1)
		);
		const container = portlet.querySelectorAll(
			'.portlet-body > .container-fluid'
		)[1];
		const firstColumn = container.querySelector('.row > .col-md-3');
		const secondColumn = container.querySelector('.row > .col-md-9');

		firstColumn.remove();
		secondColumn.className = 'col-md-12';
	}

	<portlet:namespace />resetPage();
</aui:script>