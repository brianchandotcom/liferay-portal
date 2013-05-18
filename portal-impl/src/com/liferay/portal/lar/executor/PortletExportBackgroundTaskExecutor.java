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

import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portlet.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portlet.backgroundtask.model.BTEntry;
import com.liferay.portlet.backgroundtask.service.BTEntryLocalServiceUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 */
public class PortletExportBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	@Override
	protected void doExecute(BTEntry btEntry) throws Exception {
		Map<String, Serializable> taskContextMap = btEntry.getTaskContextMap();

		long userId = (Long)taskContextMap.get("userId");
		long groupId = (Long)taskContextMap.get("groupId");
		long plid = (Long)taskContextMap.get("plid");
		String portletId = (String)taskContextMap.get("portletId");

		long[] layoutIds = (long[])taskContextMap.get("layoutIds");

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)taskContextMap.get("parameterMap");

		Date startDate = (Date)taskContextMap.get("startDate");
		Date endDate = (Date)taskContextMap.get("endDate");

		File larFile = LayoutServiceUtil.exportPortletInfoAsFile(
			plid, groupId, portletId, parameterMap, startDate, endDate);

		BTEntryLocalServiceUtil.addBTEntryAttachment(
			userId, btEntry.getBtEntryId(), larFile.getName(), larFile);
	}

}