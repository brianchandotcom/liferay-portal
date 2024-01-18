<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SegmentsSimulationDisplayContext segmentsSimulationDisplayContext = (SegmentsSimulationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:container-fluid
	cssClass="p-0 segments-simulation"
	id='<%= liferayPortletResponse.getNamespace() + "segmentsSimulationContainer" %>'
>
	<react:component
		module="{PageContentSelectors} from segments-simulation-web"
		props="<%= segmentsSimulationDisplayContext.getData() %>"
	/>
</clay:container-fluid>