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
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLUtil" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.reports.engine.ReportDataSourceType" %><%@
page import="com.liferay.portal.reports.engine.ReportFormat" %><%@
page import="com.liferay.portal.reports.engine.console.configuration.ReportsGroupServiceEmailConfiguration" %><%@
page import="com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleConstants" %><%@
page import="com.liferay.portal.reports.engine.console.exception.DefinitionFileException" %><%@
page import="com.liferay.portal.reports.engine.console.exception.DefinitionNameException" %><%@
page import="com.liferay.portal.reports.engine.console.exception.EntryEmailDeliveryException" %><%@
page import="com.liferay.portal.reports.engine.console.exception.EntryEmailNotificationsException" %><%@
page import="com.liferay.portal.reports.engine.console.exception.SourceDriverClassNameException" %><%@
page import="com.liferay.portal.reports.engine.console.exception.SourceJDBCConnectionException" %><%@
page import="com.liferay.portal.reports.engine.console.exception.SourceTypeException" %><%@
page import="com.liferay.portal.reports.engine.console.model.Definition" %><%@
page import="com.liferay.portal.reports.engine.console.model.Entry" %><%@
page import="com.liferay.portal.reports.engine.console.model.Source" %><%@
page import="com.liferay.portal.reports.engine.console.service.DefinitionLocalServiceUtil" %><%@
page import="com.liferay.portal.reports.engine.console.service.DefinitionServiceUtil" %><%@
page import="com.liferay.portal.reports.engine.console.service.EntryLocalServiceUtil" %><%@
page import="com.liferay.portal.reports.engine.console.service.EntryServiceUtil" %><%@
page import="com.liferay.portal.reports.engine.console.service.SourceLocalServiceUtil" %><%@
page import="com.liferay.portal.reports.engine.console.service.SourceServiceUtil" %><%@
page import="com.liferay.portal.reports.engine.console.service.permission.AdminResourcePermissionChecker" %><%@
page import="com.liferay.portal.reports.engine.console.service.permission.DefinitionPermissionChecker" %><%@
page import="com.liferay.portal.reports.engine.console.service.permission.EntryPermissionChecker" %><%@
page import="com.liferay.portal.reports.engine.console.service.permission.ReportsActionKeys" %><%@
page import="com.liferay.portal.reports.engine.console.service.permission.SourcePermissionChecker" %><%@
page import="com.liferay.portal.reports.engine.console.status.ReportStatus" %><%@
page import="com.liferay.portal.reports.engine.console.util.ReportsEngineConsoleUtil" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.internal.constants.ReportsEngineWebKeys" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.internal.display.context.ReportsEngineDisplayContext" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.internal.display.context.util.ReportsEngineRequestHelper" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.internal.search.DefinitionDisplayTerms" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.internal.search.DefinitionSearch" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.internal.search.EntryDisplayTerms" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.internal.search.EntrySearch" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.internal.search.SourceDisplayTerms" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.internal.search.SourceSearch" %><%@
page import="com.liferay.portal.reports.engine.console.web.admin.util.EmailConfigurationUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.Calendar" %><%@
page import="java.util.Date" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

ReportsEngineDisplayContext reportsEngineDisplayContext = new ReportsEngineDisplayContext(liferayPortletRequest, liferayPortletResponse);

ReportsEngineRequestHelper reportsEngineRequestHelper = new ReportsEngineRequestHelper(request);

ReportsGroupServiceEmailConfiguration reportsGroupServiceEmailConfiguration = reportsEngineRequestHelper.getReportsGroupServiceEmailConfiguration();

boolean hasAddDefinitionPermission = AdminResourcePermissionChecker.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_DEFINITION);
boolean hasAddSourcePermission = AdminResourcePermissionChecker.contains(permissionChecker, scopeGroupId, ReportsActionKeys.ADD_SOURCE);
%>