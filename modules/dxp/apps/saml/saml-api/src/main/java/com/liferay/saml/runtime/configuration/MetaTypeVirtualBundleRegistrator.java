/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.runtime.configuration;

import com.liferay.portal.kernel.util.StringBundler;

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

	public void close() {
		try {
			_servicesDropDownMetaTypeProvider.close();
		}
		catch (IOException ioe) {
		}

		try {
			_serviceRegistration.unregister();
		}
		catch (Exception e) {
		}

		try {
			_bundle.uninstall();
		}
		catch (BundleException be) {
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

	protected MetaTypeVirtualBundleRegistrator requireLanguageKeys(
		String filterString) {

		Attributes mainAttributes = _manifest.getMainAttributes();

		StringBundler sb = new StringBundler(5);

		sb.append("liferay.resource.bundle;bundle.symbolic.name=");
		sb.append(_symbolicName);
		sb.append(";resource.bundle.aggregate=\"");
		sb.append(filterString);
		sb.append("\";resource.bundle.base.name=\"content.Language\"");

		mainAttributes.put(
			new Attributes.Name("Provide-Capability"), sb.toString());

		return this;
	}

	private Bundle _bundle;
	private final BundleContext _bundleContext;
	private final Manifest _manifest = new Manifest();
	private final String _metatypePID;
	private ServiceRegistration<MetaTypeProvider> _serviceRegistration;
	private final ServicesDropDownMetaTypeProvider
		_servicesDropDownMetaTypeProvider;
	private final String _symbolicName;

}