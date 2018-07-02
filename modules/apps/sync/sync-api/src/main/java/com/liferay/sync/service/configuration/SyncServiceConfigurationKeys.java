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

package com.liferay.sync.service.configuration;

/**
 * @author Dennis Ju
 */
public class SyncServiceConfigurationKeys {

	public static final String SYNC_ALLOW_USER_PERSONAL_SITES =
		"sync.allow.user.personal.sites";

	public static final String SYNC_CLIENT_AUTHENTICATION_RETRY_INTERVAL =
		"sync.client.authentication.retry.interval";

	public static final String SYNC_CLIENT_BATCH_FILE_MAX_SIZE =
		"sync.client.batch.file.max.size";

	public static final String SYNC_CLIENT_FORCE_SECURITY_MODE =
		"sync.client.force.security.mode";

	public static final String SYNC_CLIENT_MAX_CONNECTIONS =
		"sync.client.max.connections";

	public static final String SYNC_CLIENT_MAX_DOWNLOAD_RATE =
		"sync.client.max.download.rate";

	public static final String SYNC_CLIENT_MAX_UPLOAD_RATE =
		"sync.client.max.upload.rate";

	public static final String SYNC_CLIENT_MIN_BUILD_ANDROID =
		"sync.client.min.build.android";

	public static final String SYNC_CLIENT_MIN_BUILD_DESKTOP =
		"sync.client.min.build.desktop";

	public static final String SYNC_CLIENT_MIN_BUILD_IOS =
		"sync.client.min.build.ios";

	public static final String SYNC_CLIENT_POLL_INTERVAL =
		"sync.client.poll.interval";

	public static final String SYNC_FILE_CHECKSUM_THRESHOLD_SIZE =
		"sync.file.checksum.threshold.size";

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static final String SYNC_FILE_DIFF_CACHE_DELETE_INTERVAL =
		"sync.file.diff.cache.delete.interval";

	public static final String SYNC_FILE_DIFF_CACHE_ENABLED =
		"sync.file.diff.cache.enabled";

	public static final String SYNC_FILE_DIFF_CACHE_EXPIRATION_TIME =
		"sync.file.diff.cache.expiration.time";

	public static final String SYNC_LAN_ENABLED = "sync.lan.enabled";

	public static final String SYNC_MAC_PACKAGE_FOLDER_EXTENSIONS =
		"sync.mac.package.folder.extensions";

	public static final String SYNC_MAC_PACKAGE_METADATA_FILE_NAMES =
		"sync.mac.package.metadata.file.names";

	public static final String SYNC_PAGINATION_DELTA = "sync.pagination.delta";

	public static final String SYNC_SERVICES_ENABLED = "sync.services.enabled";

	public static final String SYNC_VERIFY = "sync.verify";

}