<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_webdav_page") + StringPool.UNDERLINE;

String path = (String)request.getAttribute("liferay-ui:webdav:path");
%>

<div class="taglib-webdav" id="<%= randomNamespace %>webdav">
	<a class="show-webdav" href="javascript:;"><liferay-ui:message key="access-from-desktop" /></a>

	<aui:input name="webDavURL" type="resource" value='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/webdav" + themeDisplay.getScopeGroup().getFriendlyURL() + path %>' />
</div>

<aui:script use="aui-base">
	var webdavDiv = A.one('#<%= randomNamespace %>webdav');

	if (webdavDiv) {
		var webdavLink = webdavDiv.all('.show-webdav');

		if (webdavLink) {
			webdavLink.on(
				'click',
				function(event) {
					webdavDiv.toggleClass('visible');
				}
			);
		}
	}
</aui:script>