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

package com.liferay.remote.app.admin.web.internal.frontend.taglib.clay.data.set;

import com.liferay.remote.app.model.RemoteAppEntry;

import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class RemoteAppClayDataSetEntry {

	public RemoteAppClayDataSetEntry(
		RemoteAppEntry remoteAppEntry, Locale locale) {

		_remoteAppEntry = remoteAppEntry;
		_locale = locale;
	}

	public String getName() {
		return _remoteAppEntry.getName(_locale);
	}

	public long getRemoteAppEntryId() {
		return _remoteAppEntry.getRemoteAppEntryId();
	}

	public String getURL() {
		return _remoteAppEntry.getUrl();
	}

	private final Locale _locale;
	private final RemoteAppEntry _remoteAppEntry;

}