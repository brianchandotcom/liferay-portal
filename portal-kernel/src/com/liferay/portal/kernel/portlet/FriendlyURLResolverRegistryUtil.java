/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;

/**
 * @author Eduardo García
 * @author Raymond Augé
 */
public class FriendlyURLResolverRegistryUtil {

	public static FriendlyURLResolver getFriendlyURLResolver(
		String urlSeparator) {

		for (FriendlyURLResolver friendlyURLResolver : _serviceTrackerList) {
			if (Objects.equals(
					friendlyURLResolver.getURLSeparator(), urlSeparator)) {

				return friendlyURLResolver;
			}
		}

		return null;
	}

	public static FriendlyURLResolver
		getFriendlyURLResolverByDefaultURLSeparator(
			String defaultURLSeparator) {

		for (FriendlyURLResolver friendlyURLResolver : _serviceTrackerList) {
			if (Objects.equals(
					friendlyURLResolver.getDefaultURLSeparator(),
					defaultURLSeparator)) {

				return friendlyURLResolver;
			}
		}

		return null;
	}

	public static Collection<FriendlyURLResolver>
		getFriendlyURLResolversAsCollection() {

		return _serviceTrackerList.toList();
	}

	public static String[] getURLSeparators() {
		List<String> urlSeparators = new ArrayList<>();

		for (FriendlyURLResolver friendlyURLResolver : _serviceTrackerList) {
			if (friendlyURLResolver != null) {
				urlSeparators.add(friendlyURLResolver.getURLSeparator());
			}
		}

		return urlSeparators.toArray(new String[0]);
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final ServiceTrackerList<FriendlyURLResolver>
		_serviceTrackerList = ServiceTrackerListFactory.open(
			_bundleContext, FriendlyURLResolver.class);

}