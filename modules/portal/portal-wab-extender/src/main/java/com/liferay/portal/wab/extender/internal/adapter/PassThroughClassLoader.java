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

package com.liferay.portal.wab.extender.internal.adapter;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;

/**
 * Alters the logic in the <code>isRequestFromVM</code> method of Equinox's
 * <code>BundleLoader</code> class. All of the methods in this class must be
 * implemented even though they just invoke the parent class.
 *
 * @author Carlos Sierra Andrés
 */
public class PassThroughClassLoader extends ClassLoader {

	public PassThroughClassLoader(ClassLoader contextClassLoader) {
		super(contextClassLoader);
	}

	@Override
	public void clearAssertionStatus() {
		super.clearAssertionStatus();
	}

	@Override
	public URL getResource(String name) {
		return super.getResource(name);
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		return super.getResourceAsStream(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		return super.getResources(name);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return super.loadClass(name);
	}

	@Override
	public void setClassAssertionStatus(String className, boolean enabled) {
		super.setClassAssertionStatus(className, enabled);
	}

	@Override
	public void setDefaultAssertionStatus(boolean enabled) {
		super.setDefaultAssertionStatus(enabled);
	}

	@Override
	public void setPackageAssertionStatus(String packageName, boolean enabled) {
		super.setPackageAssertionStatus(packageName, enabled);
	}

	@Override
	protected Package definePackage(
			String name, String specTitle, String specVersion,
			String specVendor, String implTitle, String implVersion,
			String implVendor, URL sealBase)
		throws IllegalArgumentException {

		return super.definePackage(
			name, specTitle, specVersion, specVendor, implTitle, implVersion,
			implVendor, sealBase);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		return super.findClass(name);
	}

	@Override
	protected String findLibrary(String name) {
		return super.findLibrary(name);
	}

	@Override
	protected URL findResource(String name) {
		return super.findResource(name);
	}

	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		return super.findResources(name);
	}

	@Override
	protected Object getClassLoadingLock(String className) {
		return super.getClassLoadingLock(className);
	}

	@Override
	protected Package getPackage(String name) {
		return super.getPackage(name);
	}

	@Override
	protected Package[] getPackages() {
		return super.getPackages();
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		return super.loadClass(name, resolve);
	}

}