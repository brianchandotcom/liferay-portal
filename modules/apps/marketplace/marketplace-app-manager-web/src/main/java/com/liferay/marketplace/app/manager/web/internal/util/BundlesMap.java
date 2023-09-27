/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.app.manager.web.internal.util;

import com.liferay.marketplace.model.Module;

import java.util.HashMap;
import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class BundlesMap extends HashMap<String, Bundle> {

	public static String getKey(Bundle bundle) {
		return bundle.getSymbolicName();
	}

	public static String getKey(Module module) {
		return module.getBundleSymbolicName();
	}

	public BundlesMap(int initialCapacity) {
		super(initialCapacity);
	}

	public Bundle getBundle(Bundle bundle) {
		return get(getKey(bundle));
	}

	public Bundle getBundle(Module module) {
		return get(getKey(module));
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