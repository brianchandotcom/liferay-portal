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

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

		String name = _getName(_getResult(primaryKey));

		if (name == null) {
			return StringPool.BLANK;
		}

		return getRowCheckBox(
			httpServletRequest, checked, disabled,
			_liferayPortletResponse.getNamespace() + RowChecker.ROW_IDS + name,
			primaryKey, _getEntryRowIds(), "'#" + getAllRowIds() + "'",
			_liferayPortletResponse.getNamespace() + "toggleActionsButton();");
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest httpServletRequest, ResultRow resultRow) {

		Object result = resultRow.getObject();

		return getRowCheckBox(
			httpServletRequest, isChecked(result), isDisabled(result),
			resultRow.getPrimaryKey());
	}

	private String _getEntryRowIds() {
		return StringBundler.concat(
			"['", _liferayPortletResponse.getNamespace(), RowChecker.ROW_IDS,
			_SIMPLE_NAME_FOLDER, "', '", _liferayPortletResponse.getNamespace(),
			RowChecker.ROW_IDS, _SIMPLE_NAME_DL_FILE_SHORTCUT, "', '",
			_liferayPortletResponse.getNamespace(), RowChecker.ROW_IDS,
			_SIMPLE_NAME_FILE_ENTRY, "']");
	}

	private String _getName(Object result) {
		if (result instanceof FileEntry) {
			return _SIMPLE_NAME_FILE_ENTRY;
		}
		else if (result instanceof FileShortcut) {
			return _SIMPLE_NAME_DL_FILE_SHORTCUT;
		}
		else if (result instanceof Folder) {
			return _SIMPLE_NAME_FOLDER;
		}

		return null;
	}

	private Object _getResult(String primaryKey) {
		long entryId = GetterUtil.getLong(primaryKey);

		try {
			return DLAppServiceUtil.getFileEntry(entryId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		try {
			return DLAppServiceUtil.getFileShortcut(entryId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		try {
			return DLAppServiceUtil.getFolder(entryId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private static final String _SIMPLE_NAME_DL_FILE_SHORTCUT =
		DLFileShortcut.class.getSimpleName();

	private static final String _SIMPLE_NAME_FILE_ENTRY =
		FileEntry.class.getSimpleName();

	private static final String _SIMPLE_NAME_FOLDER =
		Folder.class.getSimpleName();

	private static final Log _log = LogFactoryUtil.getLog(EntriesChecker.class);

	private final LiferayPortletResponse _liferayPortletResponse;

}