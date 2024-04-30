<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:script sandbox="<%= true %>">
	function handleIframeMessage(event) {
		if (event.data) {
			var virtualDocument = document.createElement('html');

			virtualDocument.innerHTML = JSON.parse(event.data).data;

			const body = virtualDocument.querySelector('body');

			const portletBody = document.querySelector('.portlet-body');

			portletBody.innerHTML = '';

			portletBody.appendChild(body);

			Liferay.Util.runScriptsInElement(body);
		}
	}

	window.addEventListener('message', handleIframeMessage);
</aui:script>