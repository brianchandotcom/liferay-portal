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