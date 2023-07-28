/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.internal;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.language.LanguageOverrideProvider;
import com.liferay.portal.language.override.internal.provider.PLOOriginalTranslationThreadLocal;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = LanguageOverrideProvider.class)
public class PLOLanguageOverrideProvider implements LanguageOverrideProvider {

	@Override
	public ResourceBundle getOverrideResourceBundle(Locale locale) {
		Map<String, PLOLanguageOverrideProviderHelper.OverrideResourceBundle>
			overrideResourceBundles =
				_ploLanguageOverrideProviderHelper.
					getOverrideResourceBundlesDCLSingleton();

		if (overrideResourceBundles.isEmpty() ||
			PLOOriginalTranslationThreadLocal.isUseOriginalTranslation()) {

			return null;
		}

		return overrideResourceBundles.get(
			_ploLanguageOverrideProviderHelper.encodeKey(
				CompanyThreadLocal.getCompanyId(),
				_language.getLanguageId(locale)));
	}

	@Reference
	private Language _language;

	@Reference
	private PLOLanguageOverrideProviderHelper
		_ploLanguageOverrideProviderHelper;

}