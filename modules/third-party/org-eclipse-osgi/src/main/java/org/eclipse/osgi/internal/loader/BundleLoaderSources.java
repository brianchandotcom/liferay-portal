/*******************************************************************************
 * Copyright (c) 2003, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.internal.loader;

import java.security.AccessController;
import java.util.Map;
import org.eclipse.osgi.container.ModuleCapability;
import org.eclipse.osgi.framework.util.KeyedHashSet;
import org.eclipse.osgi.framework.util.SecureAction;
import org.eclipse.osgi.internal.loader.sources.*;
import org.osgi.framework.namespace.PackageNamespace;

public class BundleLoaderSources {
	static SecureAction secureAction = AccessController.doPrivileged(SecureAction.createSecureAction());
	private final KeyedHashSet pkgSources;
	private final BundleLoader loader;

	public BundleLoaderSources(BundleLoader loader) {
		this.pkgSources = new KeyedHashSet(false);
		this.loader = loader;
	}

	PackageSource getPackageSource(String pkgName) {
		synchronized (pkgSources) {
			PackageSource pkgSource = (PackageSource) pkgSources.getByKey(pkgName);
			if (pkgSource == null) {
				pkgSource = new SingleSourcePackage(pkgName, loader);
				pkgSources.add(pkgSource);
			}
			return pkgSource;
		}
	}

	boolean forceSourceCreation(ModuleCapability packageCapability) {
		Map<String, String> directives = packageCapability.getDirectives();
		return directives.get(PackageNamespace.CAPABILITY_EXCLUDE_DIRECTIVE) != null || directives.get(PackageNamespace.CAPABILITY_EXCLUDE_DIRECTIVE) != null;
	}

	// creates a PackageSource from an ExportPackageDescription.  This is called when initializing
	// a BundleLoader to ensure that the proper PackageSource gets created and used for
	// filtered and reexport packages.  The storeSource flag is used by initialize to indicate
	// that the source for special case package sources (filtered or re-exported should be stored 
	// in the cache.  if this flag is set then a normal SinglePackageSource will not be created
	// (i.e. it will be created lazily)
	public PackageSource createPackageSource(ModuleCapability packageCapability, boolean storeSource) {
		PackageSource pkgSource = null;

		String name = (String) packageCapability.getAttributes().get(PackageNamespace.PACKAGE_NAMESPACE);
		// check to see if it is a filtered export
		String includes = packageCapability.getDirectives().get(PackageNamespace.CAPABILITY_INCLUDE_DIRECTIVE);
		String excludes = packageCapability.getDirectives().get(PackageNamespace.CAPABILITY_EXCLUDE_DIRECTIVE);
		if (includes != null || excludes != null) {
			pkgSource = new FilteredSourcePackage(name, loader, includes, excludes);
		}

		if (storeSource) {
			if (pkgSource != null) {
				synchronized (pkgSources) {
					if (pkgSources.getByKey(name) == null) {
						pkgSources.add(pkgSource);
					}
				}
			}
		} else {
			// we are not storing the special case sources, but pkgSource == null this means this
			// is a normal package source; get it and return it.
			if (pkgSource == null) {
				pkgSource = getPackageSource(name);
				// the first export cached may not be a simple single source like we need.
				if (pkgSource.getClass() != SingleSourcePackage.class)
					return new SingleSourcePackage(name, loader);
			}
		}

		return pkgSource;
	}
}
