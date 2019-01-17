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
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Shanon Mathai
 */
@ExtendedObjectClassDefinition(category = "Google")
@Meta.OCD(
    id = "com.liferay.portal.store.gcs.configuration.GCSStoreConfiguration",
    localization = "content/Language", name = "gcs-store-configuration-name"
)
public interface GCSStoreConfiguration {

  @Meta.AD(
      description = "auth-file-help",
      name = "auth-file",
      required = false,
      deflt = "/opt/gcs/ExampleCredentialsFile.json"
  )
  public String authFileLocation();

  @Meta.AD(
      description = "bucket-name-help",
      name = "bucket-name",
      required = false,
      deflt = "mysamplebucket1"
  )
  public String bucketName();

  @Meta.AD(
      description = "max-retry-attempts-help",
      name = "max-retry-attempts",
      required = false,
      deflt = "5"
  )
  public int maxRetryAttempts();

  @Meta.AD(
      description = "initial-retry-delay-help",
      name = "initial-retry-delay",
      required = false,
      deflt = "400"
  )
  public int initialRetryDelay();

  @Meta.AD(
      description = "max-retry-delay-help",
      name = "max-retry-delay",
      required = false,
      deflt = "10000"
  )
  public int maxRetryDelay();

  @Meta.AD(
      description = "retry-delay-multiplier-help",
      name = "retry-delay-multiplier",
      required = false,
      deflt = "1.5"
  )
  public double retryDelayMultiplier();

  @Meta.AD(
      description = "initial-rpc-timeout-help",
      name = "initial-rpc-timeout",
      required = false,
      deflt = "120000"
  )
  public int initialRpcTimeout();

  @Meta.AD(
      description = "max-rpc-timeout-help",
      name = "max-rpc-timeout",
      required = false,
      deflt = "600000"
  )
  public int maxRpcTimeout();

  @Meta.AD(
      description = "rpc-timeout-multiplier-help",
      name = "rpc-timeout-multiplier",
      required = false,
      deflt = "1.0"
  )
  public double rpcTimeoutMultiplier();

  @Meta.AD(
      description = "retry-jitter-help",
      name = "retry-jitter",
      required = false,
      deflt = "false"
  )
  public boolean retryJitter();
}