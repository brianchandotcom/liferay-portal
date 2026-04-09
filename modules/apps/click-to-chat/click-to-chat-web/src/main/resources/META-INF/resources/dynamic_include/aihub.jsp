<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<aui:script position="inline">
	(function () {
		if (document.getElementById('aihub-chat-widget-script')) {
			return;
		}

		var linkElement = document.createElement('link');

		linkElement.href = 'https://ai.hub.liferay.com/index-css';
		linkElement.rel = 'stylesheet';

		document.head.appendChild(linkElement);

		var scriptElement = document.createElement('script');

		scriptElement.id = 'aihub-chatbot-widget-script';
		scriptElement.src = 'https://ai.hub.liferay.com/index-js';
		scriptElement.setAttribute(
			'chatbot-external-reference-code',
			'<%= clickToChatChatProviderAccountId %>'
		);

		document.body.appendChild(scriptElement);
	})();
</aui:script>