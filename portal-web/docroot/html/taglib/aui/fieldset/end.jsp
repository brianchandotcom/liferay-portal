<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/aui/fieldset/init.jsp" %>

		</div>
	</div>
</fieldset>

<c:if test="<%= collapsible %>">
	<aui:script sandbox="<%= true %>" use="aui-base,liferay-store">
		var storeTask = A.debounce(Liferay.Store, 100);

		function onFieldsetHide(event) {
			if (event.panel.getAttribute('id') === '<%= id %>Content') {
				var task = {};

				task['<%= id %>'] = true;

				storeTask(task);
			}
		}

		function onFieldsetShow(event) {
			if (event.panel.getAttribute('id') === '<%= id %>Content') {
				var task = {};

				task['<%= id %>'] = false;

				storeTask(task);
			}
		}

		function onStartNavigate() {
			Liferay.detach('liferay.collapse.hide', onFieldsetHide);
			Liferay.detach('liferay.collapse.show', onFieldsetShow);
			Liferay.detach('startNavigate', onStartNavigate);
		}

		Liferay.on('liferay.collapse.hide', onFieldsetHide);
		Liferay.on('liferay.collapse.show', onFieldsetShow);
		Liferay.on('startNavigate', onStartNavigate);
	</aui:script>
</c:if>