/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.usersession.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.usersession.UserSessionManager;

/**
 * @author Edward Han
 */
public class ClusterUserMessageListener extends BaseMessageListener {
	@Override
	protected void doReceive(Message message) throws Exception {
		String command = message.getString(Constants.CMD);

		if (_log.isDebugEnabled()) {
			_log.debug("Received event");
		}

		if (Constants.ADD.equals(command)) {
			long userId = message.getLong("userId");
			long companyId = message.getLong("companyId");

			if (_log.isInfoEnabled()) {
				_log.info(
					"Received cluster login event for CompanyId: " + companyId +
						" UserId: " + userId);
			}

			UserSessionManager.stopUserSession(companyId, userId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterUserMessageListener.class);

}