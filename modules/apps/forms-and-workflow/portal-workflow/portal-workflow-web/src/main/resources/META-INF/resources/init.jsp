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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.servlet.taglib.DynamicInclude" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys" %><%@
page import="com.liferay.portal.workflow.web.internal.constants.WorkflowWebKeys" %><%@
page import="com.liferay.taglib.servlet.PipingServletResponse" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %>

<portlet:defineObjects />

<%
String defaultTab = (String)renderRequest.getAttribute(WorkflowWebKeys.WORKFLOW_DEFAULT_TAB);

String tab = ParamUtil.get(request, "tab", defaultTab);

Map<String, DynamicInclude> dynamicIncludes = (Map<String, DynamicInclude>)renderRequest.getAttribute(WorkflowWebKeys.WORKFLOW_TAB_DYNAMIC_INCLUDES);
List<String> tabNames = (List<String>)renderRequest.getAttribute(WorkflowWebKeys.WORKFLOW_TAB_NAMES);
%>