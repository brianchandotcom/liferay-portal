<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:script require="frontend-js-web/index as frontendJsWeb">
	var {runScriptsInElement} = frontendJsWeb;

	function handleIframeMessage(event) {
		if (event.data) {
			var virtualDocument = document.createElement('html');

			virtualDocument.innerHTML = JSON.parse(event.data).data;

			document.querySelector('.portlet-body').innerHTML =
				virtualDocument.innerHTML;

			runScriptsInElement(virtualDocument);
		}
	}

	window.addEventListener('message', handleIframeMessage);
</aui:script>