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

package com.liferay.document.library.web.internal.search;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileShortcutException;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class EntriesChecker extends EmptyOnClickRowChecker {

	public EntriesChecker(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletResponse);

		_liferayPortletResponse = liferayPortletResponse;
	}

	@Override
	public String getAllRowsCheckBox() {
		return null;
	}

	@Override
	public String getAllRowsCheckBox(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest httpServletRequest, boolean checked,
		boolean disabled, String primaryKey) {

		FileEntry fileEntry = null;
		FileShortcut fileShortcut = null;
		Folder folder = null;

		long entryId = GetterUtil.getLong(primaryKey);

		try {
			fileEntry = DLAppServiceUtil.getFileEntry(entryId);
		}
		catch (Exception e1) {
			if (e1 instanceof NoSuchFileEntryException ||
				e1 instanceof NoSuchRepositoryEntryException) {

				try {
					fileShortcut = DLAppServiceUtil.getFileShortcut(entryId);
				}
				catch (Exception e2) {
					if (e2 instanceof NoSuchFileShortcutException) {
						try {
							folder = DLAppServiceUtil.getFolder(entryId);
						}
						catch (Exception e3) {
							return StringPool.BLANK;
						}
					}
					else {
						return StringPool.BLANK;
					}
				}
			}
			else {
				return StringPool.BLANK;
			}
		}

		String name = null;

		if (fileEntry != null) {
			name = _SIMPLE_NAME_FILE_ENTRY;
		}
		else if (fileShortcut != null) {
			name = _SIMPLE_NAME_DL_FILE_SHORTCUT;
		}
		else if (folder != null) {
			name = _SIMPLE_NAME_FOLDER;
		}

		String checkBoxRowIds = getEntryRowIds();
		String checkBoxAllRowIds = "'#" + getAllRowIds() + "'";
		String checkBoxPostOnClick =
			_liferayPortletResponse.getNamespace() + "toggleActionsButton();";

		return getRowCheckBox(
			httpServletRequest, checked, disabled,
			_liferayPortletResponse.getNamespace() + RowChecker.ROW_IDS + name,
			primaryKey, checkBoxRowIds, checkBoxAllRowIds, checkBoxPostOnClick);
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest httpServletRequest, ResultRow resultRow) {

		Object result = resultRow.getObject();

		String name = null;

		if (result instanceof FileEntry) {
			name = _SIMPLE_NAME_FILE_ENTRY;
		}
		else if (result instanceof FileShortcut) {
			name = _SIMPLE_NAME_DL_FILE_SHORTCUT;
		}
		else if (result instanceof Folder) {
			name = _SIMPLE_NAME_FOLDER;
		}
		else {
			return StringPool.BLANK;
		}

		String checkBoxRowIds = getEntryRowIds();
		String checkBoxAllRowIds = "'#" + getAllRowIds() + "'";
		String checkBoxPostOnClick =
			_liferayPortletResponse.getNamespace() + "toggleActionsButton();";

		return getRowCheckBox(
			httpServletRequest, isChecked(result), isDisabled(result),
			_liferayPortletResponse.getNamespace() + RowChecker.ROW_IDS + name,
			resultRow.getPrimaryKey(), checkBoxRowIds, checkBoxAllRowIds,
			checkBoxPostOnClick);
	}

	protected String getEntryRowIds() {
		StringBundler sb = new StringBundler(13);

		sb.append("['");
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append(RowChecker.ROW_IDS);
		sb.append(_SIMPLE_NAME_FOLDER);
		sb.append("', '");
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append(RowChecker.ROW_IDS);
		sb.append(_SIMPLE_NAME_DL_FILE_SHORTCUT);
		sb.append("', '");
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append(RowChecker.ROW_IDS);
		sb.append(_SIMPLE_NAME_FILE_ENTRY);
		sb.append("']");

		return sb.toString();
	}

	private static final String _SIMPLE_NAME_DL_FILE_SHORTCUT =
		DLFileShortcut.class.getSimpleName();

	private static final String _SIMPLE_NAME_FILE_ENTRY =
		FileEntry.class.getSimpleName();

	private static final String _SIMPLE_NAME_FOLDER =
		Folder.class.getSimpleName();

	private final LiferayPortletResponse _liferayPortletResponse;

}