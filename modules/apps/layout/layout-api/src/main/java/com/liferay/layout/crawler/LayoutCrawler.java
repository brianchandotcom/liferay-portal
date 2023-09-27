/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.crawler;

import com.liferay.portal.kernel.model.Layout;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public interface LayoutCrawler {

	public String getLayoutContent(Layout layout, Locale locale)
		throws Exception;

}