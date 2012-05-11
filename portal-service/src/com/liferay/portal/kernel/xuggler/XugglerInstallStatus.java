/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.xuggler;

import com.liferay.portal.kernel.progress.InstallStatus;
public class XugglerInstallStatus implements InstallStatus {

	public static final int COMPLETED = 2;

	public static final int COPYING = 1;

	public static final int DOWNLOADING = 0;

	public String getCurrentStatus() {
		int currentStatus = getStatus();

		if (currentStatus == 0) {
			return "downloading-xuggler";
		}
		else if (currentStatus == 1) {
			return "copying-xuggler";
		}
		else if (currentStatus == 2) {
			return "completed";
		}
		else {
			return "unknown";
		}
	}

	public void setStatus(int status) {
		_status = status;
	}

	private int getStatus() {
		return _status;
	}

	private int _status = UNKNOWN;

}