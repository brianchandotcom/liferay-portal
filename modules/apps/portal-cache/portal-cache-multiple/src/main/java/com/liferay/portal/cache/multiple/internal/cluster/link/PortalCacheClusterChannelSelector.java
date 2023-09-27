/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public interface PortalCacheClusterChannelSelector {

	public long getSelectedNumber();

	public PortalCacheClusterChannel select(
		List<PortalCacheClusterChannel> portalCacheClusterChannels,
		PortalCacheClusterEvent portalCacheClusterEvent);

}