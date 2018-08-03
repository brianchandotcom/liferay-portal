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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@
taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.calendar.model.CalendarBooking" %><%@
page import="com.liferay.calendar.service.CalendarBookingServiceUtil" %><%@
page import="com.liferay.petra.content.ContentUtil" %><%@
page import="com.liferay.petra.string.StringBundler" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.Field" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.powwow.model.PowwowMeeting" %><%@
page import="com.liferay.powwow.model.PowwowMeetingConstants" %><%@
page import="com.liferay.powwow.model.PowwowParticipant" %><%@
page import="com.liferay.powwow.model.PowwowParticipantConstants" %><%@
page import="com.liferay.powwow.model.PowwowServer" %><%@
page import="com.liferay.powwow.provider.PowwowServiceProviderUtil" %><%@
page import="com.liferay.powwow.service.PowwowMeetingLocalServiceUtil" %><%@
page import="com.liferay.powwow.service.PowwowParticipantLocalServiceUtil" %><%@
page import="com.liferay.powwow.service.PowwowServerLocalServiceUtil" %><%@
page import="com.liferay.powwow.service.permission.MeetingsPermission" %><%@
page import="com.liferay.powwow.service.permission.PowwowMeetingPermission" %><%@
page import="com.liferay.powwow.util.ActionKeys" %><%@
page import="com.liferay.powwow.util.PortletPropsValues" %><%@
page import="com.liferay.powwow.util.PowwowUtil" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Calendar" %><%@
page import="java.util.Date" %><%@
page import="java.util.Enumeration" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
PortletURL portletURL = renderResponse.createRenderURL();

String currentURL = PortalUtil.getCurrentURL(request);

Format longDateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("EEEE, MMMMM dd, yyyy 'at' h:mm a", locale, timeZone);
Format shortDateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("MM/dd/yy 'at' h:mm a", locale, timeZone);
%>