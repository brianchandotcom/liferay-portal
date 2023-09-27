/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.internal.PortalCacheClusterException;
import com.liferay.portal.kernel.cluster.Priority;

/**
 * @author Shuyang Zhou
 */
public interface PortalCacheClusterChannelFactory {

	public PortalCacheClusterChannel createPortalCacheClusterChannel(
			Priority priority)
		throws PortalCacheClusterException;

}