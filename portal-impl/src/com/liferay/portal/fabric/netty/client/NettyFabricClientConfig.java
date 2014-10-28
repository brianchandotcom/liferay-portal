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

import com.liferay.portal.fabric.netty.fileserver.CompressionLevel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
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
					PropsKeys.PORTAL_FABRIC_CLIENT_FILE_SERVER_FOLDER_COMPRESSION_LEVEL),
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

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(28);

		sb.append("Netty Fabric Client Configuration {");
		sb.append("\n\tClient id : ");
		sb.append(_id);
		sb.append("\n\tNetty Fabric Server Host : ");
		sb.append(getNettyFabricServerHost());
		sb.append("\n\tNetty Fabric Server Port : ");
		sb.append(getNettyFabricServerPort());
		sb.append("\n\tReconnect Count : ");
		sb.append(getReconnectCount());
		sb.append("\n\tReconnect Interval(ms) : ");
		sb.append(getReconnectInterval());
		sb.append("\n\tEvent Loop Group Thread Count : ");
		sb.append(getEventLoopGroupThreadCount());
		sb.append("\n\tRepository path : ");
		sb.append(getRepositoryPath());
		sb.append("\n\tRepository Get File Timeout(ms) : ");
		sb.append(getRepositoryGetFileTimeout());
		sb.append("\n\tFile Server Group Thread Count : ");
		sb.append(getFileServerGroupThreadCount());
		sb.append("\n\tFile Server Folder Compression Level : ");
		sb.append(getFileServerFolderCompressionLevel());
		sb.append("\n\tRPC Group Thread Count : ");
		sb.append(getRPCGroupThreadCount());
		sb.append("\n\tExecution Group Thread Count : ");
		sb.append(getExecutionGroupThreadCount());
		sb.append("\n\tExecution Timeout(ms) : ");
		sb.append(getExecutionTimeout());
		sb.append("\n}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private final String _id;
	private final Properties _properties;
	private final File _repositoryFolder;

}