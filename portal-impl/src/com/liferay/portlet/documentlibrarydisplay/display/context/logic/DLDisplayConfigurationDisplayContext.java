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

package com.liferay.portlet.documentlibrarydisplay.display.context.logic;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.display.context.DLActionsDisplayContext;
import com.liferay.portlet.documentlibrarydisplay.display.context.util.DLDisplayRequestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public class DLDisplayConfigurationDisplayContext {

	public DLDisplayConfigurationDisplayContext(
		DLDisplayRequestHelper dlDisplayRequestHelper) {

		_dlDisplayRequestHelper = dlDisplayRequestHelper;
		_dlActionsDisplayContext = new DLActionsDisplayContext(
			dlDisplayRequestHelper);
	}

	public List<KeyValuePair> getAvailableFileEntryColumns() {
		if (_availableFileEntryColumns == null) {
			_populateFileEntryColumns();
		}

		return _availableFileEntryColumns;
	}

	public List<KeyValuePair> getAvailableFolderColumns() {
		if (_availableFolderColumns == null) {
			_populateFolderColumns();
		}

		return _availableFolderColumns;
	}

	public List<KeyValuePair> getCurrentFileEntryColumns() {
		if (_currentFileEntryColumns == null) {
			_populateFileEntryColumns();
		}

		return _currentFileEntryColumns;
	}

	public List<KeyValuePair> getCurrentFolderColumns() {
		if (_currentFolderColumns == null) {
			_populateFolderColumns();
		}

		return _currentFolderColumns;
	}

	public DLActionsDisplayContext getDLActionsDisplayContext() {
		return _dlActionsDisplayContext;
	}

	private String[] _getAllFileEntryColumns() {
		String allFileEntryColumns = "name,size";

		if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
			allFileEntryColumns += ",downloads";
		}

		allFileEntryColumns += ",locked";

		if (_dlActionsDisplayContext.isShowActions()) {
			allFileEntryColumns += ",action";
		}

		return StringUtil.split(allFileEntryColumns);
	}

	private String[] _getAllFolderColumns() {
		String allFolderColumns = "name,num-of-folders,num-of-documents";

		if (_dlActionsDisplayContext.isShowActions()) {
			allFolderColumns += ",action";
		}

		return StringUtil.split(allFolderColumns);
	}

	private void _populateFileEntryColumns() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlDisplayRequestHelper.getDLPortletInstanceSettings();

		String[] fileEntryColumns =
			dlPortletInstanceSettings.getFileEntryColumns();

		_currentFileEntryColumns = new ArrayList<>();

		for (String fileEntryColumn : fileEntryColumns) {
			_currentFileEntryColumns.add(
				new KeyValuePair(
					fileEntryColumn,
					LanguageUtil.get(
						_dlDisplayRequestHelper.getLocale(), fileEntryColumn)));
		}

		_availableFileEntryColumns = new ArrayList<>();

		Arrays.sort(fileEntryColumns);

		Set<String> allFileEntryColumns = SetUtil.fromArray(
			_getAllFileEntryColumns());

		for (String fileEntryColumn : allFileEntryColumns) {
			if (Arrays.binarySearch(fileEntryColumns, fileEntryColumn) < 0) {
				_availableFileEntryColumns.add(
					new KeyValuePair(
						fileEntryColumn,
						LanguageUtil.get(
							_dlDisplayRequestHelper.getLocale(),
							fileEntryColumn)));
			}
		}

		_availableFileEntryColumns = ListUtil.sort(
			_availableFileEntryColumns,
			new KeyValuePairComparator(false, true));
	}

	private void _populateFolderColumns() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlDisplayRequestHelper.getDLPortletInstanceSettings();

		String[] folderColumns = dlPortletInstanceSettings.getFolderColumns();

		_currentFolderColumns = new ArrayList<>();

		for (String folderColumn : folderColumns) {
			_currentFolderColumns.add(
				new KeyValuePair(
					folderColumn,
					LanguageUtil.get(
						_dlDisplayRequestHelper.getLocale(), folderColumn)));
		}

		_availableFolderColumns = new ArrayList<>();

		Arrays.sort(folderColumns);

		Set<String> allFolderColumns = SetUtil.fromArray(
			_getAllFolderColumns());

		for (String folderColumn : allFolderColumns) {
			if (Arrays.binarySearch(folderColumns, folderColumn) < 0) {
				_availableFolderColumns.add(
					new KeyValuePair(
						folderColumn,
						LanguageUtil.get(
							_dlDisplayRequestHelper.getLocale(),
							folderColumn)));
			}
		}

		_availableFolderColumns = ListUtil.sort(
			_availableFolderColumns, new KeyValuePairComparator(false, true));
	}

	private List<KeyValuePair> _availableFileEntryColumns;
	private List<KeyValuePair> _availableFolderColumns;
	private List<KeyValuePair> _currentFileEntryColumns;
	private List<KeyValuePair> _currentFolderColumns;
	private final DLActionsDisplayContext _dlActionsDisplayContext;
	private final DLDisplayRequestHelper _dlDisplayRequestHelper;

}