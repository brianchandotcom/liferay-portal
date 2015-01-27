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
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class DLConfigurationDisplayContext {

	public DLConfigurationDisplayContext(
		HttpServletRequest request,
		DLPortletInstanceSettings dlPortletInstanceSettings) {

		_dlPortletInstanceSettings = dlPortletInstanceSettings;

		_dlActionsDisplayContext = new DLActionsDisplayContext(
			request, dlPortletInstanceSettings);
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
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
		String[] displayViews = _dlPortletInstanceSettings.getDisplayViews();

		_currentDisplayViews = new ArrayList<>();

		for (String displayView : displayViews) {
			_currentDisplayViews.add(
				new KeyValuePair(
					displayView,
					LanguageUtil.get(_themeDisplay.getLocale(), displayView)));
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
							_themeDisplay.getLocale(), displayView)));
			}
		}

		_availableDisplayViews = ListUtil.sort(
			_availableDisplayViews, new KeyValuePairComparator(false, true));
	}

	private void _populateEntryColumns() {
		String[] entryColumns = _dlPortletInstanceSettings.getEntryColumns();

		_currentEntryColumns = new ArrayList<>();

		for (String entryColumn : entryColumns) {
			_currentEntryColumns.add(
				new KeyValuePair(
					entryColumn,
					LanguageUtil.get(_themeDisplay.getLocale(), entryColumn)));
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
							_themeDisplay.getLocale(), entryColumn)));
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
	private final DLPortletInstanceSettings _dlPortletInstanceSettings;
	private final ThemeDisplay _themeDisplay;

}