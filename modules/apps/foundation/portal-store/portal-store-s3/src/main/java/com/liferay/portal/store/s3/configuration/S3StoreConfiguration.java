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

package com.liferay.portal.store.s3.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Manuel de la Peña
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.store.s3.configuration.S3StoreConfiguration",
	localization = "content/Language", name = "s3-store-configuration-name"
)
public interface S3StoreConfiguration {

	@Meta.AD(
		description = "access-key-help", name = "access-key", required = false
	)
	public String accessKey();

	@Meta.AD(
		description = "secret-key-help", name = "secret-key", required = false
	)
	public String secretKey();

	@Meta.AD(
		description = "bucket-name-help", name = "bucket-name", required = true
	)
	public String bucketName();

	@Meta.AD(
		deflt = "us-east-1", description = "s3-region-help", name = "s3-region",
		required = false
	)
	public String s3Region();

	@Meta.AD(
		deflt = "STANDARD", description = "s3-storage-class-help",
		name = "s3-storage-class",
		optionValues = {"REDUCED_REDUNDANCY", "STANDARD"}, required = false
	)
	public String s3StorageClass();

	@Meta.AD(
		deflt = "10000", description = "connection-timeout-help",
		name = "connection-timeout", required = false
	)
	public int connectionTimeout();

	@Meta.AD(
		deflt = "50", description = "http-client-max-connections-help",
		name = "http-client-max-connections", required = false
	)
	public int httpClientMaxConnections();

	@Meta.AD(
		deflt = "5", description = "http-client-max-error-retry-help",
		name = "http-client-max-error-retry", required = false
	)
	public int httpClientMaxErrorRetry();

	@Meta.AD(
		deflt = "7", description = "core-pool-size-help",
		name = "core-pool-size", required = false
	)
	public int corePoolSize();

	@Meta.AD(
		deflt = "20", description = "max-pool-size-help",
		name = "max-pool-size", required = false
	)
	public int maxPoolSize();

	@Meta.AD(
		deflt = "7", description = "cache-dir-clean-up-expunge-help",
		name = "cache-dir-clean-up-expunge", required = false
	)
	public int cacheDirCleanUpExpunge();

	@Meta.AD(
		deflt = "100", description = "cache-dir-clean-up-frequency-help",
		name = "cache-dir-clean-up-frequency", required = false
	)
	public int cacheDirCleanUpFrequency();

	@Meta.AD(
		deflt = "5242880", description = "minimum-uploads-part-size-help",
		name = "minimum-uploads-part-size", required = false
	)
	public int minimumUploadPartSize();

	@Meta.AD(
		deflt = "10485760", description = "multipart-upload-threshold-help",
		name = "multipart-upload-threshold", required = false
	)
	public int multipartUploadThreshold();

	@Meta.AD(
		description = "proxy-host-help", name = "proxy-host", required = false
	)
	public String proxyHost();

	@Meta.AD(
		deflt = "12345", description = "proxy-port-help", name = "proxy-port",
		required = false
	)
	public int proxyPort();

	@Meta.AD(
		deflt = "none", description = "proxy-auth-type-help",
		name = "proxy-auth-type",
		optionValues = {"username-password", "ntlm", "none"}, required = false
	)
	public String proxyAuthType();

	@Meta.AD(
		description = "proxy-username-help", name = "proxy-username",
		required = false
	)
	public String proxyUsername();

	@Meta.AD(
		description = "proxy-password-help", name = "proxy-password",
		required = false
	)
	public String proxyPassword();

	@Meta.AD(
		description = "ntlm-proxy-domain-help", name = "ntlm-proxy-domain",
		required = false
	)
	public String ntlmProxyDomain();

	@Meta.AD(
		description = "ntlm-proxy-workstation-help",
		name = "ntlm-proxy-workstation", required = false
	)
	public String ntlmProxyWorkstation();

}