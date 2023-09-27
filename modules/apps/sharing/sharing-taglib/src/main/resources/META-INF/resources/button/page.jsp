<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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