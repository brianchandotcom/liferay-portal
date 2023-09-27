/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.resource.bundle;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Carlos Sierra Andrés
 */
public class AggregateResourceBundleLoader implements ResourceBundleLoader {

	public AggregateResourceBundleLoader(
		ResourceBundleLoader... resourceBundleLoaders) {

		for (int i = 0; i < resourceBundleLoaders.length; i++) {
			if (resourceBundleLoaders[i] == null) {
				throw new NullPointerException(
					"Null resource bundle loader at index " + i);
			}
		}

		_resourceBundleLoaders = resourceBundleLoaders;
	}

	@Override
	public ResourceBundle loadResourceBundle(Locale locale) {
		List<ResourceBundle> resourceBundles = new ArrayList<>();

		for (ResourceBundleLoader resourceBundleLoader :
				_resourceBundleLoaders) {

			try {
				ResourceBundle resourceBundle =
					resourceBundleLoader.loadResourceBundle(locale);

				if (resourceBundle != null) {
					resourceBundles.add(resourceBundle);
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		if (resourceBundles.isEmpty()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			throw new MissingResourceException(
				StringBundler.concat(
					"Resource bundle loader ", this,
					" was unable to load resource bundle for ", languageId),
				StringPool.BLANK, languageId);
		}

		if (resourceBundles.size() == 1) {
			return resourceBundles.get(0);
		}

		return new AggregateResourceBundle(
			resourceBundles.toArray(new ResourceBundle[0]));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AggregateResourceBundleLoader.class);

	private final ResourceBundleLoader[] _resourceBundleLoaders;

}