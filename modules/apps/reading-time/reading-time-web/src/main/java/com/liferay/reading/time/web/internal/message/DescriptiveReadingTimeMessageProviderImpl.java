/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.web.internal.message;

import com.liferay.portal.kernel.language.Language;
import com.liferay.reading.time.message.ReadingTimeMessageProvider;
import com.liferay.reading.time.model.ReadingTimeEntry;

import java.time.Duration;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "display.style=descriptive",
	service = ReadingTimeMessageProvider.class
)
public class DescriptiveReadingTimeMessageProviderImpl
	implements ReadingTimeMessageProvider {

	@Override
	public String provide(Duration readingTimeDuration, Locale locale) {
		long readingTimeInMinutes = readingTimeDuration.toMinutes();

		if (readingTimeInMinutes == 0) {
			return _language.get(locale, "less-than-a-minute-read");
		}
		else if (readingTimeInMinutes == 1) {
			return _language.get(locale, "a-minute-read");
		}

		return _language.format(locale, "x-minute-read", readingTimeInMinutes);
	}

	@Override
	public String provide(ReadingTimeEntry readingTimeEntry, Locale locale) {
		return provide(
			Duration.ofMillis(readingTimeEntry.getReadingTime()), locale);
	}

	@Reference
	private Language _language;

}