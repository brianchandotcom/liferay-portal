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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Pavel Savinov
 */
@ProviderType
public class FriendlyURLEntryImpl extends FriendlyURLEntryBaseImpl {

	@Override
	public String getUrlTitle() {
		String urlTitle = super.getUrlTitle();

		if (!Validator.isBlank(urlTitle)) {
			return urlTitle;
		}

		if (getLanguageIdToUrlTitleMap().size() > 0) {
			Map<String, String> urlTitleMap = getLanguageIdToUrlTitleMap();

			Set<Map.Entry<String, String>> urlTitleEntrySet =
				urlTitleMap.entrySet();

			Iterator<Map.Entry<String, String>> itr =
				urlTitleEntrySet.iterator();

			if (itr.hasNext()) {
				Map.Entry<String, String> lang = itr.next();

				return lang.getValue();
			}
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