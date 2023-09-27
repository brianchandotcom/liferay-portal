<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<liferay-theme:defineObjects />

<iframe id="<portlet:namespace />iframe" scrolling="yes" src="<%= application.getContextPath() %>/monitoring-proxy/app/monitoring" style="bottom: 0px; height: 100%; left: 0px; min-height: 600px; overflow: hidden; overflow-x: hidden; overflow-y: hidden; position: relative; right: 0px; top: 0px; width: 100%;"></iframe>

<aui:script use="aui-autosize-iframe">
	var iframe = A.one('#<portlet:namespace />iframe');

	if (iframe) {
		iframe.plug(A.Plugin.AutosizeIframe, {
			monitorHeight: true,
		});
	}
</aui:script>