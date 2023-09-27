/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.runtime.configuration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.metatype.MetaTypeProvider;

/**
 * @author Carlos Sierra Andrés
 */
public class MetaTypeVirtualBundleRegistrator implements Closeable {

	public MetaTypeVirtualBundleRegistrator(
		BundleContext bundleContext,
		ServicesDropDownMetaTypeProvider servicesDropDownMetaTypeProvider) {

		_bundleContext = bundleContext;
		_servicesDropDownMetaTypeProvider = servicesDropDownMetaTypeProvider;

		_metatypePID = servicesDropDownMetaTypeProvider.getMetatypePID();

		Attributes mainAttributes = _manifest.getMainAttributes();

		mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1");

		_symbolicName = _metatypePID + ".virtual.bundle";

		mainAttributes.put(
			new Attributes.Name("Bundle-SymbolicName"), _symbolicName);

		mainAttributes.put(new Attributes.Name("Bundle-Version"), "1.0.0");
	}

	@Override
	public void close() {
		try {
			_servicesDropDownMetaTypeProvider.close();
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}

		try {
			_serviceRegistration.unregister();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		try {
			_bundle.uninstall();
		}
		catch (BundleException bundleException) {
			if (_log.isDebugEnabled()) {
				_log.debug(bundleException);
			}
		}
	}

	public void open() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		JarOutputStream jarOutputStream = new JarOutputStream(
			byteArrayOutputStream, _manifest);

		jarOutputStream.flush();

		jarOutputStream.close();

		_bundle = _bundleContext.installBundle(
			"virtualmetatypeprovider",
			new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

		_bundle.start();

		BundleContext otherBundleContext = _bundle.getBundleContext();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(MetaTypeProvider.METATYPE_PID, _metatypePID);

		_serviceRegistration = otherBundleContext.registerService(
			MetaTypeProvider.class, _servicesDropDownMetaTypeProvider,
			properties);
	}

	protected MetaTypeVirtualBundleRegistrator importPackage(
		String packageString) {

		Attributes mainAttributes = _manifest.getMainAttributes();

		mainAttributes.put(
			new Attributes.Name("Import-Package"), packageString);

		return this;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MetaTypeVirtualBundleRegistrator.class);

	private Bundle _bundle;
	private final BundleContext _bundleContext;
	private final Manifest _manifest = new Manifest();
	private final String _metatypePID;
	private ServiceRegistration<MetaTypeProvider> _serviceRegistration;
	private final ServicesDropDownMetaTypeProvider
		_servicesDropDownMetaTypeProvider;
	private final String _symbolicName;

}