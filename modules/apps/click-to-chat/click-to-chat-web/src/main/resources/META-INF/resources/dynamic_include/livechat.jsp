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
	window.__lc = window.__lc || {};
	window.__lc.license = '<%= clickToChatChatProviderAccountId %>';

	(function (n, t, c) {
		function i(n) {
			return e._h ? e._h.apply(null, n) : e._q.push(n);
		}
		var e = {
			_h: null,
			_q: [],
			_v: '2.0',
			call: function () {
				i(['call', c.call(arguments)]);
			},
			get: function () {
				if (!e._h)
					throw new Error(
						"[LiveChatWidget] You can't use getters before load."
					);
				return i(['get', c.call(arguments)]);
			},
			init: function () {
				var n = t.createElement('script');
				(n.async = !0),
					(n.type = 'text/javascript'),
					(n.src = 'https://cdn.livechatinc.com/tracking.js'),
					t.head.appendChild(n);
			},
			off: function () {
				i(['off', c.call(arguments)]);
			},
			on: function () {
				i(['on', c.call(arguments)]);
			},
			once: function () {
				i(['once', c.call(arguments)]);
			},
		};
		!n.__lc.asyncInit && e.init(), (n.LiveChatWidget = n.LiveChatWidget || e);
	})(window, document, [].slice);

	<c:if test="<%= themeDisplay.isSignedIn() %>">
		window.onload = function () {
			LiveChatWidget.call('set_customer_email', '<%= user.getEmailAddress() %>');
			LiveChatWidget.call('set_customer_name', '<%= user.getScreenName() %>');
		};
	</c:if>
</script>