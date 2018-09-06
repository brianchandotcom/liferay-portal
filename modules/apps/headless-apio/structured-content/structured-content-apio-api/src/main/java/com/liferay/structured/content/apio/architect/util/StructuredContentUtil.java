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

import com.liferay.apio.architect.functional.Try;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * Utility class to calculate the fileEntry of the StructuredContent
 *
 * @author Javier Gamarra
 * @review
 */
public class StructuredContentUtil {

	public static Long getFileEntryId(String value, DLAppService dlAppService) {
		return Try.fromFallible(
			() -> value
		).filter(
			StructuredContentUtil::isJSONObject
		).filter(
			s -> s.contains("uuid")
		).map(
			JSONFactoryUtil::createJSONObject
		).map(
			jsonObject -> _getFileEntry(jsonObject, dlAppService)
		).map(
			FileEntry::getFileEntryId
		).orElse(
			null
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