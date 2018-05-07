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