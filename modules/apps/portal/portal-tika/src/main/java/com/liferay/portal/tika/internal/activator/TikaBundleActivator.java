/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tika.internal.activator;

import org.apache.tika.config.ServiceLoader;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public class TikaBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		ServiceLoader.setContextClassLoader(
			TikaBundleActivator.class.getClassLoader());
	}

	@Override
	public void stop(BundleContext bundleContext) {
	}

}