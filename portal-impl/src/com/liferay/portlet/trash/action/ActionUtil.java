/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.TrashEntryConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;

import javax.portlet.ActionRequest;

/**
 * @author Eudaldo Alonso
 */
public class ActionUtil {

	public static JSONObject checkEntry(ActionRequest actionRequest)
		throws Exception {

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		String newName = ParamUtil.getString(actionRequest, "newName");

		TrashEntry entry = TrashEntryLocalServiceUtil.getTrashEntry(
			trashEntryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			trashHandler.checkDuplicateTrashEntry(
				entry, TrashEntryConstants.DEFAULT_CONTAINER_ID, newName);

			jsonObject.put("success", true);
		}
		catch (DuplicateEntryException dee) {
			jsonObject.put("duplicateEntryId", dee.getDuplicateEntryId());
			jsonObject.put("oldName", dee.getOldName());
			jsonObject.put("success", false);
			jsonObject.put("trashEntryId", dee.getTrashEntryId());
		}

		return jsonObject;
	}

}