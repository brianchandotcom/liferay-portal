<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
BackgroundTask backgroundTask = (BackgroundTask)request.getAttribute("liferay-staging:process-title:backgroundTask");
boolean listView = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-title:listView"));

BackgroundTaskDisplay backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask);

String backgroundTaskName = backgroundTaskDisplay.getDisplayName(request);

if ((liveGroup != null) && GroupCapabilityUtil.isSupportsPages(liveGroup) && liveGroup.isPrivateLayoutsEnabled()) {
	boolean processPrivateLayout = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "privateLayout");

	String publicPagesDescription = processPrivateLayout ? LanguageUtil.get(request, "private-pages") : LanguageUtil.get(request, "public-pages");

	backgroundTaskName = String.format("%s (%s)", backgroundTaskName, publicPagesDescription);
}

String domId = liferayPortletResponse.getNamespace() + "backgroundTaskName" + String.valueOf(backgroundTask.getBackgroundTaskId());
%>