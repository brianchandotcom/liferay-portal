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
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchRoleException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLUtil" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.servlet.MultiSessionMessages" %><%@
page import="com.liferay.portal.kernel.util.DateUtil" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.uuid.PortalUUIDUtil" %><%@
page import="com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinitionTitleException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowException" %><%@
page import="com.liferay.portal.workflow.constants.WorkflowWebKeys" %><%@
page import="com.liferay.portal.workflow.exception.IncompleteWorkflowInstancesException" %><%@
page import="com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException" %><%@
page import="com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys" %><%@
page import="com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDefinitionVersionConstants" %><%@
page import="com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys" %><%@
page import="com.liferay.portal.workflow.kaleo.designer.web.internal.dao.search.KaleoDefinitionVersionResultRowSplitter" %><%@
page import="com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDefinitionVersionPermission" %><%@
page import="com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context.KaleoDesignerDisplayContext" %><%@
page import="com.liferay.portal.workflow.kaleo.designer.web.internal.search.KaleoDefinitionVersionSearch" %><%@
page import="com.liferay.portal.workflow.kaleo.model.KaleoDefinition" %><%@
page import="com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion" %><%@
page import="com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalServiceUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.text.Format" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
KaleoDesignerDisplayContext kaleoDesignerDisplayContext = (KaleoDesignerDisplayContext)renderRequest.getAttribute(KaleoDesignerWebKeys.KALEO_DESIGNER_DISPLAY_CONTEXT);

kaleoDesignerDisplayContext.setKaleoDesignerRequestHelper(renderRequest);

Format dateFormatTime = null;

if (DateUtil.isFormatAmPm(locale)) {
	dateFormatTime = FastDateFormatFactoryUtil.getSimpleDateFormat(LanguageUtil.get(request, "mmm-d-yyyy-hh-mm-a"), locale, timeZone);
}
else {
	dateFormatTime = FastDateFormatFactoryUtil.getSimpleDateFormat(LanguageUtil.get(request, "mmm-d-yyyy-hh-mm"), locale, timeZone);
}
%>

<aui:script use="liferay-kaleo-designer-dialogs">
	window.<portlet:namespace/>confirmDeleteDefinition = function(deleteURL) {
		var message =
			'<%= LanguageUtil.get(request, "a-deleted-workflow-cannot-be-recovered") %>';
		var title = '<%= LanguageUtil.get(request, "delete-workflow-question") %>';

		Liferay.KaleoDesignerDialogs.openConfirmDeleteDialog(
			title,
			message,
			deleteURL
		);

		return false;
	};
</aui:script>