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

package com.liferay.portlet.documentlibrary.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.display.context.util.DLRequestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public class DLConfigurationDisplayContext {

	public DLConfigurationDisplayContext(DLRequestHelper dlRequestHelper) {
		_dlRequestHelper = dlRequestHelper;
		_dlActionsDisplayContext = new DLActionsDisplayContext(dlRequestHelper);
	}

	public List<KeyValuePair> getAvailableDisplayViews() {
		if (_availableDisplayViews == null) {
			_populateDisplayViews();
		}

		return _availableDisplayViews;
	}

	public List<KeyValuePair> getAvailableEntryColumns() {
		if (_availableEntryColumns == null) {
			_populateEntryColumns();
		}

		return _availableEntryColumns;
	}

	public List<KeyValuePair> getCurrentDisplayViews() {
		if (_currentDisplayViews == null) {
			_populateDisplayViews();
		}

		return _currentDisplayViews;
	}

	public List<KeyValuePair> getCurrentEntryColumns() {
		if (_currentEntryColumns == null) {
			_populateEntryColumns();
		}

		return _currentEntryColumns;
	}

	public DLActionsDisplayContext getDLActionsDisplayContext() {
		return _dlActionsDisplayContext;
	}

	private String[] _getAllEntryColumns() {
		String allEntryColumns = "name,size,status";

		if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
			allEntryColumns += ",downloads";
		}

		if (_dlActionsDisplayContext.isShowActions()) {
			allEntryColumns += ",action";
		}

		allEntryColumns += ",modified-date,create-date";

		return StringUtil.split(allEntryColumns);
	}

	private void _populateDisplayViews() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		String[] displayViews = dlPortletInstanceSettings.getDisplayViews();

		_currentDisplayViews = new ArrayList<>();

		for (String displayView : displayViews) {
			_currentDisplayViews.add(
				new KeyValuePair(
					displayView,
					LanguageUtil.get(
						_dlRequestHelper.getLocale(), displayView)));
		}

		Arrays.sort(displayViews);

		_availableDisplayViews = new ArrayList<>();

		Set<String> allDisplayViews = SetUtil.fromArray(
			PropsValues.DL_DISPLAY_VIEWS);

		for (String displayView : allDisplayViews) {
			if (Arrays.binarySearch(displayViews, displayView) < 0) {
				_availableDisplayViews.add(
					new KeyValuePair(
						displayView,
						LanguageUtil.get(
							_dlRequestHelper.getLocale(), displayView)));
			}
		}

		_availableDisplayViews = ListUtil.sort(
			_availableDisplayViews, new KeyValuePairComparator(false, true));
	}

	private void _populateEntryColumns() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		String[] entryColumns = dlPortletInstanceSettings.getEntryColumns();

		_currentEntryColumns = new ArrayList<>();

		for (String entryColumn : entryColumns) {
			_currentEntryColumns.add(
				new KeyValuePair(
					entryColumn,
					LanguageUtil.get(
						_dlRequestHelper.getLocale(), entryColumn)));
		}

		Arrays.sort(entryColumns);

		_availableEntryColumns = new ArrayList<>();

		Set<String> allEntryColumns = SetUtil.fromArray(_getAllEntryColumns());

		for (String entryColumn : allEntryColumns) {
			if (Arrays.binarySearch(entryColumns, entryColumn) < 0) {
				_availableEntryColumns.add(
					new KeyValuePair(
						entryColumn,
						LanguageUtil.get(
							_dlRequestHelper.getLocale(), entryColumn)));
			}
		}

		_availableEntryColumns = ListUtil.sort(
			_availableEntryColumns, new KeyValuePairComparator(false, true));
	}

	private List<KeyValuePair> _availableDisplayViews;
	private List<KeyValuePair> _availableEntryColumns;
	private List<KeyValuePair> _currentDisplayViews;
	private List<KeyValuePair> _currentEntryColumns;
	private final DLActionsDisplayContext _dlActionsDisplayContext;
	private final DLRequestHelper _dlRequestHelper;

}