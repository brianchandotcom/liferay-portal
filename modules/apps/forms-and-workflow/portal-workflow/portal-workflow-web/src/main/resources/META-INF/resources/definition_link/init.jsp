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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinition" %><%@
page import="com.liferay.portal.workflow.web.internal.display.context.WorkflowDefinitionLinkDisplayContext" %><%@
page import="com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkDisplayTerms" %><%@
page import="com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearch" %><%@
page import="com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchEntry" %>

<%@ page import="java.util.Map" %>

<%@ page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
WorkflowDefinitionLinkDisplayContext workflowDefinitionLinkDisplayContext = (WorkflowDefinitionLinkDisplayContext)renderRequest.getAttribute(WorkflowWebKeys.WORKFLOW_DEFINITION_LINK_DISPLAY_CONTEXT);
%>

<%@ include file="/definition_link/init-ext.jsp" %>