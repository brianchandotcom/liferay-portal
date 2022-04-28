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

package com.liferay.remote.app.web.internal.frontend.data.set.model;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.model.RemoteAppEntry;

import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class RemoteAppFDSEntry {

	public RemoteAppFDSEntry(RemoteAppEntry remoteAppEntry, Locale locale) {
		_remoteAppEntry = remoteAppEntry;
		_locale = locale;
	}

	public String getName() {
		return _remoteAppEntry.getName(_locale);
	}

	public long getRemoteAppEntryId() {
		return _remoteAppEntry.getRemoteAppEntryId();
	}

	public StatusInfo getStatus() {
		String label = WorkflowConstants.getStatusLabel(
			_remoteAppEntry.getStatus());

		return new StatusInfo(label, LanguageUtil.get(_locale, label));
	}

	public String getType() {
		String type = _remoteAppEntry.getType();

		if (type.equals(RemoteAppConstants.TYPE_CUSTOM_ELEMENT)) {
			return LanguageUtil.get(_locale, "custom-element");
		}
		else if (type.equals(RemoteAppConstants.TYPE_IFRAME)) {
			return LanguageUtil.get(_locale, "iframe");
		}

		return type;
	}

	private final Locale _locale;
	private final RemoteAppEntry _remoteAppEntry;

}