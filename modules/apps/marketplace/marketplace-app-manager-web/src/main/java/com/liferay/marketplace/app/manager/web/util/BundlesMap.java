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

package com.liferay.marketplace.app.manager.web.util;

import com.liferay.marketplace.model.Module;

import java.util.HashMap;
import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class BundlesMap extends HashMap<String, Bundle> {

	public static String getKey(Bundle bundle) {
		return bundle.getSymbolicName() + ";version=" + bundle.getVersion();
	}

	public static String getKey(Module module) {
		return
			module.getBundleSymbolicName() + ";version=" +
				module.getBundleVersion();
	}

	public BundlesMap(int initialCapacity) {
		super(initialCapacity);
	}

	public void load(List<Bundle> bundles) {
		for (Bundle bundle : bundles) {
			put(getKey(bundle), bundle);
		}
	}

	public Bundle removeBundle(Bundle bundle) {
		return remove(getKey(bundle));
	}

	public Bundle removeBundle(Module module) {
		return remove(getKey(module));
	}

}