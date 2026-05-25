<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
String clickToChatAIHubServiceURL = (String)request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_AIHUB_SERVICE_URL);
%>

<aui:script position="inline">
	(function () {
		if (document.getElementById('aihub-chatbot-widget-script')) {
			return;
		}

		var linkElement = document.createElement('link');

		linkElement.href =
			'<%= HtmlUtil.escapeJS(clickToChatAIHubServiceURL) %>/documents/d/global/index-css';
		linkElement.rel = 'stylesheet';

		document.head.appendChild(linkElement);

		var scriptElement = document.createElement('script');

		scriptElement.id = 'aihub-chatbot-widget-script';
		scriptElement.src =
			'<%= HtmlUtil.escapeJS(clickToChatAIHubServiceURL) %>/documents/d/global/index-js';
		scriptElement.setAttribute(
			'ai-hub-url',
			'<%= HtmlUtil.escapeJS(clickToChatAIHubServiceURL) %>'
		);
		scriptElement.setAttribute(
			'chatbot-external-reference-code',
			'<%= clickToChatChatProviderAccountId %>'
		);
		scriptElement.setAttribute(
			'liferay-dxp-url',
			'<%= HtmlUtil.escapeJS(themeDisplay.getPortalURL()) %>'
		);

		document.body.appendChild(scriptElement);
	})();
</aui:script>