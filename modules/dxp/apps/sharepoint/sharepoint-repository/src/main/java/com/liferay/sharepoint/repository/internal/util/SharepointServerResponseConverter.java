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

package com.liferay.sharepoint.repository.internal.util;

import com.liferay.document.library.repository.external.ExtRepository;
import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.document.library.repository.external.ExtRepositoryObjectType;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sharepoint.repository.internal.document.library.repository.external.model.SharepointFileEntry;
import com.liferay.sharepoint.repository.internal.document.library.repository.external.model.SharepointFolder;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SharepointServerResponseConverter {

	public SharepointServerResponseConverter(
		SharepointURLHelper sharepointURLHelper, ExtRepository extRepository,
		String siteAbsoluteURL) {

		_sharepointURLHelper = sharepointURLHelper;
		_extRepository = extRepository;
		_siteAbsoluteURL = siteAbsoluteURL;
	}

	public <T extends ExtRepositoryFileEntry & ExtRepositoryObject> T
		getExtRepositoryFileEntry(JSONObject jsonObject) {

		return (T)_createFileEntry(jsonObject.getJSONObject("d"));
	}

	public <T extends ExtRepositoryFolder & ExtRepositoryObject> T
		getExtRepositoryFolder(JSONObject jsonObject) {

		return _createFolder(jsonObject.getJSONObject("d"));
	}

	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
		ExtRepositoryObjectType extRepositoryObjectType,
		JSONObject jsonObject) {

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			return getExtRepositoryFileEntry(jsonObject);
		}

		return getExtRepositoryFolder(jsonObject);
	}

	private SharepointFileEntry _createFileEntry(JSONObject jsonObject) {
		String extRepositoryModelKey = jsonObject.getString(
			"ServerRelativeUrl");
		String name = jsonObject.getString("Name");

		String title = name;

		Date createDate = _parseDate(jsonObject.getString("TimeCreated"));
		Date modifiedDate = _parseDate(
			jsonObject.getString("TimeLastModified"));
		long size = jsonObject.getLong("Length");
		String fileVersionExtRepositoryModelKey =
			extRepositoryModelKey + StringPool.COLON +
				jsonObject.getString("UIVersion");
		String version = jsonObject.getString("UIVersionLabel");

		JSONObject authorJSONObject = jsonObject.getJSONObject("Author");

		String owner = authorJSONObject.getString("Title");

		JSONObject checkedOutByUserJSONObject = jsonObject.getJSONObject(
			"CheckedOutByUser");

		String checkedOutBy = StringPool.BLANK;

		if (!checkedOutByUserJSONObject.has("__deferred")) {
			checkedOutBy = checkedOutByUserJSONObject.getString("Title");
		}

		long effectiveBasePermissionsBits = _getEffectiveBasePermissionsBits(
			jsonObject);

		return new SharepointFileEntry(
			extRepositoryModelKey, name, title, createDate, modifiedDate, size,
			fileVersionExtRepositoryModelKey, version, owner, checkedOutBy,
			effectiveBasePermissionsBits, _sharepointURLHelper);
	}

	private <T extends ExtRepositoryFolder> T _createFolder(
		JSONObject jsonObject) {

		String extRepositoryModelKey = jsonObject.getString(
			"ServerRelativeUrl");
		String name = jsonObject.getString("Name");
		Date createDate = _parseDate(jsonObject.getString("TimeCreated"));
		Date modifiedDate = _parseDate(
			jsonObject.getString("TimeLastModified"));

		long effectiveBasePermissionsBits = _getEffectiveBasePermissionsBits(
			jsonObject);

		return (T)new SharepointFolder(
			extRepositoryModelKey, name, createDate, modifiedDate,
			effectiveBasePermissionsBits);
	}

	private long _getEffectiveBasePermissionsBits(JSONObject jsonObject) {
		JSONObject listAllItemFields = jsonObject.getJSONObject(
			"ListItemAllFields");

		if (listAllItemFields.has("__deferred")) {
			return 0;
		}

		JSONObject effectiveBasePermissions = listAllItemFields.getJSONObject(
			"EffectiveBasePermissions");

		long low = effectiveBasePermissions.getLong("Low");
		long high = effectiveBasePermissions.getLong("High");

		return low | (high << 32);
	}

	private Date _parseDate(String dateString) {
		try {
			DateFormat simpleDateFormat =
				DateFormatFactoryUtil.getSimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");

			return simpleDateFormat.parse(dateString);
		}
		catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
	}

	private final ExtRepository _extRepository;
	private final SharepointURLHelper _sharepointURLHelper;
	private final String _siteAbsoluteURL;

}