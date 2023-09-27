/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.model.impl;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class FriendlyURLEntryImpl extends FriendlyURLEntryBaseImpl {

	@Override
	public String getUrlTitle() {
		String urlTitle = super.getUrlTitle();

		if (!Validator.isBlank(urlTitle)) {
			return urlTitle;
		}

		Map<String, String> languageIdToUrlTitleMap =
			getLanguageIdToUrlTitleMap();

		if (languageIdToUrlTitleMap.isEmpty()) {
			return StringPool.BLANK;
		}

		Collection<String> urlTitles = languageIdToUrlTitleMap.values();

		Iterator<String> iterator = urlTitles.iterator();

		if (iterator.hasNext()) {
			return iterator.next();
		}

		return StringPool.BLANK;
	}

	@Override
	public String getUrlTitleMapAsXML() {
		return LocalizationUtil.getXml(
			getLanguageIdToUrlTitleMap(), getDefaultLanguageId(), "UrlTitle",
			true);
	}

	@Override
	public boolean isMain() throws PortalException {
		FriendlyURLEntry friendlyURLEntry =
			FriendlyURLEntryLocalServiceUtil.getMainFriendlyURLEntry(
				getClassNameId(), getClassPK());

		if (friendlyURLEntry.getPrimaryKey() == getPrimaryKey()) {
			return true;
		}

		return false;
	}

}