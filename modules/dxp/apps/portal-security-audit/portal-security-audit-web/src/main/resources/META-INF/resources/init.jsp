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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.lang.ClassResolverUtil" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.MethodKey" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalClassInvoker" %><%@
page import="com.liferay.portal.kernel.util.PortalClassLoaderUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.security.audit.AuditEvent" %><%@
page import="com.liferay.portal.security.audit.web.internal.AuditEventManagerUtil" %><%@
page import="com.liferay.portal.security.audit.web.internal.display.context.AuditDisplayContext" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.Calendar" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Calendar today = CalendarFactoryUtil.getCalendar(timeZone, locale);

today.add(Calendar.MINUTE, 1);

Calendar yesterday = CalendarFactoryUtil.getCalendar(timeZone, locale);

yesterday.add(Calendar.DATE, -1);

String className = ParamUtil.getString(request, "className");
String classPK = ParamUtil.getString(request, "classPK");
String clientHost = ParamUtil.getString(request, "clientHost");
String clientIP = ParamUtil.getString(request, "clientIP");
String eventType = ParamUtil.getString(request, "eventType");
String serverName = ParamUtil.getString(request, "serverName");
int serverPort = ParamUtil.getInteger(request, "serverPort");
long groupId = ParamUtil.getLong(request, "groupId");
long userId = ParamUtil.getLong(request, "userId");
String userName = ParamUtil.getString(request, "userName");

int endDateAmPm = ParamUtil.getInteger(request, "endDateAmPm", today.get(Calendar.AM_PM));
int endDateDay = ParamUtil.getInteger(request, "endDateDay", today.get(Calendar.DATE));
int endDateHour = ParamUtil.getInteger(request, "endDateHour", today.get(Calendar.HOUR));
int endDateMinute = ParamUtil.getInteger(request, "endDateMinute", today.get(Calendar.MINUTE));
int endDateMonth = ParamUtil.getInteger(request, "endDateMonth", today.get(Calendar.MONTH));
int endDateYear = ParamUtil.getInteger(request, "endDateYear", today.get(Calendar.YEAR));

int startDateAmPm = ParamUtil.getInteger(request, "startDateAmPm", yesterday.get(Calendar.AM_PM));
int startDateDay = ParamUtil.getInteger(request, "startDateDay", yesterday.get(Calendar.DATE));
int startDateHour = ParamUtil.getInteger(request, "startDateHour", yesterday.get(Calendar.HOUR));
int startDateMinute = ParamUtil.getInteger(request, "startDateMinute", yesterday.get(Calendar.MINUTE));
int startDateMonth = ParamUtil.getInteger(request, "startDateMonth", yesterday.get(Calendar.MONTH));
int startDateYear = ParamUtil.getInteger(request, "startDateYear", yesterday.get(Calendar.YEAR));

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>