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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.flat;

import com.liferay.frontend.js.loader.modules.extender.npm.model.JSBundleAdapter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.net.URL;

import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera
 */
public class FlatJSBundle extends JSBundleAdapter {

	public FlatJSBundle(Bundle bundle) {
		super(
			Long.toString(bundle.getBundleId()), bundle.getSymbolicName(),
			bundle.getVersion().toString());

		_bundle = bundle;
	}

	public Enumeration<URL> findEntries(
		String path, String filePattern, boolean recurse) {

		return _bundle.findEntries(path, filePattern, recurse);
	}

	@Override
	public URL getResource(String location) {
		return _bundle.getResource(location);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append(getId());
		sb.append(StringPool.COLON);
		sb.append(getName());
		sb.append(StringPool.AT);
		sb.append(getVersion());

		return sb.toString();
	}

	private final Bundle _bundle;

}