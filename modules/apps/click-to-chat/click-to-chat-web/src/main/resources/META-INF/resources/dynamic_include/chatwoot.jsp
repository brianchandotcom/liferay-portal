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
	(function (d, t) {
		var BASE_URL = 'https://app.chatwoot.com';
		var g = d.createElement(t),
			s = d.getElementsByTagName(t)[0];
		g.src = BASE_URL + '/packs/js/sdk.js';
		s.parentNode.insertBefore(g, s);
		g.onload = function () {
			window.chatwootSDK.run({
				websiteToken: '<%= accountToken %>',
				baseUrl: BASE_URL,
			});
		};
	})(document, 'script');
	
	window.onload = function () {
		window.$chatwoot.setUser('<%= user.getUserId() %>', {
			email: '<%= user.getEmailAddress() %>',
			name: '<%= user.getScreenName() %>',
		});
	};
</script>