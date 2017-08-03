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

package com.liferay.lcs.command;

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Igor Beslic
 */
public class DeregisterCommand implements Command {

	@Override
	public void execute(CommandMessage commandMessage) {
		boolean deregister = Boolean.valueOf(
			(String)commandMessage.get("deregister"));
		boolean invalidateToken = Boolean.valueOf(
			(String)commandMessage.get("invalidateToken"));

		_lcsConnectionManager.signOff(deregister, invalidateToken);

		if (_log.isDebugEnabled()) {
			_log.debug("Signed off server from LCS");
		}
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeregisterCommand.class);

	private LCSConnectionManager _lcsConnectionManager;

}