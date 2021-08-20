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

package com.liferay.portal.store.gcs.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Shanon Mathai
 */
@ExtendedObjectClassDefinition(category = "file-storage")
@Meta.OCD(
	id = "com.liferay.portal.store.gcs.configuration.GCSStoreConfiguration",
	localization = "content/Language", name = "gcs-store-configuration-name"
)
public interface GCSStoreConfiguration {

	@ExtendedAttributeDefinition(
		descriptionArguments = "https://cloud.google.com/iam/docs/creating-managing-service-account-keys",
		requiredInput = true
	)
	@Meta.AD(
		description = "service-account-key-help", name = "service-account-key"
	)
	public String serviceAccountKey();

	@Meta.AD(description = "bucket-name-help[gcs]", name = "bucket-name")
	public String bucketName();

	@Meta.AD(
		deflt = "5", description = "max-retry-attempts-help",
		name = "max-retry-attempts", required = false
	)
	public int maxRetryAttempts();

	@Meta.AD(
		deflt = "400", description = "initial-retry-delay-help",
		name = "initial-retry-delay", required = false
	)
	public int initialRetryDelay();

	@Meta.AD(
		deflt = "10000", description = "max-retry-delay-help",
		name = "max-retry-delay", required = false
	)
	public int maxRetryDelay();

	@Meta.AD(
		deflt = "1.5", description = "retry-delay-multiplier-help",
		name = "retry-delay-multiplier", required = false
	)
	public double retryDelayMultiplier();

	@Meta.AD(
		deflt = "120000", description = "initial-rpc-timeout-help",
		name = "initial-rpc-timeout", required = false
	)
	public int initialRPCTimeout();

	@Meta.AD(
		deflt = "600000", description = "max-rpc-timeout-help",
		name = "max-rpc-timeout", required = false
	)
	public int maxRPCTimeout();

	@Meta.AD(
		deflt = "1.0", description = "rpc-timeout-multiplier-help",
		name = "rpc-timeout-multiplier", required = false
	)
	public double rpcTimeoutMultiplier();

	@Meta.AD(
		deflt = "false", description = "retry-jitter-help",
		name = "retry-jitter", required = false
	)
	public boolean retryJitter();

	@Meta.AD(deflt = "", description = "aes256-key-help", name = "aes256-key")
	public String aes256Key();

}