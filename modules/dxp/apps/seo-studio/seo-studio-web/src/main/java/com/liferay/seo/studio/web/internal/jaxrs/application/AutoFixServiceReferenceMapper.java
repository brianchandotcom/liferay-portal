/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.jaxrs.application;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.seo.studio.spi.auto.fix.AutoFix;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author David Truong
 */
public class AutoFixServiceReferenceMapper
	implements ServiceReferenceMapper<String, AutoFix> {

	public AutoFixServiceReferenceMapper(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void map(
		ServiceReference<AutoFix> serviceReference, Emitter<String> emitter) {

		AutoFix autoFix = _bundleContext.getService(serviceReference);

		try {
			emitter.emit(autoFix.getInsightType());
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	private final BundleContext _bundleContext;

}