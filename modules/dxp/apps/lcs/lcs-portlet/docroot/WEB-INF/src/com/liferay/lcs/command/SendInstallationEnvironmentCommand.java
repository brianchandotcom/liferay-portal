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

import com.liferay.lcs.advisor.InstallationEnvironmentAdvisor;
import com.liferay.lcs.advisor.InstallationEnvironmentAdvisorFactory;
import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.ResponseMessage;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.ResponseMessageUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class SendInstallationEnvironmentCommand implements Command {

	@Override
	public void execute(CommandMessage commandMessage) throws PortalException {
		if (_log.isTraceEnabled()) {
			_log.trace("Executing send installation environment command");
		}

		Map<String, Object> payload = new HashMap<>();

		InstallationEnvironmentAdvisor installationEnvironmentAdvisor =
			InstallationEnvironmentAdvisorFactory.getInstance();

		payload.put(
			"hardwareMetadata",
			installationEnvironmentAdvisor.getHardwareMetadata());
		payload.put(
			"softwareMetadata",
			installationEnvironmentAdvisor.getSoftwareMetadata());

		ResponseMessage responseMessage =
			ResponseMessageUtil.createResponseMessage(commandMessage, payload);

		try {
			_lcsConnectionManager.sendMessage(responseMessage);
		}
		catch (Exception e) {
			_log.error("Unable to send installation environment", e);
		}
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SendInstallationEnvironmentCommand.class);

	private LCSConnectionManager _lcsConnectionManager;

}