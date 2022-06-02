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
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CETThemeFavicon;
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

	public CETItemDescriptor(CET cet) {
		_cet = cet;
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
		return null;
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"clientExtensionEntryId", String.valueOf(_cet.getPrimaryKey())
		).put(
			"name", _cet.getName(LocaleUtil.getMostRelevantLocale())
		).put(
			"type", _cet.getType()
		).put(
			"url",
			() -> {
				if (Objects.equals(
						_cet.getType(),
						ClientExtensionEntryConstants.TYPE_THEME_FAVICON)) {

					CETThemeFavicon cetThemeFavicon = (CETThemeFavicon)_cet;

					return cetThemeFavicon.getURL();
				}

				return null;
			}
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return _cet.getType();
	}

	@Override
	public String getTitle(Locale locale) {
		return _cet.getName(locale);
	}

	@Override
	public long getUserId() {
		return 0;
	}

	@Override
	public String getUserName() {
		return null;
	}

	@Override
	public boolean isCompact() {
		return true;
	}

	private final CET _cet;

}