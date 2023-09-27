<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
String shortenedStagedModelName = null;
String localizedStagedModelType = null;

long allProgressBarCountersTotal = 0;
int percentage = 100;

if (backgroundTaskStatus != null) {
	cmd = ArrayUtil.toString(ExportImportConfigurationUtil.getExportImportConfigurationParameter(backgroundTask, Constants.CMD), StringPool.BLANK);

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

	String stagedModelName = (String)backgroundTaskStatus.getAttribute("stagedModelName");

	shortenedStagedModelName = stagedModelName;

	if (Validator.isNotNull(stagedModelName) && (stagedModelName.length() > (20 + StringPool.TRIPLE_PERIOD.length()))) {
		shortenedStagedModelName = StringPool.TRIPLE_PERIOD + stagedModelName.substring(stagedModelName.length() - 20);
	}

	String stagedModelType = (String)backgroundTaskStatus.getAttribute("stagedModelType");

	if (Validator.isNotNull(stagedModelType)) {
		localizedStagedModelType = ResourceActionsUtil.getModelResource(locale, stagedModelType);
	}
}
%>