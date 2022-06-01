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

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CETThemeFavicon;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Víctor Galán
 */
public class CETItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public CETItemDescriptor(
		CETFactory cetFactory, ClientExtensionEntry clientExtensionEntry,
		String type) {

		_cetFactory = cetFactory;
		_clientExtensionEntry = clientExtensionEntry;
		_type = type;
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
		).put(
			"type", _type
		).put(
			"url",
			() -> {
				if (Objects.equals(
						_type,
						ClientExtensionEntryConstants.TYPE_THEME_FAVICON)) {

					CETThemeFavicon cetThemeFavicon =
						_cetFactory.cetThemeFavicon(_clientExtensionEntry);

					return cetThemeFavicon.getURL();
				}

				return null;
			}
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

	private final CETFactory _cetFactory;
	private final ClientExtensionEntry _clientExtensionEntry;
	private final String _type;

}