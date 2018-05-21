<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<liferay-theme:defineObjects />

<iframe id="<portlet:namespace />iframe" scrolling="yes" src="<%= application.getContextPath() %>/xpack-monitoring-proxy/app/monitoring" style="bottom: 0px; height: 100%; left: 0px; min-height: 600px; overflow-x: hidden; overflow-y: hidden; overflow: hidden; position: relative; right: 0px; top: 0px; width: 100%;"></iframe>

<aui:script use="aui-autosize-iframe">
	var iframe = A.one('#<portlet:namespace />iframe');

	if (iframe) {
		iframe.plug(
			A.Plugin.AutosizeIframe,
			{
				monitorHeight: true
			}
		);
	}
</aui:script>