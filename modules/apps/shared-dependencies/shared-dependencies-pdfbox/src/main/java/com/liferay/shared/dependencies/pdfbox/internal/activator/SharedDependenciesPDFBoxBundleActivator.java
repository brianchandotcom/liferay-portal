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