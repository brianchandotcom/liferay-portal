/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = ResourceBundleLoaderProvider.class)
public class ResourceBundleLoaderProvider {

	public ResourceBundleLoader getResourceBundleLoader(
		String bundleSymbolicName) {

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(bundleSymbolicName);

		if (resourceBundleLoader == null) {
			return ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
		}

		return new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

}