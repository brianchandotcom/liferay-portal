/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.calculator;

import com.liferay.portal.kernel.model.GroupedModel;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public interface ReadingTimeModelInfo<T extends GroupedModel> {

	public String getContent(T model);

	public String getContentType(T model);

	public Locale getLocale(T model);

}