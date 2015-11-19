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

<%@ include file="/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

String taglibOnClick = renderResponse.getNamespace() + "openInstallFromURLView()";
%>

<liferay-ui:icon
	message="install-from-url"
	onClick="<%= taglibOnClick %>"
	url="javascript:;"
/>

<aui:script>
	function <portlet:namespace />openInstallFromURLView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true
				},
				id: '<portlet:namespace />openInstallFromURLView',
				title: '<%= UnicodeLanguageUtil.get(request, "install-from-url") %>',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/install_remote_app.jsp" /><portlet:param name="redirect" value="<%= portletURL.toString() %>" /></liferay-portlet:renderURL>'
			}
		);
	}
</aui:script>