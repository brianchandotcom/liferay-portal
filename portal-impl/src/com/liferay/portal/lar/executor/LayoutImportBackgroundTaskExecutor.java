/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar.executor;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portlet.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portlet.backgroundtask.model.BTEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class LayoutImportBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	@Override
	protected void doExecute(BTEntry btEntry) throws Exception {
		Map<String, Serializable> taskContextMap = btEntry.getTaskContextMap();

		long groupId = (Long)taskContextMap.get("groupId");
		boolean privateLayout = (Boolean)taskContextMap.get("privateLayout");

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)taskContextMap.get("parameterMap");

		List<FileEntry> btEntryAttachments =
			btEntry.getAttachmentsFileEntries();

		for (FileEntry btEntryAttachment : btEntryAttachments) {
			LayoutServiceUtil.importLayouts(
				groupId, privateLayout, parameterMap,
				btEntryAttachment.getContentStream());
		}
	}

}