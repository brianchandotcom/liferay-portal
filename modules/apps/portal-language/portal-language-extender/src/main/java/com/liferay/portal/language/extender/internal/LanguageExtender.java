/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.extender.internal;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.language.LanguageResources;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = {})
public class LanguageExtender
	implements BundleTrackerCustomizer<LanguageExtension> {

	@Override
	public LanguageExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities("liferay.resource.bundle");

		if (ListUtil.isEmpty(bundleCapabilities)) {
			return null;
		}

		LanguageExtension languageExtension = new LanguageExtension(
			_bundleContext, bundle, bundleCapabilities);

		try {
			languageExtension.start();
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			languageExtension.destroy();

			throw new RuntimeException(invalidSyntaxException);
		}

		return languageExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		LanguageExtension languageExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		LanguageExtension languageExtension) {

		languageExtension.destroy();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private BundleContext _bundleContext;
	private BundleTracker<?> _bundleTracker;

	@Reference
	private LanguageResources _languageResources;

}