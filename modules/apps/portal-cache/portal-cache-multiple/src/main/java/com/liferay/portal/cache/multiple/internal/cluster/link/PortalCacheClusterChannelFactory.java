/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.internal.PortalCacheClusterException;
import com.liferay.portal.cache.multiple.internal.constants.PortalCacheDestinationNames;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheClusterChannelFactory {

	public static PortalCacheClusterChannel createPortalCacheClusterChannel(
			ClusterLink clusterLink, Priority priority,
			boolean usingCoalescedPipe)
		throws PortalCacheClusterException {

		if (usingCoalescedPipe) {
			return new PortalCacheClusterChannel(
				clusterLink, PortalCacheDestinationNames.CACHE_REPLICATION,
				new CoalescedPipePortalCacheClusterEventQueue(), priority);
		}

		return new PortalCacheClusterChannel(
			clusterLink, PortalCacheDestinationNames.CACHE_REPLICATION,
			new BlockingPortalCacheClusterEventQueue(), priority);
	}

}