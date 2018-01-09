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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

/**
 * @author Adolfo Pérez
 */
public class SharepointURLHelper {

	public SharepointURLHelper(String siteAbsoluteURL) {
		_siteAbsoluteURL = siteAbsoluteURL;
	}

	public String getAbsoluteURL(String relativeURL) {
		if (_siteAbsoluteURL.endsWith(StringPool.SLASH) ||
			relativeURL.startsWith(StringPool.SLASH)) {

			return _siteAbsoluteURL + relativeURL;
		}

		return _siteAbsoluteURL + StringPool.SLASH + relativeURL;
	}

	public String getAddFileURL(String extRepositoryFolderKey, String name) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')/Files/Add" +
				"(overwrite=true,url='%s')",
			_siteAbsoluteURL, extRepositoryFolderKey, name);
	}

	public String getAddFolderURL(String extRepositoryFolderKey) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')/Folders",
			_siteAbsoluteURL, extRepositoryFolderKey);
	}

	public String getCancelCheckedOutFileURL(String extRepositoryFileEntryKey) {
		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/UndoCheckOut",
			_siteAbsoluteURL, extRepositoryFileEntryKey);
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
			_siteAbsoluteURL, extRepositoryFileEntryKey, changeLog,
			checkInType);
	}

	public String getCheckOutFileURL(String extRepositoryFileEntryKey) {
		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/CheckOut",
			_siteAbsoluteURL, extRepositoryFileEntryKey);
	}

	public String getCopyFileURL(
		String extRepositoryFileEntryKey, String newExtRepositoryFolderKey,
		String newTitle) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/CopyTo(strnewurl=" +
				"'%s',boverwrite=false)",
			_siteAbsoluteURL, extRepositoryFileEntryKey,
			newExtRepositoryFolderKey + StringPool.SLASH + newTitle);
	}

	public <T extends ExtRepositoryObject> String getDeleteObjectURL(
		ExtRepositoryObjectType<T> extRepositoryObjectType,
		String extRepositoryObjectKey) {

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			return String.format(
				"%s/_api/web/GetFileByServerRelativeUrl('%s')",
				_siteAbsoluteURL, extRepositoryObjectKey);
		}

		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')", _siteAbsoluteURL,
			extRepositoryObjectKey);
	}

	public String getFileEntryContentURL(
		ExtRepositoryFileEntry extRepositoryFileEntry) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/OpenBinaryStream",
			_siteAbsoluteURL,
			extRepositoryFileEntry.getExtRepositoryModelKey());
	}

	public String getFilesURL(String extRepositoryFolderKey) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')/Files?$select=%s&" +
				"$expand=%s",
			_siteAbsoluteURL, extRepositoryFolderKey, _FIELDS_SELECTED_FILE,
			_FIELDS_EXPANDED_FILE);
	}

	public String getFileVersionsURL(
		ExtRepositoryFileEntry extRepositoryFileEntry) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/Versions",
			_siteAbsoluteURL,
			extRepositoryFileEntry.getExtRepositoryModelKey());
	}

	public String getFoldersURL(String extRepositoryFolderKey) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')/Folders?$select=" +
				"%s&$expand=%s",
			_siteAbsoluteURL, extRepositoryFolderKey, _FIELDS_SELECTED_FOLDER,
			_FIELDS_EXPANDED_FOLDER);
	}

	public String getMoveFileURL(
		String extRepositoryFileEntryKey, String extRepositoryFolderKey,
		String title) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/MoveTo(newurl='%s'," +
				"flags=1)",
			_siteAbsoluteURL, extRepositoryFileEntryKey,
			extRepositoryFolderKey + StringPool.SLASH + title);
	}

	public <T extends ExtRepositoryObject> String getObjectsCountURL(
		ExtRepositoryObjectType<T> extRepositoryObjectType,
		String extRepositoryFolderKey) {

		if (extRepositoryObjectType == ExtRepositoryObjectType.OBJECT) {
			return String.format(
				"%s/_api/web/GetFolderByServerRelativeUrl('%s')/ItemCount",
				_siteAbsoluteURL, extRepositoryFolderKey);
		}

		if (extRepositoryObjectType == ExtRepositoryObjectType.FOLDER) {
			return String.format(
				"%s/_api/web/GetFolderByServerRelativeUrl('%s')/Folders" +
					"?$select=ItemCount",
				_siteAbsoluteURL, extRepositoryFolderKey);
		}

		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')" +
				"/Files?$select=Level",
			_siteAbsoluteURL, extRepositoryFolderKey);
	}

	public <T extends ExtRepositoryObject> String getObjectURL(
		ExtRepositoryObjectType<T> extRepositoryObjectType,
		String extRepositoryObjectKey) {

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			return String.format(
				"%s/_api/web/GetFileByServerRelativeUrl('%s')?$select=%s&" +
					"$expand=%s",
				_siteAbsoluteURL, extRepositoryObjectKey, _FIELDS_SELECTED_FILE,
				_FIELDS_EXPANDED_FILE);
		}

		return String.format(
			"%s/_api/web/GetFolderByServerRelativeUrl('%s')?$select=%s&" +
				"$expand=%s",
			_siteAbsoluteURL, extRepositoryObjectKey, _FIELDS_SELECTED_FOLDER,
			_FIELDS_EXPANDED_FOLDER);
	}

	public String getSearchURL(String queryText, int start, int end) {
		return String.format(
			"%s/_api/search/query?QueryText='%s'&SourceID='%s'&StartRow=%d&" +
				"RowsPerPage=%d",
			_siteAbsoluteURL, HtmlUtil.escapeURL(queryText),
			_LOCAL_SHAREPOINT_RESULTS_SOURCE_ID, start, end - start);
	}

	public String getUpdateFileURL(String extRepositoryFileEntryKey) {
		return String.format(
			"%s/_api/web/GetFileByServerRelativeUrl('%s')/$value",
			_siteAbsoluteURL, extRepositoryFileEntryKey);
	}

	private static final String _FIELDS_EXPANDED_FILE = StringUtil.merge(
		Arrays.asList("Author", "CheckedOutByUser", "ListItemAllFields"));

	private static final String _FIELDS_EXPANDED_FOLDER = "ListItemAllFields";

	private static final String _FIELDS_SELECTED_FILE = StringUtil.merge(
		Arrays.asList(
			"Author/Title", "CheckedOutByUser/Title", "Length",
			"ListItemAllFields/EffectiveBasePermissions", "Name",
			"ServerRelativeUrl", "TimeCreated", "TimeLastModified", "Title",
			"UIVersion", "UIVersionLabel"));

	private static final String _FIELDS_SELECTED_FOLDER = StringUtil.merge(
		Arrays.asList(
			"Name", "ListItemAllFields/EffectiveBasePermissions",
			"ServerRelativeUrl", "TimeCreated", "TimeLastModified"));

	private static final String _LOCAL_SHAREPOINT_RESULTS_SOURCE_ID =
		"8413cd39-2156-4e00-b54d-11efd9abdb89";

	private final String _siteAbsoluteURL;

}