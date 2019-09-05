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

package com.liferay.friendly.url.model.impl;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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