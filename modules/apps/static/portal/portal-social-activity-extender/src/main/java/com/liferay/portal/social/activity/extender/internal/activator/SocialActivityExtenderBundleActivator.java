/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.social.activity.extender.internal.activator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.social.kernel.util.SocialConfiguration;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Shuyang Zhou
 */
public class SocialActivityExtenderBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_serviceTracker =
			new ServiceTracker<SocialConfiguration, BundleTracker<Void>>(
				bundleContext, SocialConfiguration.class, null) {

				@Override
				public BundleTracker<Void> addingService(
					ServiceReference<SocialConfiguration> serviceReference) {

					SocialConfiguration socialConfiguration =
						bundleContext.getService(serviceReference);

					BundleTracker<Void> bundleTracker =
						new SocialActivityBundleTracker(
							bundleContext, Bundle.ACTIVE, socialConfiguration);

					bundleTracker.open();

					return bundleTracker;
				}

				@Override
				public void removedService(
					ServiceReference<SocialConfiguration> serviceReference,
					BundleTracker<Void> bundleTracker) {

					bundleTracker.close();
				}

			};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivityExtenderBundleActivator.class);

	private ServiceTracker<SocialConfiguration, BundleTracker<Void>>
		_serviceTracker;

	private static class SocialActivityBundleTracker
		extends BundleTracker<Void> {

		@Override
		public Void addingBundle(Bundle bundle, BundleEvent event) {
			try {
				_readSocialActivity(
					bundle, "META-INF/social/liferay-social.xml");
				_readSocialActivity(
					bundle, "META-INF/social/liferay-social-ext.xml");
			}
			catch (Exception exception) {
				_log.error(
					"Unable to read social activity for bundle " +
						bundle.getSymbolicName(),
					exception);
			}

			return null;
		}

		private SocialActivityBundleTracker(
			BundleContext bundleContext, int stateMask,
			SocialConfiguration socialConfiguration) {

			super(bundleContext, stateMask, null);

			_socialConfiguration = socialConfiguration;
		}

		private void _readSocialActivity(Bundle bundle, String resourcePath)
			throws Exception {

			Enumeration<URL> enumeration = bundle.getResources(resourcePath);

			if ((enumeration == null) || !enumeration.hasMoreElements()) {
				return;
			}

			List<String> configs = new ArrayList<>();

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				configs.add(StringUtil.read(url.openStream()));
			}

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			_socialConfiguration.read(
				bundleWiring.getClassLoader(), configs.toArray(new String[0]));
		}

		private final SocialConfiguration _socialConfiguration;

	}

}