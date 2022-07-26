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