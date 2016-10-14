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

package com.liferay.portal.social.activity.extender.internal;

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
public class SocialActivityExtender implements BundleActivator {

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
	public void stop(BundleContext context) {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivityExtender.class);

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
			catch (Exception e) {
				_log.error(
					"Unable to read social activity for bundle " +
						bundle.getSymbolicName(),
					e);
			}

			return null;
		}

		private SocialActivityBundleTracker(
			BundleContext context, int stateMask,
			SocialConfiguration socialConfiguration) {

			super(context, stateMask, null);

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

			ClassLoader classLoader = bundleWiring.getClassLoader();

			_socialConfiguration.read(
				classLoader, configs.toArray(new String[configs.size()]));
		}

		private final SocialConfiguration _socialConfiguration;

	}

}