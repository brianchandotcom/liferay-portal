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

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalizationLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

/**
 * @author Pavel Savinov
 */
@ProviderType
public class FriendlyURLEntryImpl extends FriendlyURLEntryBaseImpl {

	public FriendlyURLEntryImpl() {
	}

	@Override
	public String getUrlTitle(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			FriendlyURLEntryLocalizationLocalServiceUtil.
				fetchFriendlyURLEntryLocalization(this, languageId);

		if (friendlyURLEntryLocalization != null) {
			return friendlyURLEntryLocalization.getUrlTitle();
		}

		return null;
	}

	@Override
	public boolean isLocalized() {
		int localizationCount =
			FriendlyURLEntryLocalizationLocalServiceUtil.
				getFriendlyURLEntryLocalizationCount(this);

		if (localizationCount > 0) {
			return true;
		}

		return false;
	}

}