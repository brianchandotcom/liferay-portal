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

package com.liferay.portal.deploy.hot.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class HotDeployMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		String command = message.getString("command");
		String servletContextName = message.getString("servletContextName");

		if (_log.isDebugEnabled()) {
			if (command.equals("deploy")) {
				_log.debug(servletContextName + " was deployed");
			}
			else if (command.equals("undeploy")) {
				_log.debug(servletContextName + " was undeployed");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HotDeployMessageListener.class);

}