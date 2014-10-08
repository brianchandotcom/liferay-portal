/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.cache.cluster;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheClusterLinkUtil {

	public static PortalCacheClusterLink getPortalCacheClusterLink() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortalCacheClusterLinkUtil.class);

		if (_portalCacheClusterLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"PortalCacheClusterLinkUtil has not been initialized.");
			}

			return null;
		}

		return _portalCacheClusterLink;
	}

	public static long getSubmittedEventNumber() {
		PortalCacheClusterLink portalCacheClusterLink =
			getPortalCacheClusterLink();

		if (portalCacheClusterLink == null) {
			return -1;
		}

		return portalCacheClusterLink.getSubmittedEventNumber();
	}

	public static void sendEvent(
		PortalCacheClusterEvent portalCacheClusterEvent) {

		PortalCacheClusterLink portalCacheClusterLink =
			getPortalCacheClusterLink();

		if (portalCacheClusterLink == null) {
			return;
		}

		portalCacheClusterLink.sendEvent(portalCacheClusterEvent);
	}

	public void setPortalCacheClusterLink(
		PortalCacheClusterLink portalCacheClusterLink) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portalCacheClusterLink = portalCacheClusterLink;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalCacheClusterLinkUtil.class);

	private static PortalCacheClusterLink _portalCacheClusterLink;

}