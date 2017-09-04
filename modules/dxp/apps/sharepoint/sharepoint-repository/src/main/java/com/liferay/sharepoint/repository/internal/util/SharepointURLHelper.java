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

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.document.library.repository.external.ExtRepositoryObjectType;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

/**
 * @author Adolfo Pérez
 */
public class SharepointURLHelper {

	public SharepointURLHelper(String siteAbsoluteUrl) {
		_siteAbsoluteUrl = siteAbsoluteUrl;
	}

	public String getAddFileURL(String extRepositoryFolderKey, String name) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')/Files/Add" +
				"(overwrite=true,url='%s')",
			_siteAbsoluteUrl, extRepositoryFolderKey, name);
	}

	public String getAddFolderURL(String extRepositoryFolderKey) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')/Folders",
			_siteAbsoluteUrl, extRepositoryFolderKey);
	}

	public String getCancelCheckedOutFileURL(String extRepositoryFileEntryKey) {
		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/UndoCheckOut",
			_siteAbsoluteUrl, extRepositoryFileEntryKey);
	}

	public String getCheckInFileURL(
		String extRepositoryFileEntryKey, boolean createMajorVersion,
		String changeLog) {

		int checkInType = 0;

		if (createMajorVersion) {
			checkInType = 1;
		}

		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/CheckIn(comment=" +
				"'%s',checkintype=%d)",
			_siteAbsoluteUrl, extRepositoryFileEntryKey, changeLog,
			checkInType);
	}

	public String getCheckOutFileURL(String extRepositoryFileEntryKey) {
		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/CheckOut",
			_siteAbsoluteUrl, extRepositoryFileEntryKey);
	}

	public <T extends ExtRepositoryObject> String getDeleteObjectURL(
		ExtRepositoryObjectType<T> extRepositoryObjectType,
		String extRepositoryObjectKey) {

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			return String.format(
				"%s/_api/web/GetFileByServerRelativeUrl('%s')",
				_siteAbsoluteUrl, extRepositoryObjectKey);
		}

		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')", _siteAbsoluteUrl,
			extRepositoryObjectKey);
	}

	public String getFileEntryContentURL(
		ExtRepositoryFileEntry extRepositoryFileEntry) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/OpenBinaryStream",
			_siteAbsoluteUrl,
			extRepositoryFileEntry.getExtRepositoryModelKey());
	}

	public <T extends ExtRepositoryObject> String getObjectURL(
		ExtRepositoryObjectType<T> extRepositoryObjectType,
		String extRepositoryObjectKey) {

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			return String.format(
				"%s/_api/web/GetFileByServerRelativeUrl('%s')?$select=%s&" +
					"$expand=%s",
				_siteAbsoluteUrl, extRepositoryObjectKey, _SELECTED_FILE_FIELDS,
				_EXPANDED_FILE_FIELDS);
		}

		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')?$select=%s&" +
				"$expand=%s",
			_siteAbsoluteUrl, extRepositoryObjectKey, _SELECTED_FOLDER_FIELDS,
			_EXPANDED_FOLDER_FIELDS);
	}

	private static final String _EXPANDED_FILE_FIELDS = StringUtil.merge(
		Arrays.asList("Author", "CheckedOutByUser", "ListItemAllFields"));

	private static final String _EXPANDED_FOLDER_FIELDS = "ListItemAllFields";

	private static final String _SELECTED_FILE_FIELDS = StringUtil.merge(
		Arrays.asList(
			"Author/Title", "CheckedOutByUser/Title", "Length",
			"ListItemAllFields/EffectiveBasePermissions", "Name",
			"ServerRelativeUrl", "TimeCreated", "TimeLastModified", "Title",
			"UIVersion", "UIVersionLabel"));

	private static final String _SELECTED_FOLDER_FIELDS = StringUtil.merge(
		Arrays.asList(
			"Name", "ListItemAllFields/EffectiveBasePermissions",
			"ServerRelativeUrl", "TimeCreated", "TimeLastModified"));

	private final String _siteAbsoluteUrl;

}