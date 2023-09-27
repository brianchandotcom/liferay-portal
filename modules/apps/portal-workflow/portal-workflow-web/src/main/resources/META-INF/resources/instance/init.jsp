<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/comment" prefix="liferay-comment" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>

<%@ page import="com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.asset.kernel.model.AssetRendererFactory" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowHandler" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowInstance" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowLog" %><%@
page import="com.liferay.portal.workflow.web.internal.dao.search.WorkflowInstanceResultRowSplitter" %><%@
page import="com.liferay.portal.workflow.web.internal.display.context.WorkflowInstanceEditDisplayContext" %><%@
page import="com.liferay.portal.workflow.web.internal.display.context.WorkflowInstanceViewDisplayContext" %><%@
page import="com.liferay.taglib.search.DateSearchEntry" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.Collections" %>

<liferay-frontend:defineObjects />

<%
WorkflowInstanceEditDisplayContext workflowInstanceEditDisplayContext = (WorkflowInstanceEditDisplayContext)renderRequest.getAttribute(WorkflowWebKeys.WORKFLOW_INSTANCE_EDIT_DISPLAY_CONTEXT);
WorkflowInstanceViewDisplayContext workflowInstanceViewDisplayContext = (WorkflowInstanceViewDisplayContext)renderRequest.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<%@ include file="/instance/init-ext.jsp" %>