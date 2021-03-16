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

package com.liferay.portal.search.tuning.blueprints.engine.component;

/**
 * @author Petteri Karttunen
 */
public class ServiceComponentReference<T>
	implements Comparable<ServiceComponentReference<T>> {

	public ServiceComponentReference(T serviceComponent, int serviceRanking) {
		_serviceComponent = serviceComponent;
		_serviceRanking = serviceRanking;
	}

	@Override
	public int compareTo(
		ServiceComponentReference<T> serviceComponentReference) {

		return Integer.compare(
			_serviceRanking, serviceComponentReference._serviceRanking);
	}

	public T getServiceComponent() {
		return _serviceComponent;
	}

	private final T _serviceComponent;
	private final int _serviceRanking;

}