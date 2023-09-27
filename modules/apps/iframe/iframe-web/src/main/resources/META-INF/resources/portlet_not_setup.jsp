<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
%>

<clay:alert
	cssClass="portlet-configuration"
	displayType="info"
>
	<clay:button
		displayType="link"
		label="please-configure-this-portlet-to-make-it-visible-to-all-users"
		onClick="<%= portletDisplay.getURLConfigurationJS() %>"
		small="<%= true %>"
	/>
</clay:alert>