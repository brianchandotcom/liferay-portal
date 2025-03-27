<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/panel/init.jsp" %>

		</div>
	</div>
</div>

<c:if test="<%= collapsible %>">
	<aui:script type="module">
		function storeTask(id, value) {
			import('<%= ESMURLUtil.buildURL(themeDisplay, "frontend-js-web") %>').then(
				({setSessionValue}) => setSessionValue(id, value)
			);
		}

		function onPanelHide(event) {
			if (event.panel.getAttribute('id') === '<%= id %>Content') {
				storeTask('<%= id %>', true);
			}
		}

		function onPanelShow(event) {
			if (event.panel.getAttribute('id') === '<%= id %>Content') {
				storeTask('<%= id %>', false);
			}
		}

		function onStartNavigate() {
			Liferay.detach('liferay.collapse.hide', onPanelHide);
			Liferay.detach('liferay.collapse.show', onPanelShow);
			Liferay.detach('startNavigate', onStartNavigate);
		}

		Liferay.on('liferay.collapse.hide', onPanelHide);
		Liferay.on('liferay.collapse.show', onPanelShow);
		Liferay.on('startNavigate', onStartNavigate);
	</aui:script>
</c:if>