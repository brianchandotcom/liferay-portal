/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public interface SingleVMPool {

	public void clear();

	public PortalCache<? extends Serializable, ?> getPortalCache(
		String portalCacheName);

	public PortalCacheManager<? extends Serializable, ?>
		getPortalCacheManager();

	public void removePortalCache(String portalCacheName);

}