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
	localization = "content/Language", name = "s3.store.configuration.name"
)
public interface S3StoreConfiguration {

	@Meta.AD(required = false)
	public String accessKey();

	@Meta.AD(required = false)
	public String secretKey();

	@Meta.AD(deflt = "us-east-1", required = false)
	public String s3Region();

	@Meta.AD(required = true)
	public String bucketName();

	@Meta.AD(
		deflt = "STANDARD", optionValues = {"REDUCED_REDUNDANCY", "STANDARD"},
		required = false
	)
	public String s3StorageClass();

	@Meta.AD(deflt = "50", required = false)
	public int httpClientMaxConnections();

	@Meta.AD(deflt = "7", required = false)
	public int cacheDirCleanUpExpunge();

	@Meta.AD(deflt = "100", required = false)
	public int cacheDirCleanUpFrequency();

	@Meta.AD(required = false)
	public String proxyHost();

	@Meta.AD(required = false)
	public String proxyPort();

	@Meta.AD(
		deflt = "none", optionValues = {"username-password", "ntlm", "none"},
		required = false
	)
	public String proxyAuthType();

	@Meta.AD(required = false)
	public String proxyUsername();

	@Meta.AD(required = false)
	public String proxyPassword();

	@Meta.AD(required = false)
	public String ntlmProxyDomain();

	@Meta.AD(required = false)
	public String ntlmProxyWorkstation();

}