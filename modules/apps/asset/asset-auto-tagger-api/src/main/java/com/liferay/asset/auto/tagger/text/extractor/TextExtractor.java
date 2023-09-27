/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.text.extractor;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public interface TextExtractor<T> {

	public String extract(T t, Locale locale);

	public String getClassName();

}