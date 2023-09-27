/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Pei-Jung Lan
 */
public class UADExportProcessUtil {

	public static String getApplicationKey(BackgroundTask backgroundTask) {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		return (String)taskContextMap.get("applicationKey");
	}

	public static FileEntry getFileEntry(BackgroundTask backgroundTask)
		throws PortalException {

		List<FileEntry> attachmentsFileEntries =
			backgroundTask.getAttachmentsFileEntries();

		if (ListUtil.isNotEmpty(attachmentsFileEntries)) {
			return attachmentsFileEntries.get(0);
		}

		return null;
	}

	public static String getStatusStyle(int status) {
		if (status == BackgroundTaskConstants.STATUS_FAILED) {
			return "danger";
		}
		else if (status == BackgroundTaskConstants.STATUS_IN_PROGRESS) {
			return "warning";
		}
		else if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			return "success";
		}

		return StringPool.BLANK;
	}

}