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

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.frontend.taglib.servlet.taglib.AddMenuItem" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.DateUtil" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.uuid.PortalUUIDUtil" %><%@
page import="com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinition" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinitionTitleException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowException" %><%@
page import="com.liferay.portal.workflow.exception.IncompleteWorkflowInstancesException" %><%@
page import="com.liferay.portal.workflow.web.internal.constants.WorkflowDefinitionConstants" %><%@
page import="com.liferay.portal.workflow.web.internal.dao.search.WorkflowDefinitionResultRowSplitter" %><%@
page import="com.liferay.portal.workflow.web.internal.display.context.WorkflowDefinitionDisplayContext" %><%@
page import="com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionDisplayTerms" %><%@
page import="com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionSearch" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
WorkflowDefinitionDisplayContext workflowDefinitionDisplayContext = (WorkflowDefinitionDisplayContext)renderRequest.getAttribute(WorkflowWebKeys.WORKFLOW_DEFINITION_DISPLAY_CONTEXT);

Format dateFormatTime = null;

if (DateUtil.isFormatAmPm(locale)) {
	dateFormatTime = FastDateFormatFactoryUtil.getSimpleDateFormat(LanguageUtil.get(request, "mmm-d-yyyy-hh-mm-a"), locale, timeZone);
}
else {
	dateFormatTime = FastDateFormatFactoryUtil.getSimpleDateFormat(LanguageUtil.get(request, "mmm-d-yyyy-hh-mm"), locale, timeZone);
}
%>

<%@ include file="/definition/init-ext.jsp" %>

<aui:script use="liferay-workflow-web">
	window.<portlet:namespace/>confirmDeleteDefinition = function(deleteURL) {
		var message =
			'<%= LanguageUtil.get(request, "a-deleted-workflow-cannot-be-recovered") %>';
		var title = '<%= LanguageUtil.get(request, "delete-workflow-question") %>';

		Liferay.WorkflowWeb.openConfirmDeleteDialog(title, message, deleteURL);

		return false;
	};
</aui:script>