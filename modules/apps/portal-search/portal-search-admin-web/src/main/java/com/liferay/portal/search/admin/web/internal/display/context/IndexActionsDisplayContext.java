/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import java.util.Map;

/**
 * @author Olivia Yu
 */
public class IndexActionsDisplayContext {

	public double getAvailableDiskSpace() {
		return _availableDiskSpace;
	}

	public double getCurrentDiskSpaceUsed() {
		return _currentDiskSpaceUsed;
	}

	public Map<String, Object> getData() {
		return _data;
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

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setIsLowOnDiskSpace(boolean lowOnDiskSpace) {
		_lowOnDiskSpace = lowOnDiskSpace;
	}

	private double _availableDiskSpace;
	private double _currentDiskSpaceUsed;
	private Map<String, Object> _data;
	private boolean _lowOnDiskSpace;

}