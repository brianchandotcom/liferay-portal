/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer;

import java.io.File;

/**
 * @author Shuyang Zhou
 */
public interface SiteInitializerFactory {

	public SiteInitializer create(File file, String symbolicName)
		throws Exception;

}