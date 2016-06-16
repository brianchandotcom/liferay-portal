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

package org.gradle.internal.resource.transport.http;

import org.gradle.internal.resource.connector.ResourceConnectorFactory;
import org.gradle.internal.service.ServiceRegistration;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayHttpResourcesPluginServiceRegistry
	extends HttpResourcesPluginServiceRegistry {

	@Override
	public void registerGlobalServices(
		ServiceRegistration serviceRegistration) {

		serviceRegistration.addProvider(new GlobalScopeServices());
	}

	private static class GlobalScopeServices {

		@SuppressWarnings("unused")
		public ResourceConnectorFactory createHttpConnectorFactory(
			SslContextFactory sslContextFactory) {

			return new LiferayHttpConnectorFactory(sslContextFactory);
		}

		@SuppressWarnings("unused")
		public SslContextFactory createSslContextFactory() {
			return new DefaultSslContextFactory();
		}

	}

}