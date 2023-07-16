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
	var APP_ID = '<%= clickToChatChatProviderAccountId %>';

	let intercomSettings = {
		app_id: APP_ID,
	};

	<c:if test="<%= themeDisplay.isSignedIn() %>">
		intercomSettings.name = '<%= user.getFirstName()+" "+user.getLastName()%>';
		intercomSettings.email = '<%= user.getEmailAddress() %>';
		intercomSettings.user_id = '<%= user.getUserId() %>';
        <c:if test="<%= user.getCreateDate() != null %>">
            intercomSettings.created_at = '<%= user.getCreateDate().getTime()%>'
        </c:if>
    </c:if>

	window.intercomSettings = intercomSettings;

	(function () {
		var w = window;
		var ic = w.Intercom;
		if (typeof ic === 'function') {
			ic('reattach_activator');
			ic('update', w.intercomSettings);
		}
		else {
			var d = document;
			var i = function () {
				i.c(arguments);
			};
			i.q = [];
			i.c = function (args) {
				i.q.push(args);
			};
			w.Intercom = i;
			var l = function () {
				var s = d.createElement('script');
				s.type = 'text/javascript';
				s.async = true;
				s.src = 'https://widget.intercom.io/widget/' + APP_ID;
				var x = d.getElementsByTagName('script')[0];
				x.parentNode.insertBefore(s, x);
			};
			if (document.readyState === 'complete') {
				l();
			}
			else if (w.attachEvent) {
				w.attachEvent('onload', l);
			}
			else {
				w.addEventListener('load', l, false);
			}
		}
	})();
</script>