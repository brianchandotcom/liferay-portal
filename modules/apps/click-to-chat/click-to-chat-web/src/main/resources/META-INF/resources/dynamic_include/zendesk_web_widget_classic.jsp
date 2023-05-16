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

<%@ include file="/dynamic_include/init.jsp" %>

<script>
	(function () {
		function loadZendeskScript() {
			function setZendeskUserInfo() {
				if ('<%= themeDisplay.isSignedIn() %>' === 'true') {
					zE('webWidget', 'identify', {
						email: '<%= user.getEmailAddress() %>',
						name: '<%= user.getScreenName() %>',
					});
				}
			}

			if (!document.getElementById('ze-snippet')) {
				var scriptElement = document.createElement('script');

				scriptElement.setAttribute('id', 'ze-snippet');
				scriptElement.setAttribute(
					'src',
					'https://static.zdassets.com/ekr/snippet.js?key=<%= clickToChatChatProviderAccountId %>'
				);
				scriptElement.setAttribute('type', 'text/javascript');
				scriptElement.onload = function () {
					setZendeskUserInfo();
				};

				var bodyElement = document.getElementsByTagName('body').item(0);

				bodyElement.appendChild(scriptElement);
			}
			else {
				setZendeskUserInfo();
			}
		}

		window.onload = function () {
			loadZendeskScript();
		};

		if (document.readyState === 'complete') {
			loadZendeskScript();
		}
	})();
</script>