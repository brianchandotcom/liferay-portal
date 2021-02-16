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

<%@ include file="/button/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_sharing_button_page") + StringPool.UNDERLINE;

String buttonComponentId = randomNamespace + "shareButton";
%>

<clay:button
	displayType="secondary"
	id="<%= buttonComponentId %>"
	label="share"
	small="<%= true %>"
/>

<aui:script>
	var button = document.getElementById('<%= buttonComponentId %>');

	button.addEventListener('click', () => {
		<%= request.getAttribute("liferay-sharing:button:onClick") %>;
	});
</aui:script>