/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;

/**
 * @author Shuyang Zhou
 */
public interface PortalCacheClusterChannel {

	public void destroy();

	public long getCoalescedEventNumber();

	public int getPendingEventNumber();

	public long getSentEventNumber();

	public void sendEvent(PortalCacheClusterEvent portalCacheClusterEvent);

}