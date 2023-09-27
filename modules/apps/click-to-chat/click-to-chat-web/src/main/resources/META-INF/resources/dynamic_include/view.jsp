<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<liferay-util:include page='<%= "/dynamic_include/" + clickToChatChatProviderId + ".jsp" %>' servletContext="<%= application %>" />

<script type="text/javascript">
	(function () {
		function handleVisibility(selectors, hide) {
			let selectorsList = selectors.split(',');
			if (hide) {
				selectorsList.forEach((selector) => {
					document
						.querySelectorAll(selector)
						.forEach((el) => el.classList.add('d-none', 'invisible'));
				});
			}
			else {
				selectorsList.forEach((selector) => {
					document
						.querySelectorAll(selector)
						.forEach((el) =>
							el.classList.remove('d-none', 'invisible')
						);
				});
			}
		}

		const clickToChatProviders = {
			chatwoot: function (hide) {
				if (hide) {
					document
						.querySelectorAll(
							'.woot--bubble-holder,.woot-widget-holder'
						)
						.forEach((el) => el.remove());
				}
			},
			crisp: '.crisp-client',
			hubspot: '#hubspot-messages-iframe-container',
			jivochat: 'jdiv',
			livechat: '#chat-widget-container',
			liveperson: '.LPMcontainer.LPMoverlay,.lp_desktop',
			smartsupp: '#chat-application',
			tawkto: function (hide) {
				if (window.Tawk_API) {
					if (hide) {
						window.Tawk_API.minimize();
						window.Tawk_API.hideWidget();
					}
					else if (typeof window.Tawk_API.showWidget === 'function') {
						window.Tawk_API.showWidget();
					}
				}
			},
			tidio: '#tidio-chat',
			zendesk: '#launcher,#webWidget',
		};

		Object.entries(clickToChatProviders).forEach(([key, action]) => {
			var hideElement = true;

			if (key === '<%= clickToChatChatProviderId %>') {
				hideElement = false;
			}

			if (typeof action === 'string') {
				return handleVisibility(action, hideElement);
			}
			action(hideElement);
		});
	})();
</script>