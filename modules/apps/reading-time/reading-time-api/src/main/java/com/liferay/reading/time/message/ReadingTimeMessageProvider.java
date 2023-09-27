/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.message;

import com.liferay.reading.time.model.ReadingTimeEntry;

import java.time.Duration;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public interface ReadingTimeMessageProvider {

	public String provide(Duration readingTimeDuration, Locale locale);

	public String provide(ReadingTimeEntry readingTimeEntry, Locale locale);

}