/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.internal.npm.flat;

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import org.osgi.framework.Bundle;

/**
 * Provides a complete implementation of {@link JSBundle}.
 *
 * @author Iván Zaera
 */
public class FlatJSBundle implements JSBundle {

	/**
	 * Constructs a <code>FlatJSBundle</code> with the OSGi bundle.
	 *
	 * @param bundle the OSGi bundle to which this object refers
	 */
	public FlatJSBundle(Bundle bundle, Consumer<FlatJSBundle> initConsumer) {
		_bundle = bundle;
		_initConsumer = initConsumer;
	}

	/**
	 * Adds the NPM package description to the bundle.
	 *
	 * @param jsPackage the NPM package
	 */
	public void addJSPackage(JSPackage jsPackage) {
		_jsPackages.add(jsPackage);
	}

	@Override
	public String getId() {
		return String.valueOf(_bundle.getBundleId());
	}

	@Override
	public Collection<JSPackage> getJSPackages() {
		return _jsPackagesDCLSingleton.getSingleton(this::_init);
	}

	@Override
	public String getName() {
		return _bundle.getSymbolicName();
	}

	@Override
	public URL getResourceURL(String location) {
		return _bundle.getResource(location);
	}

	@Override
	public String getVersion() {
		return String.valueOf(_bundle.getVersion());
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			getId(), StringPool.COLON, getName(), StringPool.AT, getVersion());
	}

	private Collection<JSPackage> _init() {
		_initConsumer.accept(this);

		return _jsPackages;
	}

	private final Bundle _bundle;
	private final Consumer<FlatJSBundle> _initConsumer;
	private final Collection<JSPackage> _jsPackages = new ArrayList<>();
	private final DCLSingleton<Collection<JSPackage>> _jsPackagesDCLSingleton =
		new DCLSingleton<>();

}