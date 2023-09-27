/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.connector;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Matthew Tambara
 */
public interface FrameworkCommand<T extends Serializable> extends Serializable {

	public static FrameworkCommand<Long> installBundle(
		String location, byte[] bytes) {

		return bundleContext -> {
			try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
				Bundle bundle = bundleContext.installBundle(
					location, inputStream);

				return bundle.getBundleId();
			}
		};
	}

	public static FrameworkCommand<?> startBundle(long bundleId) {
		return bundleContext -> {
			Bundle bundle = bundleContext.getBundle(bundleId);

			bundle.start();

			return null;
		};
	}

	public static FrameworkCommand<?> uninstallBundle(long bundleId) {
		return bundleContext -> {
			Bundle bundle = bundleContext.getBundle(bundleId);

			bundle.uninstall();

			return null;
		};
	}

	public T execute(BundleContext bundleContext) throws Exception;

}