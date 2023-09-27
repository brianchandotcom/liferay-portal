<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ControlledFDSDisplayContext controlledFDSDisplayContext = new ControlledFDSDisplayContext(request);
%>

<react:component
	module="{ControlledFrontendDataSet} from frontend-data-set-sample-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"id", FDSSampleFDSNames.CONTROLLED
		).put(
			"items", controlledFDSDisplayContext.getItems()
		).put(
			"showManagementBar", false
		).put(
			"style", "fluid"
		).put(
			"views", controlledFDSDisplayContext.getViews()
		).build()
	%>'
/>