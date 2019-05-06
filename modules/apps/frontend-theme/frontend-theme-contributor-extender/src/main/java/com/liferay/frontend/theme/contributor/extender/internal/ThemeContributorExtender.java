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

package com.liferay.frontend.theme.contributor.extender.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Michael Bradford
 */
@Component(immediate = true, service = {})
public class ThemeContributorExtender
	implements BundleTrackerCustomizer<ThemeContributorExtension> {

	@Override
	public ThemeContributorExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String type = headers.get("Liferay-Theme-Contributor-Type");

		if (type == null) {
			return null;
		}

		BundleWebResourcesImpl bundleWebResources = _scanForResources(bundle);

		if (bundleWebResources == null) {
			return null;
		}

		int themeContributorWeight = GetterUtil.getInteger(
			headers.get("Liferay-Theme-Contributor-Weight"));

		ThemeContributorExtension themeContributorExtension =
			new ThemeContributorExtension(
				bundle, bundleWebResources, themeContributorWeight);

		themeContributorExtension.start();

		return themeContributorExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ThemeContributorExtension themeContributorExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ThemeContributorExtension themeContributorExtension) {

		themeContributorExtension.destroy();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE | Bundle.STARTING, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private BundleWebResourcesImpl _scanForResources(Bundle bundle) {
		final List<String> cssResourcePaths = new ArrayList<>();
		final List<String> jsResourcePaths = new ArrayList<>();

		Enumeration<URL> cssEntries = bundle.findEntries(
			"/META-INF/resources", "*.css", true);
		Enumeration<URL> jsEntries = bundle.findEntries(
			"/META-INF/resources", "*.js", true);

		if (cssEntries != null) {
			while (cssEntries.hasMoreElements()) {
				URL url = cssEntries.nextElement();

				String path = url.getFile();

				path = path.replace("/META-INF/resources", "");

				int index = path.lastIndexOf('/');

				if (!StringPool.UNDERLINE.equals(path.charAt(index + 1)) &&
					!path.endsWith("_rtl.css")) {

					cssResourcePaths.add(path);
				}
			}
		}

		if (jsEntries != null) {
			while (jsEntries.hasMoreElements()) {
				URL url = jsEntries.nextElement();

				String path = url.getFile();

				jsResourcePaths.add(path.replace("/META-INF/resources", ""));
			}
		}

		if (cssResourcePaths.isEmpty() && jsResourcePaths.isEmpty()) {
			return null;
		}

		return new BundleWebResourcesImpl(cssResourcePaths, jsResourcePaths);
	}

	private BundleTracker<?> _bundleTracker;

}