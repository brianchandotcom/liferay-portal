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
	<aui:script type="module">
		function storeTask(id, value) {
			import('<%= ESMURLUtil.buildURL(themeDisplay, "frontend-js-web") %>').then(
				({setSessionValue}) => setSessionValue(id, value)
			);
		}

		function onFieldsetHide(event) {
			if (event.panel.getAttribute('id') === '<%= id %>Content') {
				storeTask('<%= id %>', true);
			}
		}

		function onFieldsetShow(event) {
			if (event.panel.getAttribute('id') === '<%= id %>Content') {
				storeTask('<%= id %>', false);
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