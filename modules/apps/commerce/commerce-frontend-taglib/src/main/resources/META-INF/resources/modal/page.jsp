<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/modal/init.jsp" %>

<%
String containerId = randomNamespace + "modal-root";
%>

<div class="modal-root" id="<%= containerId %>"></div>

<aui:script require="commerce-frontend-js/components/modal/entry as Modal">
	Modal.default('<%= HtmlUtil.escapeJS(id) %>', '<%= containerId %>', {
		id: '<%= HtmlUtil.escapeJS(id) %>',
		onClose: <%= refreshPageOnClose %>
			? function () {
					window.location.reload();
			  }
			: null,
		portletId: '<%= portletDisplay.getRootPortletId() %>',
		size: '<%= HtmlUtil.escapeJS(size) %>',
		spritemap: '<%= HtmlUtil.escapeJS(spritemap) %>',
		title: '<%= HtmlUtil.escapeJS(title) %>',
		url: '<%= HtmlUtil.escapeJS(url) %>',
	});
</aui:script>