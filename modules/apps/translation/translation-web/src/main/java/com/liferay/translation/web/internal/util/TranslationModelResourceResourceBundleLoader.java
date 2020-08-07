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

package com.liferay.translation.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.translation.constants.TranslationConstants;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "bundle.symbolic.name=com.liferay.translation.web",
	service = ResourceBundleLoader.class
)
public class TranslationModelResourceResourceBundleLoader
	implements ResourceBundleLoader {

	@Override
	public ResourceBundle loadResourceBundle(Locale locale) {
		return new AggregateResourceBundle(
			_resourceBundleLoader.loadResourceBundle(locale),
			new ResourceBundle() {

				@Override
				public Enumeration<String> getKeys() {
					Set<Locale> availableLocales =
						_language.getAvailableLocales();

					Stream<Locale> stream = availableLocales.stream();

					return Collections.enumeration(
						stream.map(
							curLocale ->
								_PREFIX + _language.getLanguageId(curLocale)
						).collect(
							Collectors.toList()
						));
				}

				@Override
				protected Object handleGetObject(String key) {
					Locale curLocale = LocaleUtil.fromLanguageId(
						StringUtil.removeSubstring(key, _PREFIX));

					return curLocale.getDisplayName(locale);
				}

			});
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private static final String _PREFIX =
		"model.resource." + TranslationConstants.RESOURCE_NAME +
			StringPool.PERIOD;

	@Reference
	private Language _language;

	@Reference(
		target = "(&(bundle.symbolic.name=com.liferay.translation.web)(!(component.name=com.liferay.translation.web.internal.util.TranslationModelResourceResourceBundleLoader)))"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}