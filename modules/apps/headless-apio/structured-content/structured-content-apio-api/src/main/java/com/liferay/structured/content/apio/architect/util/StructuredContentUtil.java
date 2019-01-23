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

package com.liferay.structured.content.apio.architect.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

import io.vavr.control.Try;

/**
 * Calculates the structured content's file entry.
 *
 * @author Javier Gamarra
 */
public class StructuredContentUtil {

	public static Long getFileEntryId(String value, DLAppService dlAppService) {
		if (!isJSONObject(value) || !value.contains("uuid")) {
			return 0L;
		}

		return Try.of(
			() -> JSONFactoryUtil.createJSONObject(value)
		).mapTry(
			jsonObject -> _getFileEntry(jsonObject, dlAppService)
		).map(
			FileEntry::getFileEntryId
		).getOrElse(
			0L
		);
	}

	public static boolean isJSONObject(String json) {
		try {
			if (json.startsWith("{") &&
				(JSONFactoryUtil.createJSONObject(json) != null)) {

				return true;
			}

			return false;
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", jsone);
			}

			return false;
		}
	}

	private static FileEntry _getFileEntry(
			JSONObject jsonObject, DLAppService dlAppService)
		throws PortalException {

		String uuid = jsonObject.getString("uuid");
		long groupId = jsonObject.getLong("groupId");

		return dlAppService.getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructuredContentUtil.class);

}