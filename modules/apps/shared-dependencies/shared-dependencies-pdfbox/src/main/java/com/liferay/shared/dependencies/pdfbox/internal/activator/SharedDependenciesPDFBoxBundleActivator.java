/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.shared.dependencies.pdfbox.internal.activator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.spi.ImageOutputStreamSpi;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.spi.ServiceRegistry;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public class SharedDependenciesPDFBoxBundleActivator
	implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		IIORegistry iioRegistry = IIORegistry.getDefaultInstance();

		for (Class<?> providerClass : _providerClasses) {
			Iterator<?> iterator = ServiceRegistry.lookupProviders(
				providerClass,
				SharedDependenciesPDFBoxBundleActivator.class.getClassLoader());

			while (iterator.hasNext()) {
				_providers.add(iterator.next());
			}
		}

		iioRegistry.registerServiceProviders(_providers.iterator());
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		IIORegistry iioRegistry = IIORegistry.getDefaultInstance();

		for (Object provider : _providers) {
			iioRegistry.deregisterServiceProvider(provider);
		}

		_providers.clear();
	}

	private static final List<Class<?>> _providerClasses = Arrays.asList(
		ImageInputStreamSpi.class, ImageOutputStreamSpi.class,
		ImageReaderSpi.class, ImageWriterSpi.class);

	private final List<Object> _providers = new ArrayList<>();

}