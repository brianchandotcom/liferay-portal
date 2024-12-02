<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div>
	<react:component
		module="{App} from marketplace-settings-web"
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