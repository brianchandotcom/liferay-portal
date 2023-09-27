/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.service.tracker.collections;

import org.osgi.framework.BundleContext;

/**
 * @author Stian Sigvartsen
 */
public interface OrderedServiceTrackerMapFactory {

	public <T> OrderedServiceTrackerMap<T> create(
		BundleContext bundleContext, Class<T> clazz, String propertyKey);

}