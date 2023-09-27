/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.fragment.bundle.watcher.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Constants;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.resource.Requirement;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Shuyang Zhou
 */
@Component(service = {})
public class PortalFragmentBundleWatcher {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_installedFragmentBundleTracker = new BundleTracker<String>(
			_bundleContext, Bundle.INSTALLED, null) {

			@Override
			public String addingBundle(Bundle bundle, BundleEvent event) {
				if (!_isFragment(bundle)) {
					return null;
				}

				return _getFragmentHost(bundle);
			}

		};

		_installedFragmentBundleTracker.open();

		Bundle systemBundle = _bundleContext.getBundle(0);

		FrameworkWiring frameworkWiring = systemBundle.adapt(
			FrameworkWiring.class);

		_resolvedBundleListener = bundleEvent -> {
			Map<Bundle, String> installedFragmentBundles =
				_installedFragmentBundleTracker.getTracked();

			if (installedFragmentBundles.isEmpty()) {
				return;
			}

			Bundle bundleEventBundle = bundleEvent.getBundle();

			Bundle originBundle = bundleEvent.getOrigin();

			List<Bundle> hostBundles = new ArrayList<>();

			if ((bundleEvent.getType() == BundleEvent.INSTALLED) &&
				(bundleEventBundle.getState() != Bundle.UNINSTALLED) &&
				_isFragment(bundleEventBundle) &&
				!Objects.equals(
					originBundle.getSymbolicName(),
					"com.liferay.portal.file.install.impl") &&
				!_hasMissingRequirements(frameworkWiring, bundleEventBundle)) {

				String hostBundleSymbolicName = installedFragmentBundles.remove(
					bundleEventBundle);

				if (Validator.isNotNull(hostBundleSymbolicName)) {
					for (Bundle bundle : bundleContext.getBundles()) {
						if (Objects.equals(
								bundle.getSymbolicName(),
								hostBundleSymbolicName) &&
							_isHostBundleStateValid(bundle)) {

							hostBundles.add(bundle);

							break;
						}
					}
				}
			}
			else if ((bundleEvent.getType() == BundleEvent.RESOLVED) &&
					 !_isFragment(bundleEventBundle)) {

				Map<String, List<Bundle>> fragmentBundlesMap = new HashMap<>();

				for (Map.Entry<Bundle, String> entry :
						installedFragmentBundles.entrySet()) {

					List<Bundle> fragmentBundles =
						fragmentBundlesMap.computeIfAbsent(
							entry.getValue(), key -> new ArrayList<>());

					fragmentBundles.add(entry.getKey());
				}

				long originBundleId = originBundle.getBundleId();

				for (Bundle bundle : bundleContext.getBundles()) {
					List<Bundle> fragmantBundles = fragmentBundlesMap.remove(
						bundle.getSymbolicName());

					if (fragmantBundles == null) {
						continue;
					}

					if (originBundleId != bundle.getBundleId()) {
						boolean needRefresh = false;

						for (Bundle fragmentBundle : fragmantBundles) {
							if ((fragmentBundle.getState() ==
									Bundle.INSTALLED) &&
								!_hasMissingRequirements(
									frameworkWiring, fragmentBundle)) {

								needRefresh = true;

								break;
							}
						}

						if (needRefresh && _isHostBundleStateValid(bundle)) {
							hostBundles.add(bundle);
						}
					}

					if (fragmentBundlesMap.isEmpty()) {
						break;
					}
				}
			}

			if (!hostBundles.isEmpty()) {
				frameworkWiring.refreshBundles(hostBundles);
			}
		};

		_bundleContext.addBundleListener(_resolvedBundleListener);
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext.removeBundleListener(_resolvedBundleListener);

		_installedFragmentBundleTracker.close();
	}

	private String _getFragmentHost(Bundle bundle) {
		Dictionary<String, String> dictionary = bundle.getHeaders(
			StringPool.BLANK);

		String fragmentHost = dictionary.get(Constants.FRAGMENT_HOST);

		if (fragmentHost == null) {
			return null;
		}

		int index = fragmentHost.indexOf(CharPool.SEMICOLON);

		if (index != -1) {
			fragmentHost = fragmentHost.substring(0, index);
		}

		return fragmentHost;
	}

	private boolean _hasMissingRequirements(
		FrameworkWiring frameworkWiring, Bundle bundle) {

		BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);

		for (Requirement requirement : bundleRevision.getRequirements(null)) {
			Collection<BundleCapability> providers =
				frameworkWiring.findProviders(requirement);

			if (providers.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @see com.liferay.portal.file.install.internal.DirectoryWatcher#_isFragment
	 */
	private boolean _isFragment(Bundle bundle) {
		BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);

		if ((bundleRevision.getTypes() & BundleRevision.TYPE_FRAGMENT) != 0) {
			return true;
		}

		return false;
	}

	private boolean _isHostBundleStateValid(Bundle bundle) {
		int hostBundleState = bundle.getState();

		if ((hostBundleState == Bundle.ACTIVE) ||
			(hostBundleState == Bundle.RESOLVED) ||
			(hostBundleState == Bundle.STARTING)) {

			return true;
		}

		return false;
	}

	private BundleContext _bundleContext;
	private BundleTracker<String> _installedFragmentBundleTracker;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	private BundleListener _resolvedBundleListener;

}