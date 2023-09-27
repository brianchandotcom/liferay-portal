/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.gradle.internal.resource.transport.http;

import org.gradle.internal.resource.connector.ResourceConnectorSpecification;
import org.gradle.internal.resource.transfer.DefaultExternalResourceConnector;
import org.gradle.internal.resource.transfer.ExternalResourceConnector;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayHttpConnectorFactory extends HttpConnectorFactory {

	public LiferayHttpConnectorFactory(
		SslContextFactory sslContextFactory,
		HttpClientHelper.Factory httpClientHelperFactory) {

		super(sslContextFactory, httpClientHelperFactory);

		_sslContextFactory = sslContextFactory;
		_httpClientHelperFactory = httpClientHelperFactory;
	}

	@Override
	public ExternalResourceConnector createResourceConnector(
		ResourceConnectorSpecification resourceConnectorSpecification) {

		DefaultHttpSettings.Builder builder = DefaultHttpSettings.builder();

		builder.withAuthenticationSettings(
			resourceConnectorSpecification.getAuthentications());
		builder.withSslContextFactory(_sslContextFactory);

		HttpSettings httpSettings = builder.build();

		HttpClientHelper httpClientHelper = _httpClientHelperFactory.create(
			httpSettings);

		HttpResourceAccessor httpResourceAccessor =
			new LiferayHttpResourceAccessor(httpClientHelper);

		HttpResourceLister httpResourceLister = new HttpResourceLister(
			httpResourceAccessor);

		HttpResourceUploader httpResourceUploader = new HttpResourceUploader(
			httpClientHelper);

		return new DefaultExternalResourceConnector(
			httpResourceAccessor, httpResourceLister, httpResourceUploader);
	}

	private final HttpClientHelper.Factory _httpClientHelperFactory;
	private final SslContextFactory _sslContextFactory;

}