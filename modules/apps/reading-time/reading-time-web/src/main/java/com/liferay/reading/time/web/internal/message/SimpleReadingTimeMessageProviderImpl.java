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

package com.liferay.reading.time.web.internal.message;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.reading.time.message.ReadingTimeMessageProvider;
import com.liferay.reading.time.model.ReadingTimeEntry;

import java.time.Duration;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, property = "display.style=simple")
public class SimpleReadingTimeMessageProviderImpl
	implements ReadingTimeMessageProvider {

	@Override
	public String provide(Duration readingTimeDuration, Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		long readingTimeInMinutes = readingTimeDuration.toMinutes();

		if (readingTimeInMinutes == 0) {
			readingTimeInMinutes = 1;
		}

		return LanguageUtil.format(
			resourceBundle,
			(readingTimeInMinutes == 1) ? "x-minute" : "x-minutes",
			readingTimeInMinutes);
	}

	@Override
	public String provide(ReadingTimeEntry readingTimeEntry, Locale locale) {
		return provide(
			Duration.ofMillis(readingTimeEntry.getReadingTime()), locale);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.reading.time.web)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}