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

<%@ include file="/fragment_entry_renderer/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_fragment_entry_renderer_page") + StringPool.UNDERLINE;

FragmentEntry fragmentEntry = (FragmentEntry)request.getAttribute("liferay-fragment:fragment-entry-renderer:fragmentEntry");
%>

<liferay-util:html-top
	outputKey="<%= randomNamespace %>"
>
	<style type="text/css">
		<%= fragmentEntry.getCss() %>
	</style>
</liferay-util:html-top>

<div id="<%= randomNamespace %>">
	<%= fragmentEntry.getHtml() %>
</div>

<aui:script>
	(function() {
		var fragment = document.getElementById('<%= randomNamespace %>');

		<%= fragmentEntry.getJs() %>;
	})();
</aui:script>