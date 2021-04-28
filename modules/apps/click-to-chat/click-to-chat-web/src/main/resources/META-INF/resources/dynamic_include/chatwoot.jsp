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

<script type="text/javascript">
	var CHATWOOT_BASE_URL = 'https://app.chatwoot.com';

	(function (d, t) {
		var g = d.createElement(t);
		var s = d.getElementsByTagName(t)[0];

		g.src = CHATWOOT_BASE_URL + '/packs/js/sdk.js';

		s.parentNode.insertBefore(g, s);
	})(document, 'script');

	<c:if test = "<%= themeDisplay.isSignedIn() %>" >
		window.onload = function () {
			window.chatwootSDK.run({
				baseUrl: CHATWOOT_BASE_URL,
				websiteToken: '<%= clickToChatChatProviderAccountId %>',
			});

			window.$chatwoot.setUser('<%= user.getUserId() %>', {
				email: '<%= user.getEmailAddress() %>',
				name: '<%= user.getScreenName() %>',
			});
		};
	</c:if>
</script>