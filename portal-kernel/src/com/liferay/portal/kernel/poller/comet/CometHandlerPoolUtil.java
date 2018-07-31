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

package com.liferay.portal.kernel.poller.comet;

/**
 * @author Edward Han
 * @author Raymond Augé
 */
public class CometHandlerPoolUtil {

	public static void closeCometHandler(String sessionId)
		throws CometException {

		getCometHandlerPool().closeCometHandler(sessionId);
	}

	public static void closeCometHandlers() throws CometException {
		getCometHandlerPool().closeCometHandlers();
	}

	public static CometHandler getCometHandler(String sessionId) {
		return getCometHandlerPool().getCometHandler(sessionId);
	}

	public static CometHandlerPool getCometHandlerPool() {
		return _cometHandlerPool;
	}

	public static void startCometHandler(
			CometSession cometSession, CometHandler cometHandler)
		throws CometException {

		getCometHandlerPool().startCometHandler(cometSession, cometHandler);
	}

	public void setCometHandlerPool(CometHandlerPool cometHandlerPool) {
		_cometHandlerPool = cometHandlerPool;
	}

	private static CometHandlerPool _cometHandlerPool;

}