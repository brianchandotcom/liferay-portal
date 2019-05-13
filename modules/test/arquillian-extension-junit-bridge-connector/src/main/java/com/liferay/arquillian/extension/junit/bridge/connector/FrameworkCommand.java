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