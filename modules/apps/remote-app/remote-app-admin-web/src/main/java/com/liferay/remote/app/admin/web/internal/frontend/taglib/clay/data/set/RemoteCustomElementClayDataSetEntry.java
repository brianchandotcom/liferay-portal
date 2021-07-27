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

import com.liferay.remote.app.model.RemoteCustomElementEntry;

import java.util.Locale;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteCustomElementClayDataSetEntry {

	public RemoteCustomElementClayDataSetEntry(
		RemoteCustomElementEntry remoteCustomElementEntry, Locale locale) {

		_remoteCustomElementEntry = remoteCustomElementEntry;
		_locale = locale;
	}

	public String getName() {
		return _remoteCustomElementEntry.getName(_locale);
	}

	public long getRemoteCustomElementEntryId() {
		return _remoteCustomElementEntry.getRemoteCustomElementEntryId();
	}

	public String getTagName() {
		return _remoteCustomElementEntry.getTagName();
	}

	public String getURL() {
		return _remoteCustomElementEntry.getUrl();
	}

	private final Locale _locale;
	private final RemoteCustomElementEntry _remoteCustomElementEntry;

}