/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.context.helper.internal.definition;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

/**
 * @author Miguel Pastor
 */
public class MockBundle implements Bundle {

	@Override
	public <A> A adapt(Class<A> clazz) {
		return null;
	}

	@Override
	public int compareTo(Bundle bundle) {
		return 0;
	}

	@Override
	public Enumeration<URL> findEntries(
		String path, String filePattern, boolean recurse) {

		return Collections.enumeration(new ArrayList<URL>());
	}

	@Override
	public BundleContext getBundleContext() {
		return null;
	}

	@Override
	public long getBundleId() {
		return 0;
	}

	@Override
	public File getDataFile(String fileName) {
		return null;
	}

	@Override
	public URL getEntry(String path) {
		return null;
	}

	@Override
	public Enumeration<String> getEntryPaths(String path) {
		return Collections.enumeration(new ArrayList<String>());
	}

	@Override
	public Dictionary<String, String> getHeaders() {
		return new HashMapDictionary<>();
	}

	@Override
	public Dictionary<String, String> getHeaders(String locale) {
		return new HashMapDictionary<>();
	}

	@Override
	public long getLastModified() {
		return 0;
	}

	@Override
	public String getLocation() {
		return StringPool.BLANK;
	}

	@Override
	public ServiceReference<?>[] getRegisteredServices() {
		return new ServiceReference<?>[0];
	}

	@Override
	public URL getResource(String name) {
		return null;
	}

	@Override
	public Enumeration<URL> getResources(String name) {
		return Collections.enumeration(new ArrayList<URL>());
	}

	@Override
	public ServiceReference<?>[] getServicesInUse() {
		return new ServiceReference<?>[0];
	}

	@Override
	public Map<X509Certificate, List<X509Certificate>> getSignerCertificates(
		int signersType) {

		return Collections.emptyMap();
	}

	@Override
	public int getState() {
		return Bundle.ACTIVE;
	}

	@Override
	public String getSymbolicName() {
		return "bundle";
	}

	@Override
	public Version getVersion() {
		return Version.emptyVersion;
	}

	@Override
	public boolean hasPermission(Object permission) {
		return false;
	}

	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.loadClass(className);
	}

	@Override
	public void start() {
		start(0);
	}

	@Override
	public void start(int options) {
	}

	@Override
	public void stop() {
	}

	@Override
	public void stop(int options) {
	}

	@Override
	public void uninstall() {
	}

	@Override
	public void update() {
	}

	@Override
	public void update(InputStream inputStream) {
	}

}