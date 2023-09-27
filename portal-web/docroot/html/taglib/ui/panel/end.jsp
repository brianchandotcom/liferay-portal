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
	<aui:script sandbox="<%= true %>" use="aui-base,liferay-store">
		var storeTask = A.debounce(Liferay.Store, 100);

		function onPanelHide(event) {
			if (event.panel.getAttribute('id') === '<%= id %>Content') {
				var task = {};

				task['<%= id %>'] = true;

				storeTask(task);
			}
		}

		function onPanelShow(event) {
			if (event.panel.getAttribute('id') === '<%= id %>Content') {
				var task = {};

				task['<%= id %>'] = false;

				storeTask(task);
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