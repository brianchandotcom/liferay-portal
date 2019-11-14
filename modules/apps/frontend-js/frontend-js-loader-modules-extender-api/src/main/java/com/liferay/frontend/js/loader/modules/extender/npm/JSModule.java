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

package com.liferay.frontend.js.loader.modules.extender.npm;

import com.liferay.portal.kernel.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents an NPM module inside a {@link JSBundle}.
 *
 * <p>
 * {@link JSModule}s belong to a {@link JSPackage} and {@link JSPackage}s belong
 * to a {@link JSBundle}.
 * </p>
 *
 * <p>
 * You can have several copies of the same <code>JSModule</code> in different
 * modules of the portal. For example, suppose you have a module named
 * <code>p@1.0.0/m</code> (the module <code>m</code> residing in package
 * <code>p</code> with version 1.0.0) containing three OSGi bundles:
 * <code>b1.jar</code>, <code>b2.jar</code>, and <code>b3.jar</code>. In this
 * scenario, you would have three JS modules in the {@link NPMRegistry} (one per
 * bundle) and one JS resolved module (depending on the algorithm used, points
 * to one of the three JS modules). The JS module is served to the browser using
 * its JS resolved module (i.e., a virtual entity passed to the browser to avoid
 * using any of the other three JS modules).
 * </p>
 *
 * <p>
 * The modules would look something like this:
 * </p>
 *
 * <b>Modules:</b>
 *
 * <ul>
 * <li>
 * <code>b1.jar:p@1.0.0/m</code> with URL <code>.../b1.jar/p@1.0.0/m</code>
 * </li>
 * <li>
 * <code>b2.jar:p@1.0.0/m</code> with URL <code>.../b2.jar/p@1.0.0/m</code>
 * </li>
 * <li>
 * <code>b3.jar:p@1.0.0/m</code> with URL <code>.../b3.jar/p@1.0.0/m</code>
 * </li>
 * </ul>
 *
 * <b>Resolved Module:</b>
 *
 * <ul>
 * <li>
 * <code>p@1.0.0/m</code> with URL <code>.../p@1.0.0/m</code>
 * </li>
 * </ul>
 *
 * <p>
 * The URL of the resolved module does not show any reference to a bundle. Also,
 * when the resolved module is requested, it's internally resolved to one of the
 * other three modules. Therefore, the requester would receive something like
 * <code>.../b2.jar/p@1.0.0/m</code>, but wouldn't notice it since it's
 * transparent to the requester.
 * </p>
 *
 * @author Iván Zaera
 */
@ProviderType
public interface JSModule extends JSResolvableBundleAsset {

	/**
	 * Returns the module dependencies declared by the NPM module.
	 *
	 * @return the module names
	 */
	public Collection<String> getDependencies();

	/**
	 * Returns the packages that contain all the NPM module's dependencies.
	 *
	 * @return the NPM package names
	 */
	public Collection<String> getDependencyPackageNames();

	/**
	 * Get the metadata that describe the module.
	 *
	 * @review
	 */
	public JSONObject getFlagsJSONObject();

	/**
	 * Returns the module's NPM package.
	 *
	 * @return the NPM package
	 */
	public JSPackage getJSPackage();

	/**
	 * Returns the module's source map.
	 *
	 * @return an {@link InputStream} that allows reading the bytes inside the
	 *         source map
	 * @throws IOException if an IO exception occurred
	 */
	public InputStream getSourceMapInputStream() throws IOException;

}