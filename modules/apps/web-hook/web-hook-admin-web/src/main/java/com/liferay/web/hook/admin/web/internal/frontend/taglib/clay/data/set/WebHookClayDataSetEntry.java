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

package com.liferay.web.hook.admin.web.internal.frontend.taglib.clay.data.set;

import com.liferay.web.hook.model.WebHookEntry;

import java.util.Locale;

/**
 * @author Eduardo García
 */
public class WebHookClayDataSetEntry {

	public WebHookClayDataSetEntry(WebHookEntry webHookEntry, Locale locale) {
		_webHookEntry = webHookEntry;
		_locale = locale;
	}

	public String getDestination() {
		return _webHookEntry.getDestination();
	}

	public String getName() {
		return _webHookEntry.getName(_locale);
	}

	public String getURL() {
		return _webHookEntry.getUrl();
	}

	public long getWebHookEntryId() {
		return _webHookEntry.getWebHookEntryId();
	}

	private final Locale _locale;
	private final WebHookEntry _webHookEntry;

}