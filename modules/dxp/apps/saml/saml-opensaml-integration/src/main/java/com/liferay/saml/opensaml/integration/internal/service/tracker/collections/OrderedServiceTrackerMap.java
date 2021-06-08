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