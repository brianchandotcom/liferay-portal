<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout_classed_model_usages_view/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-layout:layout-classed-model-usages-view:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-layout:layout-classed-model-usages-view:classPK"));

LayoutClassedModelUsagesDisplayContext layoutClassedModelUsagesDisplayContext = new LayoutClassedModelUsagesDisplayContext(request, renderRequest, renderResponse, className, classPK);
%>

<react:component
	module="layout_classed_model_usages_view/js/ViewUsages"
	props="<%= layoutClassedModelUsagesDisplayContext.getUsagesData() %>"
/>