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
	function loadTidioScript() {
		function setTidioUserInfo() {
			if ('<%= themeDisplay.isSignedIn() %>' === 'true') {
				document.tidioIdentify = {
					distinct_id: '<%= user.getUserId() %>',
					email: '<%= user.getEmailAddress() %>',
					name: '<%= user.getFirstName() %>',
				};
			}
		}

		if (!document.getElementById('tidio-script-chat')) {
			var scriptElement = document.createElement('script');

			scriptElement.setAttribute('id', 'tidio-script-chat');
			scriptElement.setAttribute(
				'src',
				'//code.tidio.co/<%= clickToChatChatProviderAccountId %>.js'
			);
			scriptElement.setAttribute('type', 'text/javascript');
			scriptElement.onload = function () {
				setTidioUserInfo();
			};

			var bodyElement = document.getElementsByTagName('body').item(0);

			bodyElement.appendChild(scriptElement);
		}
		else {
			setTidioUserInfo();
		}
	}

	window.onload = function () {
		loadTidioScript();
	};

	if (document.readyState === 'complete') {
		loadTidioScript();
	}
</script>