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

package com.liferay.commerce.product.internal.layout.admin.util;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian I. Kim
 */
public class SitemapURLProviderUtil {

	protected static Map<Locale, String> getAlternateFriendlyURLs(
		Map<Locale, String> alternateSiteURLs, long friendlyURLEntryId,
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		Map<Locale, String> alternateFriendlyURLs = new HashMap<>();

		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
					friendlyURLEntryId)) {

			String alternateSiteURL = alternateSiteURLs.get(
				LocaleUtil.fromLanguageId(
					friendlyURLEntryLocalization.getLanguageId()));

			if (alternateSiteURL != null) {
				alternateFriendlyURLs.put(
					LocaleUtil.fromLanguageId(
						friendlyURLEntryLocalization.getLanguageId()),
					alternateSiteURL);
			}
		}

		return alternateFriendlyURLs;
	}

}