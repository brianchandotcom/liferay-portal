<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="container-fluid container-fluid-max-xl py-3" data-aui-deprecation-checker data-qa-id="auiDeprecationChecker">
	<p>Checking&hellip;</p>
</div>

<aui:script>
	import(
		'<%= PortalUtil.getPathModule() %>/frontend-js-aui-sample-web/js/checker.js'
	);
</aui:script>