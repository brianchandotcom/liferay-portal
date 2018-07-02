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

package com.liferay.portal.configuration.test.util;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author     Drew Brokke
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ConfigurationTemporarySwapperException extends PortalException {

	public ConfigurationTemporarySwapperException(String msg) {
		super(msg);
	}

	public static class MustFindService
		extends ConfigurationTemporarySwapperException {

		public MustFindService(Class<?> serviceClass) {
			super(
				String.format(
					"No service found that implements interface \"%s\"",
					serviceClass));
		}

	}

	public static class ServiceMustConsumeConfiguration
		extends ConfigurationTemporarySwapperException {

		public ServiceMustConsumeConfiguration(
			String componentName, String pid) {

			super(
				String.format(
					"Component \"%s\" does not consume configuration \"%s\"",
					componentName, pid));
		}

	}

	public static class ServiceMustHaveBundle
		extends ConfigurationTemporarySwapperException {

		public ServiceMustHaveBundle(Class<?> serviceClass) {
			super(
				String.format(
					"No bundle found for the service \"%s\"", serviceClass));
		}

	}

}