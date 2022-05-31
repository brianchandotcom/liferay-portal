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

package com.liferay.client.extension.item.selector.web.internal.item.selector;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Date;
import java.util.Locale;

/**
 * @author Víctor Galán
 */
public class ClientExtensionItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public ClientExtensionItemDescriptor(
		ClientExtensionEntry clientExtensionEntry) {

		_clientExtensionEntry = clientExtensionEntry;
	}

	@Override
	public String getIcon() {
		return "api-web";
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return _clientExtensionEntry.getModifiedDate();
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"clientExtensionEntryId",
			String.valueOf(_clientExtensionEntry.getClientExtensionEntryId())
		).put(
			"name",
			_clientExtensionEntry.getName(LocaleUtil.getMostRelevantLocale())
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return _clientExtensionEntry.getType();
	}

	@Override
	public String getTitle(Locale locale) {
		return _clientExtensionEntry.getName(locale);
	}

	@Override
	public long getUserId() {
		return _clientExtensionEntry.getUserId();
	}

	@Override
	public String getUserName() {
		return _clientExtensionEntry.getUserName();
	}

	@Override
	public boolean isCompact() {
		return true;
	}

	private final ClientExtensionEntry _clientExtensionEntry;

}