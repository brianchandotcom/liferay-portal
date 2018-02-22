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
BackgroundTask backgroundTask = (BackgroundTask)request.getAttribute("liferay-staging:process-in-progress:backgroundTask");
boolean listView = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-in-progress:listView"));

BackgroundTaskStatus backgroundTaskStatus = null;

if (backgroundTask.isInProgress()) {
	backgroundTaskStatus = BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(backgroundTask.getBackgroundTaskId());
}

String cmd = null;
String stagedModelName = null;
String stagedModelType = null;

long allProgressBarCountersTotal = 0;
int percentage = 100;

if (backgroundTaskStatus != null) {
	cmd = ArrayUtil.toString(ExportImportConfigurationHelper.getExportImportConfigurationParameter(backgroundTask, Constants.CMD), StringPool.BLANK);

	long allModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));
	long allPortletAdditionCounter = GetterUtil.getLong(backgroundTaskStatus.getAttribute("allPortletAdditionCounter"));
	long currentModelAdditionCountersTotal = GetterUtil.getLong(backgroundTaskStatus.getAttribute("currentModelAdditionCountersTotal"));
	long currentPortletAdditionCounter = GetterUtil.getLong(backgroundTaskStatus.getAttribute("currentPortletAdditionCounter"));

	allProgressBarCountersTotal = allModelAdditionCountersTotal + allPortletAdditionCounter;
	long currentProgressBarCountersTotal = currentModelAdditionCountersTotal + currentPortletAdditionCounter;

	if (allProgressBarCountersTotal > 0) {
		int base = 100;

		String phase = GetterUtil.getString(backgroundTaskStatus.getAttribute("phase"));

		if (phase.equals(Constants.EXPORT) && !Objects.equals(cmd, Constants.PUBLISH_TO_REMOTE)) {
			base = 50;
		}

		percentage = Math.round((float)currentProgressBarCountersTotal / allProgressBarCountersTotal * base);
	}

	stagedModelName = (String)backgroundTaskStatus.getAttribute("stagedModelName");
	stagedModelType = (String)backgroundTaskStatus.getAttribute("stagedModelType");
}
%>