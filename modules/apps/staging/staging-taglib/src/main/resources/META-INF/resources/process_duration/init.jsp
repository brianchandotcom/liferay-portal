<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
BackgroundTask backgroundTask = (BackgroundTask)request.getAttribute("liferay-staging:process-duration:backgroundTask");
boolean listView = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-duration:listView"));

Date endDate = backgroundTask.getCompletionDate();
Date startDate = backgroundTask.getCreateDate();

long duration = 0;

if ((endDate != null) && (startDate != null) && (startDate.getTime() < endDate.getTime())) {
	duration = endDate.getTime() - startDate.getTime();
}

String timeDescription = LanguageUtil.getTimeDescription(themeDisplay.getLocale(), duration, true);
%>