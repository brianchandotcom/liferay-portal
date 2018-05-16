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
BackgroundTask backgroundTask = (BackgroundTask)request.getAttribute("liferay-staging:process-list-menu:backgroundTask");
boolean deleteMenu = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-list-menu:deleteMenu"));
boolean localPublishing = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-list-menu:localPublishing"));
boolean relaunchMenu = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-list-menu:relaunchMenu"));
boolean summaryMenu = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-list-menu:summaryMenu"));

Date completionDate = backgroundTask.getCompletionDate();

String deleteLabel = LanguageUtil.get(request, ((completionDate != null) && completionDate.before(new Date())) ? "clear" : "cancel");
%>