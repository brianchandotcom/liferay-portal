/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.summary;

import java.util.Locale;

/**
 * @author André de Oliveira
 */
public interface Summary {

	public String getContent();

	public Locale getLocale();

	public String getTitle();

}