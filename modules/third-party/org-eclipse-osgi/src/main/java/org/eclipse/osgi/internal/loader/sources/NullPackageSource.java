/*******************************************************************************
 * Copyright (c) 2003, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.internal.loader.sources;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used to optimize finding provided-packages for a bundle.
 * If the package cannot be found in a list of required bundles then this class
 * is used to cache a null package source so that the search does not need to
 * be done again.
 */
public class NullPackageSource extends PackageSource {
	private static final Map<String, NullPackageSource> sources = new ConcurrentHashMap<>();

	private NullPackageSource(String name) {
		super(name);
	}

	public SingleSourcePackage[] getSuppliers() {
		return null;
	}

	public boolean isNullSource() {
		return true;
	}

	public Class<?> loadClass(String name) {
		return null;
	}

	public URL getResource(String name) {
		return null;
	}

	public Enumeration<URL> getResources(String name) {
		return null;
	}

	public static NullPackageSource getNullPackageSource(String name) {
		return sources.computeIfAbsent(name, NullPackageSource::new);
	}

	@Override
	public List<String> listResources(String path, String filePattern) {
		return Collections.<String> emptyList();
	}
}
/* @generated */