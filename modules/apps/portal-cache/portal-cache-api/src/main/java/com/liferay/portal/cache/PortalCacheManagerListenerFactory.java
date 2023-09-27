/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public interface PortalCacheManagerListenerFactory
	<T extends PortalCacheManager<?, ?>> {

	public PortalCacheManagerListener create(
		T portalCacheManager, Properties properties);

}