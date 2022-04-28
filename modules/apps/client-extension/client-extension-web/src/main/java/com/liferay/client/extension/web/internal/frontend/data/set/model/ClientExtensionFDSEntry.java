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

package com.liferay.client.extension.web.internal.frontend.data.set.model;

import com.liferay.client.extension.constants.ClientExtensionConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class RemoteAppFDSEntry {

	public RemoteAppFDSEntry(
		ClientExtensionEntry clientExtensionEntry, Locale locale) {

		_clientExtensionEntry = clientExtensionEntry;
		_locale = locale;
	}

	public long getClientExtensionEntryId() {
		return _clientExtensionEntry.getClientExtensionEntryId();
	}

	public String getName() {
		return _clientExtensionEntry.getName(_locale);
	}

	public StatusInfo getStatus() {
		String label = WorkflowConstants.getStatusLabel(
			_clientExtensionEntry.getStatus());

		return new StatusInfo(label, LanguageUtil.get(_locale, label));
	}

	public String getType() {
		String type = _clientExtensionEntry.getType();

		if (type.equals(ClientExtensionConstants.TYPE_CUSTOM_ELEMENT)) {
			return LanguageUtil.get(_locale, "custom-element");
		}
		else if (type.equals(ClientExtensionConstants.TYPE_IFRAME)) {
			return LanguageUtil.get(_locale, "iframe");
		}

		return type;
	}

	private final ClientExtensionEntry _clientExtensionEntry;
	private final Locale _locale;

}