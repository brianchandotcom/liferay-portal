/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item.action.exception;

/**
 * @author Stefan Tanasie
 */
public class ContentDashboardItemVersionActionException extends Exception {

	public ContentDashboardItemVersionActionException(String msg) {
		super(msg);
	}

	public ContentDashboardItemVersionActionException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public ContentDashboardItemVersionActionException(Throwable throwable) {
		super(throwable);
	}

}