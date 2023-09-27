/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.service.tracker.collections;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Stian Sigvartsen
 */
public interface OrderedServiceTrackerMap<T> {

	public void close();

	public String getDefaultServiceKey();

	public List<Map.Entry<String, T>> getOrderedServices();

	public List<String> getOrderedServicesKeys();

	public T getService(String key);

	public Set<String> getServicesKeys();

}