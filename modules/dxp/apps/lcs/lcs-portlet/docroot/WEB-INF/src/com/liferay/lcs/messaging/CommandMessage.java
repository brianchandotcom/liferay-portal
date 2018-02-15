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

package com.liferay.lcs.messaging;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

/**
 * Represents a Liferay Cloud Services Protocol command message.
 *
 * @author  Ivica Cardic
 * @author  Igor Beslic
 * @version 2.1.1
 * @since   LCS 0.1
 */
public class CommandMessage extends Message {

	public static final String COMMAND_TYPE_CHECK_HEARTBEAT = "checkHeartbeat";

	public static final String COMMAND_TYPE_DEREGISTER = "deregister";

	public static final String COMMAND_TYPE_DOWNLOAD_PATCHES =
		"downloadPatches";

	public static final String COMMAND_TYPE_EXECUTE_SCRIPT = "executeScript";

	public static final String COMMAND_TYPE_INITIATE_HANDSHAKE =
		"initiateHandshake";

	public static final String COMMAND_TYPE_MESSAGE_BUS = "messageBus";

	public static final String COMMAND_TYPE_SCHEDULE_MESSAGE_LISTENERS =
		"scheduleMessageListeners";

	public static final String COMMAND_TYPE_SCHEDULE_TASKS = "scheduleTasks";

	public static final String COMMAND_TYPE_SEND_INSTALLATION_ENVIRONMENT =
		"sendInstallationEnvironment";

	public static final String COMMAND_TYPE_SEND_PATCHES = "sendPatches";

	public static final String COMMAND_TYPE_SEND_PORTAL_PROPERTIES =
		"sendPortalProperties";

	public static final String COMMAND_TYPE_UPDATE_SIGNATURE_PUBLIC_KEY =
		"updateSignaturePublicKey";

	/**
	 * Returns the message's command type.
	 *
	 * @return the message's command type
	 * @since  LCS 0.1
	 */
	public String getCommandType() {
		return _commandType;
	}

	/**
	 * Returns the message's correlation ID.
	 *
	 * @return the message's correlation ID
	 * @since  LCS 0.1
	 */
	public String getCorrelationId() {
		return _correlationId;
	}

	/**
	 * Returns <code>true</code> if the message command instructs the client to
	 * send a heartbeat message.
	 *
	 * @return <code>true</code> if the message command instructs the client to
	 *         send a heartbeat message; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public boolean isCommandTypeCheckHeartbeat() {
		return isCommandType(COMMAND_TYPE_CHECK_HEARTBEAT);
	}

	/**
	 * Returns <code>true</code> if the message command instructs the client to
	 * stop its activity and close the current LCS session.
	 *
	 * @return <code>true</code> if the message command instructs the client to
	 *         stop its activity and close the current LCS session;
	 *         <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public boolean isCommandTypeDeregister() {
		return isCommandType(COMMAND_TYPE_DEREGISTER);
	}

	/**
	 * Returns <code>true</code> if the message command instructs the client to
	 * download the patches.
	 *
	 * @return <code>true</code> if the message command instructs the client to
	 *         download the patches; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public boolean isCommandTypeDownloadPatches() {
		return isCommandType(COMMAND_TYPE_DOWNLOAD_PATCHES);
	}

	/**
	 * Returns <code>true</code> if the message command instructs the client to
	 * execute the script.
	 *
	 * @return <code>true</code> if the message command instructs the client to
	 *         execute the script; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public boolean isCommandTypeExecuteScript() {
		return isCommandType(COMMAND_TYPE_EXECUTE_SCRIPT);
	}

	/**
	 * Returns <code>true</code> if the message command instructs the LCS system
	 * to initiate the handshake process.
	 *
	 * @return <code>true</code> if the message command instructs the LCS system
	 *         to initiate the handshake process; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public boolean isCommandTypeInitiateHandshake() {
		return isCommandType(COMMAND_TYPE_INITIATE_HANDSHAKE);
	}

	@JsonIgnore
	public boolean isCommandTypeMessageBus() {
		return isCommandType(COMMAND_TYPE_MESSAGE_BUS);
	}

	@JsonIgnore
	public boolean isCommandTypeScheduleTasks() {
		return isCommandType(COMMAND_TYPE_SCHEDULE_TASKS);
	}

	/**
	 * Returns <code>true</code> if the message command instructs the client to
	 * send the installation environment information.
	 *
	 * @return <code>true</code> if the message command instructs the client to
	 *         send the installation environment information; <code>false</code>
	 *         otherwise
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public boolean isCommandTypeSendInstallationEnvironment() {
		return isCommandType(COMMAND_TYPE_SEND_INSTALLATION_ENVIRONMENT);
	}

	/**
	 * Returns <code>true</code> if the message command instructs the client to
	 * send the installed portal patches.
	 *
	 * @return <code>true</code> if the message command instructs the client to
	 *         send the installed portal patches; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public boolean isCommandTypeSendPatches() {
		return isCommandType(COMMAND_TYPE_SEND_PATCHES);
	}

	/**
	 * Returns <code>true</code> if the message command instructs the client to
	 * send the portal properties.
	 *
	 * @return <code>true</code> if the message command instructs the client to
	 *         send the portal properties; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public boolean isCommandTypeSendPortalProperties() {
		return isCommandType(COMMAND_TYPE_SEND_PORTAL_PROPERTIES);
	}

	/**
	 * Sets the command message's type.
	 *
	 * @param commandType the command message's type
	 * @since LCS 0.1
	 */
	public void setCommandType(String commandType) {
		_commandType = commandType;
	}

	/**
	 * Sets the command message's correlation ID. The correlation ID is used to
	 * match the receiver's response if the command message is sent
	 * asynchronously, and must be unique within the current LCS client session.
	 *
	 * @param correlationId the command message's correlation ID
	 * @since LCS 0.1
	 */
	public void setCorrelationId(String correlationId) {
		_correlationId = correlationId;
	}

	@Override
	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		StringBuilder sb = new StringBuilder(11);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getName());

		sb.append(", commandType=");
		sb.append(_commandType);
		sb.append(", correlationId=");
		sb.append(_correlationId);
		sb.append(", createTime=");
		sb.append(getCreateTime());
		sb.append(", key=");
		sb.append(getKey());
		sb.append(", payload=");
		sb.append(getPayload());
		sb.append(", values=");

		Map<String, Object> values = getValues();

		if (values != null) {
			sb.append(values.toString());
		}

		sb.append("}");

		_toString = sb.toString();

		return _toString;
	}

	/**
	 * Returns <code>true</code> if the message's command type matches the given
	 * command type.
	 *
	 * @return <code>true</code> if the message's command type matches the given
	 *         command type; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	protected boolean isCommandType(String commandType) {
		if ((_commandType != null) && _commandType.equals(commandType)) {
			return true;
		}

		return false;
	}

	private String _commandType;
	private String _correlationId;
	private String _toString;

}