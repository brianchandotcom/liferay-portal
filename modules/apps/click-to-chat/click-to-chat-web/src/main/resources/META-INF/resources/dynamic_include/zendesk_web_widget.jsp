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

					<%
					ClickToChatConfiguration clickToChatConfiguration = ClickToChatConfigurationUtil.getClickToChatConfiguration(themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId());

					String stringKey = clickToChatConfiguration.chatProviderSecretKey();

					byte[] keyBytes = stringKey.getBytes(StandardCharsets.UTF_8);

					String taglibJwt = Jwts.builder(
					).setHeaderParam(
						"alg", SignatureAlgorithm.HS256.getValue()
					).setHeaderParam(
						"typ", "JWT"
					).setHeaderParam(
						"kid", clickToChatConfiguration.chatProviderKeyId()
					).claim(
						"scope", "user"
					).claim(
						"name", user.getScreenName()
					).claim(
						"email", user.getEmailAddress()
					).claim(
						"external_id", String.valueOf(user.getUserId())
					).signWith(
						Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256
					).compact();
					%>

					zE('messenger', 'loginUser', (callback) => {
						callback('<%= taglibJwt %>');
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