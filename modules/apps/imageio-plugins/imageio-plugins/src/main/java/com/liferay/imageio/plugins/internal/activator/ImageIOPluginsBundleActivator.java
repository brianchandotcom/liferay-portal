/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.imageio.plugins.internal.activator;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.spi.ServiceRegistry;

import org.monte.media.jpeg.CMYKJPEGImageReaderSpi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Adolfo Pérez
 */
public class ImageIOPluginsBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_register(ImageReaderSpi.class, _imageReaderSpiSet);
		_register(ImageWriterSpi.class, _imageWriterSpiSet);

		orderImageReaderSpis();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_unregister(_imageReaderSpiSet);
		_unregister(_imageWriterSpiSet);
	}

	protected void orderImageReaderSpis() {
		IIORegistry defaultIIORegistry = IIORegistry.getDefaultInstance();

		ImageReaderSpi firstImageReaderSpi = null;
		ImageReaderSpi secondImageReaderSpi = null;

		Iterator<ImageReaderSpi> iterator =
			defaultIIORegistry.getServiceProviders(ImageReaderSpi.class, true);

		while (iterator.hasNext()) {
			ImageReaderSpi imageReaderSpi = iterator.next();

			if (imageReaderSpi instanceof CMYKJPEGImageReaderSpi) {
				secondImageReaderSpi = imageReaderSpi;
			}
			else {
				String[] formatNames = imageReaderSpi.getFormatNames();

				if (ArrayUtil.contains(formatNames, "jpg", true) ||
					ArrayUtil.contains(formatNames, "jpeg", true)) {

					firstImageReaderSpi = imageReaderSpi;
				}
			}
		}

		if ((firstImageReaderSpi != null) && (secondImageReaderSpi != null)) {
			defaultIIORegistry.setOrdering(
				ImageReaderSpi.class, firstImageReaderSpi,
				secondImageReaderSpi);
		}
	}

	private <T> void _register(Class<T> clazz, Set<T> registeredProviders) {
		IIORegistry iioRegistry = IIORegistry.getDefaultInstance();

		Iterator<T> iterator = ServiceRegistry.lookupProviders(
			clazz, ImageIOPluginsBundleActivator.class.getClassLoader());

		while (iterator.hasNext()) {
			registeredProviders.add(iterator.next());
		}

		iioRegistry.registerServiceProviders(registeredProviders.iterator());
	}

	private <T> void _unregister(Set<T> registeredProviders) {
		IIORegistry iioRegistry = IIORegistry.getDefaultInstance();

		for (T provider : registeredProviders) {
			iioRegistry.deregisterServiceProvider(provider);
		}

		registeredProviders.clear();
	}

	private final Set<ImageReaderSpi> _imageReaderSpiSet = new HashSet<>();
	private final Set<ImageWriterSpi> _imageWriterSpiSet = new HashSet<>();

}