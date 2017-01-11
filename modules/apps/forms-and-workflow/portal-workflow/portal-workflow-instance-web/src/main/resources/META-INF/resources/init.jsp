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
taglib uri="http://liferay.com/tld/comment" prefix="liferay-comment" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.asset.kernel.model.AssetRendererFactory" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowInstance" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowLog" %><%@
page import="com.liferay.portal.workflow.instance.web.internal.constants.WorkflowInstancePortletKeys" %><%@
page import="com.liferay.portal.workflow.instance.web.internal.dao.search.WorkflowInstanceResultRowSplitter" %><%@
page import="com.liferay.portal.workflow.instance.web.internal.display.context.MyWorkflowInstanceEditDisplayContext" %><%@
page import="com.liferay.portal.workflow.instance.web.internal.display.context.MyWorkflowInstanceViewDisplayContext" %><%@
page import="com.liferay.portal.workflow.instance.web.internal.display.context.WorkflowInstanceEditDisplayContext" %><%@
page import="com.liferay.portal.workflow.instance.web.internal.display.context.WorkflowInstanceViewDisplayContext" %><%@
page import="com.liferay.taglib.search.DateSearchEntry" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
WorkflowInstanceViewDisplayContext workflowInstanceViewDisplayContext = null;

if (portletName.equals(WorkflowInstancePortletKeys.WORKFLOW_INSTANCE)) {
	workflowInstanceViewDisplayContext = new WorkflowInstanceViewDisplayContext(liferayPortletRequest, liferayPortletResponse);
}
else {
	workflowInstanceViewDisplayContext = new MyWorkflowInstanceViewDisplayContext(liferayPortletRequest, liferayPortletResponse);
}
%>

<%@ include file="/init-ext.jsp" %>