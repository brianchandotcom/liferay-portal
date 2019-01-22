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

package com.liferay.portal.fabric.netty.client;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.fileserver.CompressionLevel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.File;
import java.io.Serializable;

import java.nio.file.Path;

import java.util.Properties;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricClientConfig implements Serializable {

	public NettyFabricClientConfig(String id, Properties properties) {
		_id = id;
		_properties = properties;

		_repositoryFolder = new File(
			SystemProperties.get(SystemProperties.TMP_DIR),
			"NettyFabricClient-repository-" + id);
	}

	public int getEventLoopGroupThreadCount() {
		return GetterUtil.getInteger(
			_properties.getProperty(
				PropsKeys.PORTAL_FABRIC_CLIENT_EVENT_LOOP_GROUP_THREAD_COUNT),
			1);
	}

	public int getExecutionGroupThreadCount() {
		return GetterUtil.getInteger(
			_properties.getProperty(
				PropsKeys.PORTAL_FABRIC_CLIENT_EXECUTION_GROUP_THREAD_COUNT),
			1);
	}

	public int getExecutionTimeout() {
		return GetterUtil.getInteger(
			_properties.getProperty(
				PropsKeys.PORTAL_FABRIC_CLIENT_EXECUTION_TIMEOUT),
			600000);
	}

	public CompressionLevel getFileServerFolderCompressionLevel() {
		return CompressionLevel.getCompressionLevel(
			GetterUtil.getInteger(
				_properties.getProperty(
					PropsKeys.
						PORTAL_FABRIC_CLIENT_FILE_SERVER_FOLDER_COMPRESSION_LEVEL),
				1));
	}

	public int getFileServerGroupThreadCount() {
		return GetterUtil.getInteger(
			_properties.getProperty(
				PropsKeys.PORTAL_FABRIC_CLIENT_FILE_SERVER_GROUP_THREAD_COUNT),
			1);
	}

	public String getNettyFabricServerHost() {
		return GetterUtil.getString(
			_properties.getProperty(PropsKeys.PORTAL_FABRIC_SERVER_HOST),
			"localhost");
	}

	public int getNettyFabricServerPort() {
		return GetterUtil.getInteger(
			_properties.getProperty(PropsKeys.PORTAL_FABRIC_SERVER_PORT), 8923);
	}

	public int getReconnectCount() {
		return GetterUtil.getInteger(
			_properties.getProperty(
				PropsKeys.PORTAL_FABRIC_CLIENT_RECONNECT_COUNT),
			3);
	}

	public long getReconnectInterval() {
		return GetterUtil.getInteger(
			_properties.getProperty(
				PropsKeys.PORTAL_FABRIC_CLIENT_RECONNECT_INTERVAL),
			10000);
	}

	public long getRepositoryGetFileTimeout() {
		return GetterUtil.getLong(
			_properties.getProperty(
				PropsKeys.PORTAL_FABRIC_CLIENT_REPOSITORY_GET_FILE_TIMEOUT),
			600000);
	}

	public Path getRepositoryPath() {
		return _repositoryFolder.toPath();
	}

	public int getRPCGroupThreadCount() {
		return GetterUtil.getInteger(
			_properties.getProperty(
				PropsKeys.PORTAL_FABRIC_CLIENT_RPC_GROUP_THREAD_COUNT),
			1);
	}

	public long getShutdownQuietPeriod() {
		return GetterUtil.getLong(
			_properties.getProperty(
				PropsKeys.PORTAL_FABRIC_SHUTDOWN_QUIET_PERIOD),
			1);
	}

	public long getShutdownTimeout() {
		return GetterUtil.getLong(
			_properties.getProperty(PropsKeys.PORTAL_FABRIC_SHUTDOWN_TIMEOUT),
			1);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{eventLoopGroupThreadCount=");
		sb.append(getEventLoopGroupThreadCount());
		sb.append(", executionGroupThreadCount=");
		sb.append(getExecutionGroupThreadCount());
		sb.append(", executionTimeout=");
		sb.append(getExecutionTimeout());
		sb.append(", fileServerFolderCompressionLevel=");
		sb.append(getFileServerFolderCompressionLevel());
		sb.append(", fileServerGroupThreadCount=");
		sb.append(getFileServerGroupThreadCount());
		sb.append(", id=");
		sb.append(_id);
		sb.append(", nettyFabricServetHost=");
		sb.append(getNettyFabricServerHost());
		sb.append(", nettyFabricServerPort=");
		sb.append(getNettyFabricServerPort());
		sb.append(", reconnectCount=");
		sb.append(getReconnectCount());
		sb.append(", reconnectInterval=");
		sb.append(getReconnectInterval());
		sb.append(", repositoryGetFileTimeout=");
		sb.append(getRepositoryGetFileTimeout());
		sb.append(", repositoryPath=");
		sb.append(getRepositoryPath());
		sb.append(", rpcGroupThreadCount=");
		sb.append(getRPCGroupThreadCount());
		sb.append(", shutdownQuietPeriod=");
		sb.append(getShutdownQuietPeriod());
		sb.append(", shutdownTimeout=");
		sb.append(getShutdownTimeout());
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private final String _id;
	private final Properties _properties;
	private final File _repositoryFolder;

}