/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@ProviderType
public interface MultiVMPool {

	public void clear();

	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName);

	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName, boolean mvcc);

	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName, boolean mvcc, boolean sharded);

	public PortalCacheManager<? extends Serializable, ? extends Serializable>
		getPortalCacheManager();

	public void removePortalCache(String portalCacheName);

}