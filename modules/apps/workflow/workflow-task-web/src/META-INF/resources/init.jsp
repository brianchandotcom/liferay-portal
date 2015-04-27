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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.portal.kernel.workflow.WorkflowException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowHandler" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowHandlerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowInstance" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowLog" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowTask" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowTaskDueDateException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil" %><%@
page import="com.liferay.portal.util.WebKeys" %><%@ 
page import="java.io.Serializable" %><%@
page import="java.util.Map" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetRenderer" %><%@
page import="com.liferay.portlet.asset.model.AssetRendererFactory" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PortletKeys" %><%@
page import="com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse" %><%@ 
page import="java.util.ArrayList" %><%@ 
page import="java.util.List" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="java.text.Format" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.workflow.task.web.portlet.search.WorkflowTaskDisplayTerms" %><%@
page import="com.liferay.workflow.task.web.portlet.search.WorkflowTaskSearch" %><%@
page import="javax.portlet.WindowState" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="javax.portlet.PortletMode" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.model.Role" %><%@
page import="com.liferay.portal.service.RoleLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil" %>


<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

WindowState windowState = liferayPortletRequest.getWindowState();
%>

<%@ include file="/init-ext.jsp" %>

<%!
private boolean _hasOtherAssignees(long[] pooledActorsIds, WorkflowTask workflowTask, User user) {
	if (pooledActorsIds.length == 0) {
		return false;
	}

	if (workflowTask.isCompleted()) {
		return false;
	}

	if ((pooledActorsIds.length == 1) && (pooledActorsIds[0] == user.getUserId())) {
		return false;
	}

	return true;
}

private boolean _isAssignedToUser(WorkflowTask workflowTask, User user) {
	if (workflowTask.getAssigneeUserId() == user.getUserId()) {
		return true;
	}

	return false;
}
%>