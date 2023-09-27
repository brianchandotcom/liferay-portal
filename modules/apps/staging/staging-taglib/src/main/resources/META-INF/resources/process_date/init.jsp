<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Date date = (Date)request.getAttribute("liferay-staging:process-date:date");
String labelKey = GetterUtil.getString(request.getAttribute("liferay-staging:process-date:labelKey"));
boolean listView = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-date:listView"));

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(FastDateFormatConstants.MEDIUM, FastDateFormatConstants.SHORT, locale, timeZone);
%>