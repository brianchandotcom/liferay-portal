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

package com.liferay.trash.web.internal.util;

import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;

/**
 * @author Eudaldo Alonso
 */
public class TrashUndoUtil {

	public static void addRestoreData(
			ActionRequest actionRequest,
			List<ObjectValuePair<String, Long>> entries)
		throws Exception {

		if ((entries == null) || entries.isEmpty()) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<String> restoreClassNames = new ArrayList<>();
		List<String> restoreEntryLinks = new ArrayList<>();
		List<String> restoreEntryMessages = new ArrayList<>();
		List<String> restoreLinks = new ArrayList<>();
		List<String> restoreMessages = new ArrayList<>();

		for (ObjectValuePair<String, Long> entry : entries) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(entry.getKey());

			String restoreLink = trashHandler.getRestoreContainerModelLink(
				actionRequest, entry.getValue());
			String restoreMessage = trashHandler.getRestoreMessage(
				actionRequest, entry.getValue());

			if (Validator.isNull(restoreLink) ||
				Validator.isNull(restoreMessage)) {

				continue;
			}

			restoreClassNames.add(trashHandler.getClassName());

			String restoreEntryLink = trashHandler.getRestoreContainedModelLink(
				actionRequest, entry.getValue());

			restoreEntryLinks.add(restoreEntryLink);

			TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
				entry.getValue());

			String restoreEntryTitle = trashRenderer.getTitle(
				themeDisplay.getLocale());

			restoreEntryMessages.add(restoreEntryTitle);

			restoreLinks.add(restoreLink);
			restoreMessages.add(restoreMessage);
		}

		Map<String, List<String>> data = new HashMap<>();

		data.put("restoreClassNames", restoreClassNames);
		data.put("restoreEntryLinks", restoreEntryLinks);
		data.put("restoreEntryMessages", restoreEntryMessages);
		data.put("restoreLinks", restoreLinks);
		data.put("restoreMessages", restoreMessages);

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA,
			data);
	}

	public static void addRestoreData(
			ActionRequest actionRequest, String className, long classPK)
		throws Exception {

		List<ObjectValuePair<String, Long>> entries = new ArrayList<>();

		ObjectValuePair<String, Long> objectValuePair = new ObjectValuePair<>(
			className, classPK);

		entries.add(objectValuePair);

		addRestoreData(actionRequest, entries);
	}

}