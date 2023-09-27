/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.osgi;

import com.liferay.portal.kernel.module.util.SystemBundleProvider;

import org.apache.sling.testing.mock.osgi.MockOsgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public class TestSystemBundleProvider implements SystemBundleProvider {

	public static BundleContext getBundleContext() {
		return _bundleContext;
	}

	@Override
	public Bundle getSystemBundle() {
		return _bundleContext.getBundle();
	}

	@Override
	public int order() {
		return 1;
	}

	private static final BundleContext _bundleContext =
		MockOsgi.newBundleContext();

}