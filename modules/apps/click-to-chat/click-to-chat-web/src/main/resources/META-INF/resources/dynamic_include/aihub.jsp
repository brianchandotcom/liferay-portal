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

		var scriptElement = document.createElement('script');

		scriptElement.id = 'aihub-chat-widget-script';
		scriptElement.src = 'https://aihub.liferay.com/chat/widget.js';
		scriptElement.setAttribute(
			'data-account-id',
			'<%= clickToChatChatProviderAccountId %>'
		);

		<c:if test="<%= themeDisplay.isSignedIn() %>">
			scriptElement.setAttribute(
				'data-user-email',
				'<%= user.getEmailAddress() %>'
			);
			scriptElement.setAttribute('data-user-name', '<%= user.getFullName() %>');
		</c:if>

		document.body.appendChild(scriptElement);
	})();
</aui:script>