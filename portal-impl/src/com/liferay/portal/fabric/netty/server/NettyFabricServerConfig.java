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

import com.liferay.portal.fabric.netty.fileserver.CompressionLevel;
import com.liferay.portal.kernel.util.StringBundler;
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
			PropsValues.PORTAL_FABRIC_SERVER_FILE_SERVER_FOLDER_COMPRESSION_LEVEL);
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

	public int getWorkerGroupThreadCount() {
		return PropsValues.PORTAL_FABRIC_SERVER_WORKER_GROUP_THREAD_COUNT;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(24);

		sb.append("Netty Fabric Server Configuration {");
		sb.append("\n\tNetty Fabric Server Host : ");
		sb.append(getNettyFabricServerHost());
		sb.append("\n\tNetty Fabric Server Port : ");
		sb.append(getNettyFabricServerPort());
		sb.append("\n\tBoss Group Thread Count : ");
		sb.append(getBossGroupThreadCount());
		sb.append("\n\tWorker Group Thread Count : ");
		sb.append(getWorkerGroupThreadCount());
		sb.append("\n\tRepository parent path : ");
		sb.append(getRepositoryParentPath());
		sb.append("\n\tRepository Get File Timeout(ms) : ");
		sb.append(getRepositoryGetFileTimeout());
		sb.append("\n\tRegistration Group Thread Count : ");
		sb.append(getRegistrationGroupThreadCount());
		sb.append("\n\tFile Server Group Thread Count : ");
		sb.append(getFileServerGroupThreadCount());
		sb.append("\n\tFile Server Folder Compression Level : ");
		sb.append(getFileServerFolderCompressionLevel());
		sb.append("\n\tRPC Group Thread Count : ");
		sb.append(getRPCGroupThreadCount());
		sb.append("\n\tRPC Relay Timeout(ms) : ");
		sb.append(getRPCRelayTimeout());
		sb.append("\n}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

}