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

package com.liferay.portal.fabric.netty.server;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.fileserver.CompressionLevel;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricServerConfig implements Serializable {

	public int getBossGroupThreadCount() {
		return PropsValues.PORTAL_FABRIC_SERVER_BOSS_GROUP_THREAD_COUNT;
	}

	public CompressionLevel getFileServerFolderCompressionLevel() {
		return CompressionLevel.getCompressionLevel(
			PropsValues.
				PORTAL_FABRIC_SERVER_FILE_SERVER_FOLDER_COMPRESSION_LEVEL);
	}

	public int getFileServerGroupThreadCount() {
		return PropsValues.PORTAL_FABRIC_SERVER_FILE_SERVER_GROUP_THREAD_COUNT;
	}

	public String getNettyFabricServerHost() {
		return PropsValues.PORTAL_FABRIC_SERVER_HOST;
	}

	public int getNettyFabricServerPort() {
		return PropsValues.PORTAL_FABRIC_SERVER_PORT;
	}

	public int getRegistrationGroupThreadCount() {
		return PropsValues.PORTAL_FABRIC_SERVER_REGISTRATION_GROUP_THREAD_COUNT;
	}

	public long getRepositoryGetFileTimeout() {
		return PropsValues.PORTAL_FABRIC_SERVER_REPOSITORY_GET_FILE_TIMEOUT;
	}

	public Path getRepositoryParentPath() {
		return Paths.get(
			SystemProperties.get(SystemProperties.TMP_DIR),
			PropsValues.PORTAL_FABRIC_SERVER_REPOSITORY_PARENT_FOLDER);
	}

	public int getRPCGroupThreadCount() {
		return PropsValues.PORTAL_FABRIC_SERVER_RPC_GROUP_THREAD_COUNT;
	}

	public long getRPCRelayTimeout() {
		return PropsValues.PORTAL_FABRIC_SERVER_RPC_RELAY_TIMEOUT;
	}

	public long getShutdownQuietPeriod() {
		return PropsValues.PORTAL_FABRIC_SHUTDOWN_QUIET_PERIOD;
	}

	public long getShutdownTimeout() {
		return PropsValues.PORTAL_FABRIC_SHUTDOWN_TIMEOUT;
	}

	public int getWorkerGroupThreadCount() {
		return PropsValues.PORTAL_FABRIC_SERVER_WORKER_GROUP_THREAD_COUNT;
	}

	public long getWorkerStartupTimeout() {
		return PropsValues.PORTAL_FABRIC_SERVER_WORKER_STARTUP_TIMEOUT;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{bossGroupThreadCount=");
		sb.append(getBossGroupThreadCount());
		sb.append(", fileServerFolderCompressionLevel=");
		sb.append(getFileServerFolderCompressionLevel());
		sb.append(", fileServerGroupThreadCount=");
		sb.append(getFileServerGroupThreadCount());
		sb.append(", nettyFabricServerHost=");
		sb.append(getNettyFabricServerHost());
		sb.append(", nettyFabricServerPort=");
		sb.append(getNettyFabricServerPort());
		sb.append(", registrationGroupThreadCount=");
		sb.append(getRegistrationGroupThreadCount());
		sb.append(", repositoryGetFileTimeout=");
		sb.append(getRepositoryGetFileTimeout());
		sb.append(", repositoryParentPath=");
		sb.append(getRepositoryParentPath());
		sb.append(", rpcGroupThreadCount=");
		sb.append(getRPCGroupThreadCount());
		sb.append(", rpcRelayTimeout=");
		sb.append(getRPCRelayTimeout());
		sb.append(", workerGroupThreadCount=");
		sb.append(getWorkerGroupThreadCount());
		sb.append(", workerStartupTimeout=");
		sb.append(getWorkerStartupTimeout());
		sb.append(", shutdownQuietPeriod=");
		sb.append(getShutdownQuietPeriod());
		sb.append(", shutdownTimeout=");
		sb.append(getShutdownTimeout());
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

}