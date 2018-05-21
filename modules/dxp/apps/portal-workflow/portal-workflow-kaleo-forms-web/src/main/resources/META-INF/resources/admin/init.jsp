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

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.dynamic.data.lists.exception.RecordSetDDMStructureIdException" %><%@
page import="com.liferay.dynamic.data.lists.exception.RecordSetNameException" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecordConstants" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecordVersion" %><%@
page import="com.liferay.dynamic.data.lists.service.DDLRecordServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.constants.DDMPortletKeys" %><%@
page import="com.liferay.dynamic.data.mapping.exception.RequiredStructureException" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormField" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMTemplateConstants" %><%@
page import="com.liferay.dynamic.data.mapping.model.LocalizedValue" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.util.DDMDisplay" %><%@
page import="com.liferay.dynamic.data.mapping.util.DDMUtil" %><%@
page import="com.liferay.petra.string.CharPool" %><%@
page import="com.liferay.portal.kernel.dao.search.DisplayTerms" %><%@
page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker" %><%@
page import="com.liferay.portal.kernel.exception.PortalException" %><%@
page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.search.BaseModelSearchResult" %><%@
page import="com.liferay.portal.kernel.search.Field" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinition" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowTask" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil" %><%@
page import="com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.exception.KaleoProcessDDMTemplateIdException" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPair" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessServiceUtil" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.service.permission.KaleoFormsPermission" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.web.internal.display.context.KaleoFormsAdminDisplayContext" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.web.internal.display.context.KaleoFormsViewRecordsDisplayContext" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.web.internal.search.KaleoProcessSearch" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.web.internal.security.permission.resource.DDMStructurePermission" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.web.internal.util.KaleoFormsUtil" %><%@
page import="com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.WindowState" %>

<%
String refererPortletName = ParamUtil.getString(request, "refererPortletName", portletName);
String refererWebDAVToken = ParamUtil.getString(request, "refererWebDAVToken", portletConfig.getInitParameter("refererWebDAVToken"));
String scopeTitle = ParamUtil.getString(request, "scopeTitle");
boolean showGlobalScope = ParamUtil.getBoolean(request, "showGlobalScope");
boolean showManageTemplates = ParamUtil.getBoolean(request, "showManageTemplates", true);
boolean showToolbar = ParamUtil.getBoolean(request, "showToolbar", true);

KaleoFormsAdminDisplayContext kaleoFormsAdminDisplayContext = (KaleoFormsAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DDMDisplay ddmDisplay = kaleoFormsAdminDisplayContext.getDDMDisplay();

long scopeClassNameId = PortalUtil.getClassNameId(ddmDisplay.getStructureType());

String scopeTemplateType = ddmDisplay.getTemplateType();

String templateTypeValue = StringPool.BLANK;

if (scopeTemplateType.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) {
	templateTypeValue = DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY;
}
else if (scopeTemplateType.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM)) {
	templateTypeValue = DDMTemplateConstants.TEMPLATE_TYPE_FORM;
}
%>