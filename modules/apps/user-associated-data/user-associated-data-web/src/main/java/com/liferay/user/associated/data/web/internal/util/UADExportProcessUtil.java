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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
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