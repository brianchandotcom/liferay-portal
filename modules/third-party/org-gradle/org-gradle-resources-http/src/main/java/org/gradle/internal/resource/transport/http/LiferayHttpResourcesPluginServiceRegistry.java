/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			SslContextFactory sslContextFactory,
			HttpClientHelper.Factory httpClientHelperFactory) {

			return new LiferayHttpConnectorFactory(
				sslContextFactory, httpClientHelperFactory);
		}

		@SuppressWarnings("unused")
		public SslContextFactory createSslContextFactory() {
			return new DefaultSslContextFactory();
		}

	}

}