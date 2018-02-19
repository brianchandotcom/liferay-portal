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

import com.liferay.lcs.exception.CompressionException;
import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.ResponseMessage;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.ResponseMessageUtil;
import com.liferay.petra.json.web.service.client.JSONWebServiceException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingUtil;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Ivica Cardic
 */
public class ExecuteScriptCommand implements Command {

	@Override
	public void execute(CommandMessage commandMessage) throws PortalException {
		if (_log.isTraceEnabled()) {
			_log.trace("Executing execute script command");
		}

		try {
			executeScript(commandMessage);
		}
		catch (Exception e) {
			StringBuilder sb = new StringBuilder(4);

			sb.append("Failed to execute script");

			if (e instanceof CompressionException ||
				e instanceof JSONWebServiceException) {

				sb.append(". Unable to send execution status feedback to LCS ");
				sb.append("gateway. Please note that even script executed, ");
				sb.append("execution result won't be registered at LCS ");
				sb.append("dashboard");
			}

			_log.error(sb.toString(), e);
		}
	}

	public void executeScript(CommandMessage commandMessage)
		throws CompressionException, JSONWebServiceException {

		Map<String, Object> inputObjects = new HashMap<>();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		UnsyncPrintWriter unsyncPrintWriter = UnsyncPrintWriterPool.borrow(
			unsyncByteArrayOutputStream);

		inputObjects.put("out", unsyncPrintWriter);

		String script = (String)commandMessage.getPayload();

		if (_log.isDebugEnabled()) {
			_log.debug("Executing script " + script);
		}

		String payload = null;

		String error = null;

		try {
			ScriptingUtil.exec(
				(Set<String>)null, inputObjects, "groovy", script,
				new String[0]);

			unsyncPrintWriter.flush();

			payload = unsyncByteArrayOutputStream.toString();
		}
		catch (ScriptingException se) {
			error = se.getMessage();
		}

		ResponseMessage responseMessage =
			ResponseMessageUtil.createResponseMessage(
				commandMessage, payload, error);

		_lcsConnectionManager.sendMessage(responseMessage);
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExecuteScriptCommand.class);

	private LCSConnectionManager _lcsConnectionManager;

}