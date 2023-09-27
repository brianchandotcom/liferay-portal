/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.extension;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier de Arcos
 */
@Component(service = ExtensionProviderRegistry.class)
public class ExtensionProviderRegistryImpl
	implements ExtensionProviderRegistry {

	@Override
	public List<ExtensionProvider> getExtensionProviders(
		long companyId, String className) {

		return TransformUtil.transformToList(
			_serviceTracker.getServices(new ExtensionProvider[0]),
			extensionProvider -> {
				if (extensionProvider.isApplicableExtension(
						companyId, className)) {

					return extensionProvider;
				}

				return null;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.create(
			bundleContext, ExtensionProvider.class, null);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<ExtensionProvider, ExtensionProvider>
		_serviceTracker;

}