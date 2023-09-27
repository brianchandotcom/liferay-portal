/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.internal.provider;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.language.override.provider.PLOOriginalTranslationProvider;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = PLOOriginalTranslationProvider.class)
public class PLOOriginalTranslationProviderImpl
	implements PLOOriginalTranslationProvider {

	@Override
	public String get(Locale locale, String key) {
		try (SafeCloseable safeCloseable =
				PLOOriginalTranslationThreadLocal.setWithSafeCloseable(true)) {

			return _language.get(locale, key, null);
		}
	}

	@Reference
	private Language _language;

}