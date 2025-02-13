/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryInfoItemLanguagesProvider
	implements InfoItemLanguagesProvider<ObjectEntry> {

	@Override
	public String[] getAvailableLanguageIds(ObjectEntry objectEntry) {
		Set<String> availableLanguageIds = new HashSet<>();

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
		}

		return availableLanguageIds.toArray(new String[0]);
	}

	@Override
	public String getDefaultLanguageId(ObjectEntry objectEntry) {
		return objectEntry.getDefaultLanguageId();
	}

}