/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.item.selector.web.internal.item.selector;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.ThemeFaviconCET;
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
			"cetExternalReferenceCode", _cet.getExternalReferenceCode()
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

					ThemeFaviconCET themeFaviconCET = (ThemeFaviconCET)_cet;

					return themeFaviconCET.getURL();
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