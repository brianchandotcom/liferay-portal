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

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import org.eclipse.osgi.container.ModuleRevision;
import org.eclipse.osgi.container.ModuleWiring;
import org.eclipse.osgi.framework.util.KeyedElement;
import org.eclipse.osgi.internal.framework.EquinoxBundle;
import org.eclipse.osgi.internal.framework.EquinoxContainer;
import org.eclipse.osgi.internal.loader.BundleLoader;
import org.eclipse.osgi.internal.loader.SystemBundleLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.service.packageadmin.PackageAdmin;

public abstract class PackageSource implements KeyedElement {
	protected final String id;

	public PackageSource(String id) {
		// others depend on the id being interned; see SingleSourcePackage.equals
		this.id = id.intern();
	}

	public String getId() {
		return id;
	}

	public abstract SingleSourcePackage[] getSuppliers();

	public boolean compare(KeyedElement other) {
		return id.equals(((PackageSource) other).getId());
	}

	public int getKeyHashCode() {
		return id.hashCode();
	}

	public Object getKey() {
		return id;
	}

	public boolean isNullSource() {
		return false;
	}

	public abstract Class<?> loadClass(String name) throws ClassNotFoundException;

	public abstract URL getResource(String name);

	public abstract Enumeration<URL> getResources(String name) throws IOException;

	//TODO See how this relates with FilteredSourcePackage. Overwriting or doing a double dispatch might be good.
	// This is intentionally lenient; we don't force all suppliers to match (only one)
	// it is better to get class cast exceptions in split package cases than miss an event
	public boolean hasCommonSource(PackageSource other) {
		if (other == null)
			return false;
		if (this == other)
			return true;
		SingleSourcePackage[] suppliers1 = getSuppliers();
		SingleSourcePackage[] suppliers2 = other.getSuppliers();
		if (suppliers1 == null || suppliers2 == null)
			return false;
		// This will return true if the specified source has at least one
		// of the suppliers of this source.
		for (int i = 0; i < suppliers1.length; i++)
			for (int j = 0; j < suppliers2.length; j++)
				if (suppliers2[j].equals(suppliers1[i]))
					return true;
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(id).append(" -> "); //$NON-NLS-1$
		SingleSourcePackage[] suppliers = getSuppliers();
		if (suppliers == null) {
			return builder.append(String.valueOf(null)).toString();
		}
		builder.append('[');
		for (int i = 0; i < suppliers.length; i++) {
			if (i > 0) {
				builder.append(',');
			}
			builder.append(suppliers[i].getLoader());
		}
		builder.append(']');
		return builder.toString();
	}

	public abstract Collection<String> listResources(String path, String filePattern);

	/**
	 * Used by ServiceReferenceImpl for isAssignableTo
	 * @param registrant Bundle registering service
	 * @param client Bundle desiring to use service
	 * @param className class name to use
	 * @param serviceClass class of original service object
	 * @param container the equinox container
	 * @return true if assignable given package wiring
	 */
	public static boolean isServiceAssignableTo(Bundle registrant, Bundle client, String className, Class<?> serviceClass, EquinoxContainer container) {
		// 1) if the registrant == client always return true
		if (registrant == client) {
			return true;
		}
		// 2) get the package name from the specified className
		String pkgName = BundleLoader.getPackageName(className);
		if (pkgName.startsWith("java.")) //$NON-NLS-1$
			return true;

		BundleLoader producerBL = getBundleLoader(registrant);
		if (producerBL == null)
			return false;
		BundleLoader consumerBL = getBundleLoader(client);
		if (consumerBL == null)
			return false;
		// 3) for the specified bundle, find the wiring for the package.  If no wiring is found return true
		PackageSource consumerSource = consumerBL.getPackageSource(pkgName);
		if (consumerSource == null)
			return true;
		// work around the issue when the package is in the EE and we delegate to boot for that package
		if (container.isBootDelegationPackage(pkgName)) {
			Bundle systemBundle = container.getStorage().getModuleContainer().getModule(0).getBundle();
			SystemBundleLoader systemLoader = (SystemBundleLoader) getBundleLoader(systemBundle);
			if (systemLoader.isExportedPackage(pkgName)) {
				return true; // in this case we have a common source from the EE
			}
		}
		// 4) For the registrant bundle, find the wiring for the package.
		PackageSource producerSource = producerBL.getPackageSource(pkgName);
		if (producerSource == null) {
			if (serviceClass != null && ServiceFactory.class.isAssignableFrom(serviceClass)) {
				@SuppressWarnings("deprecation")
				Bundle bundle = container.getPackageAdmin().getBundle(serviceClass);
				if (bundle != null && bundle != registrant)
					// in this case we have a wacky ServiceFactory that is doing something we cannot 
					// verify if it is correct.  Instead of failing we allow the assignment and hope for the best
					// bug 326918
					return true;
			}
			// 5) If no wiring is found for the registrant bundle then find the wiring for the classloader of the service object.  If no wiring is found return false.
			producerSource = getPackageSource(serviceClass, pkgName, container.getPackageAdmin());
			if (producerSource == null)
				return false;
		}
		// 6) If the two wirings found are equal then return true; otherwise return false.
		return producerSource.hasCommonSource(consumerSource);
	}

	private static PackageSource getPackageSource(Class<?> serviceClass, String pkgName, @SuppressWarnings("deprecation") PackageAdmin packageAdmin) {
		if (serviceClass == null)
			return null;
		@SuppressWarnings("deprecation")
		Bundle serviceBundle = packageAdmin.getBundle(serviceClass);
		if (serviceBundle == null)
			return null;
		BundleLoader producerBL = getBundleLoader(serviceBundle);
		if (producerBL == null)
			return null;
		PackageSource producerSource = producerBL.getPackageSource(pkgName);
		if (producerSource != null)
			return producerSource;
		// try the interfaces
		Class<?>[] interfaces = serviceClass.getInterfaces();
		// note that getInterfaces never returns null
		for (int i = 0; i < interfaces.length; i++) {
			producerSource = getPackageSource(interfaces[i], pkgName, packageAdmin);
			if (producerSource != null)
				return producerSource;
		}
		// try super class
		return getPackageSource(serviceClass.getSuperclass(), pkgName, packageAdmin);
	}

	private static BundleLoader getBundleLoader(Bundle bundle) {
		ModuleRevision producer = ((EquinoxBundle) bundle).getModule().getCurrentRevision();
		ModuleWiring producerWiring = producer.getWiring();
		return producerWiring == null ? null : (BundleLoader) producerWiring.getModuleLoader();
	}
}
