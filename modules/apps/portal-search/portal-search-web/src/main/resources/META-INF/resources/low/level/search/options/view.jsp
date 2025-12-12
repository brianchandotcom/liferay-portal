<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>

<%
renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
%>

<clay:alert
	displayType="info"
>
	<clay:button
		cssClass="align-baseline border-0 p-0"
		displayType="link"
		label="low-level-search-options-help"
		onClick="<%= portletDisplay.getURLConfigurationJS() %>"
		small="<%= true %>"
	/>
</clay:alert>