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

package com.liferay.document.library.web.internal.display.context.logic;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public class DLPortletInstanceSettingsHelper {

	public DLPortletInstanceSettingsHelper(DLRequestHelper dlRequestHelper) {
		_dlRequestHelper = dlRequestHelper;
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

	public List<KeyValuePair> getAvailableMimeTypes() {
		if (_availableMimeTypes == null) {
			_populateMimeTypes();
		}

		return _availableMimeTypes;
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

	public List<KeyValuePair> getCurrentMimeTypes() {
		if (_currentMimeTypes == null) {
			_populateMimeTypes();
		}

		return _currentMimeTypes;
	}

	public String[] getEntryColumns() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		String[] entryColumns = dlPortletInstanceSettings.getEntryColumns();

		String portletName = _dlRequestHelper.getPortletName();

		if (!isShowActions()) {
			entryColumns = ArrayUtil.remove(entryColumns, "action");
		}
		else if (!portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) &&
				 !portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN) &&
				 !ArrayUtil.contains(entryColumns, "action")) {

			entryColumns = ArrayUtil.append(entryColumns, "action");
		}

		return entryColumns;
	}

	public boolean isShowActions() {
		String portletName = _dlRequestHelper.getPortletName();
		String portletResource = _dlRequestHelper.getPortletResource();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN) ||
			portletName.equals(PortletKeys.MY_WORKFLOW_TASK) ||
			portletResource.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN) ||
			portletResource.equals(PortletKeys.MY_WORKFLOW_TASK)) {

			return true;
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		return dlPortletInstanceSettings.isShowActions();
	}

	public boolean isShowSearch() {
		String portletName = _dlRequestHelper.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
			return true;
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		return dlPortletInstanceSettings.isShowFoldersSearch();
	}

	public boolean isShowTabs() {
		String portletName = _dlRequestHelper.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
			return true;
		}

		return false;
	}

	private String[] _getAllEntryColumns() {
		String allEntryColumns = "name,description,size,status";

		if (PropsValues.VIEW_COUNTS_ENABLED) {
			allEntryColumns += ",downloads";
		}

		if (isShowActions()) {
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
						_dlRequestHelper.getLocale(),
						_displayViews.get(displayView))));
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
							_dlRequestHelper.getLocale(),
							_displayViews.get(displayView))));
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

	private void _populateMimeTypes() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		String[] mediaGalleryMimeTypes =
			dlPortletInstanceSettings.getMimeTypes();

		Arrays.sort(mediaGalleryMimeTypes);

		ThemeDisplay themeDisplay = _dlRequestHelper.getThemeDisplay();

		_currentMimeTypes = new ArrayList<>();

		for (String mimeType : mediaGalleryMimeTypes) {
			_currentMimeTypes.add(
				new KeyValuePair(
					mimeType,
					LanguageUtil.get(themeDisplay.getLocale(), mimeType)));
		}

		_availableMimeTypes = new ArrayList<>();

		Set<String> allMediaGalleryMimeTypes =
			DLUtil.getAllMediaGalleryMimeTypes();

		for (String mimeType : allMediaGalleryMimeTypes) {
			if (Arrays.binarySearch(mediaGalleryMimeTypes, mimeType) < 0) {
				_availableMimeTypes.add(
					new KeyValuePair(
						mimeType,
						LanguageUtil.get(themeDisplay.getLocale(), mimeType)));
			}
		}
	}

	private static final Map<String, String> _displayViews = HashMapBuilder.put(
		"descriptive", "list"
	).put(
		"icon", "cards"
	).put(
		"list", "table"
	).build();

	private List<KeyValuePair> _availableDisplayViews;
	private List<KeyValuePair> _availableEntryColumns;
	private List<KeyValuePair> _availableMimeTypes;
	private List<KeyValuePair> _currentDisplayViews;
	private List<KeyValuePair> _currentEntryColumns;
	private List<KeyValuePair> _currentMimeTypes;
	private final DLRequestHelper _dlRequestHelper;

}