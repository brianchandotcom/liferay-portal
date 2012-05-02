/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.poller.comet;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Edward Han
 */
public class CometHandlerPoolUtil {

	public static void closeCometHandler(String sessionId)
		throws CometException {

		_cometHandlerPool.closeCometHandler(sessionId);
	}

	public static void closeCometHandlers() throws CometException {
		_cometHandlerPool.closeCometHandlers();
	}

	public static CometHandler getCometHandler(String sessionId) {
		return _cometHandlerPool.getCometHandler(sessionId);
	}

	public static CometHandlerPool getCometHandlerPool() {
		return _cometHandlerPool;
	}

	public static void startCometHandler(
			CometSession cometSession, CometHandler cometHandler)
		throws CometException {

		_cometHandlerPool.startCometHandler(cometSession, cometHandler);
	}

	public void setCometHandlerPool(CometHandlerPool cometHandlerPool) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_cometHandlerPool = cometHandlerPool;
	}

	private static CometHandlerPool _cometHandlerPool;

}