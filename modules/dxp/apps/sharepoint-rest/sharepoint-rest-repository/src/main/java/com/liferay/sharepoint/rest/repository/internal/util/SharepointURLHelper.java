/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.rest.repository.internal.util;

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.document.library.repository.external.ExtRepositoryObjectType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

/**
 * @author Adolfo Pérez
 */
public class SharepointURLHelper {

	public SharepointURLHelper(String siteAbsoluteURL, String resultsSourceId) {
		_siteAbsoluteURL = siteAbsoluteURL;
		_resultsSourceId = resultsSourceId;
	}

	public String getAddFileURL(String extRepositoryFolderKey, String name) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativePath(decodedUrl='%s')/Files" +
				"/Add(overwrite=false,url='%s')",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFolderKey),
			HttpUtil.encodePath(name));
	}

	public String getAddFolderURL(String extRepositoryFolderKey) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativePath(decodedUrl='%s')" +
				"/Folders",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFolderKey));
	}

	public String getCancelCheckedOutFileURL(String extRepositoryFileEntryKey) {
		return String.format(
			"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')" +
				"/UndoCheckOut",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFileEntryKey));
	}

	public String getCheckInFileURL(
		String extRepositoryFileEntryKey, boolean createMajorVersion,
		String changeLog) {

		int checkInType = 0;

		if (createMajorVersion) {
			checkInType = 1;
		}

		return String.format(
			"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')" +
				"/CheckIn(comment='%s',checkintype=%d)",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFileEntryKey),
			changeLog, checkInType);
	}

	public String getCheckOutFileURL(String extRepositoryFileEntryKey) {
		return String.format(
			"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')/CheckOut",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFileEntryKey));
	}

	public String getCopyFileURL(
		String extRepositoryFileEntryKey, String newExtRepositoryFolderKey,
		String newTitle) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')" +
				"/CopyTo(strnewurl='%s',boverwrite=false)",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFileEntryKey),
			HttpUtil.encodePath(newExtRepositoryFolderKey) + StringPool.SLASH +
				HttpUtil.encodePath(newTitle));
	}

	public <T extends ExtRepositoryObject> String getDeleteObjectURL(
		ExtRepositoryObjectType<T> extRepositoryObjectType,
		String extRepositoryObjectKey) {

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			return String.format(
				"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')",
				_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryObjectKey));
		}

		return String.format(
			"%s/_api/web/GetFolderByServerRelativePath(decodedUrl='%s')",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryObjectKey));
	}

	public String getFileEntryContentURL(
		ExtRepositoryFileEntry extRepositoryFileEntry) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')" +
				"/OpenBinaryStream",
			_siteAbsoluteURL,
			HttpUtil.encodePath(
				extRepositoryFileEntry.getExtRepositoryModelKey()));
	}

	public String getFilesURL(String extRepositoryFolderKey) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativePath(decodedUrl='%s')" +
				"/Files?$select=%s&$expand=%s",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFolderKey),
			_FIELDS_SELECTED_FILE, _FIELDS_EXPANDED_FILE);
	}

	public String getFileVersionContentURL(
		ExtRepositoryFileEntry extRepositoryFileEntry, String versionId) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')" +
				"/Versions(%s)",
			_siteAbsoluteURL,
			HttpUtil.encodePath(
				extRepositoryFileEntry.getExtRepositoryModelKey()),
			versionId);
	}

	public String getFileVersionsURL(
		ExtRepositoryFileEntry extRepositoryFileEntry) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')/Versions",
			_siteAbsoluteURL,
			HttpUtil.encodePath(
				extRepositoryFileEntry.getExtRepositoryModelKey()));
	}

	public String getFoldersURL(String extRepositoryFolderKey) {
		return String.format(
			"%s/_api/web/GetFolderByServerRelativePath(decodedUrl='%s')" +
				"/Folders?$select=%s&$expand=%s",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFolderKey),
			_FIELDS_SELECTED_FOLDER, _FIELDS_EXPANDED_FOLDER);
	}

	public String getMoveFileURL(
		String extRepositoryFileEntryKey, String extRepositoryFolderKey,
		String title) {

		return String.format(
			"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')" +
				"/MoveTo(newurl='%s',flags=1)",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFileEntryKey),
			HttpUtil.encodePath(extRepositoryFolderKey) + StringPool.SLASH +
				HttpUtil.encodePath(title));
	}

	public <T extends ExtRepositoryObject> String getObjectsCountURL(
		ExtRepositoryObjectType<T> extRepositoryObjectType,
		String extRepositoryFolderKey) {

		if (extRepositoryObjectType == ExtRepositoryObjectType.OBJECT) {
			return String.format(
				"%s/_api/web/GetFolderByServerRelativePath(decodedUrl='%s')" +
					"/ItemCount",
				_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFolderKey));
		}

		if (extRepositoryObjectType == ExtRepositoryObjectType.FOLDER) {
			return String.format(
				"%s/_api/web/GetFolderByServerRelativePath(decodedUrl='%s')" +
					"/Folders?$select=ItemCount",
				_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFolderKey));
		}

		return String.format(
			"%s/_api/web/GetFolderByServerRelativePath(decodedUrl='%s')" +
				"/Files?$select=Level",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFolderKey));
	}

	public <T extends ExtRepositoryObject> String getObjectURL(
		ExtRepositoryObjectType<T> extRepositoryObjectType,
		String extRepositoryObjectKey) {

		if (extRepositoryObjectType == ExtRepositoryObjectType.FILE) {
			return String.format(
				"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')" +
					"?$select=%s&$expand=%s",
				_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryObjectKey),
				_FIELDS_SELECTED_FILE, _FIELDS_EXPANDED_FILE);
		}

		return String.format(
			"%s/_api/web/GetFolderByServerRelativePath(decodedUrl='%s')" +
				"?$select=%s&$expand=%s",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryObjectKey),
			_FIELDS_SELECTED_FOLDER, _FIELDS_EXPANDED_FOLDER);
	}

	public String getSearchURL(String queryText, int start, int end) {
		return String.format(
			"%s/_api/search/query?QueryText='%s'&SourceID='%s'&StartRow=%d&" +
				"RowsPerPage=%d",
			_siteAbsoluteURL, HtmlUtil.escapeURL(queryText), _resultsSourceId,
			start, end - start);
	}

	public String getUpdateFileURL(String extRepositoryFileEntryKey) {
		return String.format(
			"%s/_api/web/GetFileByServerRelativePath(decodedUrl='%s')/$value",
			_siteAbsoluteURL, HttpUtil.encodePath(extRepositoryFileEntryKey));
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

	private final String _resultsSourceId;
	private final String _siteAbsoluteURL;

}