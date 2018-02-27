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
BackgroundTask backgroundTask = (BackgroundTask)request.getAttribute("liferay-staging:process-title:backgroundTask");
boolean listView = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-title:listView"));

BackgroundTaskDisplay backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask);

String backgroundTaskName = backgroundTaskDisplay.getDisplayName(request);

boolean processPrivateLayout = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "privateLayout");

String publicPagesDescription = (processPrivateLayout) ? LanguageUtil.get(request, "private-pages") : LanguageUtil.get(request, "public-pages");

backgroundTaskName = String.format("%s (%s)", backgroundTaskName, publicPagesDescription);

String domId = liferayPortletResponse.getNamespace() + "backgroundTaskName" + String.valueOf(backgroundTask.getBackgroundTaskId());
%>