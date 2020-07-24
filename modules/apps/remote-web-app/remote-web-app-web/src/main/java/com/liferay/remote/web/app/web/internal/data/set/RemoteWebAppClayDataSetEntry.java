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

package com.liferay.remote.web.app.web.internal.data.set;

import com.liferay.remote.web.app.model.RemoteWebAppEntry;

import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class RemoteWebAppClayDataSetEntry {

	public RemoteWebAppClayDataSetEntry(
		RemoteWebAppEntry remoteWebAppEntry, Locale locale) {

		_remoteWebAppEntry = remoteWebAppEntry;
		_locale = locale;
	}

	public long getEntryId() {
		return _remoteWebAppEntry.getEntryId();
	}

	public String getName() {
		return _remoteWebAppEntry.getName(_locale);
	}

	public String getURL() {
		return _remoteWebAppEntry.getUrl();
	}

	private final Locale _locale;
	private final RemoteWebAppEntry _remoteWebAppEntry;

}