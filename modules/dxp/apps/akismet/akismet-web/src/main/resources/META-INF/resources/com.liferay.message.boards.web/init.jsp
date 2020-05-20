<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.akismet.internal.security.permission.MBResourcePermission" %><%@
page import="com.liferay.message.boards.constants.MBCategoryConstants" %><%@
page import="com.liferay.message.boards.model.MBCategory" %><%@
page import="com.liferay.message.boards.model.MBMessage" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %>

<%
MBMessage message = (MBMessage)request.getAttribute("edit_message.jsp-message");

boolean spam = false;

if (message.getStatus() == WorkflowConstants.STATUS_DENIED) {
	spam = true;
}

String currentURL = PortalUtil.getCurrentURL(request);
%>

<liferay-theme:defineObjects />

<portlet:defineObjects />