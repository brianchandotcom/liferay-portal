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

<%@ include file="/layout/view/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "layout_type_controller_content_page") + StringPool.UNDERLINE;

for (FragmentEntryInstanceLink fragmentEntryInstanceLink : fragmentEntryInstanceLinks) {
	String fragmentEntryInstanceLinkNamespace = randomNamespace + fragmentEntryInstanceLink.getPosition();
%>

	<liferay-util:html-top outputKey="<%= fragmentEntryInstanceLinkNamespace %>">
		<style type="text/css">
			<%= fragmentEntryInstanceLink.getCss() %>
		</style>
	</liferay-util:html-top>

	<div id="<%= fragmentEntryInstanceLinkNamespace %>">
		<%= fragmentEntryInstanceLink.getHtml() %>
	</div>

	<aui:script>
		(function() {
		var fragment = document.getElementById("<%= fragmentEntryInstanceLinkNamespace %>");

		<%= fragmentEntryInstanceLink.getJs() %>
		}());
	</aui:script>

<%
}
%>