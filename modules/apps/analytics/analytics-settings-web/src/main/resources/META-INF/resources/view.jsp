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