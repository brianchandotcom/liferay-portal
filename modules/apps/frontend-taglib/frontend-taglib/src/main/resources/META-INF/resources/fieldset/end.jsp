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

<%@ include file="/fieldset/init.jsp" %>

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