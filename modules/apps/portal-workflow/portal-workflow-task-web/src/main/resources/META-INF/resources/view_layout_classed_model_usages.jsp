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
String redirect = ParamUtil.getString(request, "redirect");

String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "usages") + ": " + workflowTaskDisplayContext.getAssetTitle(workflowTask));
%>

<liferay-layout:layout-classed-model-usages-admin
	className="<%= className %>"
	classPK="<%= classPK %>"
/>