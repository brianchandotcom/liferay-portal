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

package com.liferay.sync.engine.util;

/**
 * @author Shinn Lok
 */
public class PropsValues {

	public static final String SYNC_CONFIGURATION_DIRECTORY = PropsUtil.get(
		PropsKeys.SYNC_CONFIGURATION_DIRECTORY);

	public static final String SYNC_DATABASE_NAME = PropsUtil.get(
		PropsKeys.SYNC_DATABASE_NAME);

	public static final String[] SYNC_FILE_BLACKLIST_CHARS = PropsUtil.getArray(
		PropsKeys.SYNC_FILE_BLACKLIST_CHARS);

	public static final String[] SYNC_FILE_BLACKLIST_CHARS_LAST =
		PropsUtil.getArray(PropsKeys.SYNC_FILE_BLACKLIST_CHARS_LAST);

	public static final String[] SYNC_FILE_BLACKLIST_NAMES = PropsUtil.getArray(
		PropsKeys.SYNC_FILE_BLACKLIST_NAMES);

	public static final int SYNC_FILE_CHECKSUM_THRESHOLD_SIZE =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_FILE_CHECKSUM_THRESHOLD_SIZE));

	public static final boolean SYNC_FILE_IGNORE_HIDDEN = Boolean.valueOf(
		PropsUtil.get(PropsKeys.SYNC_FILE_IGNORE_HIDDEN));

	public static final String[] SYNC_FILE_IGNORE_NAMES = PropsUtil.getArray(
		PropsKeys.SYNC_FILE_IGNORE_NAMES);

	public static final String[] SYNC_FILE_PATCHING_IGNORE_FILE_EXTENSIONS =
		PropsUtil.getArray(PropsKeys.SYNC_FILE_PATCHING_IGNORE_EXTENSIONS);

	public static final int SYNC_FILE_PATCHING_THRESHOLD_SIZE_RATIO =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_FILE_PATCHING_THRESHOLD_SIZE_RATIO));

	public static final int SYNC_HTTP_CONNECTION_TIMEOUT = Integer.parseInt(
		PropsUtil.get(PropsKeys.SYNC_HTTP_CONNECTION_TIMEOUT));

	public static final int SYNC_HTTP_SOCKET_TIMEOUT = Integer.parseInt(
		PropsUtil.get(PropsKeys.SYNC_HTTP_SOCKET_TIMEOUT));

	public static final int SYNC_LAN_SERVER_BROADCAST_INTERVAL =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SERVER_BROADCAST_INTERVAL));

	public static final int SYNC_LAN_SERVER_MAX_CONNECTIONS = Integer.parseInt(
		PropsUtil.get(PropsKeys.SYNC_LAN_SERVER_MAX_CONNECTIONS));

	public static final int SYNC_LAN_SERVER_PORT = Integer.parseInt(
		PropsUtil.get(PropsKeys.SYNC_LAN_SERVER_PORT));

	public static final long SYNC_LAN_SERVER_WRITE_DELAY = Long.parseLong(
		PropsUtil.get(PropsKeys.SYNC_LAN_SERVER_WRITE_DELAY));

	public static final int SYNC_LAN_SESSION_DOWNLOAD_CONNECT_TIMEOUT =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_DOWNLOAD_CONNECT_TIMEOUT));

	public static final int SYNC_LAN_SESSION_DOWNLOAD_MAX_PER_ROUTE =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_DOWNLOAD_MAX_PER_ROUTE));

	public static final int SYNC_LAN_SESSION_DOWNLOAD_MAX_TOTAL =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_DOWNLOAD_MAX_TOTAL));

	public static final int SYNC_LAN_SESSION_DOWNLOAD_SOCKET_TIMEOUT =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_DOWNLOAD_SOCKET_TIMEOUT));

	public static final int SYNC_LAN_SESSION_QUERY_CONNECT_TIMEOUT =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_QUERY_CONNECT_TIMEOUT));

	public static final int SYNC_LAN_SESSION_QUERY_POOL_MAX_SIZE =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_QUERY_POOL_MAX_SIZE));

	public static final int SYNC_LAN_SESSION_QUERY_SOCKET_TIMEOUT =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_QUERY_SOCKET_TIMEOUT));

	public static final int SYNC_LAN_SESSION_QUERY_TOTAL_TIMEOUT =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_QUERY_TOTAL_TIMEOUT));

	public static final int SYNC_LAN_SESSION_QUEUE_CHECK_INTERVAL =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_QUEUE_CHECK_INTERVAL));

	public static final int SYNC_LAN_SESSION_QUEUE_DURATION_RATE =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_QUEUE_DURATION_RATE));

	public static final int SYNC_LAN_SESSION_QUEUE_MAX_DURATION =
		Integer.parseInt(
			PropsUtil.get(PropsKeys.SYNC_LAN_SESSION_QUEUE_MAX_DURATION));

	public static final String SYNC_LOGGER_CONFIGURATION_FILE = PropsUtil.get(
		PropsKeys.SYNC_LOGGER_CONFIGURATION_FILE);

	public static final String SYNC_OAUTH_ACCESS_TOKEN_URL = PropsUtil.get(
		PropsKeys.SYNC_OAUTH_ACCESS_TOKEN_URL);

	public static final String SYNC_OAUTH_AUTHORIZATION_URL = PropsUtil.get(
		PropsKeys.SYNC_OAUTH_AUTHORIZATION_URL);

	public static final String SYNC_OAUTH_REQUEST_TOKEN_URL = PropsUtil.get(
		PropsKeys.SYNC_OAUTH_REQUEST_TOKEN_URL);

	public static final String SYNC_OAUTH_WEBSITE_URL = PropsUtil.get(
		PropsKeys.SYNC_OAUTH_WEBSITE_URL);

}