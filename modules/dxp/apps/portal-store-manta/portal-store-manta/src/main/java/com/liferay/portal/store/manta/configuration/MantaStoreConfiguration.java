/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.store.manta.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Ryan Park
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.store.manta.configuration.MantaStoreConfiguration",
	localization = "content/Language", name = "manta-store-configuration-name"
)
public interface MantaStoreConfiguration {

	@Meta.AD(
		description = "manta-url-help", name = "manta-url", required = true
	)
	public String mantaURL();

	@Meta.AD(
		description = "manta-user-help", name = "manta-user", required = true
	)
	public String mantaUser();

	@Meta.AD(
		description = "manta-key-id-help", name = "manta-key-id",
		required = true
	)
	public String mantaKeyId();

	@Meta.AD(
		description = "manta-key-store-path-help",
		name = "manta-key-store-path", required = true
	)
	public String mantaKeyStorePath();

	@Meta.AD(
		description = "manta-base-path-help", name = "manta-base-path",
		required = true
	)
	public String mantaBasePath();

	@Meta.AD(
		deflt = "20000", description = "connection-timeout-help",
		name = "connection-timeout", required = false
	)
	public int connectionTimeout();

	@Meta.AD(
		deflt = "3", description = "http-client-max-error-retry-help",
		name = "http-client-max-error-retry", required = false
	)
	public int httpClientMaxErrorRetry();

	@Meta.AD(
		deflt = "24", description = "http-client-max-connections-help",
		name = "http-client-max-connections", required = false
	)
	public int httpClientMaxConnections();

	@Meta.AD(
		deflt = "false", description = "multipart-upload-enabled-help",
		name = "multipart-upload-enabled", required = false
	)
	public boolean multipartUploadEnabled();

	@Meta.AD(
		deflt = "10485760", description = "multipart-upload-threshold-help",
		name = "multipart-upload-threshold", required = false
	)
	public int multipartUploadThreshold();

}