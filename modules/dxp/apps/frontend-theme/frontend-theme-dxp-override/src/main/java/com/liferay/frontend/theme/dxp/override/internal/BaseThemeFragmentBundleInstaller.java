/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.frontend.theme.dxp.override.internal;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseThemeFragmentBundleInstaller {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Bundle systemBundle = bundleContext.getBundle(0);

		FrameworkWiring frameworkWiring = systemBundle.adapt(
			FrameworkWiring.class);

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.RESOLVED | Bundle.STARTING | Bundle.ACTIVE,
			new BundleTrackerCustomizer<Bundle>() {

				@Override
				public Bundle addingBundle(Bundle bundle, BundleEvent event) {
					String symbolicName = bundle.getSymbolicName();

					if (!symbolicName.equals(getHostBundleSymbolicName())) {
						return null;
					}

					String location =
						"theme-fragment:" + bundle.getSymbolicName();

					Bundle fragmentBundle = bundleContext.getBundle(location);

					if (fragmentBundle != null) {
						return fragmentBundle;
					}

					try {
						fragmentBundle = bundleContext.installBundle(
							location, _generateBundleContent());

						frameworkWiring.resolveBundles(
							Collections.singleton(fragmentBundle));

						_trackedBundles.put(bundle, fragmentBundle);

						return null;
					}
					catch (Exception e) {
						_log.error(
							"Unable to generate fragment bundle for " + bundle,
							e);
					}

					return null;
				}

				@Override
				public void modifiedBundle(
					Bundle bundle, BundleEvent event, Bundle fragmentBundle) {
				}

				@Override
				public void removedBundle(
					Bundle bundle, BundleEvent event, Bundle fragmentBundle) {
				}

			});

		_bundleTracker.open();

		BundleContext systemBundleContext = systemBundle.getBundleContext();

		Bundle currentBundle = bundleContext.getBundle();

		systemBundleContext.addBundleListener(
			new BundleListener() {

				@Override
				public void bundleChanged(BundleEvent bundleEvent) {
					Bundle bundle = bundleEvent.getBundle();

					if (!bundle.equals(currentBundle)) {
						return;
					}

					if (bundleEvent.getType() == BundleEvent.STARTED) {

						// In case of STARTED, that means the current bundle
						// was updated or refreshed, we must unregister this
						// self monitor listener to release the previous bundle
						// revision

						systemBundleContext.removeBundleListener(this);

						return;
					}

					if (bundleEvent.getType() != BundleEvent.UNINSTALLED) {
						return;
					}

					for (Map.Entry<Bundle, Bundle> entry :
							_trackedBundles.entrySet()) {

						Bundle hostBundle = entry.getKey();

						Bundle fragmentBundle = entry.getValue();

						try {
							fragmentBundle.uninstall();

							frameworkWiring.refreshBundles(
								Collections.singleton(hostBundle));
						}
						catch (BundleException be) {
							_log.error(
								StringBundler.concat(
									"Unable to uninstall fragment bundle ",
									fragmentBundle, " for host bundle ",
									hostBundle));
						}
					}

					systemBundleContext.removeBundleListener(this);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	protected abstract String getHostBundleSymbolicName();

	protected abstract String[] getResources();

	private InputStream _generateBundleContent() throws IOException {
		String hostBundleSymbolicName = getHostBundleSymbolicName();

		Class<?> clazz = getClass();

		Bundle bundle = FrameworkUtil.getBundle(clazz);

		Version version = bundle.getVersion();

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME,
					hostBundleSymbolicName.concat("-fragment"));
				attributes.putValue(
					Constants.BUNDLE_VERSION, version.toString());
				attributes.putValue(
					Constants.FRAGMENT_HOST, hostBundleSymbolicName);
				attributes.putValue("Manifest-Version", "2");

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();

				for (String resource : getResources()) {
					jarOutputStream.putNextEntry(
						new ZipEntry("images/".concat(resource)));

					StreamUtil.transfer(
						clazz.getResourceAsStream(resource), jarOutputStream,
						false);

					jarOutputStream.closeEntry();
				}
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseThemeFragmentBundleInstaller.class);

	private BundleTracker<Bundle> _bundleTracker;
	private final Map<Bundle, Bundle> _trackedBundles = new HashMap<>();

}