/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.search.Indexer;

import java.util.List;
import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayContext {

	public double getAvailableDiskSpace() {
		return _availableDiskSpace;
	}

	public double getCurrentDiskSpaceUsed() {
		return _currentDiskSpaceUsed;
	}

	public Map<String, List<Indexer<?>>> getIndexersMap() {
		return _indexersMap;
	}

	public List<String> getIndexReindexerClassNames() {
		return _indexReindexerClassNames;
	}

	public NavigationItemList getNavigationItemList() {
		return _navigationItemList;
	}

	public String getSelectedTab() {
		return _selectedTab;
	}

	public boolean isLowOnDiskSpace() {
		return _lowOnDiskSpace;
	}

	public void setAvailableDiskSpace(double availableDiskSpace) {
		_availableDiskSpace = availableDiskSpace;
	}

	public void setCurrentDiskSpaceUsed(double currentDiskSpaceUsed) {
		_currentDiskSpaceUsed = currentDiskSpaceUsed;
	}

	public void setIndexersMap(Map<String, List<Indexer<?>>> indexersMap) {
		_indexersMap = indexersMap;
	}

	public void setIndexReindexerClassNames(
		List<String> indexReindexerClassNames) {

		_indexReindexerClassNames = indexReindexerClassNames;
	}

	public void setIsLowOnDiskSpace(boolean lowOnDiskSpace) {
		_lowOnDiskSpace = lowOnDiskSpace;
	}

	public void setNavigationItemList(NavigationItemList navigationItemList) {
		_navigationItemList = navigationItemList;
	}

	public void setSelectedTab(String selectedTab) {
		_selectedTab = selectedTab;
	}

	private double _availableDiskSpace;
	private double _currentDiskSpaceUsed;
	private Map<String, List<Indexer<?>>> _indexersMap;
	private List<String> _indexReindexerClassNames;
	private boolean _lowOnDiskSpace;
	private NavigationItemList _navigationItemList;
	private String _selectedTab;

}